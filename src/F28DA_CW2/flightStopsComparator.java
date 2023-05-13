package F28DA_CW2;

import java.util.Comparator;

public class flightStopsComparator implements Comparator<flightStops>{
	
	@Override
	//Code compares flight stops based on the number of hops and cost
	public int compare(flightStops first, flightStops second) {
		int hops = 0;
		int firstHop = first.getAmount('h');
		int secondHop = second.getAmount('h');
		if(firstHop > secondHop)
			    hops = 1;
		else if(firstHop == secondHop)
			   hops = 0;
		else
			hops = -1;
		
		int cost = 0;
		int firstCost = first.getAmount('c');
		int secondCost = second.getAmount('c');
		if(firstCost > secondCost)
			 cost = 1;
		else if(firstCost == secondCost)
			 cost = 0;
		else
			cost = -1;
		
		return (hops == 0) ? cost : hops;
	}
	
	

}
