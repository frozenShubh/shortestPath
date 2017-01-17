package com.adidas.assignment.model;

import java.util.HashMap;
import java.util.Map;

import com.adidas.assignment.question.Question;

/**
 * @author Shubham
 * Data node for parsing data triggers
 */
public class DataNode implements Node{
	String word;
	Map<String, Node> nextWords = new HashMap<>();
	Map<String, String> dataMap = new HashMap<>();
 
	
	public DataNode(String word) {
		this.word=word;
	}
	
	public Node addNextWord(Node question){
		question.addData(dataMap);
		nextWords.put(question.getWord().toLowerCase(), question);
		return question;

	}
	
	@Override
	public void addData(Map<String, String> data) {
		this.dataMap.putAll(data);
	}

	@Override
	public  Pair<Question, Map<String, String>> parse(String s, Map<String, String> dataMap){
		s=s.trim();
		String[] words = s.split(" ");
		if(words.length<1){
			throw new RuntimeException("Doesnt seem like a Data Node, incorrect question");
		}
		String value = words[0];
		String word = words[1];
		dataMap.put(this.word, value);
		if(nextWords.containsKey(word.toLowerCase())){
			return nextWords.get(word.toLowerCase()).parse(s.substring(value.length() + word.length() + 1), dataMap);
		}else{
			throw new RuntimeException("Incorrect Question");
		}
		
	}

	@Override
	public String getWord() {
		return word;
	}
}
