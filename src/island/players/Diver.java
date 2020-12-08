package island.players;

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
}
