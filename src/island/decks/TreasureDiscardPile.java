package island.decks;

import island.cards.Card;

public class TreasureDiscardPile extends DiscardPile<Card> {

	// Instantiate singleton
	private static TreasureDiscardPile treasureDiscardPile = new TreasureDiscardPile();
	
	private TreasureDiscardPile() {
		super();
	}
	
	public static TreasureDiscardPile getInstance() {
		return treasureDiscardPile;
	}
}
