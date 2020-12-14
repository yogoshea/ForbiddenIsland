package island.players;

import java.util.ArrayList;
import java.util.List;

import island.components.IslandBoard;
import island.components.IslandTile;

/**
 * Class to represent Explorer role of a player in the game.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public class Explorer extends Player {

	/**
	 * Constructor for Explorer instance.
	 * @param String representing name of player.
	 */
	public Explorer (String name) {
		
		// Explorer's starting island tile set to Copper Gate.
		super(name, "Explorer", IslandTile.COPPER_GATE);
	}

	@Override
	public List<IslandTile> getSwimmableTiles(IslandBoard islandBoard) {
		
		// Retrieve player's IslandTile
		IslandTile currentTile = this.getPawn().getTile();
		List<IslandTile> nearestTiles = new ArrayList<IslandTile>();
		double checkDistance;
		
		// Iterate over all island tiles that are not sun
		for (IslandTile islandTile : islandBoard.getNonSunkTiles()) {
			
			// Determine distance between tiles from IslandBoard class method
			checkDistance = islandBoard.calcDistanceBetweenTiles(currentTile, islandTile);
			
			// Check if sure distance is less than or equal to sqrt(2), this includes diagonal tiles
			if (checkDistance <= Math.sqrt(2)) {
				nearestTiles.add(islandTile); // Add tiles to list of swimmable tiles
			}
		}
		return nearestTiles;
	}

}
