package com.adidas.sample.adidas;

import java.util.Map;

import com.adidas.assignment.model.FliteTracker;
import com.adidas.assignment.model.Pair;
import com.adidas.assignment.question.Question;
import com.adidas.assignment.question.QuestionParser;

import junit.framework.TestCase;

/**
 * Unit test for Flighttracker App.
 */
public class AppTest 
    extends TestCase
{
	FliteTracker tracker = new FliteTracker();

	@Override
	protected void setUp() throws Exception {
		tracker.buildConnections("Connection: NUE-FRA-43, NUE-AMS-67, FRA-AMS-17, FRA-LHR-27, LHR-NUE-23");
		QuestionParser.buildQuestions();
		super.setUp();
	}
    /**
     * Basic Tests
     */
    public void testApp()
    {	
    	Pair<Question, Map<String, String>> pair = QuestionParser.parseQuestion("#1: What is the price of the connection NUE-FRA-LHR?");
        assertEquals((pair.getFirst().ask(tracker, pair.getSecond())), "70");
        
        pair = QuestionParser.parseQuestion("#2: What is the cheapest connection from NUE to AMS?");
        assertEquals((pair.getFirst().ask(tracker, pair.getSecond())), "NUE-FRA-AMS-60");
        
        pair = QuestionParser.parseQuestion("#3: What is the cheapest connection from FRA to FRA?");
        assertEquals((pair.getFirst().ask(tracker, pair.getSecond())), "FRA-LHR-NUE-FRA-93");
        
        pair = QuestionParser.parseQuestion("#7: How many different connections with maximum 300 stops exists between NUE and FRA?");
        assertEquals((pair.getFirst().ask(tracker, pair.getSecond())), "101");
        
        pair = QuestionParser.parseQuestion("#9: Find all connections from NUE to LHR below 170 Euros!");
        assertEquals((pair.getFirst().ask(tracker, pair.getSecond())), "[NUE-FRA-LHR-70, NUE-FRA-LHR-NUE-FRA-LHR-163]");

    }
    
}
