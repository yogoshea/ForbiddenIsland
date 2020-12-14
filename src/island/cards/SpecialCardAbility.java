package island.cards;

/**
 * Enum class to represent possible special cards abilities that can be
 * used throughout the game. 
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public enum SpecialCardAbility {

	HELICOPTER_LIFT("Helicopter Lift"),
	SANDBAG("Sandbag"),
	WATER_RISE("Water Rise");
	
	private final String name;

	/**
	 * Private constructor for SpecialCardAbility
	 * @param name describing the card ability
	 */
	private SpecialCardAbility(String name) {
		this.name = name;
	}
	
	/**
	 * Getter method for name of ability.
	 * @return String representation of ability name.
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
