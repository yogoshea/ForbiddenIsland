package island.cards;

/**
 * Abstract class to be extended by different card types used throughout game.
 * @author Eoghan O'Shea and Robert McCarthy
 * @param <T> type associated with a specific card implementation.
 *
 */
public abstract class Card<T> {
	
	// Card attributes
	private final String name;
	private final T utility;
	
	/**
	 * Constructor called by subclasses when instantiating cards.
	 * @param String to set card name as.
	 * @param object value to be associated with card.
	 */
	Card(String typeName, T utility) {
		this.name = typeName + " Card";
		this.utility = utility;
	}
	
	/**
	 * Getter method for name of card.
	 * @return String value of card name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter method for utility of a given card instance.
	 * @return value associated with card. 
	 */
	public T getUtility() {
		return utility;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
