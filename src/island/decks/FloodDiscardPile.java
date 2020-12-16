package island.decks;

import island.cards.FloodCard;

/**
 * FloodDiscardPile class is a DiscardPile subclass to be filled with FloodCard instances.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FloodDiscardPile extends DiscardPile<FloodCard> {
	
	// Instantiate singleton
	private static FloodDiscardPile floodDiscardPile;
	
	/**
	 * Private constructor of FloodDiscardPile singleton.
	 */
	private FloodDiscardPile() {}
	
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
	public void reset() {
		floodDiscardPile = null;
	}

}
