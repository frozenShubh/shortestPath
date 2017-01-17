package com.adidas.assignment.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Shubham
 * Path is the equivalent connection. This has the path and total cost of the path. 
 */
public class Path implements Comparable<Path>{
	
	LinkedList<String> path;
	int cost;
	
	public Path(LinkedList<String> path, int cost) {
		this.path=path;
		this.cost = cost;
	}


	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public void addToPath(List<String> extra){
		String end = path.get(path.size()-1);
		path.clear();
		path.addAll(extra);
		path.add(end);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for(String o:path){
			builder.append(o+"-");
		}
		return  builder.toString() + cost;
	}


	@Override
	public int compareTo(Path o) {
		return this.cost - o.cost;
	}
	
	public LinkedList<String> getPath() {
		return path;
	}
	
	public static List<String> parse(String pathString) {
		String[] paths = pathString.split("-");
		return Arrays.asList(paths);
	}
	
	public Path replacePortWithLoop(String port, Path loop ){
		Path newPath = new Path(new LinkedList<>(path), cost);
		int index = newPath.getPath().indexOf(port);
		newPath.getPath().remove(port);
		newPath.getPath().addAll(index, loop.getPath());
		newPath.cost = newPath.cost + loop.getCost();
		return newPath;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.toString().hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(obj.toString());
	}
	
	
	
	
}
