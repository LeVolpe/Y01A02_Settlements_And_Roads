import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Chris Loftus
 * @version 1.0 (25th February 2016)
 */

public class Map {
	ArrayList<Settlement> settlements = new ArrayList<>();
	ArrayList<Road> roads = new ArrayList<>();
	int numberOfSettlements = 0;
	int numberOfRoads = 0;

	public Map() {
		// INSERT CODE
	}

	/**
	 * In this version we display the result of calling toString on the command
	 * line. Future versions may display graphically
	 */
	public int getNumberOfRoads() {
		return roads.toArray().length;
	}

	public void display() {
		System.out.println(toString());
	}

	public void addSettlement(Settlement newSettlement) throws IllegalArgumentException {
		settlements.add(newSettlement);
		System.out.println("[map.addSettlement] " + newSettlement.toString() + " Created!");
		// STEP 5: INSERT CODE HERE
	}

	public void removeSettlement(Settlement oldSettlement) throws IllegalArgumentException {
		settlements.remove(oldSettlement);
	}

	public void addRoad(Road newRoad) {
		roads.add(newRoad);
	}

	public void removeRoad(Road oldRoad) {
		roads.remove(oldRoad);
	}

	public Road getRoads(String name) {
		for (Road r : roads) {
			if (r.getName().equalsIgnoreCase(name)) {
				return r;
			}
		}
		return null;
	}

	public void printSettlements() {
		for (Settlement s : settlements) {
			System.out.println(s);
		}
	}

	public void printRoads() {
		for (Road r : roads) {
			System.out.println(r);
		}
	}

	public Settlement getSettlements(String name) {
		for (Settlement s : settlements) {
			if (s.getName().toLowerCase().equals(name.toLowerCase())) {
				return s;
			}
		}
		return null;
	}

	// STEPS 7-10: INSERT METHODS HERE, i.e. those similar to addSettlement and required
	// by the Application class

	public void load() {
		try {
			System.out.println("[map.load] Loading Settlements...");
			numberOfSettlements = 0;

			FileInputStream fis = new FileInputStream("Settlements.txt");
			BufferedReader sS = new BufferedReader(new InputStreamReader(fis));
			String line;
			while ((line = sS.readLine()) != null) {
				numberOfSettlements++;
				String[] dataSplit = line.split(",");
				createSettlement(dataSplit[0], dataSplit[1], Integer.parseInt(dataSplit[2]));
			}

			System.out.println("[map.load] Settlements found: " + numberOfSettlements);
			sS.close();

		} catch (Exception e) {
			System.out.println("[map.load] Settlement File not found...");
		}

		System.out.print("\n");

		try {
			System.out.println("[map.load] Loading Roads...");
			numberOfRoads = 0;

			FileInputStream fis = new FileInputStream("Roads.txt");
			BufferedReader sS = new BufferedReader(new InputStreamReader(fis));
			String line;
			while ((line = sS.readLine()) != null) {
				numberOfRoads++;
				String[] dataSplit = line.split(",");
				//System.out.println("Creating " + dataSplit[0] + ", " + dataSplit[1] + ", "
						//+ dataSplit[2] + ", " + dataSplit[3] + ", " + Double.parseDouble(dataSplit[4]));
				createRoad(dataSplit[0], dataSplit[1], dataSplit[2], dataSplit[3], Double.parseDouble(dataSplit[4]));

				//System.out.println("Creating " + dataSplit[0] + ", " + dataSplit[1] + ", "
						//+ dataSplit[3] + ", " + dataSplit[2] + ", " + Double.parseDouble(dataSplit[4]));
				createRoad(dataSplit[0], dataSplit[1], dataSplit[3], dataSplit[2], Double.parseDouble(dataSplit[4]));
			}

			System.out.println("[map.load] Roads found: " + getNumberOfRoads()/2);
			sS.close();

		} catch (Exception e) {
			System.out.println("[map.load] Roads File not found...");
		}

		System.out.print("\n");
	}

