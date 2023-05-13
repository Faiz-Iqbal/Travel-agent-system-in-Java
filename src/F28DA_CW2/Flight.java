package F28DA_CW2;

public class Flight implements IFlight {

	private String flightCode;
	private Airport depart;
	private String depGMTime;
	private Airport dest;
	private String destGMTime;
	private int totalCost;


	public Flight(Airport departure, Airport arrival, String[] flight) {
		this.flightCode = flight[0];
		this.depart = departure;
		this.depGMTime = formatTimeString(flight[2]);
		this.dest = arrival;
		this.destGMTime = formatTimeString(flight[4]);
		this.totalCost = Integer.parseInt(flight[5]);
	}

	public static String formatTimeString(String time) {
		if(time.length() == 1) { // If the length of the time string is 1, pad with 3 zeros
			time = ("000" + time);
			return time;
		}
		else if(time.length() == 2) { // If the length of the time string is 2, pad with 2 zeros
			time = ("00" + time);
			return time;
		}
		else if(time.length() == 3) { // If the length of the time string is 3, pad with 1 zero
			time = ("0" + time);
			return time;
		}
		else { // If the length of the time string is already 4, return the same string
			return time;
		}
	}

	@Override
	public String getFlightCode() {
		// TODO Auto-generated method stub
		return this.flightCode;
	}

	@Override
	public Airport getTo() {
		// TODO Auto-generated method stub
		return this.dest;
	}

	@Override
	public Airport getFrom() {
		// TODO Auto-generated method stub
		return this.depart;
	}

	@Override
	public String getFromGMTime() {
		// TODO Auto-generated method stub
		return this.depGMTime;
	}

	@Override
	public String getToGMTime() {
		// TODO Auto-generated method stub
		return this.destGMTime;
	}

	@Override
	public int getCost() {
		// TODO Auto-generated method stub
		return this.totalCost;
	}

	//calculates the flight duration between two airports using the departure and destination time
	public int getFlightDuration() {
		int flightDur = FlyingPlanner.timeDuration(this.depGMTime, this.destGMTime);
		return flightDur;
	}


}
