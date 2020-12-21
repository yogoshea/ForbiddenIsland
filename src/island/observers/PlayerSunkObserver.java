package island.observers;

import island.components.IslandTile;
import island.game.GameController;
import island.game.GameEndings;
import island.players.GamePlayers;
import island.players.Player;

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
	private PlayerSunkObserver(GameController gc, GamePlayers players) {
		this.gameController = gc;
		this.players = players;
	}
	
	/**
	 * Singleton instance getter method
	 * @param Reference to GameController
	 * @param Reference to GamePlayers
	 * @return Single instance of PlayerSunkObserver class
	 */
	public static PlayerSunkObserver getInstance(GameController gc, GamePlayers players) {
		if (playerSunkObserver == null) {
			playerSunkObserver = new PlayerSunkObserver(gc, players);
		}
		return playerSunkObserver;
	}

	/**
	 * Update method called when state of IslandTile changes to sunk
	 */
	@Override
	public void update(Subject subject) {

		IslandTile updatedTile = (IslandTile) subject; // down-cast to IslandTile TODO: is this needed?
		
		// Check if Player on IslandTile
		for (Player player : players) {
			
			if (player.getPawn().getTile().equals(updatedTile)) {
				
				// Check if Player can move to another IslandTile
				if (! gameController.movePlayerToSafety(player)) {

					gameController.endGame(GameEndings.PLAYER_SUNK);
				}
			}
		}
	}

	// Singleton reset for JUnit testing
	public void reset() {
		playerSunkObserver = null;
	}
	
}