package island.players;

import java.util.List;

import island.components.IslandBoard;
import island.components.IslandTile;

/**
 * Class to represent Diver role of a player in the game.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public class Diver extends Player {

	/**
	 * Constructor for Diver instance.
	 * @param String representing name of player.
	 */
	public Diver(String name) {
		
		// Diver's starting island tile set to Iron Gate.
		super(name, "Diver", IslandTile.IRON_GATE);
	}

	@Override
	public List<IslandTile> getSwimmableTiles(IslandBoard islandBoard) {
		
		// Get tiles adjacent to player
		IslandTile currentTile = this.getPawn().getTile();
		List<IslandTile> nearestTiles = islandBoard.getAdjacentTiles(currentTile);
		
		// Set initial shortest distance to high value
		double shortestDistance = 100; 
		double checkDistance;
		
		// Check if no adjacent tiles are available to move to
		if (nearestTiles.isEmpty()) {
			
			// Iterate over all available island tiles
			for (IslandTile islandTile : islandBoard.getNonSunkTiles()) {
				
				// Check the distance between current tile and other tiles
				checkDistance = islandBoard.calcDistanceBetweenTiles(currentTile, islandTile);
				if (checkDistance < shortestDistance) {
					
					// Update list of nearest tiles
					shortestDistance  = checkDistance;
					nearestTiles.clear();
					nearestTiles.add(islandTile);
					
				} else if (checkDistance == shortestDistance) {
					nearestTiles.add(islandTile);
				}
			}
		}
		return nearestTiles;
	}
}
