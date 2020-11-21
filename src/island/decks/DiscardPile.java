package island.decks;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to be extending by game's discard piles;
 * FloodDiscardPile and TreasureDiscardPile.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 * @param <E>
 */
public abstract class DiscardPile<E> {

	private List<E> pile;
	
	protected DiscardPile() {
		pile = new ArrayList<E>();
	}
	
	public List<E> getAllCards() {
		return pile;
	}
	
	public void addCard(E card) {
		pile.add(card);
	}
	
}
