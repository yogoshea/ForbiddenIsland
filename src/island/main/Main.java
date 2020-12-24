package island.main;

import island.components.GameModel;
import island.controllers.GameController;
import island.view.GameView;

/**
 * Main class controls the game events at the highest level; client code for game
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Main {
	
	/**
	 * Main method to control the game-play (client code)
	 * @param command line arguments
	 */
	public static void main(String[] args) {
		
		// Retrieve MVC instances
		GameModel gameModel = GameModel.getInstance();
		GameView gameView =  GameView.getInstance();
		GameController gameController = GameController.getInstance(gameModel, gameView);

		// Setup and begin playing game
		gameController.setup();
		gameController.playGame();
	}
}
