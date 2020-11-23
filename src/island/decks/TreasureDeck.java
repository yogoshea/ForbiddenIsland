package island.decks;

import java.util.ArrayList;
import java.util.List;

import island.cards.Card;
import island.cards.HelicopterLiftCard;
import island.cards.SandbagCard;
import island.cards.TreasureCard;
import island.cards.TreasureDeckCard;
import island.cards.WaterRiseCard;
import island.components.Treasure;

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
		
		// populate deck with necessary cards
		final int cardsPerTreasure = 5;
		final int helicopterCardCount = 3;
		final int sandbagCardCount = 2;
		final int waterRiseCardCount = 3;
		
		for (int i = 0; i < cardsPerTreasure; i++)
			for (Treasure t : Treasure.values())
				this.addCardToDeck(new TreasureCard(t));
		
		for (int i = 0; i < helicopterCardCount; i++)
			this.addCardToDeck(new HelicopterLiftCard());
		
		for (int i = 0; i < sandbagCardCount; i++)
			this.addCardToDeck(new SandbagCard());

		for (int i = 0; i < waterRiseCardCount; i++)
			this.addCardToDeck(new WaterRiseCard());
	}
	
	@Override
	public void refill() {
		List<TreasureDeckCard> temp = new ArrayList<TreasureDeckCard>();
		temp = TreasureDiscardPile.getInstance().removeAllCards();
		for(TreasureDeckCard c : temp) {
			treasureDeck.addCardToDeck(c);
		}
	}
	
	public static TreasureDeck getInstance() {
		return treasureDeck;
	}
	
}