	public void save(int myCase) {
		switch (myCase) {
			case 1:
				try {
					PrintWriter saveRoads = new PrintWriter(new FileWriter("Roads.txt"));
					for (Road r : roads) {
						System.out.print("Saving Road " + r.getName() + "... ");
						saveRoads.print(r.getName() + "," + r.getClassification() + ","
								+ r.getSourceSettlement().getName() + "," + r.getDestinationSettlement().getName() + "," + r.getLength() + ",");
						System.out.println("Complete.");
					}
					saveRoads.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					PrintWriter saveSettlements = new PrintWriter(new FileWriter("Settlements.txt"));
					for (Settlement s : settlements) {
						System.out.print("Saving Settlement " + s.getName() + "... ");
						saveSettlements.print(s.getName() + "," + s.getKind() + "," + s.getPopulation() + ",");
						System.out.println("Complete.");
					}
					saveSettlements.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			case 2:
				try {
					PrintWriter saveRoads = new PrintWriter(new FileWriter("Roads.txt"));
					for (Road r : roads) {
						System.out.print("Saving Road " + r.getName() + "... ");
						saveRoads.println(r.getName() + "," + r.getClassification() + ","
								+ r.getSourceSettlement().getName() + "," + r.getDestinationSettlement().getName()
								+ "," + r.getLength());
						System.out.println("Complete.");
					}
					saveRoads.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					PrintWriter saveSettlements = new PrintWriter(new FileWriter("Settlements.txt"));
					for (Settlement s : settlements) {
						System.out.print("Saving Settlement " + s.getName() + "... ");
						saveSettlements.println(s.getName() + "," + s.getKind() + "," + s.getPopulation());
						System.out.println("Complete.");
					}
					saveSettlements.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;

		}
		// STEP 6: INSERT CODE HERE
	}

	private void createRoad(String roadName, String myClass, String source, String dest, double roadLength) {
		try {
			Classification roadClassification;
			Settlement roadSource, roadDestination;

			boolean exists = false;

			for (Road r : roads) {
				if (r.getName().equalsIgnoreCase(roadName) && r.getSourceSettlement().getName().equalsIgnoreCase(source) &&
						r.getDestinationSettlement().getName().equalsIgnoreCase(dest)) {
					exists = true;
					break;
				}
			}

			if (!exists) {
				while ((roadClassification = getClassification(myClass)) == null) {
					System.out.println("[map.createRoad] Classification Exception...");
				}

				while ((roadSource = getSettlements(source)) == null) {
					System.out.println("[map.createRoad] Source Settlement Exception...");
				}

				while ((roadDestination = getSettlements(dest)) == null) {
					System.out.println("[map.createRoad] Destination Settlement Exception...");
				}

				Road newRoad = new Road(roadName, roadClassification, roadSource, roadDestination, roadLength);
				addRoad(newRoad);

				System.out.println("[map.createRoad] Road{" + roadName + ", " + roadClassification + ", "
						+ roadSource.getName() + ", " + roadDestination.getName() + ", " + roadLength);

			} else {
				System.out.println("[map.createRoad] Road: " + roadName + " already exists!");
			}
		} catch (Exception e) {
			System.out.println("[map.createRoad] Main Exception...");
			e.printStackTrace();
		}
	}

	private void createSettlement(String name, String type, int population) {
		if (getSettlements(name) == null) {

			SettlementType newType = null;
			while ((newType = checkSettlementType(type)) == null) {
				System.out.println("Options are: \n'village' \n'hamlet'\n'city'\n'town'\nPlease try again...");
			}

			Settlement newSettlement = new Settlement(name, newType, population);

			addSettlement(newSettlement);
		} else {
			System.out.println("[map.createSettlement] Settlement: " + name + " already exists!");
		}
	}

	public Classification getClassification(String name) {
		for (Classification c : Classification.values()) {
			if (c.toString().toLowerCase().equals(name.toLowerCase())) {
				return c;
			}
		}
		return null;
	}

	public SettlementType checkSettlementType(String type) {
		for (SettlementType t : SettlementType.values()) {
			if (t.toString().toLowerCase().equals(type.toLowerCase())) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Not implimented
	 */

	public String toString() {
		String result = "";
		// INSERT CODE HERE
		return result;
	}
}
