package island.decks;

import island.cards.TreasureDeckCard;

/**
 * TreasureDeck class is a deck filled with TreasureCards,
 * HelicopterLiftCards, SandbagCards and WaterRiseCards.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class TreasureDeck extends Deck<TreasureDeckCard> {
	
	// Instantiate singleton
	private static TreasureDeck treasureDeck = new TreasureDeck();
	
	private TreasureDeck() {
		super();
	}
	
	public static TreasureDeck getInstance() {
		return treasureDeck;
	}

}
