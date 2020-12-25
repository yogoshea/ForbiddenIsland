package island.decks;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import island.cards.Card;

/**
 * Abstract class to be implemented by decks used in game; TreasureDeck, FloodDeck.
 * @author Eoghan O'Shea and Robert McCarthy
 * @param <E>
 * @param <E> Elements to be placed in Deck subclass instances.
 * 
 */
public abstract class Deck<E extends Card<?>> implements CardCollection<E> {
	
	// Stack to store deck cards
	private Stack<E> deck;
	//The decks corresponding discard pile
	private DiscardPile<E> correspondingDiscardPile;
	
	/**
	 * Constructor to be called when Deck subclasses instantiated.
	 */
	Deck() {
		deck = new Stack<E>();
	}
	
	@Override
	public void addCard(E card) {
		deck.add(card);
		this.shuffle(); // shuffle deck whenever new element added
	}	
	
	@Override
	public List<E> getAllCards() {
		return deck;
	}
	
	@Override
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
	 * Refills an empty deck with cards from the corresponding discard pile.
	 */
	public void refill() {
		
		for (E c : correspondingDiscardPile.getAllCards()) {
			this.addCard(c);
		}
		correspondingDiscardPile.removeAllCards();
		this.shuffle();
	}
	
	public void setCorrespondingDiscardPile(DiscardPile<E> discardPile) {
		this.correspondingDiscardPile = discardPile;
	}

}
