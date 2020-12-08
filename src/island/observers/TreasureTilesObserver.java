package island.observers;

import java.util.List;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.Treasure;
import island.components.IslandTile.FloodStatus;
import island.game.GameController;
import island.players.GamePlayers;

public class TreasureTilesObserver implements Observer {

	private static TreasureTilesObserver foolsLandingObserver;
	private GameController gameController;
	private IslandBoard islandBoard;
	private GamePlayers players;
	
	private TreasureTilesObserver(GameController gc, IslandBoard islandBoard, GamePlayers players) {
		this.gameController = gc;
		this.islandBoard = islandBoard;
		this.players = players;
	}
	
	/**
	 * @param IslandBoard, GameController
	 * @return single instance of IslandBoardObserver class 
	 */
	public static TreasureTilesObserver getInstance(GameController gc, IslandBoard islandBoard, GamePlayers players) {
		if (foolsLandingObserver == null) {
			foolsLandingObserver = new TreasureTilesObserver(gc, islandBoard, players);
		}
		return foolsLandingObserver;
	}
	
	@Override
	public void update(Subject subject) {
		
		// first check if treasure has been captured already, then check if other tile remaining on board
		IslandTile updatedTile = (IslandTile) subject; // down-cast to IslandTile
		Treasure associatedTreasure = updatedTile.getAssociatedTreasure();
		
		// Check if updated IslandTile has sunk
		if (updatedTile.isSunk()) {
			
			// Check if associated Treasure has already been captured
			if (! players.getCapturedTreasures().contains(associatedTreasure)) {
				
				// Iterate of IslandTiles with Treasure on IslandBoard
				for (IslandTile otherTreasureTile : islandBoard.getTreasureTiles()) {
					
					// Check for IslandTile that holds the same Treasure as the newly sunk IslandTile
					if (otherTreasureTile.getAssociatedTreasure().equals(associatedTreasure)) {
						
						// Check if this IlsandTile has already sunk
						if ((! otherTreasureTile.equals(updatedTile)) && (otherTreasureTile.isSunk())) {
							
							// Invoke GameController method to end the game
							gameController.endGame("Cannot capture all treasures anymore"); // TODO: name specific treasure??
						}
					}
				}
			}
		}
	}
}
//		//Can't use subject.findTileLocation() as subject has no method findTileLocation()???
//		for(int i = 0; i < 4; i++) {
//			int[] pos1 = islandBoard.getTileLocation( pairedTreasureTiles[i][1] ); 
//			int[] pos2 = islandBoard.getInstance().getTileLocation( pairedTreasureTiles[i][2] );
//			List<Treasure> capturedTreasures = players.getCapturedTreasures();
//			boolean alreadyCaptured = capturedTreasures.contains(pairedTreasureTiles[i][1].getAssociatedTreasure());
//			
//			//If both tiles are sunk and associated treasure not captured
//			if( (pos1[0] < 0 && pos2[0] < 0) && !alreadyCaptured) {
//				return true;
//			}
//		}
//		return false;
//
//}
