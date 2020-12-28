package island.cards;

import island.components.Treasure;

/**
 * Class to represent treasure cards. 
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class TreasureCard extends Card<Treasure> {
	
	/**
	 * Constructor for TreasureCard instance.
	 * @param Treasure associated with a given TreasureCard.
	 */
	public TreasureCard(Treasure treasure) {
		super(treasure.getName(), treasure);
	}

}
