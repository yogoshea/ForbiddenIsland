package island.decks;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Abstract class to be extending by game's discard piles;
 * FloodDiscardPile and TreasureDiscardPile.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 * @param <E>
 */
public abstract class DiscardPile<E> {//TODO: singleton?

	private List<E> pile; //TODO: make stack??
	
	protected DiscardPile() {
		pile = new ArrayList<E>();
	}
	
	public List<E> getAllCards() {
		return pile;
	}
	
	public void addCard(E card) {
		pile.add(card);
	}
	
	public void shuffle() {
	    Collections.shuffle(this.pile);
	}
	
	/**
	 * method to remove and return all cards from the pile 
	 */
	public List<E> removeAllCards() {
		//TODO: check if pile is empty??
		shuffle();
		List<E> temp = new ArrayList<E>(pile);
	    pile.clear();
	    return temp;
	}

	
}
