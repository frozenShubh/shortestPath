package com.adidas.assignment.question;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import com.adidas.assignment.model.FliteTracker;
import com.adidas.assignment.model.Path;

/**
 * @author Shubham
 * Question Enum represents a command pattern. Each enum value is a command that is generated through question parsing.
 * This is extensible design and you can add multiple commands.  
 * A service discovery mechanism (factory) can be written which will output appropriate Question of basis 
 * of certain parameters. 
 */
public enum Question {
	PRICE{
		@Override
		public String ask(FliteTracker tracker, Map<String, String> dataMap) {
			return (tracker.priceEngine(Path.parse(dataMap.get("connection"))))==0?"No such connection found!":Integer.toString(tracker.priceEngine(Path.parse(dataMap.get("connection"))));
		}
		
	}, CHEAPEST {
		@Override
		public String ask(FliteTracker tracker, Map<String, String> dataMap) {
			SortedSet<Path> shorttestPathSet = tracker.priceEndToEnd(dataMap.get("from"), dataMap.get("to"));
			if(shorttestPathSet==null){
				return ("No such connection found!");
			}
			if(shorttestPathSet.first().getCost()==0){
				int i =0;
				for(Path path: shorttestPathSet){
					if(i==1)
						return path.toString();
					i++;
				}
			}
			return (shorttestPathSet.first().toString());
			
		}
	}, MAXSTOPS {
		@Override
		public String ask(FliteTracker tracker, Map<String, String> dataMap) {
			Set<Path> resultPaths = new HashSet<>();
			SortedSet<Path> shorttestPathSet = tracker.priceEndToEnd(dataMap.get("between"), dataMap.get("and"));
			if(shorttestPathSet==null){
				return ("No such connection found!");
			}
			for(Path path: shorttestPathSet){
				int currentPathSize = path.getPath().size() - 2;
				for(String port: path.getPath()){
					int length = currentPathSize;
						SortedSet<Path> loopPathSet = tracker.priceEndToEnd(port, port);
						for(Path loopPath: loopPathSet){
							Path newPath = path.replacePortWithLoop(port, loopPath);
							if(loopPath.getPath().size()<=1)
								continue;
							while(length<=Integer.parseInt(dataMap.get("maximum"))){
								length = length + loopPath.getPath().size() - 1 ;
								if(length<=Integer.parseInt(dataMap.get("maximum"))){
									resultPaths.add(newPath);
								}
								newPath = newPath.replacePortWithLoop(port, loopPath);
							}
							length=currentPathSize;
						}
				}
				if(currentPathSize<=Integer.parseInt(dataMap.get("maximum"))){
					resultPaths.add(path);
				}

			}
			return Integer.toString(resultPaths.size());
		}
	}, MINSTOPS {
		@Override
		public String ask(FliteTracker tracker, Map<String, String> dataMap) {
			SortedSet<Path> shorttestPathSet = tracker.priceEndToEnd(dataMap.get("between"), dataMap.get("and"));
			int num = 0;
			if(shorttestPathSet==null){
				return ("No such connection exists");
			}
			
			for(Path path: shorttestPathSet){
				if(path.getPath().size()>=Integer.parseInt(dataMap.get("minimum"))){
					num++;
				}
			}
			return Integer.toString(num);
			
		}
	}, EXACTSTOPS {
		@Override
		public String ask(FliteTracker tracker, Map<String, String> dataMap) {
			SortedSet<Path> shorttestPathSet = tracker.priceEndToEnd(dataMap.get("between"), dataMap.get("and"));
			Set<Path> resultPaths = new HashSet<>();
			if(shorttestPathSet==null){
				return ("No such connection found!");
			}
			
			for(Path path: shorttestPathSet){
				int currentPathSize = path.getPath().size() - 2;
				if(currentPathSize<=Integer.parseInt(dataMap.get("exactly"))){
					for(String port: path.getPath()){
						int length = currentPathSize;
							SortedSet<Path> loopPathSet = tracker.priceEndToEnd(port, port);
							for(Path loopPath: loopPathSet){
								Path newPath = path.replacePortWithLoop(port, loopPath);
								if(loopPath.getPath().size()<=1){
									continue;
								}
									
								while(length<=Integer.parseInt(dataMap.get("exactly"))){
									length = length + loopPath.getPath().size() - 1 ;
									if(length==Integer.parseInt(dataMap.get("exactly")))
										resultPaths.add(newPath);
									newPath = newPath.replacePortWithLoop(port, loopPath);
								}
								length=currentPathSize;
							}
					}
					if(currentPathSize==Integer.parseInt(dataMap.get("exactly")))
						resultPaths.add(path);
;
				}
		
			}
			
			return Integer.toString(resultPaths.size());
		}
	}, FINDQUES {
		@Override
		public String ask(FliteTracker tracker, Map<String, String> dataMap) {
			SortedSet<Path> shorttestPathSet = tracker.priceEndToEnd(dataMap.get("from"), dataMap.get("to"));
			Set<Path> outputPathSet = new HashSet<>();
			if(shorttestPathSet==null){
				return ("No such connection found!");
			}
			
			for(Path path: shorttestPathSet){
				int currentCost = path.getCost();
				if(currentCost<=Integer.parseInt(dataMap.get("below"))){
					for(String port: path.getPath()){
							SortedSet<Path> loopPathSet = tracker.priceEndToEnd(port, port);
							for(Path loopPath: loopPathSet){
								Path newPath = path.replacePortWithLoop(port, loopPath);
								if(loopPath.getPath().size()<=1){
									continue;
								}
									
								while(newPath.getCost()<=Integer.parseInt(dataMap.get("below"))){
									if(newPath.getCost()<=Integer.parseInt(dataMap.get("below")))
										outputPathSet.add(newPath);
									newPath = newPath.replacePortWithLoop(port, loopPath);

								}
								newPath=new Path(new LinkedList<>(path.getPath()), path.getCost());;
							}
					}
					if(currentCost<=Integer.parseInt(dataMap.get("below")))
						outputPathSet.add(path);
				}
		
			}
			
			return(outputPathSet.toString());
			
		}
	};
	
	public abstract String ask(FliteTracker tracker, Map<String, String> dataMap);
}
