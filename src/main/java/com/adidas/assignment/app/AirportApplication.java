package com.adidas.assignment.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.adidas.assignment.model.FliteTracker;
import com.adidas.assignment.model.Pair;
import com.adidas.assignment.question.Question;
import com.adidas.assignment.question.QuestionParser;

/**
 * @author Shubham
 * Main application. Works with both file input and system input. Exits system input 
 * on two enters. (newline character)
 */
public class AirportApplication {
	
	static List<Pair<Question, Map<String, String>>> questions = new ArrayList<>(); 
	static List<String> answers = new ArrayList<>();
	static FliteTracker tracker = new FliteTracker();

	public static void main(String[] args) {
		Scanner s = null;
		if(args.length!=0){
			File f = new File(args[0]);
			try {
				s = new Scanner(f);
			} catch (FileNotFoundException e) {
				System.out.println("Invalid file name, not found. ");
				System.exit(1);
			}
		}else{
			s = new Scanner(System.in);
		}
		String nextString = s.nextLine();
		tracker.buildConnections(nextString);
		QuestionParser.buildQuestions();
		
		int i=1;
		//parse questions

		while(s.hasNextLine()){
			String nextLine = s.nextLine();
			if(nextLine==null || nextLine.trim().isEmpty() || nextLine.equals("\n") )
				break;
			Pair<Question, Map<String, String>> pair = QuestionParser.parseQuestion(nextLine);
			answers.add(pair.getFirst().ask(tracker, pair.getSecond()));
		}

		for(String answer: answers){
			System.out.println("#" + i++ + ": " + answer);
		}
	}

}
