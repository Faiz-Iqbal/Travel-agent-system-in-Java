package F28DA_CW2;

import java.util.Set;

public interface IFlyingPlannerPartC<A extends IAirportPartB, F extends IFlight> {

	/**
	 * Returns the set of airports directly connected to the given airport. Two
	 * airports are directly connected if there exist two flights connecting them in
	 * a single hop in both direction.
	 */
	Set<A> directlyConnected(A airport);

	/**
	 * Records, for each airport object, the airport's directly connected set and
	 * its size. Returns the sum of all airports' directly connected set sizes.
	 */
	int setDirectlyConnected();

	/**
	 * Creates a DAG containing all airports but only the flights flying to an
	 * airport destination with strictly higher number of directly connected
	 * airports than the origin airport. Returns the number of flights of the DAG.
	 * The {@link #setDirectlyConnected()} method must be called before this method
	 * is called.
	 */
	int setDirectlyConnectedOrder();

	/**
	 * Returns the set of airports reachable from the given airport that have
	 * strictly more direct connections. The {@link #setDirectlyConnectedOrder()}
	 * must be called before this method is used.
	 */
	Set<Airport> getBetterConnectedInOrder(Airport airport);

	/**
	 * Returns the airport code of a best airport for the meet up of two people
	 * located in two different airports (airport codes) accordingly to the journeys
	 * costs. The meet-up should be different than the two stating airports.
	 */
	String leastCostMeetUp(String at1, String at2) throws FlyingPlannerException;

	/**
	 * Returns the airport code of a best airport for the meet up of two people
	 * located in two different airports (airport codes) accordingly to the number
	 * of connections. The meet-up should be different than the two stating
	 * airports.
	 */
	String leastHopMeetUp(String at1, String at2) throws FlyingPlannerException;

	/**
	 * Returns the airport code of a best airport for the earliest meet up of two
	 * people located in two different airports (airport codes) when departing at a
	 * given time. The meet-up should be different than the two stating airports.
	 */
	String leastTimeMeetUp(String at1, String at2, String startTime) throws FlyingPlannerException;

}
