package island.components;

/**
 * Enum class to represent treasures present in game.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public enum Treasure {

	THE_CRYSTAL_OF_FIRE("The Crystal of Fire"),
	THE_EARTH_STONE("The Earth Stone"),
	THE_OCEANS_CHALICE("The Oceans Chalice"),
	THE_STATUE_OF_THE_WIND("The Statue of the Wind");
	
	private String name;
	
	/**
	 * Private constructor of Treasure Enum values.
	 * @param name associated with Treasure.
	 */
	private Treasure(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method for name of Treasure.
	 * @return String representation of Treasure.
	 */
	public String getName() {
		return name;
	}
	
	// TODO: toString() as well?
	
}
