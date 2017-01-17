package com.adidas.assignment.model;

/**
 * @author Shubham
 * Simple object to return two values. 
 * @param <A>
 * @param <B>
 */
public class Pair<A,B> {
 
	A a;
	B b;
	
	Pair(A a, B b){
		this.a = a;
		this.b=b;
	}
	
	public A getFirst(){
		return a;
	}
	
	public B getSecond(){
		return b;
	}
	
}
