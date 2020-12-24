package island.observers;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.Treasure;
import island.controllers.GameController;
import island.controllers.GameEndings;
import island.players.GamePlayers;
import island.view.GameView;
import island.view.Messages;

public class TreasureTilesObserver implements Observer {

	private static TreasureTilesObserver treasureTilesObserver;
	private GameController gameController;
	private IslandBoard islandBoard;
	private GamePlayers players;
	
	/**
	 * Constructor for TreasureTilesObserver
	 * @param Reference to GameController
	 * @param Reference to IslandBoard
	 * @param Reference to GamePlayers
	 */
	private TreasureTilesObserver(GameController gc, IslandBoard islandBoard, GamePlayers players, GameView gameView) {
		this.gameController = gc;
		this.islandBoard = islandBoard;
		this.players = players;
	}
	
	/**
	 * Singleton instance getter method
	 * @param Reference to GameController
	 * @param Reference to IslandBoard
	 * @param Reference to GamePlayers
	 * @return single instance of IslandBoardObserver class 
	 */
	public static TreasureTilesObserver getInstance(GameController gc, IslandBoard islandBoard, GamePlayers players, GameView gameView) {
		if (treasureTilesObserver == null) {
			treasureTilesObserver = new TreasureTilesObserver(gc, islandBoard, players, gameView);
		}
		return treasureTilesObserver;
	}
	
	/**
	 * Update method called when state of IslandTile changes to sunk
	 */
	@Override
	public void update(Subject subject) {
		
		// first check if treasure has been captured already, then check if other tile remaining on board
		IslandTile updatedTile = (IslandTile) subject; // down-cast to IslandTile
		Treasure associatedTreasure = updatedTile.getAssociatedTreasure();
			
		// Check if associated Treasure has already been captured
		if (! players.getCapturedTreasures().contains(associatedTreasure)) {
			
			// Iterate of IslandTiles with Treasure on IslandBoard
			for (IslandTile otherTreasureTile : islandBoard.getTreasureTiles()) {
				
				// Check for IslandTile that holds the same Treasure as the newly sunk IslandTile
				if (otherTreasureTile.getAssociatedTreasure().equals(associatedTreasure)) {
					
					// Check if this IslandTile has already sunk
					if ((! otherTreasureTile.equals(updatedTile)) && (otherTreasureTile.isSunk())) {
						
						Messages.showTreasureSunk(otherTreasureTile, updatedTile);
						// Invoke GameController method to end the game
						gameController.endGame(GameEndings.TREASURE_SUNK);
					}
				}
			}
		}
	}
	
	// Singleton reset for JUnit testing
	public static void reset() {
		for (IslandTile tile : IslandBoard.getInstance().getTreasureTiles())
			tile.detach(treasureTilesObserver);
		treasureTilesObserver = null;
	}
}