package island.game;

import java.util.ArrayList;
import java.util.List;
import island.players.Player;

/**
 * Controls the flow of the gameplay and obtains player choices
 * through GameView
 * @author Eoghan O'Shea
 *
 */
public class GameController {
	
	// Instantiate singleton
	private static GameController gameController = new GameController();
	
	private GameView gameView;
	private GameModel gameModel;
	private ActionController actionController;
	
	/**
	 * Constructor to retrieve view and model instances
	 */
	private GameController() {
		gameView = GameView.getInstance();
		gameModel = GameModel.getInstance();
	}
	
	/**
	 * @return single instance of GameController
	 */
	public static GameController getInstance() {
		return gameController;
	}
	
	/**
	 * Initialise game components and add players to game
	 */
	public void setupGame() {
		
		// Show gameView welcome screen
		gameView.showWelcome();
		
		// Request players from user through GameView TODO: check for valid user input
		String temp = gameView.promptUser("How many players are there?");
		int playerCount = Integer.parseInt(temp);
		List<String> playerNames = new ArrayList<String>();
		
		// iterate over number of players
		for (int i = 1; i <= playerCount; i++) {
			playerNames.add(gameView.promptUser("Please enter the name of Player " + i)); // TODO: check for valid name input
		}
		
		// setup game components with new players
		gameModel.setupGameComponents(playerNames);
		
		// update user view
		gameView.updateView(gameModel);
		
		System.exit(0);
	}
	
	/**
	 * Control the overall flow of gameplay 
	 */
	public void playGame() {
		actionController = ActionController.getInstance();
		for (Player p : gameModel.getPlayers()) {
			
			// take a number of actions
			actionController.takeActions(p);
			
			// Draw two cards from Treasure Deck
			p.drawFromTreasureDeck(2); // TODO: call gameView update from this method?
			
			// Draw number of Flood card equal to Water Level
//			p.drawFromFloodDeck();
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
