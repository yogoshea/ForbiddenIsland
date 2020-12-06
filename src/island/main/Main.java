package island.main;

import island.game.GameController;
import island.game.GameModel;
import island.game.GameView;

/**
 * Main class controls the game events at the highest level; client code for game
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Main {
	
	/**
	 * main method to control the game-play (client code)
	 * @param args, command line arguments
	 */
	public static void main(String[] args) {
		
		System.out.println("Creating Game...");
		
		GameModel gameModel = GameModel.getInstance();
		GameView gameView =  GameView.getInstance();
		GameController gameController = GameController.getInstance(gameModel, gameView);

		gameController.setup();
		gameController.playGame();
		
		System.out.println("Game Over");

	}
}
