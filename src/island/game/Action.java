package island.game;

/**
 * Enum to represent all possible actions a player can take on
 * a given turn.
 * @author Eoghan O'Shea and Robert McCaerthy
 *
 */
public enum Action {
	
	MOVE("Move"),
	SHORE_UP("Shore Up"),
	GIVE_TREASURE_CARD("Give Treasure Card"),
	CAPTURE_TREASURE("Capture Treasure"),
	NONE("None");
	
	private String name;
	
	/**
	 * Private constructor of Action Enum.
	 * @param String representation of particular action.
	 */
	private Action(String name) {
		this.name = name;
	}

	/**
	 * Getter method for name of action.
	 * @return String representation of action.
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}