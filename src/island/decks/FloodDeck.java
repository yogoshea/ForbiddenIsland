package island.decks;

import island.cards.FloodDeckCard;

/**
 * TreasureDeck class is a deck filled with TreasureCards,
 * HelicopterLiftCards, SandbagCards and WaterRiseCards.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FloodDeck extends Deck<FloodDeckCard> {
	
	// Instantiate singleton
	private static FloodDeck floodDeck = new FloodDeck();
	
	private FloodDeck() {
		super();
		// TODO: add card for each IslandTile
	}
	
	public static FloodDeck getInstance() {
		return floodDeck;
	}

}