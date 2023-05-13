package F28DA_CW2;

import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import org.jgrapht.*;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.*;

public class FlyingPlanner implements IFlyingPlannerPartB<Airport,Flight>, IFlyingPlannerPartC<Airport,Flight> {

	// Graphs to store airport and flight data
	private Graph<Airport, Flight> g1 = new SimpleDirectedWeightedGraph<>(Flight.class);
	private Graph<Airport, Flight> g2 = new DirectedAcyclicGraph<>(Flight.class);

	//Return the graph storing airports.
	public Graph<Airport, Flight> getGraph() {
		return g1;
	}

	public static int timeDuration(String begin, String finish) {
		// Declaring variables for the hour and minute of the starting and finishing times of a flight
		int beginHH,beginMM,finishHH,finishMM = 0;

		// Separate the hour and minute values of the starting time and convert them to integers
		beginHH = Integer.parseInt(begin.substring(0, 2));
		beginMM = Integer.parseInt(begin.substring(2));

		// Separate the hour and minute values of the finishing time and convert them to integers
		finishHH = Integer.parseInt(finish.substring(0, 2));
		finishMM = Integer.parseInt(finish.substring(2));

		// Create LocalTime objects that store the beginning and finish times
		LocalTime beginTime = LocalTime.of(beginHH, beginMM);
		LocalTime finishTime = LocalTime.of(finishHH, finishMM);

		// Calculate the duration between the start and end times
		Duration timeDifference = Duration.between(beginTime, finishTime);

		// If the duration is negative, add a day to get the correct duration
		if(timeDifference.isNegative())
			timeDifference = timeDifference.plusDays(1);

		// Convert the duration to minutes and return the result as an integer
		return (int) timeDifference.toMinutes();
	}


	@Override
	public boolean populate(FlightsReader fr) {
		// Get the set of airports from the FlightsReader 
		HashSet<String[]> airportsSet = fr.getAirports();
		// Get the set of flights from the FlightsReader 
		HashSet<String[]> flightsSet = fr.getFlights();
		return populate(airportsSet, flightsSet);
	}


	@Override
	/**
	 * Populates the graph with airports and flights.
	 *
	 * @param airportsData The set of airports to add to the graph
	 * @param flightsData The set of flights to add to the graph
	 * @return true if the graph is successfully populated, else false
	 */
	public boolean populate(HashSet<String[]> airportsData, HashSet<String[]> flightsData) {
		// Loop through each airport in the set and add it to the graph as a vertex
		for(String[] airportData : airportsData) {
			g1.addVertex(new Airport(airportData));
		}

		// Loop through each flight in the set and add it to the graph as an edge between the departure and arrival airports
		for(String[] flightData : flightsData) {
			Airport departureAirport = airport(flightData[1]); // Get the departure airport
			Airport arrivalAirport = airport(flightData[3]); // Get the arrival airport
			g1.addEdge(departureAirport, arrivalAirport, new Flight(departureAirport, arrivalAirport, flightData)); // Add the flight as an edge between the airports
		}

		return true; // Return true to indicate if graph was successfully populated
	}

	@Override
	public Airport airport(String airportCode) {  
		// Loop through each Airport object in the set of vertices of the graph g1.
		for(Airport airport : g1.vertexSet()) { 
			// Check if the airport code of the current Airport object is equal to the input airport code.
			if(airport.getCode().equals(airportCode)) 
				return airport;
		}
		return null;  //return null to indicate that the airport code is invalid.
	}

	@Override
	public Flight flight(String code) {
		// Loop through all the flights in the graph
		for (Flight flight : g1.edgeSet()) {
			// Check if the flight code matches the given code
			if (flight.getFlightCode().equals(code))
				// Return the flight if it matches the code
				return flight;
		}
		// Return null if no flight is found with the given code
		return null;
	}


	/**
	 * least-cost journey from a given departure airport to a given arrival airport.
	 */
	@Override
	public Journey leastCost(String from, String to) throws FlyingPlannerException {
		return leastCost(from, to, null); 
	}

	/**
	 * least-hop journey from a given departure airport to a given arrival airport.
	 */
	@Override
	public Journey leastHop(String from, String to) throws FlyingPlannerException {
		return leastHop(from, to, null);
	}

