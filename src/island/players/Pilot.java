package island.players;

import java.util.ArrayList;
import java.util.List;

import island.components.IslandBoard;
import island.components.IslandTile;

/**
 * Class to represent Navigator role of a player in the game.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public class Pilot extends Player {
	
	/**
	 * Constructor for Pilot instance.
	 * @param String representing name of player.
	 */
	public Pilot(String name) {
		
		// Pilot's starting island tile set to Fools Landing.
		super(name, "Pilot", IslandTile.FOOLS_LANDING);
	}

	@Override
	public List<IslandTile> getSwimmableTiles(IslandBoard islandBoard) {

		// Retrieve player's IslandTile
		IslandTile currentTile = this.getPawn().getTile();
		List<IslandTile> allAvailableTiles = new ArrayList<IslandTile>();
		
		// Pilot can move to any available island tile if current tile sinks
		for (IslandTile tile : islandBoard.getNonSunkTiles()) {
			if (! tile.equals(currentTile)) {
				allAvailableTiles.add(tile);
			}
		}
		return allAvailableTiles;
	}
}
