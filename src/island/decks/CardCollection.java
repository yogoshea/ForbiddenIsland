package island.decks;

import java.util.List;

import island.cards.Card;

/**
 * Interface to be implemented by Card storage classes in game.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 * @param <E> Type of Card subclass to be stored by a CardCollection
 */
public interface CardCollection<E extends Card<?>> {
	
	/**
	 * Adds specified card to collection.
	 * @param card instance to add to collection.
	 */
	public void addCard(E card);
	
	/**
	 * Retrieves list of all cards present in collection.
	 * @return List of card elements.
	 */
	public List<E> getAllCards();
	
	/**
	 * Shuffles order of cards in the collection.
	 */
	public void shuffle();

}
