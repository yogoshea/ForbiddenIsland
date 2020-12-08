package island.observers;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.game.GameController;
import island.players.GamePlayers;
import island.players.Player;

public class PlayerSunkObserver implements Observer {
	
	private static PlayerSunkObserver playerSunkObserver;
	private GameController gameController;
	private IslandBoard islandBoard;
	private GamePlayers players;
	
	private PlayerSunkObserver(GameController gc, IslandBoard islandBoard, GamePlayers players) {
		this.gameController = gc;
		this.islandBoard = islandBoard;
		this.players = players;
	}
	
	public static PlayerSunkObserver getInstance(GameController gc, IslandBoard islandBoard, GamePlayers players) {
		if (playerSunkObserver == null) {
			playerSunkObserver = new PlayerSunkObserver(gc, islandBoard, players);
		}
		return playerSunkObserver;
	}

	@Override
	public void update(Subject subject) {

		IslandTile updatedTile = (IslandTile) subject; // down-cast to IslandTile

		// Check if updated IslandTile has sunk
		if (updatedTile.isSunk()) {
			
			// Check if Player on IslandTile
			for (IslandTile playerTile : islandBoard.getPawnLocations().values()) {
				
				if (playerTile.equals(updatedTile)) {
					
					// Retrieve Player on IslandTile
					Pawn playerPawn = islandBoard.getPawnOnTile(updatedTile);
					Player playerOnTile = 
							
					// Check if Player can move to another IslandTile
					if (! gameController.movePlayerToSafety(playerOnTile, updatedTile)) {
						
						gameController.endGame("Player has sunk :(");
					}
//					Pawn playerPawn = islandBoard.getdd
					
				}
				
			}
			
			
		}
		
	}

}

///*
// * Method to check if game is over due to player being on a sunken tile
// * Should player be an observer here? check via player?
// */
//public static boolean checkPlayerTiles() {
//	for(Player p : GamePlayers.getInstance().getPlayersList()) {
//		int[] pos = IslandBoard.getInstance().getTileLocation( p.getCurrentTile() );
//		if(pos[0] < 0) {
//			return true;				
//		}
//	}
//	return false;
//}
