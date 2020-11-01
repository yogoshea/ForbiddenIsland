package island.players;

import island.board.IslandTile;

public class Engineer extends Player {

	/**
	 * Constructor for Engineer
	 */
	public Engineer(String name) {
		super(name);
		System.out.println(name + " is the Engineer");
//		startingTile = new IslandTile("Bronze Gate");
	}
	
}
