package island.players;

import java.util.List;

import island.components.IslandBoard;
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
	
	@Override
	public List<IslandTile> getSwimmableTiles(IslandBoard islandBoard) {
		return islandBoard.getNonSunkTiles(); // TODO: change to diagonal tiles!
	}
}
