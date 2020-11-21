package island.decks;

import island.cards.TreasureDeckCard;

public class TreasureDiscardPile extends DiscardPile<TreasureDeckCard> {

	// Instantiate singleton
	private static TreasureDiscardPile treasureDiscardPile = new TreasureDiscardPile();
	
	private TreasureDiscardPile() {
		super();
	}
	
	public static TreasureDiscardPile getInstance() {
		return treasureDiscardPile;
	}
}
