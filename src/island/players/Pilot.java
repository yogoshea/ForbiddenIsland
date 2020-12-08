package island.players;

import java.util.List;

import island.components.IslandBoard;
import island.components.IslandTile;

public class Pilot extends Player {
	
	private static IslandTile startingTile = IslandTile.FOOLS_LANDING;
	
	public Pilot(String name) {
		super(name, startingTile);
	}

	@Override
	public String toString() {
		return super.toString() + " (Pilot)";
	}
	
	@Override
	public List<IslandTile> getSwimmableTiles(IslandBoard islandBoard) {
		return islandBoard.getNonSunkTiles();
	}
}
