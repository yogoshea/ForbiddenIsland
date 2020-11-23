package island.decks;

import java.util.Collections;
import java.util.Stack;

import island.cards.Card;

/**
 * Abstract class to be implemented by decks used in game;
 * TreasureDeck, FloodDeck
 * @author Eoghan O'Shea and Robert McCarthy
 *
 * @param <E>
 */
public abstract class Deck<E> {
	
	private Stack<E> deck;
	// private DiscardPile<E> associatedDiscardPile;??
	
	// constructor accessible to subclasses
	protected Deck() {
		deck = new Stack<E>();
	}
	
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	public E drawCard() {
		//TODO refill deck as soon as it empties??
		if (deck.empty()) {
			refill();
		}
		return deck.pop();
	}
	
	public Stack<E> getAllCards() {
		return deck;
	}
	
	public void addCardToDeck(E card) {
		deck.add(card);
		this.shuffle(); // shuffle deck whenever new card added
	}
	
	//TODO Polymorphism? - implement this method here (rather than separate subclasses) using an associatedDiscardPile??
	//Could be a bit tricky to do?
	public abstract void refill();

}
