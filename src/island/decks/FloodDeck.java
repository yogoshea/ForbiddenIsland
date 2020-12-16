package island.decks;

import island.cards.FloodCard;
import island.components.IslandTile;

/**
 * FloodDeck class is a Deck subclass filled with FloodCard instances.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FloodDeck extends Deck<FloodCard> {
	
	// Instantiate singleton
	private static FloodDeck floodDeck;
	
	/**
	 * Private constructor for FloodDeck singleton.
	 */
	private FloodDeck() {

		// Add FloodCard to deck for each IslandTile value
		for (IslandTile it : IslandTile.values()) {
			this.addCard(new FloodCard(it));
		}
	}
	
	/**
	 * Getter method for singleton instance.
	 * @return Instance of FloodDeck singleton.
	 */
	public static FloodDeck getInstance() {
		if (floodDeck == null) {
			floodDeck = new FloodDeck();
		}
		return floodDeck;
	}
	
	@Override
	public void refill() {
//		List<FloodCard> temp = new ArrayList<FloodCard>();
//		temp = FloodDiscardPile.getInstance().removeAllCards();
		FloodDiscardPile floodDiscardPile = FloodDiscardPile.getInstance();
		for (FloodCard fc : floodDiscardPile.getAllCards()) {
			floodDeck.addCard(fc);
		}
		floodDiscardPile.removeAllCards();
	}

	// Singleton reset for JUnit testing
	public void reset() {
		floodDeck = null;
	}

}