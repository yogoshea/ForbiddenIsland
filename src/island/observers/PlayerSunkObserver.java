package island.observers;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.Pawn;
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
//		this.islandBoard = islandBoard;
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
		Pawn pawn;
		
		// Check if Player on IslandTile
		for (Player player : players) {
			
			pawn = player.getPawn();
			if (pawn.getTile().equals(updatedTile)) {
				
				// Check if Player can move to another IslandTile
				if (! gameController.movePlayerToSafety(pawn)) {
					
					gameController.endGame(); //TODO: end game enum
				}
			}
		}
	}

}