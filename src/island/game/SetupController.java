package island.game;

import island.cards.*;
import island.components.IslandBoard;
import island.components.IslandTile;
import island.decks.FloodDeck;
import island.decks.FloodDiscardPile;
import island.decks.TreasureDeck;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import island.players.Diver;
import island.players.Engineer;
import island.players.Explorer;
import island.players.GamePlayers;
import island.players.Messenger;
import island.players.Navigator;
import island.players.Pilot;
import island.players.Player;


public class SetupController { 
	
	// Singleton instance
	private static SetupController setupController;
	
	private GameModel gameModel;
	private GameView gameView;
	public static final int intitialNumFloodCards = 6;
	public static final int initialNumTreasureCards = 2;
	
	/**
	 * Constructor for SetupController singleton.
	 * @param Reference to GameModel.
	 * @param Reference to GameView.
	 */
	private SetupController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
	}

	/**
	 * Getter method for singleton instance.
	 * @param Reference to GameModel.
	 * @param Reference to GameView.
	 * @return single instance of SetupController.
	 */
	public static SetupController getInstance(GameModel gameModel, GameView gameView) {
		if (setupController == null) {
			setupController = new SetupController(gameModel, gameView);
		}
		return setupController;
	}
	
	/**
	 * Sets up the initial conditions of the game components.
	 */
	public void setupGame() {
		
//		List<String> playerNames = gameView.getPlayers();
		startIslandSinking();
		assignPlayerRoles(gameView.getPlayers());
		handOutInitialTreasureCards();
		gameModel.getWaterMeter().setLevel(gameView.pickStartingWaterLevel());
	}

	/**
	 * Begins the sinking of the island.
	 */
	private void startIslandSinking() {
		
		// Get component instances from model
		FloodDeck floodDeck = gameModel.getFloodDeck();
		IslandBoard islandBoard = gameModel.getIslandBoard();
		FloodDiscardPile floodDiscardPile = gameModel.getFloodDiscardPile();
		IslandTile tile;
		FloodCard newFloodCard;

		// Iterate over six new Flood Cards
		for (int i = 0; i < intitialNumFloodCards; i++) {
			
			// Draw FloodCard from deck
			newFloodCard = floodDeck.drawCard();
			
			tile = newFloodCard.getUtility();

			// Flood corresponding IslandTile on board
			islandBoard.getTile(tile).setToFlooded();
			
//			gameView.showTileFlooded(tile);

			// Add card to flood discard pile
			floodDiscardPile.addCard(newFloodCard);
		}
		
	}
	
	/**
	 * Assigns player roles at start of game by instantiating randomly assigned 
	 * player classes to new players.
	 * @param List of names of new players of game.
	 */
	private void assignPlayerRoles(List<String> playerNames) {
		
		GamePlayers players = gameModel.getGamePlayers();
		
		// Create stack of possible roles players can have
		Stack<String> possibleRoles = new Stack<String>();
		possibleRoles.addAll(Arrays.asList("Diver", "Engineer", "Explorer", 
				"Messenger", "Navigator", "Pilot"));
		
		// Randomise stack order
		Collections.shuffle(possibleRoles); 
		
		// Iterate over number of players
		for (String playerName : playerNames) {
			
			// TODO: check if player name already given in game view, this could be allowable
			// e.g. Dave the Explorer and Dave the Messenger is okay I suppose
			
			// Instantiate specific player subclasses 
			switch (possibleRoles.pop()) {

				case "Diver":
					players.addPlayer(new Diver(playerName));
					break;
					
				case "Engineer":
					players.addPlayer(new Engineer(playerName));
					break;
				
				case "Explorer":
					players.addPlayer(new Explorer(playerName));
					break;
				
				case "Messenger":
					players.addPlayer(new Messenger(playerName));
					break;
					
				case "Navigator":
					players.addPlayer(new Navigator(playerName));
					break;
					
				case "Pilot":
					players.addPlayer(new Pilot(playerName));
					break;
			}
		}
	}
	
	/**
	 * Initially gives players two Treasure Cards
	 */
	private void handOutInitialTreasureCards() {
		
		int cardsDrawnCount;
		Card<?> drawnCard;
		
		// Get component instances from model
		TreasureDeck treasureDeck = gameModel.getTreasureDeck();
		
		// Iterate of players in game
		for (Player p : gameModel.getGamePlayers()) {
			
			cardsDrawnCount = 0;
			do {
				drawnCard = treasureDeck.drawCard();

				// Check if water rise card has been drawn
				if (drawnCard.getUtility().equals(SpecialCardAbility.WATER_RISE)) {
					treasureDeck.addCard(drawnCard); // Put water Rise cards back in deck
				} else {
					cardsDrawnCount++;
					p.addCard(drawnCard);
				}
			} while (cardsDrawnCount < initialNumTreasureCards);
		}
	}
	
	// Singleton reset for JUnit testing
	public static void reset() {
		setupController = null;
	}

}