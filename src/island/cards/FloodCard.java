package island.cards;

import island.components.IslandTile;

/**
 * Class to represent cards that can be placed in the flood deck.
 * Each FloodCard has an IslandTile associated with it.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FloodCard extends Card<IslandTile> {
	
	/**
	 * Constructor for FloodCard instances.
	 * @param IslandTile associated with newly created FloodCard.
	 */
	public FloodCard(IslandTile islandTile) {
		super(islandTile.getName() + " Flood", islandTile);
	}
	
}
