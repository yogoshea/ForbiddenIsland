package island.game;

import island.cards.*;
import island.components.IslandBoard;
import island.components.WaterMeter;
import island.decks.TreasureDeck;

import java.util.List;
import java.util.Scanner;

import island.players.GamePlayers;
import island.players.Player;

//TODO: Make game singleton?

/**
 * Game class describes in full the status of the game and 
 * defines getters and setters to be used by GameController
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameModel { 
	
	// Instantiate Singleton
	private static GameModel gameModel = new GameModel();

	private IslandBoard islandBoard;
	private GamePlayers players;
	private TreasureDeck treasureDeck;
	private WaterMeter waterMeter;
	private boolean gameOver;
	private boolean gameWon;
	
	/**
	 * Game constructor, instantiates all required game components
	 */
	private GameModel() {
		
//		// TODO: delete these
//		gameOver = false;
//		gameWon = false;

		// retrieve game component instances
		islandBoard = IslandBoard.getInstance();
		players = GamePlayers.getInstance();
		treasureDeck = TreasureDeck.getInstance();
		waterMeter = WaterMeter.getInstance(); 
	}

	/**
	 * @return single instance of GameModel class
	 */
	public static GameModel getInstance() {
		return gameModel;
	}
	
	/**
	 * Setup the initial conditions of the game components
	 */
	public void setupGameComponents(List<String> playerNames) {
		islandBoard.startSinking();
		players.assignPlayerRoles(playerNames);
		treasureDeck.handOutInitialTreasureCards(2); // hand out 2 card to each player

//		e.g. waterMeter.setLevel(3); // TODO: give user option to make higher for added difficulty
//		players.setInitialPositions(); // TODO: delete?
	}
	
	// TODO: getters and setters for Game info
	
	/**
	 * Game players getter method
	 * @return single instance of game players
	 */
	public GamePlayers getPlayers() {
		return players;
	}
	
	/**
	 * Island board getter method
	 * @return single instance of Island board
	 */
	public IslandBoard getIslandBoard() {
		return islandBoard;
	}
	
	
	
	public void setGameOver() {
		gameOver = true;
		// GameView.showEndGameScreen()
		// TODO: Ask about this!
		// System.exit(0);
		// OR
		// return 0
	}
	
	public String toString() {
		String gameState = "Info";
		//TODO: Implement -> Print island map, treasures captured, watermeter, player cards (or do cards during turns?) 
		return gameState;
	}
}
