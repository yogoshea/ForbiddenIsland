package island.game;

import java.util.ArrayList;
import java.util.List;

import island.components.WaterMeter;
import island.players.Player;

/**
 * Controls the flow of the gameplay and obtains player choices
 * through GameView
 * @author Eoghan O'Shea
 *
 */
public class GameController {
	
	// Instantiate singleton
	private static GameController gameController;
	
	private GameView gameView;
	private GameModel gameModel;
	private SetupController setupController;
	private ActionController actionController;
	
	/**
	 * Constructor to retrieve view and model instances
	 */
	private GameController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
		setupController = SetupController.getInstance(gameModel, gameView);
		actionController = ActionController.getInstance(gameModel, gameView);
	}
	
	/**
	 * @return single instance of GameController
	 */
	public static GameController getInstance(GameModel gameModel, GameView gameView) {
		if (gameController == null) {
			gameController = new GameController(gameModel, gameView);
		}
		return gameController;
	}
	
	/**
	 * Initialise game components and add players to game
	 */
	public void setup() {
		
		// Show gameView welcome screen
		gameView.showWelcome();
		
		// Setup game components with new players obtain form user through GameView
		setupController.setupGame();
		
		// Update user view
		gameView.updateView(gameModel);
		
//		System.exit(0);
	}
	
	/**
	 * Control the overall flow of gameplay 
	 */
	public void playGame() {
		
		// repeat player turns until winning/losing conditions observed
		while(true) {
			for (Player p : gameModel.getGamePlayers()) {
				
				// take a number of actions
				actionController.takeActions(p);
				
				// Draw two cards from Treasure Deck
//				System.out.println("Drawing 2 cards from Treasure Deck...");
				p.drawFromTreasureDeck(2); // TODO: call gameView update from this method?
				
				// Draw number of Flood card equal to Water Level
//				System.out.println("Drawing Flood cards...");
	//			p.drawFromFloodDeck(WaterMeter.getInstance().getLevel());
			}
		}
	}
	
	/**
	 * method call by observers that have encountered game ending conditions
	 */
	public void endGame() {
		gameView.showEnding();	
	}

}


// TODO: control the flow of game in here...

//while(!gameOver && !gameWon) {
//	// Iterate over each  Player to take turns (Randomise order?)
//	for (Player p : players.getPlayersList()) {
//		
//		if(!gameOver && !gameWon) {
//			
//			System.out.println(islandBoard.toString());
//			System.out.println("It is "+p.toString()+"s turn");
//			p.takeTurn(userInput);
//			//TODO: How to end game if Game Over happens mid turn???? 
//		}
//	}
//}
//
//if(gameWon) {
//	//TODO: Implement game win
////	return true;
//} else {
//	//TODO: Implement game loss
//}
//
//}
