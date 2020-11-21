package island.decks;

import island.cards.FloodCard;

public class FloodDiscardPile extends DiscardPile<FloodCard> {
	
	// Instantiate singleton
	private static FloodDiscardPile floodDiscardPile = new FloodDiscardPile();
	
	private FloodDiscardPile() {
		super();
	}
	
	public static FloodDiscardPile getInstance() {
		return floodDiscardPile;
	}

}
