import static org.junit.Assert.*;
import F28DA_CW2.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

public class FlyingPlannerTest {

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

}