	@Override
	public Journey leastCost(String pointA, String pointB, List<String> excludingAp)
			throws FlyingPlannerException {

		//Get starting and ending airport codes
		Airport strt = airport(pointA);
		Airport end = airport(pointB);

		//Remove the excluded airports from the graph
		if(excludingAp != null) { 
			for(String apCode : excludingAp) { 
				Airport airport = airport(apCode);
				g1.removeVertex(airport);
			}
		}

		//Set the edge weights to the costs of the flights
		for(Flight flight : g1.edgeSet()) {
			g1.setEdgeWeight(flight, flight.getCost());
		}

		//Calculate the shortest path using Dijkstra's algorithm
		GraphPath<Airport, Flight> path = DijkstraShortestPath.findPathBetween(g1, strt, end);

		//Throw an exception if no path is found
		if(path == null) {
			throw new FlyingPlannerException("No Paths Found");
		}

		//Create a Journey object representing the calculated path and return it
		Journey journey = new Journey(path);
		return journey;


	}

	@Override
	public Journey leastHop(String pointA, String pointB, List<String> excludingAp)
			throws FlyingPlannerException {

		//Get starting and ending airport codes
		Airport strt = airport(pointA);
		Airport end = airport(pointB);

		// Remove the excluded airports from the graph
		if(excludingAp != null) { 
			for(String apCode : excludingAp) { 
				Airport airport = airport(apCode);
				g1.removeVertex(airport);
			}
		}

		// Set edge weights to 1 to represent number of hops
		for(Flight flight : g1.edgeSet()) {
			g1.setEdgeWeight(flight, 1d);
		}

		// Find shortest path using DijkstraShortestPath
		GraphPath<Airport, Flight> path = DijkstraShortestPath.findPathBetween(g1, strt, end);

		// Throw exception if no path is found
		if(path == null) {
			throw new FlyingPlannerException("No Paths Found");
		}

		// Create Journey object from the shortest path and return it
		Journey journey = new Journey(path);
		return journey;
	}


	@Override
	public Set<Airport> directlyConnected(Airport airport) {
		// Create a new hash set to hold the directly connected airports
		Set<Airport> directlyConnectedAP = new HashSet<>();

		// Loop through all vertices in the graph
		for(Airport vertice : g1.vertexSet()) {
			// Check if there is an edge between the input airport and the current vertex in both directions
			if( g1.containsEdge(vertice, airport) && g1.containsEdge(airport, vertice) ) {
				// If there is an edge in both directions, add the current vertex to the set of directly connected airports
				directlyConnectedAP.add(vertice);
			}
		}
		// Return the set of directly connected airports
		return directlyConnectedAP;
	}

	@Override
	public int setDirectlyConnected() {
		int sumDCA = 0;
		// iterate over all airports in the graph g1
		for(Airport airport : g1.vertexSet()) {
			// calculate the set of directly connected airports for the current airport
			airport.setDicrectlyConnected(directlyConnected(airport));
			// add the number of directly connected airports to the sum
			sumDCA += directlyConnected(airport).size();
		}

		// return the total number of directly connected airports in the graph g1
		return sumDCA;
	}

	@Override
	public int setDirectlyConnectedOrder() {
		// loop through all the edges of the graph
		for(Flight flight : g1.edgeSet()) {
			// get the source and target airports of the edge
			Airport src = g1.getEdgeSource(flight);
			Airport trgt = g1.getEdgeTarget(flight);

			// check if the source airport has less directly connected airports than the target airport
			if(src.getDicrectlyConnected().size() < trgt.getDicrectlyConnected().size()) {
				// add the source and target airports to the new graph
				g2.addVertex(src);
				g2.addVertex(trgt);
				// add the edge between the source and target airports to the new graph
				g2.addEdge(src, trgt, flight);
			}
		}

		// return the number of edges in the new graph
		return g2.edgeSet().size();
	}

	@Override
	public Set<Airport> getBetterConnectedInOrder(Airport airport) {
		// Create a new hash set to store the airports that are better connected in order
		Set<Airport> betterConnectedInOrder = new HashSet<>();

		// Iterate over all airports in the g2 graph
		for(Airport destinationAirport : g2.vertexSet()) {
			// Check if there is a path between the source and the destination airports in the g2 graph
			if(DijkstraShortestPath.findPathBetween(g2, airport, destinationAirport) != null) {
				// If there is a path, add the destination airport to the set of better connected airports
				betterConnectedInOrder.add(destinationAirport);
			}
		}

		// Remove the source airport from the set of better connected airports
		betterConnectedInOrder.remove(airport);

		// Return the set of better connected airports
		return betterConnectedInOrder;
	}

