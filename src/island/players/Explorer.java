package island.players;

import island.components.IslandTile;

public class Explorer extends Player {

	private static IslandTile startingTile = IslandTile.COPPER_GATE;

	public Explorer (String name) {
		super(name, startingTile);
	}

	@Override
	public String toString() {
		return super.toString() + " (Explorer)";
	}
}
