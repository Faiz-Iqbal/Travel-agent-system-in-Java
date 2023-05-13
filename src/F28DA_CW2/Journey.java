package F28DA_CW2;

import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.jgrapht.*;
public class Journey implements IJourneyPartB<Airport, Flight>, IJourneyPartC<Airport, Flight> {

	private List<Airport> airportsJourney;
	private List<Flight> flightsJourney;

	public Journey(List<Airport> airports , List<Flight> flights) {
		this.airportsJourney = airports;
		this.flightsJourney = flights;
	}

	public Journey(GraphPath<Airport, Flight> path) {
		this.airportsJourney = path.getVertexList();
		this.flightsJourney = path.getEdgeList();
	}

	@Override
	public List<String> getStops() {
		List<String> airportList = new ArrayList<>();

		for(Airport airport : airportsJourney)
			airportList.add(airport.getCode());

		return airportList;
	}

	@Override
	public List<String> getFlights() {
		List<String> flightList = new ArrayList<>();

		for(Flight flight : flightsJourney)
			flightList.add(flight.getFlightCode());

		return flightList;
	}

	@Override
	public int totalHop() {
		// TODO Auto-generated method stub
		return flightsJourney.size();
	}

	@Override
	public int totalCost() {
		int total = 0;

		for(Flight flight : flightsJourney)
			total += flight.getCost();

		return total;
	}

	@Override
	public int airTime() {
		int totalAirTime = 0;

		for(Flight flight : this.flightsJourney) {
			String leave = flight.getFromGMTime();
			String arrive = flight.getToGMTime();

			int airTime = FlyingPlanner.timeDuration(leave, arrive);
			totalAirTime += airTime;
		}
		return totalAirTime;
	}

	@Override
	public int connectingTime() {
		int totalConnectingTime = 0;

		for(int count=0 ; count<flightsJourney.size()-1 ; count++) {
			Flight flight1 = flightsJourney.get(count);
			Flight flight2 = flightsJourney.get(count+1);

			String flight1Arrival = flight1.getToGMTime();
			String flight2Departure = flight2.getFromGMTime();

			int duration = FlyingPlanner.timeDuration(flight1Arrival, flight2Departure);
			totalConnectingTime += duration;
		}

		return totalConnectingTime;
	}

	@Override
	public int totalTime() {
		int airTime = airTime();
		int connectingTime = connectingTime();
		return airTime + connectingTime;
	}

	@Override
	public int totalAirmiles() {
		double multiplier = this.totalCost() * 0.03;
		return (int) (this.airTime() * multiplier);
	}






}
