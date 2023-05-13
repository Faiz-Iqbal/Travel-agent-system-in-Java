import F28DA_CW2.*;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class FPTest {

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

	@Test
	public void hopTest() {
		try {
			Journey j = fi.leastHop("SJP", "WRO");
			assertEquals(3, j.totalHop());
		} catch (FlyingPlannerException fpe) {
			System.err.println(fpe.getStackTrace());
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
	public void PathTest() {
		try {
			Journey i = fi.leastCost("EDI", "DXB");
			LinkedList<String> l = new LinkedList<String>();
			l.add("EDI");
			l.add("IST");
			l.add("RUH");
			l.add("DXB");

			assertEquals(l, i.getStops());

		} catch (FlyingPlannerException e) {
			fail();
		}
	}
}

