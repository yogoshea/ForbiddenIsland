package island.decks;

import java.util.Collections;
import java.util.Stack;

import island.cards.Card;

/**
 * Abstract class to be implemented by decks used in game; TreasureDeck, FloodDeck.
 * @author Eoghan O'Shea and Robert McCarthy
 * @param <E>
 * @param <E> Elements to be placed in Deck subclass instances.
 * 
 */
public abstract class Deck<E extends Card<?>> {
	
	// Stack to store deck cards
	private Stack<E> deck;
	
	/**
	 * Constructor to be called when Deck subclasses instantiated.
	 */
	Deck() {
		deck = new Stack<E>();
	}
	
	/**
	 * Shuffles order of cards in the deck.
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}
	
	/**
	 * Draws card from the deck.
	 * @return Card from top of deck.
	 */
	public E drawCard() {
		
		// Refill deck if empty
		if (deck.empty()) {
			refill();
		}
		return deck.pop();
	}
	
	/**
	 * Retrieves all cards present in deck.
	 * @return Stack of all deck elements.
	 */
	public Stack<E> getAllCards() {
		return deck;
	}
	
	/**
	 * Adds specified card to deck.
	 * @param Element to add to deck.
	 */
	public void addCard(E card) {
		deck.add(card);
		this.shuffle(); // shuffle deck whenever new element added
	}
	
	/**
	 * Refills an empty deck with cards from the corresponding discard pile.
	 */
	public abstract void refill();

}
