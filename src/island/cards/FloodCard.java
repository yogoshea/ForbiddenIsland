package island.cards;

import island.components.IslandTile;

/**
 * Class to represent cards that can be placed in the flood deck.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FloodCard extends Card {
	
	private IslandTile correspondingIslandTile;

	// IslandTile must be passed into constructor
	public FloodCard(IslandTile tile) {
//		super();
		this.correspondingIslandTile = tile;
	}
	
	public IslandTile getCorrespondingIslandTile() {
		return correspondingIslandTile;
	}
	
	@Override
	public String toString() {
		return "Flood Card ~~> " + correspondingIslandTile;
	}

}
