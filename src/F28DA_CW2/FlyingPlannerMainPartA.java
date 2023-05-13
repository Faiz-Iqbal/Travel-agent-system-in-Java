package F28DA_CW2;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleGraph;

/**
 * 
 * @author mohammedfaiziqbal
 *
 */

public class FlyingPlannerMainPartA {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		// The following code is from HelloJGraphT.java of the org.jgrapth.demo package

		//		System.err.println("The example code is from HelloJGraphT.java "
		//							+ "from the org.jgrapt.demo 			package.");
		//		System.err.println("Use similar code to build the small graph "
		//							+ "from Part A by hand.");
		//		System.err.println("Note that you will need to use a different graph "
		//							+ "class as your graph is not just a Simple Graph.");
		//		System.err.println("Once you understand how to build such graph by hand, "
		//							+ "move to Part B to build a more substantial graph.");
		// Code is from HelloJGraphT.java of the org.jgrapth.demo package (start)

		Graph<String, DefaultEdge> g = new SimpleDirectedWeightedGraph<>(DefaultEdge.class);

		//Variables to store cities
		String v1 = "edinburgh";
		String v2 = "heathrow";
		String v3 = "dubai";
		String v4 = "sydney";
		String v5 = "kuala lumpur";

		//Add cities to their respective vertices
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);

		//Add edges that represent flights between the cities
		//Flights route between Edinburgh and Heathrow
		g.addEdge(v1,v2);
		g.addEdge(v2, v1);
		//Flights route between Edinburgh and Dubai
		g.addEdge(v1, v3);
		g.addEdge(v3, v1);
		//Flights route between Heathrow and Dubai
		g.addEdge(v2, v3);
		g.addEdge(v3, v2);
		//Flights route between Heathrow and Sydney
		g.addEdge(v2, v4);
		g.addEdge(v4, v2);
		//Flights route between Dubai and Sydney
		g.addEdge(v3, v5);
		g.addEdge(v5, v3);
		//Flights route between Sydney and Kuala Lumpur
		g.addEdge(v4, v5);
		g.addEdge(v5, v4);

		//Add weights to each edge (Cost of flight)
		//Flights cost between Edinburgh and Heathrow
		g.setEdgeWeight(v1, v2, 80);
		g.setEdgeWeight(v2, v1, 80);
		//Flights cost between Edinburgh and Dubai
		g.setEdgeWeight(v1, v3, 150);
		g.setEdgeWeight(v3, v1, 150);
		//Flights cost between Heathrow and Dubai
		g.setEdgeWeight(v2, v3, 130);
		g.setEdgeWeight(v3, v2, 130);
		//Flights cost between Heathrow and Sydney
		g.setEdgeWeight(v2, v4, 570);
		g.setEdgeWeight(v4, v2, 570);
		//Flights cost between Dubai and Sydney
		g.setEdgeWeight(v3, v5, 170);
		g.setEdgeWeight(v5, v3, 170);
		//Flights cost between Sydney and Kuala Lumpur
		g.setEdgeWeight(v4, v5, 150);
		g.setEdgeWeight(v5, v4, 150);


		//Find the shortest path using Dijkstra's Shortest Path
		DijkstraShortestPath<String, DefaultEdge> shortestPath = new DijkstraShortestPath<String, DefaultEdge>(g);



		//Interface print statements
		System.out.println("*****************************");
		System.out.println("**   Travel Agent System   **");
		System.out.println("*****************************");
		System.out.println();

		//Prints airports available
		System.out.println("The following airport are used: ");
		//Stores the list of airports into a set which avoids duplicates
		Set<String> verticesSet = new HashSet<>(g.vertexSet());
		//Convert set to an array in order to print them
		String[] verticesArray = verticesSet.toArray(new String[0]);
		//Prints the list of Airports stored in array
		for (int i = 0; i < verticesArray.length; i++) {
			System.out.println((i+1) + ". " + verticesArray[i]);
		}

		//Cheapest Journey finder
		String start,end; //stores the Starting and Destination airport

		System.out.println();
		System.out.println("Please enter your start airport: ");
		start = scan.nextLine().toLowerCase(); //User inputs starting airport
		System.out.println("Please enter your destination airport: ");
		end = scan.nextLine().toLowerCase(); //User enters Destination Airport
		System.out.println();
		
		//Catches IllegalArgumentException to avoid users entering wrong airport details
		try {
			
			if(start.equals(end)) {
				System.err.println("Cannot travel from " + start +" to " + end);
				return;
			}
			
			/* Gets shortest path between the start and end vertices (Starting
			 * and destination airports), this is done by calling getpath method from
			 * DijktrasShortestPath class.*/
			GraphPath<String, DefaultEdge> path = shortestPath.getPath(start, end);

			/* 
			 *Gets a list of vertices in the shortest path and add them to a list object
			 *in order
			 */
			List<String> vertexList = path.getVertexList();

			//Counter to keep track of plane changes
			int planeChanges = -1;
			
			System.out.println("Cheapest Path: ");

			/*Loops through the vertex list and prints the sequence of vertices(airports)
			 * visited in the shortest path. 
			 */
			for(int i = 0; i < vertexList.size() - 1; i++) {
				//The loop prints each
				//vertex in the list along with the adjacent vertex, 
				//which indicates a flight path
				System.out.println((i+1) + ". " + vertexList.get(i) + 
						" -> " + vertexList.get(i+1));
				planeChanges++; //Increment Plane changes 
			}

			System.out.println();
			//Prints number of plane transfers
			System.out.println("Number of plane changes: "+ planeChanges);

			//Prints the cost of the cheapest path
			System.out.println("Cheapest Path: £"
					+shortestPath.getPathWeight(start, end));
		} catch (IllegalArgumentException e) {
		    // handle exception
		    System.err.println("Please enter valid Airport names");
		}

	}

}
