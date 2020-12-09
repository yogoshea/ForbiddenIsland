package island.players;

import island.components.IslandTile;

/**
 * Class to represent Engineer role of a player in the game
 * @author Eoghan O'Shea and Robert McCarthy
 */
public class Engineer extends Player {

	private final static IslandTile startingTile = IslandTile.BRONZE_GATE;

	/**
	 * Constructor for Engineer class
	 */
	public Engineer(String name) {
		super(name, startingTile);
		this.setShoreUpQuantity(2);
	}
	
	@Override
	public String toString() {
		return super.toString() + " (Engineer)";
	}
}
