package F28DA_CW2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlyingPlannerMainPartBC {

	// Scanner to take input from user
	private static Scanner scan = new Scanner(System.in);

	// Print Flight and Print Journey with old formatting

	// Function to print the flight details
	private static void printFlightDetails(Flight flight) {
		// Print the flight details
		System.out.printf("%-45s", flight.getFrom().getName() + " (" + flight.getFrom().getCode() + ")");
		System.out.printf("%-7s", flight.getFromGMTime().substring(0, 2) + ":" + flight.getFromGMTime().substring(2, 4));
		System.out.printf("%-10s", flight.getFlightCode());
		System.out.printf("%-45s", flight.getTo().getName() + " (" + flight.getTo().getCode() + ")");
		System.out.printf("%-7s", flight.getToGMTime().substring(0, 2) + ":" + flight.getToGMTime().substring(2, 4));
		System.out.println();
	}

	// Function to print the journey details which includes the flight details
	private static void printJourneyDetails(List<String> flights, FlyingPlanner fi) {
		System.out.println("----------------------------------------------------------------------------------------------------------------------");
		System.out.printf("%-1s %-45s %-7s %-10s %-42s %-3s\n", "Leg", "Leave", "At", "On", "Arrive", "At");
		System.out.println("----------------------------------------------------------------------------------------------------------------------");

		int i = 1;
		// Call the function to print each flight detail
		for (String code : flights) {
			System.out.printf("%-4s", " " + i);
			i++;
			printFlightDetails(fi.flight(code));
			System.out.println("----------------------------------------------------------------------------------------------------------------------");

		}
		System.out.println();
	}


	// Print Flight and Print Journey with New formatting

	// Function to print the flight details
	//	private static void printFlightDetails(Flight flight) {
	//		// Print the flight details
	//		System.out.printf("%-42s", /*flight.getFrom().getName() +*/ " (" + flight.getFrom().getCode() + ")");
	//		System.out.printf("%-9s", flight.getFromGMTime().substring(0, 2) + ":" + flight.getFromGMTime().substring(2, 4));
	//		System.out.printf("%-11s", flight.getFlightCode());
	//		System.out.printf("%-42s", /*flight.getTo().getName() +*/ " (" + flight.getTo().getCode() + ")");
	//		System.out.printf("%-9s", flight.getToGMTime().substring(0, 2) + ":" + flight.getToGMTime().substring(2, 4));
	//		System.out.println();
	//	}
	//
	//	// Function to print the journey details which includes the flight details
	//	private static void printJourneyDetails(List<String> flights, FlyingPlanner fi) {
	//		System.out.println("+--------+-----------------------------------------+--------+----------+--------------------------------------+--------+");
	//		System.out.printf("| %-6s | %-40s | %-7s | %-10s | %-40s | %-7s |\n", "Leg", "Leave", "At", "On", "Arrive", "At");
	//		System.out.println("+--------+--------------------------------------+--------+----------+--------------------------------------+--------+");
	//
	//		int i = 1;
	//		// Call the function to print each flight detail
	//		for (String code : flights) {
	//			System.out.printf("| %-7s | ", " " + i);
	//			i++;
	//			printFlightDetails(fi.flight(code));
	//			System.out.println("+--------+-----------------------------------------+--------+----------+--------------------------------------+--------+");
	//		}
	//		System.out.println();
	//	}

	// Function to print the whole journey
	private static void printJourney(Journey j, FlyingPlanner fi) {
		printJourneyDetails(j.getFlights(), fi);
		System.out.println("Total Cost for the journey is     : £" + j.totalCost());
		System.out.println("Total Air Time for the journey is : " + j.airTime() + " minutes.");
		System.out.println("Total Connecting Time is          : " + j.connectingTime() + " minutes.");
		System.out.println("Total Time for the journey is     : " + j.totalTime() + " minutes.\n");
	}

	//Function to print the full journey according to the choices
	private static void fullJourney(FlyingPlanner fi,int userChoice,int exChoice,String startCode,String endCode,List<String> exclude)
	{
		//Printing the journey details 
		System.out.println("Journey from (" + startCode + ") and (" + endCode + ") is");
		System.out.println();

		try 
		{
			//If the exclude airports are not there
			if(exChoice==0)
			{
				//If least cost is selected
				if(userChoice==1)
				{
					//Calling the function to print the journey with least cost 
					printJourney(fi.leastCost(startCode, endCode),fi);
				}
				else
				{
					//Calling the function to print the journey with least stops
					printJourney(fi.leastHop(startCode, endCode),fi);
				}
			}
			//If exclude airports is not selected
			else
			{
				//if least cost is selected
				if(userChoice==1)
				{
					//Calling the function to print the journey with least cost
					printJourney(fi.leastCost(startCode, endCode,exclude),fi);
				}
				else
				{
					//Calling the function to print the journey with least stops
					printJourney(fi.leastHop(startCode, endCode,exclude),fi);

				}
			}
		} 
		catch (FlyingPlannerException e) 
		{
			e.printStackTrace();
		}
	}

	private static void errorMessage() {

		System.err.println("Please enter valid details");
		System.exit(0);

	}
	
	public static void thankYouMessage() {
		//Thank you message
		System.out.println("---------------------------------------");
		System.out.println("Thank you for using Travel Agent System");
		System.out.println("---------------------------------------");
		System.out.println();
	}

	//Main Interface
	private static void project(FlyingPlanner fi)
	{	
		System.out.println("***************************************************");
		System.out.println("*    Please Enter your Departure Airport Code:    *");
		System.out.println("***************************************************");
		System.out.println();

		//Stores Departure airport code
		String depCode = scan.nextLine().toUpperCase();

		System.out.println("***************************************************");
		System.out.println("*   Please Enter your Destination Airport Code:   *");
		System.out.println("***************************************************");
		System.out.println();

		//Stores Destination airport code
		String desCode = scan.nextLine().toUpperCase();

		if((fi.airport(depCode)==null) || (fi.airport(desCode)==null)){
			errorMessage();
		}
		
		//Lets user choose Least cost or least stop
		System.out.println("Would you like to choose Least Cost (1) or Least Stops (2)??");
		int userInput = scan.nextInt();
		System.out.println();
		//If the input is not valid then terminating code
		if(userInput!=1 && userInput!=2)
		{
			errorMessage();
		}


		System.out.println("Would you like to exclude any airports???");


		System.out.println("**************************************");
		System.out.println("*     1    ->         Yes            *");
		System.out.println("*     2    ->          No            *");
		System.out.println("**************************************");
		int userInput2 = scan.nextInt();
		System.out.println();

		//Checking If the user selects yes for excluding the airports 
		if(userInput2 == 1)
		{

			System.out.println("------------------------------------------------------");
			System.out.println("  Enter the codes of the airports you want to exclude ");
			System.out.println("            	 [ type * to stop ]                   ");
			System.out.println("------------------------------------------------------");
			String airportCodes ;

			List<String> arpts= new ArrayList<String>();

			
			for(;;)
			{
				System.out.print(">> ");
				//User inputs airports they want to exclude
				airportCodes = scan.next().toUpperCase();
				if(airportCodes.equalsIgnoreCase("*"))
				{
					break;
				}
				//If user enters invalid code, display error message and terminate code
				if(!(fi.airport(airportCodes)==null))
				{

					if(!(airportCodes.equalsIgnoreCase(depCode)) && !(airportCodes.equalsIgnoreCase(desCode)))
					{
						arpts.add(airportCodes);	
					}
					else
					{errorMessage();}
				}
				else
				{errorMessage();}
			}

			

			//Print the full journey
			fullJourney(fi,userInput,1,depCode,desCode,arpts);
			
			//Prints thank you message
			thankYouMessage();
		}
		//User selects No for excluding airports
		else if(userInput2 == 2)
		{
			fullJourney(fi,userInput,2,depCode,desCode,null);
			
			//Prints thank you message
			thankYouMessage();
		}
		//If user enters invalid number, display error message
		else
		{
			errorMessage();

		}

	}

	public static void main(String[] args) 
	{
		FlyingPlanner fi;
		fi = new FlyingPlanner();
		try 
		{
			//Calling the populate function to populate the graph 
			fi.populate(new FlightsReader());
			//Calling the main function
			project(fi);
		} 
		catch (FileNotFoundException | FlyingPlannerException e) 
		{
			e.printStackTrace();
		}
	}

}