package island.observers;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.WaterMeter;
import island.game.GameController;

/**
 * Observer class to react to island tiles sinking
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class IslandTileObserver implements Observer {
	
	private static IslandTileObserver islandTileObserver;
	private IslandBoard islandBoard;
	private GameController gameController;
	
	private IslandTileObserver(IslandBoard islandBoard, GameController gc) {
		this.islandBoard = islandBoard;
		this.gameController = gc;
	}
	
	/**
	 * @param IslandBoard, GameController
	 * @return single instance of IslandBoardObserver class 
	 */
	public static IslandTileObserver getInstance(IslandBoard islandBoard, GameController gc) {
	if (islandTileObserver == null) {
		islandTileObserver = new IslandTileObserver(islandBoard, gc);
	}
	return islandTileObserver;
}
	
	/**
	 * Update method called when state of IslandTile changes
	 */
	@Override
	public void update(Subject updatedIslandTile) {
		
		updatedIslandTile = (IslandTile) updatedIslandTile; // Cast to IslandTile TODO: check instance of IslandTile ??
		
		if (updatedIslandTile.equals(IslandTile.FOOLS_LANDING)) {
			
		}
		
	}

}