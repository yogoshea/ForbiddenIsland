package island.players;

import island.components.IslandTile;

public class Navigator extends Player {
	
	private static IslandTile startingTile = IslandTile.GOLD_GATE;

	public Navigator(String name) {
		super(name, startingTile);
		System.out.println(name + " is the Navigator");
	}

	@Override
	public String toString() {
		return super.toString() + " (Navigator)";
	}
}
