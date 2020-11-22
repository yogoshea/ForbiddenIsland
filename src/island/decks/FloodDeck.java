package island.decks;

import island.cards.FloodCard;
import island.components.IslandTile;

/**
 * TreasureDeck class is a deck filled with TreasureCards,
 * HelicopterLiftCards, SandbagCards and WaterRiseCards.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FloodDeck extends Deck<FloodCard> {
	
	// Instantiate singleton
	private static FloodDeck floodDeck = new FloodDeck();
	
	private FloodDeck() {
		super(); // TODO: check if needed
		
		// add FloodCard to deck for each IslandTile
		for (IslandTile it : IslandTile.values()) {
			this.addCardToDeck(new FloodCard(it));
		}
	}
	
	public static FloodDeck getInstance() {
		return floodDeck;
	}

}