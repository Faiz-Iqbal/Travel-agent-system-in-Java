import F28DA_CW2.*;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SkyRoutesTest {

	FlyingPlanner fi = new FlyingPlanner();
	FlightsReader fr;

	@Before public void initialize() {
		try {
			fr = new FlightsReader();
			fi.populate(fr.getAirports(), fr.getFlights());
		} catch (FileNotFoundException | FlyingPlannerException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void test1() {
		try {
			Journey i = fi.leastCost("EDI", "DXB");
//			System.out.println(i.getStops());
//			System.out.println(i.getFlights());
//			System.out.println(i);
			System.out.println(i.totalCost());
			assertEquals(3,i.totalHop());
			assertEquals(374,i.totalCost());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void test2() {
		try {
			Journey i = fi.leastCost("EDI", "CTU");
			System.out.println(i.getStops());
			System.out.println(i.getFlights());
			assertEquals(2,i.totalHop());
			assertEquals(498,i.totalCost());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void test10() {
		try {
			String s = fi.leastCostMeetUp("DXB", "EDI");
			assertEquals("IST",s);
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void test20() {
		try {
			LinkedList<String> exclude = new LinkedList<String>();
			exclude.add("LHR");
			exclude.add("LGW");
			exclude.add("LCY");
			exclude.add("FRA");
			exclude.add("AMS");
			exclude.add("CDG");
			exclude.add("IST");
			exclude.add("GLA");
			exclude.add("NCE");
			exclude.add("MUC");
			exclude.add("CWL");
			exclude.add("EWR");
			Journey i = fi.leastCost("DXB", "EDI", exclude);
			System.out.println(i.getStops());
			fail();
		} catch (FlyingPlannerException e) {
			assertTrue(true);
		}
	}

	@Test
	public void test30() {
		try {
			String meetUp = fi.leastTimeMeetUp("SYD", "EDI", "0600");
			assertEquals("SAT",meetUp);
		} catch (FlyingPlannerException e) {
			fail();
		}
	}
	
	@Test
	public void test31() {
		try {
			String meetUp = fi.leastTimeMeetUp("EDI", "SYD", "2200");
			assertEquals("BNE",meetUp);
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void test40() {
		try {
			Journey i = fi.leastCost("NCL","NTL");
			System.out.println(i);
			assertEquals(1035,i.totalCost());
			assertEquals(1061,i.airTime());
			assertEquals(1710,i.connectingTime());
			assertEquals(2771,i.totalTime());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void test41() {
		try {
			Journey i = fi.leastCost("EDI","NCE");
			System.out.println(i);
			assertEquals(208,i.totalTime());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void test42() {
		try {
			Journey i = fi.leastCost("SYD","NCE");
			System.out.println(i);
			assertEquals(4721,i.totalTime());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	@Test
	public void testCR() {
		try {
			Journey h = fi.leastHop("LAX", "ADD");
			String[] a = {"LAX", "DXB", "ADD"};
			List<String> l = Arrays.asList(a);
			assertEquals(l,h.getStops());
		} catch (FlyingPlannerException e) {
			fail();
		}
	}

	
	
}
