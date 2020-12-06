package island.decks;

import java.util.ArrayList;
import java.util.List;

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
	private static FloodDeck floodDeck;
	
	private FloodDeck() {
		super(); // TODO: check if needed
		
		// add FloodCard to deck for each IslandTile
		for (IslandTile it : IslandTile.values()) {
			this.addCardToDeck(new FloodCard(it));
		}
	}
	
	public static FloodDeck getInstance() {
		if (floodDeck == null) {
			floodDeck = new FloodDeck();
		}
		return floodDeck;
	}
	
	@Override
	public void refill() {
		List<FloodCard> temp = new ArrayList<FloodCard>();
		temp = FloodDiscardPile.getInstance().removeAllCards();
		for(FloodCard c : temp) {
			floodDeck.addCardToDeck(c);
		}
	}

}