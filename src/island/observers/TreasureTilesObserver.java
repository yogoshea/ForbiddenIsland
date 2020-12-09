package island.observers;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.Treasure;
import island.game.GameController;
import island.players.GamePlayers;

public class TreasureTilesObserver implements Observer {

	private static TreasureTilesObserver foolsLandingObserver;
	private GameController gameController;
	private IslandBoard islandBoard;
	private GamePlayers players;
	
	/**
	 * Constructor for TreasureTilesObserver
	 * @param Reference to GameController
	 * @param Reference to IslandBoard
	 * @param Reference to GamePlayers
	 */
	private TreasureTilesObserver(GameController gc, IslandBoard islandBoard, GamePlayers players) {
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
	public static TreasureTilesObserver getInstance(GameController gc, IslandBoard islandBoard, GamePlayers players) {
		if (foolsLandingObserver == null) {
			foolsLandingObserver = new TreasureTilesObserver(gc, islandBoard, players);
		}
		return foolsLandingObserver;
	}
	
	//TODO: simplify/make more readable
	/**
	 * Update method called when state of IslandTile changes to sunk
	 */
	@Override
	public void update(Subject subject) {
		
		// first check if treasure has been captured already, then check if other tile remaining on board
		IslandTile updatedTile = (IslandTile) subject; // down-cast to IslandTile
		Treasure associatedTreasure = updatedTile.getAssociatedTreasure(); // TODO: check for null Treasure or change to NO_TREASURE or try catch exception maybe?
		
		// Check if IslandTile has an associated Treasure
		if(associatedTreasure != null) {
			
			// Check if associated Treasure has already been captured
			if (! players.getCapturedTreasures().contains(associatedTreasure)) {
				
				// Iterate of IslandTiles with Treasure on IslandBoard
				for (IslandTile otherTreasureTile : islandBoard.getTreasureTiles()) {
					
					// Check for IslandTile that holds the same Treasure as the newly sunk IslandTile
					if (otherTreasureTile.getAssociatedTreasure().equals(associatedTreasure)) {
						
						// Check if this IslandTile has already sunk
						if ((! otherTreasureTile.equals(updatedTile)) && (otherTreasureTile.isSunk())) {
							
							// Invoke GameController method to end the game
							gameController.endGame(); // TODO: end game Enum
						}
					}
				}
			}
			
		}
		
	}
}