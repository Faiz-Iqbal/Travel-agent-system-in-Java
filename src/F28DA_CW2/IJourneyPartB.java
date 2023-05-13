package F28DA_CW2;

import java.util.List;

public interface IJourneyPartB<A extends IAirportPartB,F extends IFlight> {

	/** Returns the list of airports codes composing the journey */
	List<String> getStops();
	
	/** Returns the list of flight codes composing the journey */
	List<String> getFlights();
	
	/** Returns the number of connections of the journey */
	int totalHop();
	
	/** Returns the total cost of the journey */
	int totalCost();
	
	/** Returns the total time in flight of the journey */
	int airTime();
	
}
