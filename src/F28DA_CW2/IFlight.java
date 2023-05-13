package F28DA_CW2;

public interface IFlight {

	String getFlightCode();

	Airport getTo();

	Airport getFrom();
	
	String getFromGMTime();
	
	String getToGMTime();

	int getCost();

}