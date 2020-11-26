package island.players;

import island.components.IslandTile;

public class Messenger extends Player {

	private static IslandTile startingTile = IslandTile.SILVER_GATE;

	public Messenger(String name) {
		super(name, startingTile);
		System.out.println(name + " is the Messenger");
	}

	@Override
	public String toString() {
		return super.toString() + " (Messenger)";
	}
}
