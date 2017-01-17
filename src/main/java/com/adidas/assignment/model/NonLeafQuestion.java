package com.adidas.assignment.model;

import java.util.HashMap;
import java.util.Map;

import com.adidas.assignment.question.Question;

/**
 * @author shubham
 * non leaf question node. 
 *
 */
public class NonLeafQuestion implements Node{
	
	Map<String, Node> nextWords = new HashMap<>();
	String word;
	Map<String, String> dataMap = new HashMap<>();
	
	public NonLeafQuestion(String word) {
		this.word=word;
	}
	
	public Node addNextWord(Node question){
		question.addData(this.dataMap);
		nextWords.put(question.getWord().toLowerCase(), question);
		return question;
	}

	public Map<String, Node> getNextWords() {
		return nextWords;
	}

	public void setNextWords(Map<String, Node> nextWords) {
		this.nextWords = nextWords;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public void addData(Map<String, String> data) {
		this.dataMap.putAll(data);
	}
	
	public  Pair<Question, Map<String, String>> parse(String s, Map<String, String> dataMap){
		s = s.trim();
		String word = s.split(" ")[0];
		if(nextWords.containsKey(word.toLowerCase())){
			return nextWords.get(word.toLowerCase()).parse(s.substring(word.length()).trim(), dataMap);
		}else{
			throw new RuntimeException("Incorrect Question");
		}
		
	}
	
	
	
	
}
