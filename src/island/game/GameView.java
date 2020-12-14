package island.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import island.cards.Card;
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

	//TODO: New class for these show methods? How to clean up/shorten? Enum: ErrorMessages
	/**
	 * Displays welcome view
	 */
	public void showWelcome() {
		System.out.println("Welcome to Forbidden Island!");
		// TODO: add ASCII art
	}
	
	/**
	 * Displays message telling user there are no tiles to move to
	 */
	public void showNoMoveTiles() {
		System.out.println("No tiles available to move to");
	}
	
	/**
	 * Displays message telling user there are no tiles to move to
	 */
	public void showNoShoreUpTiles() {
		System.out.println("No available tiles to shore-up");
	}
	
	/**
	 * Displays message telling user there are no other players on their tile
	 */
	public void showNoPlayersOnSameTile() {
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
		System.out.println("No treasure found at " + tile.toString());
	}
	
	/**
	 * Displays message telling user there is no treasure on their current tile
	 */
	public void showNotEnoughCards(Treasure treasure) {
		System.out.println("You need 4 " + treasure.toString() + " cards to capture this treasure");
	}
	
	/**
	 * Tells user which players turn it is
	 */
	public void showPlayerTurn(Player p) {
//		System.out.println();
		System.out.println("It is the turn of: " + p.toString()); //SJould I be using prompt user function??
		//displayPlayerInformation(p)????? Yeah, maybe include this in the updatable display
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
	public void showTreasureCardDrawn(Card card) {
		System.out.println("You have drawn: " + card.toString());
	}
	
	/**
	 * Tells user that treasure cards are being drawn
	 */
	public void showWaterRise(int level) {
		System.out.println("The island is flooding!! \n" + "NEW WATER LEVEL: " + Integer.toString(level));
	}
	
	/**
	 * Tells user that the player doesn't have a helicopter lift card
	 */
	public void showNoHeliCard(Player player) {
		System.out.println(player.toString() + " does not have a Helicopter Lift card");
	}
	
	/**
	 * Tells user that the player doesn't have a helicopter lift card
	 */
	public void showNoSandbagCard(Player player) {
		System.out.println(player.toString() + " does not have a Sandbag card");
	}
	
	public void showGameWin() {
		System.out.println("You win");
	}
	
	/**
	 * Tells user that the player doesn't have a helicopter lift card
	 */
	public void showTileFlooded(IslandTile tile) {
		System.out.println(tile.toString() + " has been flooded!!");
	}
	
	public void showTileSunk(IslandTile tile) {
		System.out.println(tile.toString() + " has SUNK!!!");
	}
	
	public void showAlreadyCaptured(Treasure treasure) {
		System.out.println(treasure.toString() + " has already been captured");
	}
	
	public void showTreasureCaptured(Treasure treasure) {
		System.out.println("You have captured "+treasure.toString());
	}
	
	
	
	// Request players from user TODO: check for valid user input
	public List<String> getPlayers() {
		String temp = promptUser("How many players are there?");
		int playerCount = Integer.parseInt(temp);
		List<String> playerNames = new ArrayList<String>();
		
		// iterate over number of players
		for (int i = 1; i <= playerCount; i++) {
			playerNames.add(promptUser("Please enter the name of Player " + i)); // TODO: check for valid name input
			// TODO: check for length less than tileCharWidth
		}
		return playerNames;
	}
	
	/**
	 * Asks user question specified by string
	 * @param String to print to view
	 * @return String entered by user
	 */
	private String promptUser(String string) {
		System.out.println(string);
		return userInput.nextLine();		
	}
	
	
	/**
	 * Called from within model to provide latest game status to display
	 */
	public void updateView(GameModel gameModel) {
		//TODO: display num cards in flood deck and treasure deck?
		// similar to observer to model changes, no
		//TODO: add time delays between prints? Maybe, yeah. Or just user press return/input any key to continue
		// TODO: better way to update screen?
//		System.out.println("\n".repeat(20));
		GameGraphics.refreshDisplay(gameModel);
		
	}
	
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
	public Action getPlayerActionChoice(Player player, int availableActions) {
		
		String prompt = player.toString()+": Select one of the following actions: ("+availableActions+" remaining)";
		return pickFromList( Arrays.asList(Action.values()) , prompt);
		
	}
	
	//OR just go straight to pickFromList() from ActionController???
	//This way just means you aren't passing a string
	public IslandTile pickTileDestination(List<IslandTile> tiles) {//TODO:Give chance to change mind?
		String prompt = "Which tile do you wish to move to?";
		return pickFromList(tiles, prompt);
	}
	
	public IslandTile pickSwimmableTile(List<IslandTile> tiles) {
		String prompt = "YOUR TILE HAS SUNK!!\nWhich tile do you wish to move to?";
		return pickFromList(tiles, prompt);
	}
	
	public IslandTile pickShoreUpTile(List<IslandTile> tiles) {
		String prompt = "Which tile do you wish to shore up?";
		return pickFromList(tiles, prompt);
	}
	
	public Player pickPlayerToRecieveCard(List<Player> players) {
		String prompt = "Which player do you wish to give a card to?";
		return pickFromList(players, prompt);
	}
	
	public Card pickCardToGive(List<Card> cards) {
		String prompt = "Which card do you wish to give?";
		return pickFromList(cards, prompt);
	}
	
	public Card pickCardToDiscard(Player player) {
		List<Card> cards = player.getCards();
		String prompt = player.toString() + ", you have too many cards in your hand, which do you wish to discard?";
		Card card = pickFromList(cards, prompt); //TODO: allow to play heli or sandcard??
		System.out.println("You have discarded: " + card.toString());
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
	
	public List<Player> pickHeliPlayers(List<Player> players, IslandTile destination) {
		
		String prompt;
		List<Player> heliPlayers = new ArrayList<Player>();
		
		for(Player player : players) {
			
			prompt = "Does " + player.toString() + " wish to move to " + destination.toString() + "? \n";
			prompt += "[Y]/[N]";
			System.out.println(prompt);
			
			if(userInput.nextLine().equals("Y")) {
				heliPlayers.add(player);
			}
		}
		
		return heliPlayers;
	}
	
	
	
	public <E> E pickFromList(List<E> items, String prompt){
		//TODO: check for correct user input, check list isn't empty
		int index;
		System.out.println("\n" + prompt);
		
		int i = 1;
		String options = "\n";
		for(E item : items) {
			options += item.toString()+" ["+Integer.toString(i)+"], ";
			i++;
		}
		System.out.println(options); //TODO: print vertically to look better?
		
		index = Integer.parseInt(scanNextLine(prompt+options)) - 1; //TODO:proper check for invalid input
		
		if(index > items.size() - 1) {
			System.out.println("Invalid Choice, choose again");
			return pickFromList(items, "");
		}
		return items.get(index);
	}
	
/////////////////////////////////////////////////////////////////////////////
//Game scanner pasted in between these lines
//TODO: Figure out whether to keep separate game scanner class -> probably neater to
//TODO: Uses scanNextLine() in all appropriate scanning cases (when heli and sand cards van be played)
	
	public String scanNextLine(String initialPrompt) { // TODO: maybe this checking should be in the controller? Not sure
		//Print prompt in here (rather than before function)??
		
		String input = userInput.nextLine();
		
		while(input.equals("HELI") || input.equals("SAND")) {
			if(input.equals("HELI")) {
				gameController.getPlaySpecialCardController().heliRequest();
			}
			if(input.equals("SAND")) {
				gameController.getPlaySpecialCardController().sandbagRequest();
			}
			
			System.out.println(initialPrompt);
			input = userInput.nextLine();
		}
		 
		return input;
	}
	
//	public void heliRequest() {
//		String prompt = "Which player wishes to play a heli card?";
//		Player p = pickFromList(GamePlayers.getInstance().getPlayersList(), prompt);
//		p.playHeliCard();
//	}
//	
//	public void sandbagRequest() {
//		String prompt = "Which player wishes to play a sandbag card?";
//		Player p = pickFromList(GamePlayers.getInstance().getPlayersList(), prompt); //bad practice to instantiate here?
//		p.playSandBagCard();
//	}
	
	//End of GameScanner
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * Sets the views controller
	 * @param GameController
	 */
	public void setController(GameController gameController) {
		this.gameController = gameController;
	}
	
	
	
	/**
	 * Prints question for players
	 * @param String to show on view
	 */
	public void askUser(String string) { //TODO: maybe don't need
		System.out.println(string);
	}
	
	/**
	 * Retrieves string input from user
	 * @return String received from input Scanner
	 */
	public String getUserInput() { //TODO: maybe don't need
		return userInput.nextLine();
	}

	/**
	 * Displays ending view, with message giving reason for game end
	 */
	public void showEnding(GameEndings ending) {
		//TODO: Make individual show endings for each scenario and call them from observer so can see details of how game ended
		
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

		case WIN:
			System.out.println("!!!!The game has been won!!!!");
			break;
				
		}
		
	}


}
