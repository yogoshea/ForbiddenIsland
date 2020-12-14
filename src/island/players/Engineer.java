package island.players;

import island.components.IslandTile;

/**
 * Class to represent Engineer role of a player in the game.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public class Engineer extends Player {

	/**
	 * Constructor for Engineer instance.
	 * @param String representing name of player.
	 */
	public Engineer(String name) {
		
		/* 
		 * Engineer's starting island tile set to Silver Gate, and the Engineer
		 * can shore up 2 tiles per action.
		 */
		super(name, "Engineer", IslandTile.BRONZE_GATE, 2);
	}
	
}
