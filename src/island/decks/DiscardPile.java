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
public abstract class DiscardPile<E extends Card<?>> implements CardCollection<E> {

	// List to store discard pile cards
	private List<E> pile;
	
	/**
	 * Constructor to be called when DiscardPile subclasses instantiated.
	 */
	DiscardPile() {
		pile = new ArrayList<E>();
	}
	
	@Override
	public void addCard(E card) {
		pile.add(card);
	}	
	
	@Override
	public List<E> getAllCards() {
		return pile;
	}
	
	@Override
	public void shuffle() {
		Collections.shuffle(pile);
	}
	
	/**
	 * Removes all cards from the discard pile.
	 * @return List of all cards removed from discard pile. 
	 */
	public void removeAllCards() {
	    pile.clear();
	}
	
}
