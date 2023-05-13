import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import F28DA_CW2.*;
public class FlyingPlannerProvidedTest {

	FlyingPlanner fi;

	@Before
	public void initialize() {
		fi = new FlyingPlanner();
		try {
			fi.populate(new FlightsReader());
		} catch (FileNotFoundException | FlyingPlannerException e) {
			e.printStackTrace();
		}
	}

	// Example test cases for Part B

	@Test
	public void leastCostTest() {
		try {
			Journey i = fi.leastCost("EDI", "DXB");
			assertEquals(3, i.totalHop());
			assertEquals(374, i.totalCost());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void leastHopTest() {
		try {
			Journey i = fi.leastHop("EDI", "DXB");
			assertEquals(2, i.totalHop());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void leastCostExclTest() {
		try {
			LinkedList<String> exclude = new LinkedList<String>();
			exclude.add("LHR");
			exclude.add("PRG");
			exclude.add("LGW");
			exclude.add("LCY");
			exclude.add("DUS");
			exclude.add("FRA");
			exclude.add("WAW");
			exclude.add("AMS");
			exclude.add("CDG");
			exclude.add("IST");
			exclude.add("GLA");
			exclude.add("CWL");
			exclude.add("EWR");
			exclude.add("BOS");
			Journey i = fi.leastCost("DXB", "EDI", exclude);
			System.out.println(i.getStops());
			fail();
		} catch (FlyingPlannerException e) {
			assertTrue(true);
		}
	}

	// Example test cases for Part C
	

	@Test
	public void directlyConnectedTest() {
		Airport lhr = fi.airport("LHR");
		Set<Airport> s = fi.directlyConnected(lhr);
		assertEquals(138, s.size());
	}

	@Test
	public void setDirectlyConnectedTest() {
		int sum = fi.setDirectlyConnected();
		assertEquals(9436, sum);
	}

	@Test
	public void betterConnectedInOrderEDITest() {
		fi.setDirectlyConnected();
		fi.setDirectlyConnectedOrder();
		Airport edi = fi.airport("EDI");
		Set<Airport> betterConnected = fi.getBetterConnectedInOrder(edi);
		assertEquals(30, betterConnected.size());
	}

	@Test
	public void betterConnectedInOrderDXBTest() {
		fi.setDirectlyConnected();
		fi.setDirectlyConnectedOrder();
		Airport dxb = fi.airport("DXB");
		Set<Airport> betterConnected = fi.getBetterConnectedInOrder(dxb);
		assertEquals(11, betterConnected.size());
	}

	@Test
	public void leastCostTimeTest() {
		try {
			Journey i = fi.leastCost("EDI", "DXB");
			assertEquals(436, i.airTime());
			assertEquals(1495, i.connectingTime());
			assertEquals(1931, i.totalTime());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void leastHopMeetUpTest() {
		try {
			String meetUp = fi.leastCostMeetUp("EDI", "DXB");
			assertEquals("IST", meetUp);
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void leastTimeMeetUpTest() {
		try {
			String meetUp = fi.leastTimeMeetUp("NCL", "NTL", "0600");
			assertEquals("CDG", meetUp);
		} catch (FlyingPlannerException e) {
			fail();
		}
	}
	
	@Test
	public void airMilesTest() {
		try {
			int airmiles = fi.leastCost("EDI", "DOH").totalAirmiles();
			assertEquals(3856, airmiles);
		} catch (FlyingPlannerException e) {
			fail();
		}
	}
	
	@Test
	public void airMilesTest2() {
		try {
			int airmiles = fi.leastHop("EDI", "DOH").totalAirmiles();
			assertEquals(4585, airmiles);
		} catch (FlyingPlannerException e) {
			fail();
		}
	}
	
	// Example of advanced test case using a custom graph
	@Test
	public void leastCostCustomTest() {
		FlyingPlanner fp = new FlyingPlanner();
		HashSet<String[]> airports = new HashSet<String[]>();
		String[] a1= {"A1","City1","AirportName1"}; airports.add(a1);
		String[] a2= {"A2","City2","AirportName2"}; airports.add(a2);
		String[] a3= {"A3","City2","AirportName3"}; airports.add(a3);
		HashSet<String[]>  flights = new HashSet<String[]>();
		String[] f1= {"F1","A1","1000","A2","1100","500"}; flights.add(f1);
		String[] f2= {"F2","A1","1000","A3","1100","50"}; flights.add(f2);
		String[] f3= {"F3","A3","1000","A2","1100","50"}; flights.add(f3);
		fp.populate(airports, flights);
		try {
			Journey lc = fp.leastCost("A1", "A2");
			
			assertEquals(100,lc.totalCost());
			assertEquals(2,lc.totalHop());
			assertEquals(360, lc.totalAirmiles());
			assertEquals(120, lc.airTime());
			assertEquals(1500, lc.totalTime());
			
			
			
		} catch (FlyingPlannerException e) {
			e.printStackTrace();
			fail();
		}
	}
}
