package island.decks;

import island.cards.FloodCard;

/**
 * FloodDiscardPile class is a DiscardPile subclass to be filled with FloodCard instances.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FloodDiscardPile extends DiscardPile<FloodCard> {
	
	// Singleton instance
	private static FloodDiscardPile floodDiscardPile;
	
	/**
	 * Private constructor of FloodDiscardPile singleton.
	 */
	private FloodDiscardPile() {} // Start with empty discard pile.
	
	/**
	 * Getter method for singleton instance.
	 * @return Single instance of FloodDiscardPile.
	 */
	public static FloodDiscardPile getInstance() {
		if (floodDiscardPile == null) {
			floodDiscardPile = new FloodDiscardPile();
		}
		return floodDiscardPile;
	}

	// Singleton reset for JUnit testing
	public static void reset() {
		floodDiscardPile = null;
	}

}
