package com.adidas.assignment.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

public class FliteTracker {
	
	HashMap<String, Airport> airmap = new HashMap<>();
	Map<Airport, Map<Airport, SortedSet<Path>>> shortestPathCache = new HashMap<>();
	//from, to, pathset

	
	/**
	 * graph builder. Basically builds the entire graph in and stores in airmap. 
	 * This can be replaced by spring.xml to build a graph.
	 * @param connectionString
	 * @return
	 */
	public Map<String, Airport> buildConnections(String connectionString){
		
		String[] stringOne = connectionString.split(":");
		if(!stringOne[0].equals("Connection")){
			throw new RuntimeException("Invalid connection String");
		}else{
			String[] connections = stringOne[1].split(",");
			for(String conn: connections){
				String[] thispath = conn.split("-");
				Airport start, end = null;
				if(airmap.containsKey(thispath[0].trim())){
					start = airmap.get(thispath[0].trim());
				}else{
					start = new Airport(thispath[0].trim());
					airmap.put(thispath[0].trim(), start);
				}
				
				if(airmap.containsKey(thispath[1].trim())){
					end = airmap.get(thispath[1].trim());
				}else{
					end = new Airport(thispath[1].trim());
					airmap.put(thispath[1].trim(), end);
				}
				
				Integer cost = Integer.parseInt(thispath[2].trim());
				start.addPath(end, cost);
				airmap.get(thispath[0].trim()).addPath(end, cost);
				
			}
		}
		return airmap;
	}
	
	/**
	 * gives price of a certain path
	 * @param path 
	 * @return
	 */
	public int priceEngine(List<String> path){
		int cost = 0;
		Airport firstAirport = airmap.get(path.get(0));
		int index =1;
		while(index<path.size()){
			Airport searchAir = airmap.get(path.get(index));
			if(searchAir!=null && firstAirport.toAirports.containsKey(searchAir)){
				cost = cost + firstAirport.toAirports.get(searchAir);
			}else{
				return 0;
			}
			firstAirport = searchAir;
			index++;
		}
		
		return cost;
	}
	
	/**
	 * Generates all paths from "from" , to "to" in the order of the cost. Also saves them into cache 
	 * to avoid repetition in a single call. 
	 * @param from = from airport
	 * @param to - to airport
	 * @return
	 */
	public SortedSet<Path> priceEndToEnd(String from, String to){

		from = from.trim();
		to = to.trim();
		Airport fromAirport = airmap.get(from);
		if(fromAirport==null){
			return null;
		}
		Set<Airport> visited = new HashSet<>();
		Stack<Airport> airportStack = new Stack<>();
		airportStack.push(fromAirport);
		Map<Airport, SortedSet<Path>> shortestPathThis = shortestPathCache.get(fromAirport);
		Path loopPath = new Path(new LinkedList<>(Arrays.asList(new String[]{fromAirport.name})), 0);
		
		
		if(shortestPathThis==null){
			shortestPathThis = new HashMap<>();
			shortestPathCache.put(fromAirport, shortestPathThis);
			
			shortestPathThis.put(fromAirport, new TreeSet());
			shortestPathThis.get(fromAirport).add(loopPath);
			
			while(!airportStack.isEmpty()){
				//examining airport 
				Airport thisAirport = airportStack.pop();
				
				//calculate shortest paths 
				if(!visited.contains(thisAirport)){
					for(Airport port: thisAirport.toAirports.keySet()){
						Integer neighborCost = thisAirport.toAirports.get(port);
						
						if(!shortestPathThis.containsKey(port)){
							SortedSet<Path> pathSet = new TreeSet<Path>();
							shortestPathThis.put(port, pathSet);
						}

						//all shortest path
						for(Path thisPath: shortestPathThis.get(thisAirport)){
							LinkedList<String> pathStack = new LinkedList<>();
							pathStack.addAll(thisPath.path);
							pathStack.add(port.name);
							int cost = thisPath.cost + neighborCost;
							Path addPath = new Path(pathStack, cost);
							shortestPathThis.get(port).add(addPath);
						}
						
						airportStack.push(port);
					}	
					visited.add(thisAirport);

				}
				
			}
		}
		
		
		return shortestPathThis.get(airmap.get(to));
		
	}

}
