package com.adidas.assignment.question;

import java.util.HashMap;
import java.util.Map;

import com.adidas.assignment.model.DataNode;
import com.adidas.assignment.model.LeafQuestionNode;
import com.adidas.assignment.model.Node;
import com.adidas.assignment.model.NonLeafQuestion;
import com.adidas.assignment.model.Pair;

public class QuestionParser {
	
	static Node rootNode = new NonLeafQuestion("#root");
	
	/**
	 * this should be done with a spring.xml to be able to load question 
	 * nodes from a single file and not code. This serves as a basic rule engine for 
	 * parsing questions. Builds the decision tree. 
	 */
	public static void buildQuestions(){
		
		
		Node what = rootNode.addNextWord(new NonLeafQuestion("what"));
		Node is = what.addNextWord(new NonLeafQuestion("is"));
		Node the = is.addNextWord(new NonLeafQuestion("the"));
		
		Node price = the.addNextWord(new NonLeafQuestion("price"));
		Node of = price.addNextWord(new NonLeafQuestion("of"));
		Node the2 = of.addNextWord(new NonLeafQuestion("the"));
		Node connection = the2.addNextWord(new DataNode("connection"));
		Node priceQuestion = connection.addNextWord(new LeafQuestionNode("?", Question.PRICE));
		
		Node cheapest = the.addNextWord(new NonLeafQuestion("cheapest"));
		Node connection2 = cheapest.addNextWord(new NonLeafQuestion("connection"));
		Node from = connection2.addNextWord(new DataNode("from"));
		Node to = from.addNextWord(new DataNode("to"));
		Node cheapestQuestion = to.addNextWord(new LeafQuestionNode("?", Question.CHEAPEST));
		
		Node how = rootNode.addNextWord(new NonLeafQuestion("how"));
		Node many = how.addNextWord(new NonLeafQuestion("many"));
		Node different = many.addNextWord(new NonLeafQuestion("different"));
		Node connections3 = different.addNextWord(new NonLeafQuestion("connections"));
		Node with = connections3.addNextWord(new NonLeafQuestion("with"));
		
		
		Node maximum = with.addNextWord(new DataNode("maximum"));
		Node stopsmax = maximum.addNextWord(new NonLeafQuestion("stops"));
		Node exists = stopsmax.addNextWord(new NonLeafQuestion("exists"));
		Node between = exists.addNextWord(new DataNode("between"));
		Node and = between.addNextWord(new DataNode("and"));
		Node maxstopsQuestion = and.addNextWord(new LeafQuestionNode("?", Question.MAXSTOPS));

		
		Node minimum = with.addNextWord(new DataNode("minimum"));
		Node stopsmin = minimum.addNextWord(new NonLeafQuestion("stops"));
		Node existsmin = stopsmin.addNextWord(new NonLeafQuestion("exists"));
		Node betweenmin = existsmin.addNextWord(new DataNode("between"));
		Node andmin = betweenmin.addNextWord(new DataNode("and"));
		Node mixstopsQuestion = andmin.addNextWord(new LeafQuestionNode("?", Question.MINSTOPS));

		
		Node exactly = with.addNextWord(new DataNode("exactly"));
		Node stopexact = exactly.addNextWord(new NonLeafQuestion("stop"));
		Node existsexact = stopexact.addNextWord(new NonLeafQuestion("exists"));
		Node betweenexact = existsexact.addNextWord(new DataNode("between"));
		Node andexact = betweenexact.addNextWord(new DataNode("and"));
		Node exactstopsQuestion = andexact.addNextWord(new LeafQuestionNode("?", Question.EXACTSTOPS));
		
		Node find = rootNode.addNextWord(new NonLeafQuestion("find"));
		Node all = find.addNextWord(new NonLeafQuestion("all"));
		Node connections4 = all.addNextWord(new NonLeafQuestion("connections"));
		Node fromhow = connections4.addNextWord(new DataNode("from"));
		Node tohow = fromhow.addNextWord(new DataNode("to"));
		Node below = tohow.addNextWord(new DataNode("below"));
		Node euros = below.addNextWord(new NonLeafQuestion("euros"));
		Node findQuestion = euros.addNextWord(new LeafQuestionNode("?", Question.FINDQUES));
	}
	
	/**
	 * parses question and generates appropriate question object. 
	 * @param question
	 * @return
	 */
	public static Pair<Question, Map<String, String>> parseQuestion(String question){
		    String[] questionparts = question.split(":");
		    question = questionparts[1].trim();
			question = question.replace("?", " ?");
			question = question.replace("!", " ?");
			Pair<Question, Map<String, String>>  result = rootNode.parse(question, new HashMap<String, String>());
			return result;
	}
	
}
