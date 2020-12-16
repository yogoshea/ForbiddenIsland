package island.decks;

import java.util.ArrayList;
import java.util.List;

import island.cards.Card;

import java.util.Collections;

/**
 * Abstract class to be extending by game's discard piles;
 * FloodDiscardPile and TreasureDiscardPile.
 * @author Eoghan O'Shea and Robert McCarthy
 * @param <E> Element to be placed in DiscardPile subclass instances.
 * 
 */
public abstract class DiscardPile<E extends Card<?>> {

	// List to store discard pile cards
	private List<E> pile;
	
	/**
	 * Constructor to be called when DiscardPile subclasses instantiated.
	 */
	DiscardPile() {
		pile = new ArrayList<E>();
	}
	
	/**
	 * Getter method for all cards in discard pile.
	 * @return List of Cards instances in discard pile.
	 */
	public List<E> getAllCards() {
		return pile;
	}
	
	/**
	 * Adds card to discard pile.
	 * @param Card instance to be added to discard pile.
	 */
	public void addCard(E card) {
		pile.add(card);
	}
	
	/**
	 * Shuffles order of cards in the discard pile.
	 */
	public void shuffle() {
	    Collections.shuffle(this.pile);
	}
	
	/**
	 * Removes all cards from the discard pile.
	 * @return List of all cards removed from discard pile. 
	 */
	public void removeAllCards() {
//		shuffle();
//		List<E> temp = getAllCards();
	    pile.clear();
//	    return temp;
	}

	
}
