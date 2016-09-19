

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class to test the Road and Settlement classes
 *
 * @author Chris Loftus (add your name and change version number/date)
 * @version 1.0 (24th February 2016)
 */
public class Application {

	private Scanner scan;
	private Map map;
	private Settlement fromSettlement;
	private Settlement toSettlement;

	public Application() {
		scan = new Scanner(System.in);
		map = new Map();
	}

	private void runMenu() {
		help();

		try {
			String userInput = "";
			while (!(userInput = getInput("Main menu >> ")).equals("q")) {

				switch (userInput) {
					case "cs":
						getSettlementDetails();
						break;
					case "ds":
						deleteSettlement();
						break;
					case "cr":
						getRoadDetails();
						break;
					case "dr":
						deleteRoad();
						break;
					case "dm":
						displayMap();
						break;
					case "sm":
						saveMap();
						break;
					case "lm":
						loadMap();
						break;
					case "h":
						help();
						break;
					case "test":
						map.printRoads();
						map.printSettlements();
						System.out.println();
						break;
					case "gps":
						printConnections();
						break;
					default:
						System.out.println("Invalid command!");
						help();
				}
			}
		} catch (Exception e) {
			System.out.println("No worries I caught the error");
		}
	}

	// STEP 1: ADD PRIVATE UTILITY MENTHODS HERE. askForRoadClassifier, save and load provided

	private void save() {
		map.save(2);
	}

	private void load() {
		map.load();
	}

	private void printMenu() {
		// STEP 1: ADD CODE HERE
	}

	public static void main(String args[]) {
		Application app = new Application();
		app.load();
		app.runMenu();
		app.save();
	}

	/**
	 * GPS Methods
	 */

	private void printConnections() {
		try{
			Settlement settlement;
			if (!((settlement = map.getSettlements(getInput("From Settlement >> "))) == null)) {
				fromSettlement = settlement;
			}

			Road roadMatrix[][] = new Road[map.numberOfSettlements][map.numberOfRoads];
			Road[] roads = printRoads(fromSettlement);

			for (Road r : roads) {
				System.out.println("[app.printConnections]" + r.getSourceSettlement().getName() + " --> " + r.getName() + " --> "
						+ r.getDestinationSettlement().getName() + " (" + r.getLength() + ")");
			}
		} catch (Exception e) {

		}
	}

	private Road[] printRoads(Settlement settlement) {
		Road[] roadsConnected = new Road[10];
		int idx = 0;

		try {
			for (Road r : settlement.getAllRoads()) {
				if (r.getSourceSettlement().equals(settlement)) {

					//System.out.println(idx + " In the loop...");
					roadsConnected[idx] = r;
					idx++;
					//System.out.print(" --> " + r.getName() + "(" + r.getLength() + ")");
				}
			}
		} catch (StackOverflowError e) {

		}

		return roadsConnected;
	}

	private void printSettlements(Road[] roads) {
		try {
			for (Road r : roads) {
				if (!(r.getDestinationSettlement().equals(fromSettlement))) {
					System.out.print(" --> " + r.getDestinationSettlement().getName());
					//printRoads(road.getDestinationSettlement());
				} else {
					System.out.print(" --> End. \n");

				}
			}
		} catch (StackOverflowError e) {

		}
	}

	/**
	 * Settlement Methods
	 */

	private void getSettlementDetails() throws IOException {

		String name;
		if (map.getSettlements(name = getInput("Name of the settlement >> ")) == null) {

			SettlementType newType;

			int population = Integer.parseInt(getInput("What is the population? >> "));

			/**
			 *
			 *
			 * Hamlet       < 100
			 * village      100 - 1000
			 * Town         1000 - 100000
			 * City         100000 - 300000
			 */
			if (population < 100) {
				newType = SettlementType.HAMLET;
			} else if (population >= 100 && population < 1000) {
				newType = SettlementType.VILLAGE;
			} else if (population >= 1000 && population < 100000) {
				newType = SettlementType.TOWN;
			} else if (population >= 100000 && population < 300000) {
				newType = SettlementType.CITY;
			} else {
				while ((newType = map.checkSettlementType(getInput("Type of the settlement >> "))) == null) {
					System.out.println("Options are: \n'village' \n'hamlet'\n'city'\n'town'\nPlease try again...");
				}
			}

			// System.out.println("Settlement type = " + newType);

			Settlement newSettlement = new Settlement(name, newType, population);
			map.addSettlement(newSettlement);
		} else {
			System.out.println("Settlement: " + name + " already exists!");
		}
	}

	private void deleteSettlement() {
		Settlement oldSettlement;
		if (!((oldSettlement = map.getSettlements(getInput("What settlement do you want to delete >> "))) == null)) {
			for (Road r : oldSettlement.getRoads()) {
				r.getSourceSettlement().removeRoad(r);
				r.getDestinationSettlement().removeRoad(r);
				map.removeRoad(r);
			}

			map.removeSettlement(oldSettlement);
		}
		System.out.println(oldSettlement.getName() + " successfully removed.");
	}

	/**
	 * Road Methods
	 */

	private void getRoadDetails() {
		String name;
		Classification roadClassification;
		Settlement roadSource, roadDestination;

		if (!((name = getInput("Name of the road >> ")).equals(""))) {

			while ((roadClassification = map.getClassification(getInput("Road Classification >> "))) == null) {
				System.out.println("Options are: \n'm' \n'a'\n'b'\n'u'\nPlease try again...");
			}

			while ((roadSource = map.getSettlements(getInput("Road Source Settlement >> "))) == null) {
				System.out.println("Options are: ");
				map.printSettlements();
			}

			while ((roadDestination = map.getSettlements(getInput("Road Destination Settlement >> "))) == null) {
				System.out.println("Options are: ");
				map.printSettlements();
			}

			double roadLength = Double.parseDouble(getInput("Road Length >>"));

			Road newRoad;
			newRoad = new Road(name, roadClassification, roadSource, roadDestination, roadLength);
			map.addRoad(newRoad);
			newRoad = new Road(name, roadClassification, roadDestination, roadSource, roadLength);
			map.addRoad(newRoad);

			roadSource.addRoad(newRoad);
			roadDestination.addRoad(newRoad);
		} else {
			System.out.println("Road: " + name + " already exists!");
		}
	}

	private void deleteRoad() {
		Road oldRoad;
		if (!((oldRoad = map.getRoads(getInput("What road do you want to delete >> "))) == null)) {
			map.removeRoad(oldRoad);
		}
		System.out.println(oldRoad.getName() + " successfully removed.");
	}

	/**
	 * Map Methods
	 */

	private void displayMap() {
		System.out.println("[SERVER] >> Displaying the map.");
		map.printRoads();
		map.printSettlements();
	}

	private void saveMap() {
		System.out.println("[SERVER] >> Saving the map.");
		map.save(2);
	}

	private void loadMap() {
		System.out.println("[SERVER] >> Loading the map.");
		map.load();
	}

	/**
	 * Extra
	 */

	private String getInput(String output) {
		System.out.print(output);
		return scan.nextLine(); // Scans the next token of the input as an int.
	}

	private void help() {
		System.out.println("'cs': to create a settlement.");
		System.out.println("'ds': to delete a settlement.");
		System.out.println("'cr': to create a road.");
		System.out.println("'dr': to delete a road.");
		System.out.println("'dm': to display a map.");
		System.out.println("'sm': to save a map.");
		System.out.println("'he': for help.");
	}
}