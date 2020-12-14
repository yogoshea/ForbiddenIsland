package island.players;

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
		
		// Pilot can move to any available island tile if current tile sinks
		return islandBoard.getNonSunkTiles();
	}
}