	@Override
	public String leastCostMeetUp(String startAp1, String startAp2) throws FlyingPlannerException {
		// Create a list of all airports that can be used as a meetup point between startAp1 and startAp2
		List<String> codes = codeList(startAp1, startAp2);

		// Create a list to hold the costs of each possible meetup point
		List<Integer> costs = new LinkedList<>();
		// Loop through all possible meetup points
		for(String apCode : codes) {
			// Find the least cost journey from startAp1 to the meetup point
			Journey j1 = leastCost(startAp1, apCode);
			// Find the least cost journey from startAp2 to the meetup point
			Journey j2 = leastCost(startAp2, apCode);
			// Calculate the total cost of both journeys and add it to the costs list
			int cost = j1.totalCost() + j2.totalCost();
			costs.add(cost);
		}

		// Find the lowest cost from the costs list
		int lowestCost = Collections.min(costs);
		// Find the index of the lowest cost in the costs list
		int index = costs.indexOf(lowestCost);
		// Find the corresponding meetup point using the index found above
		String leastCostMeetUp = codes.get(index);

		// Return the airport code for the least cost meetup point
		return leastCostMeetUp;
	}

	@Override
	public String leastHopMeetUp(String startAp1, String startAp2) throws FlyingPlannerException {
		// Get a list of airport codes between startAp1 and startAp2
		List<String> codes = codeList(startAp1, startAp2);

		// Create a list to store the number of hops and cost for each stop
		List<flightStops> stopCosts = new LinkedList<>();
		for(String apCode : codes) {
			// Get the least hop journey from startAp1 to the current stop
			Journey journey1 = leastHop(startAp1, apCode);
			// Get the least hop journey from startAp2 to the current stop
			Journey journey2 = leastHop(startAp2, apCode);
			// Calculate the total cost by adding the costs of the two journeys
			int totalCost = journey1.totalCost() + journey2.totalCost();
			// Calculate the total number of hops by adding the number of hops in the two journeys
			int noOfHops = journey1.totalHop() + journey2.totalHop();
			// Create a new StopCostClass object with the current stop's code, number of hops, and total cost
			stopCosts.add(new flightStops(apCode, noOfHops, totalCost));
		}

		// Sort the list of stop costs in ascending order by the number of hops
		stopCosts.sort(new flightStopsComparator());
		// Get the code of the stop with the least number of hops
		String leastHopMeetUp = stopCosts.get(0).getCode();

		// Return the code of the stop with the least number of hops
		return leastHopMeetUp;
	}


	@Override
	public String leastTimeMeetUp(String startAp1, String startAp2, String startTime) throws FlyingPlannerException {	
		// Get a list of airport codes for the starting airports
		List<String> codes = codeList(startAp1, startAp2);

		// Initialize the minimum time and corresponding airport code
		int minimumTime = Integer.MAX_VALUE;
		String correspondingCode = null;

		// Loop through each airport code in the list
		for(String code : codes) {

			// Find the least-time journey from startAp1 to the current airport
			Journey journeyA = leastTime(startAp1, code);
			// Get the first flight in the journey
			Flight flightA = flight(journeyA.getFlights().get(0));
			// Calculate the waiting duration between the start time and the departure time of the flight
			int waitingDurationA = timeDuration(startTime, flightA.getFromGMTime());
			// Get the total duration of the journey
			int journeyAdur = journeyA.totalTime();
			// Calculate the total time from the start time to the destination airport
			int t1 = waitingDurationA + journeyAdur;

			// Find the least-time journey from startAp2 to the current airport
			Journey journeyB = leastTime(startAp2, code);
			// Get the first flight in the journey
			Flight flightB = flight(journeyB.getFlights().get(0));
			// Calculate the waiting duration between the start time and the departure time of the flight
			int waitingDurationB = timeDuration(startTime, flightB.getFromGMTime());
			// Get the total duration of the journey
			int journeyBdur = journeyB.totalTime();
			// Calculate the total time from the start time to the destination airport
			int t2 = waitingDurationB + journeyBdur;

			// This if-else statement checks whether the total time to reach the meeting point 
			// from startAp1 is less than the total time to reach the meeting point from startAp2.
			if(t1 < t2) {
				// If t1 is less than minimumTime, set the minimumTime to t1, 
				// and set the correspondingCode to the current code value in the for loop.
				if(t1 < minimumTime) {
					minimumTime = t1;
					correspondingCode = code;
					// Otherwise, continue with the next iteration of the for loop.
				} else {
					continue;
				}
			}
			// If t2 is less than t1, then the minimum time to reach the meeting point is via startAp2.
			else if(t2 < t1) {
				// If t2 is less than minimumTime, set the minimumTime to t2, 
				// and set the correspondingCode to the current code value in the for loop.
				if(t2 < minimumTime) {
					minimumTime = t2;
					correspondingCode = code;
					// Otherwise, continue with the next iteration of the for loop.
				} else {
					continue;
				}
			}
			// If t1 is equal to t2, it does not matter which airport is used.
			else {
				// If t1 is less than minimumTime, set the minimumTime to t1, 
				// and set the correspondingCode to the current code value in the for loop
				if(t1 < minimumTime) {
					minimumTime = t1;
					correspondingCode = code;
					// Otherwise, continue with the next iteration of the for loop.
				} else {
					continue;
				}
			}

		}
		// Once all codes have been checked, return the correspondingCode with the minimum time.
		return correspondingCode;
	}


