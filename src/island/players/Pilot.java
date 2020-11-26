package island.players;

import island.components.IslandTile;

public class Pilot extends Player {
	
	private static IslandTile startingTile = IslandTile.FOOLS_LANDING;
	
	public Pilot(String name) {
		super(name, startingTile);
		System.out.println(name + " is the Pilot");
	}

	@Override
	public String toString() {
		return super.toString() + " (Pilot)";
	}
}
