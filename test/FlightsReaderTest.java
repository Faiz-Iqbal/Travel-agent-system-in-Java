import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

import F28DA_CW2.*;

public class FlightsReaderTest {

	@Test
	public void test1() {
		try {
			FlightsReader reader = new FlightsReader();
			assertEquals(9605,reader.getFlights().size());
			assertEquals(1119,reader.getAirports().size());
		} catch (FileNotFoundException e) {
			fail("File not found");
		} catch (FlyingPlannerException e) {
			fail("SkyRoutes");
		}
	}
	
}
