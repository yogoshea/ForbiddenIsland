package island.components;

/**
 * Enum class to represent treasures present in game.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public enum Treasure {

	THE_CRYSTAL_OF_FIRE("Crystal of Fire"),
	THE_EARTH_STONE("Earth Stone"),
	THE_OCEANS_CHALICE("Oceans Chalice"),
	THE_STATUE_OF_THE_WIND("Statue of the Wind");
	
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
	
	@Override
	public String toString() {
		return name;
	}
	
}
