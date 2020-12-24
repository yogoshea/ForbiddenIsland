package island.components;

import island.decks.FloodDeck;
import island.decks.FloodDiscardPile;
import island.decks.TreasureDeck;
import island.decks.TreasureDiscardPile;
import island.players.GamePlayers;

/**
 * Game class describes in full the status of the game and 
 * defines getters and setters to be used by GameController
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameModel { 
	
	// Singleton instance
	private static GameModel gameModel;

	// Game components
	private IslandBoard islandBoard;
	private FloodDeck floodDeck;
	private FloodDiscardPile floodDiscardPile;
	private GamePlayers gamePlayers;
	private TreasureDeck treasureDeck;
	private TreasureDiscardPile treasureDiscardPile;
	private WaterMeter waterMeter;
	
	/**
	 * Game constructor, instantiates all required game components
	 */
	private GameModel() {
		
		// Retrieve game component instances
		islandBoard = IslandBoard.getInstance();
		floodDiscardPile = FloodDiscardPile.getInstance();
		floodDeck = FloodDeck.getInstance(floodDiscardPile);
		gamePlayers = GamePlayers.getInstance();
		treasureDiscardPile = TreasureDiscardPile.getInstance();
		treasureDeck = TreasureDeck.getInstance(treasureDiscardPile);		
		waterMeter = WaterMeter.getInstance(); 
	}

	/**
	 * Singleton instance getter method
	 * @return single instance of GameModel class
	 */
	public static GameModel getInstance() {
		if (gameModel == null) {
			gameModel = new GameModel();
		}
		return gameModel;
	}
	
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
	public GamePlayers getGamePlayers() {
		return gamePlayers;
	}
	
	/**
	 * Treasure deck getter method
	 * @return single instance of treasure deck
	 */
	public TreasureDeck getTreasureDeck() {
		return treasureDeck;
	}
	
	/**
	 * Treasure discard pile getter method
	 * @return single instance of treasure deck
	 */
	public TreasureDiscardPile getTreasureDiscardPile() {
		return treasureDiscardPile;
	}
	
	/**
	 * Water meter getter method
	 * @return single instance of water meter
	 */
	public WaterMeter getWaterMeter() {
		return waterMeter;
	}

	// Singleton reset for JUnit testing
	public static void reset() {
		IslandBoard.reset();
		FloodDeck.reset();
		FloodDiscardPile.reset();
		GamePlayers.reset();
		TreasureDeck.reset();
		TreasureDiscardPile.reset();
		WaterMeter.reset();
		gameModel = null;
	}
}
