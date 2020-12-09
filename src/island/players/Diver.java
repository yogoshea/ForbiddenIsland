package island.players;

import java.util.List;

import island.components.IslandBoard;
import island.components.IslandTile;

/**
 * Class to represent Diver role of a player in the game
 * @author Eoghan O'Shea and Robert McCarthy
 */
public class Diver extends Player {

	private static IslandTile startingTile = IslandTile.IRON_GATE;
	
	public Diver(String name) {
		super(name, startingTile);
	}

	@Override
	public String toString() {
		return super.toString() + " (Diver)";
	}
	
	/**
	 * Method to determine swimmable locations for Pilot
	 */
	@Override
	public List<IslandTile> getSwimmableTiles(IslandBoard islandBoard) {
		
		IslandTile currentTile = this.getPawn().getTile();
		List<IslandTile> nearestTiles = islandBoard.getAdjacentTiles(currentTile);
		double shortestDistance = 100;
		double checkDistance;
		
		if (nearestTiles.isEmpty()) {
			
			for (IslandTile islandTile : islandBoard.getNonSunkTiles()) {
				
				checkDistance = islandBoard.getDistanceBetweenTiles(currentTile, islandTile);
				if (checkDistance < shortestDistance) {
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
