import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents a road that is linked to two settlements: source and destination.
 *
 * @author Chris Loftus
 * @version 1.0 (20th February 2016)
 */

public class Settlement {
	private String name;
	private int population;
	private SettlementType kind;
	private ArrayList<Road> roads;

	/**
	 * Constructor to build a settlement
	 *
	 * @param nm The name of the settlement
	 */
	public Settlement(String nm, SettlementType k, int pop) {
		this(); // Means call the other constructor
		name = nm;
		kind = k;
		population = pop;
		roads = new ArrayList<>();
	}

	public Settlement() {
		// INSERT CODE HERE
	}

	/**
	 * The name of the settlement. Note that there is no way to change
	 * the name once created.
	 *
	 * @return The name of the settlement.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The current population
	 *
	 * @return The population
	 */
	public int getPopulation() {
		return population;
	}

	public ArrayList<Road> getRoads() {
		return roads;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRoads(ArrayList<Road> roads) {
		this.roads = roads;
	}

	/**
	 * Change the population size
	 *
	 * @param size The new population size
	 */
	public void setPopulation(int size) {
		this.population = size;
	}

	/**
	 * The kind of settlement, e.g. village, town etc
	 *
	 * @return The kind of settlement
	 */
	public SettlementType getKind() {
		return kind;
	}

	/**
	 * The population has grown or the settlement has been granted a new status (e.g. city status)
	 *
	 * @param kind The new settlement kind
	 */
	public void setKind(SettlementType kind) {
		this.kind = kind;
	}

	/**
	 * Add a new road to the settlement.
	 *
	 * @param road The new road to add.
	 * @throws IllegalArgumentException if the settlement already contains the road
	 */
	public void addRoad(Road road) {
		roads.add(road);
	}

	/**
	 * Returns a ArrayList of Roads that match the given name
	 *
	 * @param name The name of the road to find
	 * @return An ArrayList of Road objects (will be a maximum of two
	 * items for any settlements: e.g. A487 goes from Aber to
	 * Penparcau and from Aber to Bow Street
	 */
	public ArrayList<Road> findRoads(String name) {
		ArrayList<Road> roadsFound = new ArrayList<>();

		// INSERT CODE HERE
		return roadsFound;
	}

	/**
	 * Deletes all the roads attached to this settlement. It will
	 * also detach these roads from the settlements at the other end
	 * of each road
	 */
	public void deleteRoads() {
		// We have to find the settlements at the other end of the
		// road and detach the road from that end too
		// INSERT CODE HERE (SEE delete BELOW)

		// I'll give you this line!
		roads.clear();
	}

	/**
	 * Deletes the given road attached to this settlement. It will
	 * also detach this road from the settlements at the other end
	 * of the road
	 *
	 * @param road The road to delete.
	 * @throws IllegalArgumentException is thrown if the Road is not connected
	 *                                  to this settlement
	 */
	public void removeRoad(Road road) throws IllegalArgumentException {
		roads.remove(road);
	}

	/**
	 * Returns a list of all the roads connected to this settlement
	 *
	 * @return The roads attached to this settlement
	 */
	public ArrayList<Road> getAllRoads() {
		// Notice how we create a separate array list object
		// and return that instead of the roads. This is so
		// that we don't break encapsulation and data hiding.
		// If I returned roads, then the calling code could change
		// change it directly which would be dangerous
		ArrayList<Road> result = new ArrayList<>();
		for (Road rd : roads) {
			result.add(rd);
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Settlement other = (Settlement) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * The state of the settlement including information about
	 * connected roads
	 *
	 * @return The data in the settlement object.
	 */
	@Override
	public String toString() {
		return "Settlement{" + name + ", " + kind + ", " + population + ", " + roads + "}";
	}
}
