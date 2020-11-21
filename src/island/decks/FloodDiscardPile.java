package island.decks;

import island.cards.FloodDeckCard;

public class FloodDiscardPile extends DiscardPile<FloodDeckCard> {
	
	// Instantiate singleton
	private static FloodDiscardPile floodDiscardPile = new FloodDiscardPile();
	
	private FloodDiscardPile() {
		super();
	}
	
	public static FloodDiscardPile getInstance() {
		return floodDiscardPile;
	}

}
