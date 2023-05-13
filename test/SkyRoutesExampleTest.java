import F28DA_CW2.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

public class SkyRoutesExampleTest {

	IFlyingPlannerPartB<Airport,Flight> fi = new FlyingPlanner();
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


}
