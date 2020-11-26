package island.players;

import island.components.IslandTile;

public class Diver extends Player {

	private static IslandTile startingTile = IslandTile.IRON_GATE;
	
	public Diver(String name) {
		super(name, startingTile);
		System.out.println(name + " is the Diver");
	}

	@Override
	public String toString() {
		return super.toString() + " (Diver)";
	}
}
