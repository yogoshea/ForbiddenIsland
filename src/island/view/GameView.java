package island.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import island.cards.Card;
import island.components.GameModel;
import island.controllers.GameController;
import island.controllers.GameEndings;
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
	private final Notifier notifier;
	private final Prompter prompter;
	public static final int MAX_NAME_LENGTH = 8;
	public static final String HELI = "HELI";
	public static final String SAND = "SAND";
	
	/**
	 * Private constructor for GameView singleton.
	 * @param Refernce to GameController.
	 */
	private GameView() {
		this.userInput = new Scanner(System.in);
		notifier = new Notifier(this);
		prompter = new Prompter(this);
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
	 * Displays welcome graphics.
	 */
	public void showWelcome() {
		Graphics.displayWelcome();
	}
	
	/**
	 * Displays ending graphics.
	 */
	public void showEnding(GameEndings ending) {
		Graphics.displayEnding(ending);
	}
	
	/**
	 * Called from within controller to display latest game status
	 */
	public void updateView(GameModel gameModel, Player p) {
		
		Graphics.refreshDisplay(gameModel);
		// Show who's turn it is
		notifier.showPlayerTurn(p);
	}
	
	/**
	 * Getter method for GameView's notifier.
	 * @return Notifier instance.
	 */
	public Notifier getNotifier() {
		return notifier;
	}
	
	/**
	 * Getter method for GameView's prompter.
	 * @return Prompter instance.
	 */
	public Prompter getPrompter() {
		return prompter;
	}
	
	/**
	 * Sets the view's Special Card controller
	 * @param SpecialCardController to be used by GameView.
	 */
	public void setController(SpecialCardController specialCardController) {
		this.specialCardController = specialCardController;
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
	 * Simple method used by Notifier to indicate message to be displayed to user.
	 * @param String of output message to show.
	 */
	protected void show(String output) {
		System.out.println(output);
	}
	
	/**
	 * Method to scan in [Enter] inputs when they are requested.
	 * @param prompt, prompting user to press [Enter] to continue
	 */
	protected void scanEnter(String prompt) {
		System.out.println(prompt);
		//If a special card request is made then it is executed and scanEnter() is called again to return user to original position
		if(checkSpecialCardRequest()) {
			scanEnter(prompt);
		}
	}
	
	/**
	 * Method to take in any list of items and get an item choice from the user. Via this method, all input is checked for special card requests 
	 * @param <E> type of items player will be choosing from
	 * @param items list for the player to choose from
	 * @param prompt to prompt player with
	 * @return chosen item
	 */
	protected <E> E pickFromList(List<E> items, String prompt){
		int index;
		String options = createOptionsString(items);
		
		// Prompt user to ask which item they would like to pick
		System.out.println("\n" + prompt);
		// Print item options to user
		System.out.println(options);
		// Scan in users choice of item
		index = scanValidInt(prompt+"\n"+options, 1, items.size()) - 1;

		// Return chosen item
		return items.get(index);
	}
	
	/**
	 * Method to prompt player to discard a card when they have more than 5 cards. Ensures that when a HELI of SAND card is played from hand, a card no longer needs to be discarded 
	 * @param cards of players hand from which 1 must be discarded
	 * @param prompt player to discard a card
	 * @return
	 */
	public Card<?> pickDiscardCard(List<Card<?>> cards, String prompt){ 
		int choice;
		int initialSize = cards.size();
		String options = createOptionsString(cards);
		
		// Prompt user to ask which item they would like to pick
		System.out.println("\n" + prompt);
		// Print item options to user
		System.out.println(options);
		
		//Check for valid user input
		if(userInput.hasNextInt()) {
			choice = userInput.nextInt();
			userInput.nextLine();
			if(choice >= 1 && choice <= cards.size()) {
				//If valid input then return input
				return cards.get(choice-1);
			}
		// If input is not valid and a special card request has been made
		} else if (checkSpecialCardRequest()) {
			//If the special request results in the players card count decreasing --> Discard no longer required. Return null
			if(cards.size() < initialSize) {
				return null;
			}
		}
		//If invalid input, try again
		System.out.println("Please input a valid number\n");
		return pickDiscardCard(cards, prompt);
	}
	
	/**
	 * Creates a String used to tell user what their options are, based on the provided list of items
	 * @param <E> - the type of the items to be chosen
	 * @param items - the list of items user is choosing from
	 * @return String that is created
	 */
	private <E> String createOptionsString(List<E> items) {
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
		return options; //Return string of options
	}
	
	/**
	 * Method to ensure user input is valid when the input must be an int
	 * @param prompt - the initial prompt given to the player
	 * @param min - the minimum valid number that can be input
	 * @param - the maximum valid number that can be input
	 * @return the valid user input
	 */
	private int scanValidInt(String prompt, int min, int max) {
		int choice;
		
		// If input includes an int
		if(userInput.hasNextInt()) {
			choice = userInput.nextInt();
			userInput.nextLine();
			//If valid input then return input
			if(choice >= min && choice <= max) {
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
	 * @param prompt asking what the players names are
	 * @param playerNames - the names of other players to ensure same name is not chosen twice
	 * @return chosen name
	 */
	private String scanValidName(String prompt, List<String> playerNames) {
		String name;
		System.out.println(prompt);
		
		//Loop until valid name is input
		while(true) {
			name = userInput.nextLine();
			//If name too long or short
			if(name.length() > MAX_NAME_LENGTH || name.length() < 1) {
				System.out.println("Name must be between 1 and 8 characters");
			//If name already taken
			} else if(playerNames.contains(name)) {
				System.out.println("This name has already been taken");
			} else {
				return name; //Return valid name
			}
			//If name not valid then reprint prompt and try again
			System.out.println("\n"+prompt);
		}
	}
	
	/**
	 * When called, this method checks user input for a HELI or SAND special card request
	 * @return Boolean of whether or not a special request had been made
	 */
	private boolean checkSpecialCardRequest() {
		
		String input = userInput.nextLine();
		
		//If special request made then attempt to play special card and return true
		if(input.equals(HELI) || input.equals(SAND)) {
			specialCardController.specialCardRequest(input);
			return true;
		} else {return false;}
	}
	
	
	// Singleton reset for JUnit testing
	public static void reset() {
		gameView = null;
	}

}
