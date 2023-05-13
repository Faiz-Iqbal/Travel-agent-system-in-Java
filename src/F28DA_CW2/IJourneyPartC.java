package F28DA_CW2;

public interface IJourneyPartC<A extends IAirportPartB,F extends IFlight> {

	/** Returns the total time in connection of the journey */
	int connectingTime();
	
	/** Returns the total travel time of the journey */
	int totalTime();
	
	/** Returns the total airmiles points the journey would award the traveller
	 * This is calculated by the total time of the flight (in air time only) times
	 * one third of the total cost of the journey */
	int totalAirmiles();
}
