package island.game;

import island.cards.*;
import island.components.IslandBoard;
import island.components.WaterMeter;
import island.decks.TreasureDeck;

import java.util.Scanner;

import island.players.GamePlayers;
import island.players.Player;

/**
 * Game class describes in full the status of the game and 
 * acts as Facade for client code to interact with game
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Game { // TODO: rename to GameFacade?
	
	private Scanner userInput;
	private IslandBoard islandBoard;
	private GamePlayers players;
	private TreasureDeck treasureDeck;
	private WaterMeter waterMeter;
	private boolean gameOver;
	private boolean gameWon;
	
	/**
	 * Game constructor, instantiates all required game components
	 */
	public Game() {
		
		gameOver = false;
		gameWon = false;

		// create scanner to read input from players
		userInput = new Scanner(System.in);
		
		// initialise game components
		islandBoard = IslandBoard.getInstance();
		islandBoard.startSinking();
		players = GamePlayers.getInstance();
		players.addPlayers(userInput);
		treasureDeck = TreasureDeck.getInstance();
		handOutTreasureCards();
		waterMeter = WaterMeter.getInstance(); 
//		e.g. waterMeter.setLevel(3); // TODO: give user option to make higher for added difficulty
		players.setInitialPositions();
	}
	
	public void playGame() {
		// TODO: control the flow of game in here...
		
		// Iterate over each  Player to take turns (Randomise order?)
		while(!gameOver && !gameWon) {
			for (Player p : players.getPlayersList()) {
				if(!gameOver && !gameWon) {
					System.out.println("It is "+p.toString()+"s turn");
					p.takeTurn(userInput);
					//TODO: How to end game if game over happens mid turn???? 
				}
			}
		}
		
		if(gameWon) {
			//TODO: Implement game win
		} else {
			//TODO: Implement game loss
		}
		
	}
	
	
	/**
	 * method to initially give players cards from the Treasure Deck
	 */
	private void handOutTreasureCards() {
		// TODO: maybe move this into separate class

		int cardsDrawnCount;
		final int numberOfCardsPerPlayer = 2;
		TreasureDeckCard drawnCard;
		
		// iterate of players in game
		for (Player p : players.getPlayersList()) {
			
			cardsDrawnCount = 0;
			
			do {
				drawnCard = treasureDeck.drawCard();
//				System.out.println("Drawn card: " + drawnCard);
				if (drawnCard instanceof WaterRiseCard) {
					treasureDeck.addCardToDeck(drawnCard);
				} else {
					cardsDrawnCount++;
					p.receiveTreasureDeckCard(drawnCard);
					// TODO: change to p.drawFromTreasureDeck(2);
				}
			} while (cardsDrawnCount < numberOfCardsPerPlayer);
		}
//		System.out.println("Finished handing out cards!");
	}
	
	// TODO: getters and setters for Game info
	public void setGameOver() {
		gameOver = true;
	}
}
