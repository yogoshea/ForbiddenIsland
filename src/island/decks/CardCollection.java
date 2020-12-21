package island.decks;

import java.util.List;

import island.cards.Card;

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
