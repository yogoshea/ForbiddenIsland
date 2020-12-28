package island.players;

import island.components.IslandTile;

/**
 * Class to represent Navigator role of a player in the game.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public class Navigator extends Player {
	
	/**
	 * Constructor for Navigator instance.
	 * @param String representing name of player.
	 */
	public Navigator(String name) {
		
		// Navigator's starting island tile set to Gold Gate.
		super(name, "Navigator", IslandTile.GOLD_GATE);
	}

}
