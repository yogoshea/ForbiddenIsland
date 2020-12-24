package island.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import island.components.GameModel;
import island.controllers.GameController;
import island.controllers.SpecialCardController;
import island.players.Player;

/**
 * Class to represent the user interface view, called from controller classes.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameView {
	
	// Singleton instance
	private static GameView gameView;

	// GameView references
	private Scanner userInput;
	private SpecialCardController specialCardController;
	public static final int MAX_NAME_LENGTH = 8;
	public static final String HELI = "HELI";
	public static final String SAND = "SAND";
	
	/**
	 * Private constructor for GameView singleton.
	 * @param Refernce to GameController.
	 */
	private GameView() {
		this.userInput = new Scanner(System.in);
	}
	
	/**
	 * Getter method for singleton instance.
	 * @return single instance of GameView
	 */
	public static GameView getInstance() {
		if (gameView == null) {
			gameView = new GameView();
		}
		return gameView;
	}
	
	/**
	 * Called from within model to provide latest game status to display
	 */
	public void updateView(GameModel gameModel, Player p) {
		//TODO: display num cards in flood deck and treasure deck?
		Graphics.refreshDisplay(gameModel);
		// Show who's turn it is
		Messages.showPlayerTurn(p);
	}
	
	/**
	 * Method to take in any list of items and get item choice from player. Via this method, all input is checked for special card requests 
	 * @param <E> type of items player will be choosing from
	 * @param items list for the player to choose from
	 * @param prompt to prompt player with
	 * @return chosen item
	 */
	public <E> E pickFromList(List<E> items, String prompt){
		int index;
		// Prompt user to ask which item they would like to pick
		System.out.println("\n" + prompt);
		
		// Create string with each item in list as an option
		int i = 1;
		String options = "\n";
		for(E item : items) {
			if(i==1) {
				options += item.toString()+" ["+Integer.toString(i)+"]";
			} else {
				if(i % 5 == 1)
					options += ",\n" + item.toString()+" ["+Integer.toString(i)+"]";
				else
					options += ", " + item.toString()+" ["+Integer.toString(i)+"]";
			}
			i++;
		}
		// Print item options to user
		System.out.println(options);
		// Scan in users choice
		index = scanValidInt(prompt+"\n"+options, 1, items.size()) - 1;
		// Return chosen item
		return items.get(index);
	}
	
	/**
	 * Method to ensure user input is valid when an int must be input
	 * @param prompt is the initial prompt given to the player
	 * @param min is the minimum valid number that can be input
	 * @param max is the maximum valid number that can be input
	 * @return the valid user input
	 */
	public int scanValidInt(String prompt, int min, int max) {
		int choice;
		
		// If input includes an int
		if(userInput.hasNextInt()) {
			choice = userInput.nextInt();
			userInput.nextLine();
			if(choice >= min && choice <= max) {
				//If valid input then return input
				return choice;
			}
			System.out.println("Please input a valid number\n");
		
		// If input is not an int and if a special card request has not been made
		} else if (!checkSpecialCardRequest()) {
			System.out.println("Please input a valid number\n");
		} 
		// If invalid input, reprint prompt and try again by calling function recursively
		System.out.println(prompt);
		return scanValidInt(prompt, min, max);
	}
	
	/**
	 * Method to ensure input player names are valid
	 * @param prompt is the prompt asking what the players names are
	 * @param playerNames, the names of other players to ensure same name is not chosen twice
	 * @return chosen name
	 */
	public String scanValidName(String prompt, List<String> playerNames) {
		
		String name;
		System.out.println(prompt);
		
		//Loop until valid name found
		while(true) {
			name = userInput.nextLine();
			//If name too long
			if(name.length() > MAX_NAME_LENGTH) {
				System.out.println("The max character length is 8");
			//If name already taken
			} else if(playerNames.contains(name)) {
				System.out.println("This name has already been taken");
			} else {
				//Return valid name
				return name;
			}
			//If name not valid then reprint prompt and try again
			System.out.println("\n"+prompt);
		}
	}
	
	/**
	 * Method to scan in [Enter] inputs when they are requested.
	 * @param prompt, prompting user to press [Enter] to continue
	 */
	public void scanEnter(String prompt) {
		System.out.println(prompt);
		//If special card request is made then it is executed and scanEnter() is called again to return user to original position
		if(checkSpecialCardRequest()) {
			scanEnter(prompt);
		}
	}
	
	/**
	 * When called, method checks user input for a HELI or SAND special card request
	 * @return Boolean of whether or not a special request had been made
	 */
	public boolean checkSpecialCardRequest() {
		
		String input = userInput.nextLine();
		
		//If special request made then attempt to play special card and return true
		if(input.equals(HELI) || input.equals(SAND)) {
			specialCardController.specialCardRequest(input);
			return true;
		} else {return false;}
	}
	
	/**
	 * Method to get the names of players who will be playing the game
	 * @return a list of player names which will be added to the game
	 */
	public List<String> getPlayers() {
		
		//Prompt user
		String prompt = "How many players are there? (2-4 players allowed)";
		System.out.println(prompt);
		
		//Get amount of players to play
		int playerCount = scanValidInt(prompt, GameController.MIN_PLAYERS, GameController.MAX_PLAYERS); //Ensures a valid number is chosen
		
		List<String> playerNames = new ArrayList<String>();
		
		// iterate over number of players to get each players name
		for (int i = 1; i <= playerCount; i++) {
			playerNames.add(scanValidName("Please enter the name of Player " + i + ": (Max 8 characters)", playerNames)); // checks for valid name input
		}
		return playerNames;
	}
	
	/**
	 * Sets the views controller
	 * @param GameController
	 */
	public void setControllers(SpecialCardController specialCardController) {
		this.specialCardController = specialCardController;
	}

	// Singleton reset for JUnit testing
	public static void reset() {
		gameView = null;
	}
	

}
