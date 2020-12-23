package island.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import island.cards.Card;
import island.cards.SpecialCardAbility;
import island.components.IslandTile;
import island.components.Treasure;
import island.players.Player;

/**
 * Class to represent the user interface view
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameView {
	
	// Singleton instance
	private static GameView gameView;

	// GameView references
	private Scanner userInput;
	private GameController gameController;
	private SpecialCardController specialCardController;
	public static final int MIN_PLAYERS = 2;
	public static final int MAX_PLAYERS = 4;//TODO: put these in controller?
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
		GameGraphics.refreshDisplay(gameModel);
		//Show who's turn it is
		showPlayerTurn(p);		
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
		String options = "\n"; //TODO: remove?
		for(E item : items) {
			if(i==1) {
				options += item.toString()+" ["+Integer.toString(i)+"]";
			} else {
				if(i % 5 == 1)
					options += ",\n" + item.toString()+" ["+Integer.toString(i)+"]"; // TODO: have toString implemented in all classes??
				else
					options += ", " + item.toString()+" ["+Integer.toString(i)+"]";
			}
			i++;
		}
		// Print item options to user
		System.out.println(options);
		// Scan in users choice
		index = scanValidInt(prompt+"\n"+options, 1, items.size()) - 1;
		//return chosen item
		return items.get(index);
	}
	
	/**
	 * Method to ensure user input is valid when an int must be input
	 * @param prompt is the intitial prompt given to the player
	 * @param min is the minimum valid number that can be input
	 * @param max is the maximum valid number that can be input
	 * @return the valid user input
	 */
	public int scanValidInt(String prompt, int min, int max) {//TODO:print prompt in here?
		int choice;
		
		// If input includes an int
		if(userInput.hasNextInt()) {
			choice = userInput.nextInt();
			userInput.nextLine();//TODO: make tidier?
			if(choice >= min && choice <= max) {
				//If valid input then return input
				return choice;
			}
			System.out.println("Please input a valid number\n"); //TODO: remove code duplication of prints
		
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
	 * When called, mehtod checks user input for a HELI or SAND special card request
	 * @return Boolean of whether or not a special request had been made
	 */
	public boolean checkSpecialCardRequest() {
		
		String input = userInput.nextLine();
		
		//If special request made then attempt to play special card and return true
		if(input.equals(HELI) || input.equals(SAND)) {
			specialCardController.specialCardRequest(input);
			return true;
		} else{return false;}
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
		int playerCount = scanValidInt(prompt, MIN_PLAYERS, MAX_PLAYERS); //Ensures a valid number is chosen
		
		List<String> playerNames = new ArrayList<String>();
		
		// iterate over number of players to get each players name
		for (int i = 1; i <= playerCount; i++) {
			playerNames.add(scanValidName("Please enter the name of Player " + i + ": (Max 8 characters)", playerNames)); // checks for valid name input
		}
		return playerNames;
	}
	
	
//******************************************************************************************************
//The following are methods called by Controllers which call in turn pickFromList() to get users choice of a particular type of objects.
//This ensures the gameView can decide how exactly to prompt the user, without a controller specifying a string.
//This is to match the MVC pattern and allow for different Views to be implemented with the same Controllers and Model.
//******************************************************************************************************
	
	/**
	 * Retrieve user choice of game's initial water level.
	 * @return integer value representing selected water level.
	 */
	public int pickStartingWaterLevel() {
		String prompt = "What water level would you like to start on?";
		List<String> startingDifficulties = Arrays.asList("Novice", "Normal", "Elite", "Legendary");
		return startingDifficulties.indexOf(pickFromList(startingDifficulties, prompt)) + 1;
	}		
	
	/**
	 * Called from within model to get player action choice
	 */
	public Action pickAction(int availableActions) {
		String prompt = "Select one of the following actions: ("+availableActions+" remaining)";
		return pickFromList( Arrays.asList(Action.values()) , prompt);
	}
	
	//TODO: should we comment all these or just have generic comment at top?
	public IslandTile pickTileDestination(List<IslandTile> tiles) {
		String prompt = "Which tile do you wish to move to?";
		return pickFromList(tiles, prompt);
	}
	
	public IslandTile pickSwimmableTile(Player player, List<IslandTile> tiles) {
		String prompt = player.toString()+", YOUR TILE HAS SUNK!!\nWhich tile do you wish to move to?";
		return pickFromList(tiles, prompt);
	}
	
	public IslandTile pickShoreUpTile(List<IslandTile> tiles) {
		String prompt = "Which tile do you wish to shore up?";
		return pickFromList(tiles, prompt);
	}
	
	public Boolean shoreUpAnother() {
		String prompt = "As an Engineer you may shore-up 2 tiles. Shore-up another?";
		List<String> choices = Arrays.asList("Yes", "No"); //Create choices
		String choice = pickFromList(choices, prompt);
		return choice.equals("Yes");
	}
	
	public Player pickPlayerToRecieveCard(List<Player> players) {
		String prompt = "Which player do you wish to give a card to?";
		return pickFromList(players, prompt);
	}
	
	public Card<?> pickCardToGive(List<Card<?>> treasureCards) {
		String prompt = "Which card do you wish to give?";
		return pickFromList(treasureCards, prompt);
	}
	
	public Card<?> pickCardToDiscard(Player player) {
		List<Card<?>> cards = player.getCards();
		String prompt = player.getName() + ", you have too many cards in your hand, which do you wish to discard?";
		Card<?> card = pickFromList(cards, prompt);
		System.out.println("You have discarded: " + card.getName()); //TODO: create show method for this
		return card;
	}
	
	public Player pickHeliPlayer(List<Player> players) {
		String prompt = "Which player requested a Helicopter Lift?";
		return pickFromList(players, prompt);
	}
	
	public IslandTile pickHeliDestination(List<IslandTile> availableTiles) {
		String prompt = "Which tile do you wish to helicopter to?";
		return pickFromList(availableTiles, prompt);
	}
	
	public Player pickSandbagPlayer(List<Player> players) {
		String prompt = "Which player wants to play a Sandbag card?";
		return pickFromList(players, prompt);
	}
	
	public Player pickRequestPlayer(List<Player> players, SpecialCardAbility ability) {
		String prompt = "Which player wants to play a " +ability.toString()+ " card?";
		return pickFromList(players, prompt);
	}
	
	public List<Player> pickHeliPlayers(List<Player> players, IslandTile destination) {
		
		String prompt;
		List<Player> heliPlayers = new ArrayList<Player>();
		
		//Check each player to see if they wish to take the lift
		for(Player player : players) {
			prompt = "Does " + player.getName() + " wish to move to " + destination.getName() + "? \n";
			prompt += "[Y]/[N]";
			System.out.println(prompt);
			if(userInput.nextLine().equals("Y")) { //Doesn't use pickFromList as players aren't allowed play a special card at this point
				heliPlayers.add(player);
			}
		}
		return heliPlayers;
	}
	
	public Boolean pickKeepOrGive() {
		String prompt = "Do you wish to keep your card or give it to another Player?";
		List<String> choices = Arrays.asList("Keep", "Give");
		String choice = pickFromList(choices, prompt);
		return choice.equals("Keep");
	}
	
	
//******************************************************************************************************
//The following are methods called by Controllers which display a message to the user, giving information of game events.
//This ensures the gameView can decide how exactly to tell the user about the event.
//This is to match the MVC pattern and allow for different Views to be implemented with the same Controllers and Model.
//******************************************************************************************************
		
	//TODO: New class for these show methods? How to clean up/shorten? Enum: ErrorMessages
	/**
	 * Displays welcome view
	 */
	public void showWelcome() {
		GameGraphics.displayWelcomeMessage();
	}
	
	public void showSkippingActions() {
		System.out.println("Skipping actions...");
	}
	
	/**
	 * Displays message telling user there are no tiles to move to
	 */
	public void showNoMoveTiles() {
		System.out.println("No tiles available to move to");
	}
	
	public void showSuccessfulMove(Player player, IslandTile tile) {
		System.out.println(player.getName() + " has moved to " + tile.getName());
	}
	
	/**
	 * Displays message telling user there are no tiles to move to
	 */
	public void showNoShoreUpTiles() {
		System.out.println("No available tiles to shore-up");
	}
	
	public void showSuccessfulShoreUp(IslandTile tile) {
		System.out.println(tile.getName() + " has been shored up");
	}
	
	/**
	 * Displays message telling user there are no other players on their tile
	 */
	public void showNoAvailablePlayers() {
		System.out.println("No other players on your tile");
	}
	
	/**
	 * Displays message telling user they have no treasure cards
	 */
	public void showNoTreasureCards() {
		System.out.println("No treasure cards in your hand");
	}
	
	/**
	 * Displays message telling user there is no treasure on their current tile
	 */
	public void showNoTreasure(IslandTile tile) {
		System.out.println("No treasure found at " + tile.getName());
	}
	
	/**
	 * Displays message telling user there is no treasure on their current tile
	 */
	public void showNotEnoughCards(Treasure treasure) {
		System.out.println("You need 4 " + treasure.getName() + " cards to capture this treasure");
	}
	
	/**
	 * Tells user which players turn it is
	 */
	public void showPlayerTurn(Player p) {
		System.out.println("It is the turn of: " + p.getName());
	}
	
	/**
	 * Tells user that treasure cards are being drawn
	 */
	public void showDrawingTreasureCards() {
		System.out.println("Drawing 2 treasure cards...");
	}
	
	/**
	 * Tells user that treasure cards are being drawn
	 */
	public void showTreasureCardDrawn(Card<?> card) {
		System.out.println("\n--> You have drawn: " + card.getName());
	}
	
	public void showCardGiven(Card<?> card,Player giver, Player reciever) {
		System.out.println(giver.toString()+ " has given a " +card.toString()+ " to " +reciever.toString());
	}
	
	/**
	 * Tells user that treasure cards are being drawn
	 */
	public void showWaterRise(int level) {
		System.out.println("\nNEW WATER LEVEL: " + Integer.toString(level));
		System.out.println("The flood deck has been refilled");
	}
	
	/**
	 * Tells user that the player doesn't have a helicopter lift card
	 */
	public void showNoSpecialCard(Player player, SpecialCardAbility ability) {
		System.out.println(player.getName() + " does not have a "+ability.toString()+" Sandbag card");
	}
		
	public void showGameWin() {
		System.out.println("You win");
	}
	
	public void showEnterToContinue() {
		String prompt = "\nTo continue, press [Enter]...";
		scanEnter(prompt);
	}
	
	public void showDrawTreasureCards() {
		String prompt = "\nTo draw your two treasure cards, press [Enter]...";
		scanEnter(prompt);
	}
	
	public void showDrawFloodCards() {
		String prompt = "\nTo draw your flood cards, press [Enter]...";
		scanEnter(prompt);
	}
	
	public void showSpecialCardDone() {
		String prompt = "\nTo return to before request was made, press Enter[]...";
		scanEnter(prompt);
	}
	
	/**
	 * Tells user that the player doesn't have a helicopter lift card
	 */
	public void showTileFlooded(IslandTile tile) {
		System.out.println(tile.getName() + " has been flooded!!");
	}
	
	public void showTileSunk(IslandTile tile) {
		System.out.println(tile.getName() + " has SUNK!!!");
	}
	
	public void showAlreadyCaptured(Treasure treasure) {
		System.out.println(treasure.getName() + " has already been captured");
	}
	
	public void showTreasureCaptured(Treasure treasure) {
		System.out.println("You have captured "+treasure.getName());
	}
	
	public void showTreasureSunk(IslandTile firstTile, IslandTile secondTile) {
		System.out.println("\n" + firstTile.toString()+" and "+secondTile.toString()+" are both sunk and "+firstTile.getAssociatedTreasure()+" hasn't been captured");
	}
	
	public void showPlayerSunk(Player player) {
		System.out.println("\n"+player.toString()+" could not reach any safes tiles!!!");
	}

	/**
	 * Displays ending view, with message giving reason for game end
	 */
	public void showEnding(GameEndings ending) {
		
		System.out.println(); // newline
		switch(ending) {
		
		case FOOLS_LANDING_SUNK:
			System.out.println("GAME OVER - Fools Landing has Sunk");
			break;
			
		case TREASURE_SUNK:
			System.out.println("GAME OVER - A treasure has Sunk");
			break;
			
		case PLAYER_SUNK:
			System.out.println("GAME OVER - A player has Sunk");
			break;

		case MAX_WATER_LEVEL:
			System.out.println("GAME OVER - Maximum water level has been reached");
			break;

		case WIN:
			System.out.println("!!!! The game has been won !!!!"); // TODO: add congratulations
			break;
			
		default:
			System.out.println("Game has ended");
			break;
				
		}	
	}
	
//***********************************************************************************************************
//***********************************************************************************************************
	
	/**
	 * Sets the views controller
	 * @param GameController
	 */
	public void setControllers(GameController gameController, SpecialCardController specialCardController) {
		this.gameController = gameController;
		this.specialCardController = specialCardController;
	}

	// Singleton reset for JUnit testing
	public static void reset() {
		gameView = null;
	}
	

}
