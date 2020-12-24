package island.observers;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.controllers.GameController;
import island.controllers.GameEndings;
import island.players.GamePlayers;
import island.players.Player;
import island.view.GameView;
import island.view.Messages;

/**
 * Observer class to react to IslandTiles that sink with players on them
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class PlayerSunkObserver implements Observer {
	
	// Singleton instance
	private static PlayerSunkObserver playerSunkObserver;

	private GameController gameController;
	private GamePlayers players;
	
	/**
	 * Constructor for PlayerSunkObserver 
	 * @param Reference to GameController
	 * @param Reference to GamePlayers
	 */
	private PlayerSunkObserver(GameController gc, GamePlayers players, GameView gameView) {
		this.gameController = gc;
		this.players = players;
	}
	
	/**
	 * Singleton instance getter method
	 * @param Reference to GameController
	 * @param Reference to GamePlayers
	 * @return Single instance of PlayerSunkObserver class
	 */
	public static PlayerSunkObserver getInstance(GameController gc, GamePlayers players, GameView gameView) {
		if (playerSunkObserver == null) {
			playerSunkObserver = new PlayerSunkObserver(gc, players, gameView);
		}
		return playerSunkObserver;
	}

	/**
	 * Update method called when state of IslandTile changes to sunk
	 */
	@Override
	public void update(Subject subject) {

		// Check if Player on IslandTile
		for (Player player : players) {
			
			if (player.getPawn().getTile().equals((IslandTile) subject)) {
				
				// Check if Player can move to another IslandTile
				if (! gameController.movePlayerToSafety(player)) {
					Messages.showPlayerSunk(player);
					gameController.endGame(GameEndings.PLAYER_SUNK);
				}
			}
		}
	}

	// Singleton reset for JUnit testing
	public static void reset() {
		for (IslandTile tile : IslandBoard.getInstance().getAllTiles())
			tile.detach(playerSunkObserver);
		playerSunkObserver = null;
	}
	
}