	private List<String> codeList(String dep, String des){
		// Create a new linked list of strings to hold the codes.
		List<String> codes = new LinkedList<>();
		// Initialize the maximum number of codes and the number of hops.
		int maxNumber = 0;
		int noOfHops = 0;

		// Start a do-while loop to generate all paths from dep to des
		do {
			// Get all paths from dep to des 
			codes = allPaths(dep, des, noOfHops);
			// Set the maximum number of codes to the size of the list.
			maxNumber = codes.size();
			// Increment the number of hops for the next iteration.
			noOfHops++;
			// Continue the loop while the maximum number of codes is less than 30 and the number of hops is less than 6.
		} while(maxNumber<30 && noOfHops<6);

		// Return the list of codes.
		return codes;
	}

	private List<String> allPaths(String pointA, String pointB, int maxNumber){
		Airport strt = airport(pointA); // get the Airport object corresponding to pointA
		Airport end = airport(pointB); // get the Airport object corresponding to pointB

		AllDirectedPaths<Airport, Flight> allDirectedPaths = new AllDirectedPaths<>(g1); // create an object of AllDirectedPaths with the graph g1
		List<GraphPath<Airport, Flight>> allDirectedPathsList = allDirectedPaths.getAllPaths(strt, end, true, maxNumber); // get all the possible paths from the start airport to end airport with a maximum limit of maxNumber

		List<String> codesList = new LinkedList<>(); // create an empty list to hold the airport codes

		for(GraphPath<Airport, Flight> path : allDirectedPathsList) { // iterate over all the paths
			if(path.getEdgeList().size() != 1) { // check if the path has more than one edge
				List<Airport> airportList = path.getVertexList(); // get the list of airports in the path
				for(Airport airport : airportList) { // iterate over all the airports in the path
					String apCode = airport.getCode(); // get the airport code
					if(!apCode.equalsIgnoreCase(pointA) && !apCode.equalsIgnoreCase(pointB) && !codesList.contains(apCode)) // check if the airport code is not equal to the start or end airport and is not already present in the list
						codesList.add(apCode); // add the airport code to the list
				}
			}
		}

		return codesList; // return the list of airport codes

	}

	public Journey leastTime(String pointA, String pointB) throws FlyingPlannerException {
		// Get the airport objects for the given start and end points
		Airport strt = airport(pointA);
		Airport end = airport(pointB);

		// Set the weight of each flight edge in the graph as its duration
		for(Flight flights : g1.edgeSet()) {
			g1.setEdgeWeight(flights, flights.getFlightDuration());
		}

		// Find the shortest path between the start and end airports using Dijkstra's algorithm
		GraphPath<Airport, Flight> path = DijkstraShortestPath.findPathBetween(g1, strt, end);

		// If no path is found, throw a FlyingPlannerException with an error message
		if(path == null) {
			throw new FlyingPlannerException("No Paths Found");
		}

		// Convert the shortest path into a Journey object and return it
		Journey journey = new Journey(path);
		return journey;
	}


}
