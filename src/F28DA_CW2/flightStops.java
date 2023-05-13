package F28DA_CW2;

import java.util.List;

public class flightStops {
	
	private String airportCode;
	private int stops;
	private int sumCost;
	
	public flightStops(String code, int stops, int cost) {
		this.airportCode = code;
		this.stops = stops;
		this.sumCost = cost;
	}
	
	public String getCode() {
		return this.airportCode;
	}
	
	//Returns stops if mode is 's' and sumCost otherwise
	public int getAmount(char mode) {
		if(mode == 's')
			return this.stops;
		else
			return this.sumCost;
	}
	
	public void sortClass(List<flightStops> list) {
		
	}

}
