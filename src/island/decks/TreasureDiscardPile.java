package island.decks;

import island.cards.Card;

/**
 * TreasureDiscardPile class is a discard pile to be filled with TreasureCard and
 * SpecialCard instances.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class TreasureDiscardPile extends DiscardPile<Card<?>> {

	// Singleton instance
	private static TreasureDiscardPile treasureDiscardPile;
	
	/**
	 * Private constructor for TreasureDiscardPile singleton.
	 */
	private TreasureDiscardPile() {}
	
	/**
	 * Getter method for singleton instance.
	 * @return single instance of TreasureDiscardPile.
	 */
	public static TreasureDiscardPile getInstance() {
		if (treasureDiscardPile == null) {
			treasureDiscardPile = new TreasureDiscardPile();
		}
		return treasureDiscardPile;
	}
	
	// Singleton reset for JUnit testing
	public void reset() {
		treasureDiscardPile = null;
	}
	
}
