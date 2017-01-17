package com.adidas.assignment.model;

import java.util.Map;

import com.adidas.assignment.question.Question;

/**
 * @author Shubham
 * Basic NLP implemented using a simple decision tree. This is a node interface. 
 *
 */
public interface Node {
	public void addData(Map<String, String> data);
	public Node addNextWord(Node question);
	public  Pair<Question, Map<String, String>> parse(String s, Map<String, String> dataMap);
	public String getWord();
}
