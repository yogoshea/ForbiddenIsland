package island.main;

import island.game.Game;
import island.mvc.GameController;

/**
 * GamePlay class controls the game events at the highest level; client code for game
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GamePlay {
	
	/**
	 * main method to control the game-play (client code)
	 * @param args, command line arguments
	 */
	public static void main(String[] args) {
		
		System.out.println("Creating Game...");
		GameController gameController = new GameController();
		System.out.println("Game Over");

	}
}
