package com.adidas.assignment.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Shubham
 * An airport is a node in graph parlance. 
 * For the sake of simplicity have used Integer as an edge. If an edge has more than one property, 
 * it should be replaced by an object. 
 */
public class Airport {
	
	Map<Airport, Integer> toAirports = new HashMap<>();
	String name = "UNKNOWN";
	
	@Override
	public String toString() {
		return name ;
	} 
	
	public Airport(String name) {
		super();
		this.name = name;
	}

	public void addPath(Airport toAirport, int cost){
		 this.toAirports.put(toAirport, cost);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airport other = (Airport) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	
}
