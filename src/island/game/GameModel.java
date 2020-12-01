package island.game;

import island.cards.*;
import island.components.IslandBoard;
import island.components.WaterMeter;
import island.decks.FloodDeck;
import island.decks.FloodDiscardPile;
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
	private static GameModel gameModel;

	private IslandBoard islandBoard;
	private FloodDeck floodDeck;
	private FloodDiscardPile floodDiscardPile;
	private GamePlayers players;
	private TreasureDeck treasureDeck;
	private WaterMeter waterMeter;
	private boolean gameOver;
	private boolean gameWon;
	
	/**
	 * Game constructor, instantiates all required game components
	 */
	private GameModel() {
		
//		// TODO: delete these, use observers instead
//		gameOver = false;
//		gameWon = false;

		// retrieve game component instances TODO: make sure in correct order prevent race conditions!
		islandBoard = IslandBoard.getInstance();
		floodDeck = FloodDeck.getInstance();
		floodDiscardPile = FloodDiscardPile.getInstance();
		players = GamePlayers.getInstance();
		treasureDeck = TreasureDeck.getInstance();
		waterMeter = WaterMeter.getInstance(); 
	}

	/**
	 * @return single instance of GameModel class
	 */
	public static GameModel getInstance() {
		if (gameModel == null) {
			gameModel = new GameModel();
		}
		return gameModel;
	}
	
//	/**
//	 * Setup the initial conditions of the game components
//	 */
//	public void setupGameComponents(List<String> playerNames) {
//		islandBoard.startSinking();
//		players.assignPlayerRoles(playerNames);
//		treasureDeck.handOutInitialTreasureCards(2); // hand out 2 card to each player
//
////		e.g. waterMeter.setLevel(3); // TODO: give user option to make higher for added difficulty
////		players.setInitialPositions(); // TODO: delete?
//	}
	
	// TODO: getters and setters for Game info
	/**
	 * Island board getter method
	 * @return single instance of Island board
	 */
	public IslandBoard getIslandBoard() {
		return islandBoard;
	}
	
	/**
	 * Flood deck getter method
	 * @return single instance of flood deck
	 */
	public FloodDeck getFloodDeck() {
		return floodDeck;
	}	
	
	/**
	 * Flood discard pile getter method
	 * @return single instance of flood deck
	 */
	public FloodDiscardPile getFloodDiscardPile() {
		return floodDiscardPile;
	}	
	
	/**
	 * Game players getter method
	 * @return single instance of game players
	 */
	public GamePlayers getPlayers() {
		return players;
	}
	
	/**
	 * Treasure deck getter method
	 * @return single instance of treasure deck
	 */
	public TreasureDeck getTreasureDeck() {
		return treasureDeck;
	}
	
	/**
	 * Water meter getter method
	 * @return single instance of water meter
	 */
	public WaterMeter getWaterMeter() {
		return waterMeter;
	}
	
	
//	
//	public void setGameOver() {
//		gameOver = true;
//		// GameView.showEndGameScreen()
//		// TODO: Ask about this!
//		// System.exit(0);
//		// OR
//		// return 0
//	}
	
	public String toString() {
		String gameState = "Info";
		//TODO: Implement -> Print island map, treasures captured, watermeter, player cards (or do cards during turns?) 
		return gameState;
	}
}
