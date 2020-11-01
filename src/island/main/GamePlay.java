package island.main;

import java.util.Scanner;

import island.game.Game;
import island.players.Player;

/**
 * GamePlay class controls the game events at the highest level
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GamePlay {
	
	/**
	 * main method to control the game-play
	 * @param args, command line arguments
	 */
	public static void main(String[] args) {
		
		
		// Create new Game instance
		System.out.println("Creating Game");
		Game game = new Game();

		// Iterate over each  Player to take turns (Randomise order?)
//		for (Player p : game.getPlayers()) {
//			p.takeTurn();
//			System.out.println(p);
//		}
		
		// ask if they would like to play again
//		System.out.println("Would you like to play again?");
//		Scanner reader = new Scanner(System.in);
		
	}

}
