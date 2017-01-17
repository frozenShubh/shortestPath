package com.adidas.assignment.model;

import java.util.HashMap;
import java.util.Map;

import com.adidas.assignment.question.Question;

/**
 * Leaf node. Basic leaf node that ends a question. 
 * @author eros
 *
 */
public class LeafQuestionNode implements Node{
	String word;
	Question question;
	Map<String, String> dataMap = new HashMap<>();


	public LeafQuestionNode(String word, Question question) {
		this.word=word;
		this.question = question;
	}

	@Override
	public void addData(Map<String, String> data) {
		this.dataMap=data;
	}

	@Override
	public Node addNextWord(Node question) {
		throw new IllegalStateException("Cannot be called");
	}

	@Override
	public Pair<Question, Map<String, String>> parse(String s, Map<String, String> dataMap) {
		s = s.trim();

		String[] words = s.split(" ");
		if(words.length!=1){
			throw new RuntimeException("Doesnt seem like a End Node, incorrect question");
		}
		String word = words[0];
		return new Pair<Question, Map<String,String>>(this.question, dataMap);
	}

	@Override
	public String getWord() {
		return word;
	}
}
