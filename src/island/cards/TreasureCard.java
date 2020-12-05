package island.cards;

import island.components.Treasure;

/**
 * Class to represent treasure cards. 
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class TreasureCard extends Card implements TreasureDeckCard {
	
	private Treasure associatedTreasure;

	// Associated Treasure must be set in constructor
	public TreasureCard(Treasure treasure) {
//		super();
		this.associatedTreasure = treasure;
	}

	public Treasure getAssociatedTreasure() {
		return associatedTreasure;
	}
	
	public String toString() {
		return associatedTreasure.toString() + " Card";
	}

	
//	THE_CRYSTAL_OF_FIRE,
//	THE_EARTH_STONE,
//	THE_OCEANS_CHALICE,
//	THE_STATUE_OF_THE_WIND;

}
