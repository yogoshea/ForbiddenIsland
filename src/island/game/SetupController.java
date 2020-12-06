package island.game;

import island.cards.*;
import island.components.IslandBoard;
import island.components.WaterMeter;
import island.decks.FloodDeck;
import island.decks.FloodDiscardPile;
import island.decks.TreasureDeck;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
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
	
	// Instantiate Singleton
	private static SetupController setupController;
	
	private GameModel gameModel;
	private GameView gameView;
	
	private SetupController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
	}

	public static SetupController getInstance(GameModel gameModel, GameView gameView) {
		if (setupController == null) {
			setupController = new SetupController(gameModel, gameView);
		}
		return setupController;
	}
	
	/**
	 * Setup the initial conditions of the game components
	 */
	public void setupGame() {
		List<String> playerNames = gameView.getPlayers();
		startIslandSinking();
		assignPlayerRoles(playerNames);
		handOutInitialTreasureCards();
//		e.g. waterMeter.setLevel(3); // TODO: give user option to make higher for added difficulty
//		players.setInitialPositions(); // TODO: delete?
	}
	
	/**
	 * Begin the sinking of the island
	 */
	private void startIslandSinking() {
		
		// Get component instances from model
		FloodDeck floodDeck = gameModel.getFloodDeck();
		IslandBoard islandBoard = gameModel.getIslandBoard();
		FloodDiscardPile floodDiscardPile = gameModel.getFloodDiscardPile();

		// Iterate over six new Flood Cards
		for (int i = 0; i < 6; i++) {
			
			// Draw FloodCard from deck
			FloodCard newFloodCard = floodDeck.drawCard();

			// Flood corresponding IslandTile on board
			islandBoard.floodOrSinkTile(newFloodCard.getCorrespondingIslandTile());

			// Add card to flood discard pile
			floodDiscardPile.addCard(newFloodCard);
		}
		//TODO: Print out tiles that were flooded (via observer?)
	}
	
	private void assignPlayerRoles(List<String> playerNames) {
		
		List<Player> playersList = gameModel.getGamePlayers().getPlayersList();
		
		// create stack of possible roles players can have
		Stack<String> possibleRoles = new Stack<String>();
		possibleRoles.addAll(Arrays.asList("Diver", "Engineer", "Explorer", 
				"Messenger", "Navigator", "Pilot"));
		
		// randomise stack order
		Collections.shuffle(possibleRoles); 
		
		// iterate over number of players
		for (String playerName : playerNames) {
			
			// TODO: check if player name already given
			
			// instantiate specific player subclasses 
			switch (possibleRoles.pop()) {

				case "Diver":
					playersList.add(new Diver(playerName));
					break;
					
				case "Engineer":
					playersList.add(new Engineer(playerName));
					break;
				
				case "Explorer":
					playersList.add(new Explorer(playerName));
					break;
				
				case "Messenger":
					playersList.add(new Messenger(playerName));
					break;
					
				case "Navigator":
					playersList.add(new Navigator(playerName));
					break;
					
				case "Pilot":
					playersList.add(new Pilot(playerName));
					break;
			}
		}
	}
	
	/**
	 * Initially give players two Treasure Cards
	 */
	private void handOutInitialTreasureCards() {
		
		int cardsDrawnCount;
		final int numberOfCardsPerPlayer = 2;
		TreasureDeckCard drawnCard;
		
		// Get component instances from model
		TreasureDeck treasureDeck = gameModel.getTreasureDeck();
		
		// iterate of players in game
		for (Player p : GamePlayers.getInstance().getPlayersList()) {
			
			cardsDrawnCount = 0;
			do {
				drawnCard = treasureDeck.drawCard();
//				System.out.println("Drawn card: " + drawnCard);
				if (drawnCard instanceof WaterRiseCard) {
					treasureDeck.addCardToDeck(drawnCard); // Put water Rise cards back in deck
				} else {
					cardsDrawnCount++;
					p.receiveTreasureDeckCard(drawnCard);
					// TODO: change to p.drawFromTreasureDeck(2); ??
				}
			} while (cardsDrawnCount < numberOfCardsPerPlayer);
		}
	}
/*	Don't need as level set to 1 in constructor
	private void setWaterLevel() {
		
		final int initialWaterLevel = 5;
		
		gameModel.getWaterMeter();
		
	}
*/
}