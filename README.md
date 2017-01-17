Application that implements the shortest path algorithm for a graph of airports 
with costs as the edges. 

Can be run in the following format : 
java -jar app.jar input.txt or cat input.txt | java -jar app.jar

both input file or stdin are acceptable arguments. 

To run, just check out the repo and run : 

mvn package to create the jar file. 

run the jar file using method above. 

Input format: 
Connection: NUE-FRA-43, NUE-AMS-67, FRA-AMS-17, FRA-LHR-27, LHR-NUE-23
#1: What is the price of the connection NUE-FRA-LHR?
#2: What is the price of the connection NUE-AMS-LHR?
#3: What is the price of the connection NUE-FRA-LHR-NUE?
#4: What is the cheapest connection from NUE to AMS?
#5: What is the cheapest connection from AMS to FRA?
#6: What is the cheapest connection from LHR to LHR?
#7: How many different connections with maximum 3 stops exists between NUE and FRA?
#8: How many different connections with exactly 1 stop exists between LHR and AMS?
#9: Find all connections from NUE to LHR below 170 Euros!


Output Format: 
#1: 70
#2: No such connection found!
#3: 93
#4: NUE-FRA-AMS-60
#5: No such connection found!
#6: LHR-NUE-FRA-LHR-93
#7: 2
#8: 1
#9: [NUE-FRA-LHR-70, NUE-FRA-LHR-NUE-FRA-LHR-163]

Note: Except the cheapest path, all question take into account loops. (the minimum stops question does not take into account the loops, since no limit can be defined) 
The FliteTracker Application implements the classic shortest path and all path algorithms. 
