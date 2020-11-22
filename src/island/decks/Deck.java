package island.decks;

import java.util.Collections;
import java.util.Stack;

/**
 * Abstract class to be implemented by decks used in game;
 * TreasureDeck, FloodDeck
 * @author Eoghan O'Shea and Robert McCarthy
 *
 * @param <E>
 */
public abstract class Deck<E> {
	
	private Stack<E> deck;
	
	// constructor accessible to subclasses
	protected Deck() {
		deck = new Stack<E>();
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public E drawCard() {
		return deck.pop();
	}
	
	public Stack<E> getAllCards() {
		return deck;
	}
	
	public void addCardToDeck(E card) {
		deck.add(card);
		this.shuffle(); // shuffle deck whenever new card added
	}

}
