package F28DA_CW2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class FlightsReader {

	private static final File flightsDatasetFolder = new File("FlightsDataset");
	private static final File flightsDatasetFlights = new File(flightsDatasetFolder, "flights.csv");
	private static final File flightsDatasetAirports = new File(flightsDatasetFolder, "airports.csv");

	private HashSet<String[]> airports;
	private HashSet<String[]> flights;

	/**
	 * Reads the flights dataset. To use this class:
	 * <ol>
	 * <li>Make sure the provided {@code FlightsDataset} directory is accessible by
	 * your program (Eclipse: the directory needs to be in your project directory,
	 * not in {@code src})</li>
	 * <li>Create an object of the class FlightsReader: <br>
	 * {@code FlightsReader reader = new FlightsReader();}</li>
	 * <li>If no exception is raised, the list of airports and flights are available
	 * using the following methods. If an exception is raised, check the location of
	 * the {@code FlightsDataset} directory. <br>
	 * <ul>
	 * <li>{@code reader.getAirpors();}</li>
	 * <li>{@code reader.getFlights();}</li>
	 * </ul>
	 * </ol>
	 * This flights dataset was derived from the OpenFlights database.
	 */
	public FlightsReader() throws FileNotFoundException, FlyingPlannerException {
		this.airports = new HashSet<String[]>();
		this.flights = new HashSet<String[]>();

		// Adding flights, and building list of needed airport
		HashSet<String> airportsNeeded = new HashSet<String>();
		Scanner flightsScanner = new Scanner(flightsDatasetFlights);
		while (flightsScanner.hasNextLine()) {
			String line = flightsScanner.nextLine();
			String[] fields = line.split(",");
			String from = fields[1];
			String to = fields[3];
			airportsNeeded.add(from);
			airportsNeeded.add(to);
			this.flights.add(fields);
		}
		flightsScanner.close();

		// Adding airports
		Scanner airportsScanner = new Scanner(flightsDatasetAirports);
		while (airportsScanner.hasNextLine()) {
			String line = airportsScanner.nextLine();
			String[] fields = line.split(",");
			if (airportsNeeded.contains(fields[0])) {
				this.airports.add(fields);
			}
		}
		airportsScanner.close();
	}

	/**
	 * Returns a hash set of airport details (0: airport code, 1: city, 2: airport
	 * name)
	 */
	public HashSet<String[]> getAirports() {
		return this.airports;
	}

	/**
	 * Returns a hash set of flight details (0: flight code, 1: airport code of
	 * departure, 2: departure time GMT, 3: airport code of arrival, 4: arrival time
	 * GMT, 5: flight cost)
	 */
	public HashSet<String[]> getFlights() {
		return this.flights;
	}

}