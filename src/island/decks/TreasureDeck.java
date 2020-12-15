package island.decks;

import island.cards.Card;
import island.cards.SpecialCard;
import island.cards.SpecialCardAbility;
import island.cards.TreasureCard;
import island.components.Treasure;

/**
 * TreasureDeck class is a deck filled with TreasureCard and
 * SpecialCard instances.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class TreasureDeck extends Deck<Card> {
	
	// Instantiate singleton
	private static TreasureDeck treasureDeck = new TreasureDeck();
	
	/**
	 * Private constructor for TreasureDeck singleton.
	 */
	private TreasureDeck() {
		
		// populate deck with necessary cards
		final int cardsPerTreasure = 5;
		final int helicopterLiftCardCount = 3;
		final int sandbagCardCount = 2;
		final int waterRiseCardCount = 3;
		
		for (int i = 0; i < cardsPerTreasure; i++)
			for (Treasure t : Treasure.values())
				this.addCardToDeck(new TreasureCard(t));
		
		for (int i = 0; i < helicopterLiftCardCount; i++)
			this.addCardToDeck(new SpecialCard(SpecialCardAbility.HELICOPTER_LIFT));
		
		for (int i = 0; i < sandbagCardCount; i++)
			this.addCardToDeck(new SpecialCard(SpecialCardAbility.SANDBAG));

		for (int i = 0; i < waterRiseCardCount; i++)
			this.addCardToDeck(new SpecialCard(SpecialCardAbility.WATER_RISE));
	}
	
	@Override
	public void refill() {
//		List<Card> temp = new ArrayList<Card>();
//		temp = TreasureDiscardPile.getInstance().removeAllCards();
		for(Card<?> c : TreasureDiscardPile.getInstance().removeAllCards()) {
			treasureDeck.addCardToDeck(c);
		}
	}
	
	/**
	 * Getter method for singleton instance.
	 * @return single instance of TreasureDeck.
	 */
	public static TreasureDeck getInstance() {
		return treasureDeck;
	}
	
}
