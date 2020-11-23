package island.mvc;

import java.util.Scanner;

import island.game.Game;

//
public class GameController {
	
	private static GameController gameController = new GameController();
	
	private Scanner userInput;
	private Game game;
	
	private GameController() {
		userInput = new Scanner(System.in);
		game = new Game(userInput);
		//Scanner used within Game is from the GameController -> all scanning is via Controller (does this count as MVC???) 
		game.playGame();
	}
	
	//All printing to command line goes through controller via this function. Does this count as MVC???
	public static void printToCommandLine(String s) {
		System.out.println(s);
	}
	
	public static GameController getInstance() {
		return gameController;
	}


}
