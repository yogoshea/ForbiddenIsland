package island.decks;

import island.cards.FloodCard;
import island.components.IslandTile;

/**
 * FloodDeck class is a Deck subclass filled with FloodCard instances.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FloodDeck extends Deck<FloodCard> {
	
	// Singleton instance
	private static FloodDeck floodDeck;
	
	/**
	 * Private constructor for FloodDeck singleton.
	 */
	private FloodDeck(DiscardPile<FloodCard> floodDiscardPile) {

		// Add FloodCard to deck for each IslandTile value
		for (IslandTile it : IslandTile.values()) {
			this.addCard(new FloodCard(it));
		}
		
		super.setCorrespondingDiscardPile(floodDiscardPile);
	}
	
	/**
	 * Getter method for singleton instance.
	 * @return Instance of FloodDeck singleton.
	 */
	public static FloodDeck getInstance(DiscardPile<FloodCard> floodDiscardPile) {
		if (floodDeck == null) {
			floodDeck = new FloodDeck(floodDiscardPile);
		}
		return floodDeck;
	}

	// Singleton reset for JUnit testing
	public static void reset() {
		floodDeck = null;
	}

}