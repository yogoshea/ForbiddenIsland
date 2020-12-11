package island.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import island.cards.Card;
import island.components.GameEndings;
import island.components.IslandTile;
import island.components.Treasure;
import island.game.ActionController.Action;
import island.players.Player;

/**
 * Class to represent the user interface view
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameView {
	
	// Singleton instance
	private static GameView gameView;

	private Scanner userInput;
	private GameController gameController;
	
	//TODO: make appropriate strings constant variables
	
	// display component dimensions
	final int tileCharWidth = 25; // change to make tiles wider
	final int displayCharWidth = 6 * tileCharWidth; // display width in characters
	
	
	private GameView() {
		userInput = new Scanner(System.in);
	}
	
	/**
	 * @return single instance of GameView
	 */
	public static GameView getInstance() {
		if (gameView == null) {
			gameView = new GameView();
		}
		return gameView;
	}

	
	//TODO: New class for these show methods? How to clean up/shorten?
	/**
	 * Displays welcome view
	 */
	public void showWelcome() {
		System.out.println("Welcome to Forbidden Island!\n");
		// TODO: add ASCII art
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
		System.out.println(player.toString()+" has moved to "+tile.toString());
	}
	
	/**
	 * Displays message telling user there are no tiles to move to
	 */
	public void showNoShoreUpTiles() {
		System.out.println("No available tiles to shore-up");
	}
	
	public void showSuccessfulShoreUp(IslandTile tile) {
		System.out.println(tile.toString() + " has been shored up");
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
		//TODO: show what stage of turn they are at i.e if they have already completed actions or drawn treasure cards
		//TODO: integrate with updateView better
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
	
	public void showEnterToContinue() {//TODO: stop having to press enter twice here when HELI played
		String prompt = "\nTo continue, press Enter[]...";
		System.out.println(prompt);
		scanNextLine(prompt);
	}
	
	public void showDrawTreasureCards() {
		String prompt = "\nTo draw your two treasure cards, press Enter[]...";
		System.out.println(prompt);
		scanNextLine(prompt);
	}
	
	public void showDrawFloodCards() {
		String prompt = "\nTo draw your flood cards, press Enter[]...";
		System.out.println(prompt);
		scanNextLine(prompt);
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
	
	public void showSpecialCardDone() {
		System.out.println("\nTo return to before request was made, press Enter[]...");
		userInput.nextLine();
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
	public void updateView(GameModel gameModel, Player p) {
		//TODO: display num cards in flood deck and treasure deck?
		// similar to observer to model changes, no
		//TODO: add time delays between prints? Maybe, yeah. Or just user press return/input any key to continue
		// TODO: better way to update screen?
//		System.out.println("\n".repeat(20));
		
		System.out.println("=".repeat(displayCharWidth));
		System.out.println(String.format("%-" + displayCharWidth/2 + "s" + "%-" + displayCharWidth/2 + "s", 
				"FORBIDDEN ISLAND", "WATER LEVEL: " + gameModel.getWaterMeter().getWaterLevel())); // TODO: This make sense ??
		System.out.println("=".repeat(displayCharWidth));
		displayIslandBoard(gameModel);
		
		// display current player information
		System.out.println("=".repeat(displayCharWidth));
		System.out.println("PLAYER INFORMATION");
		System.out.println("=".repeat(displayCharWidth));
		displayPlayerInformation(gameModel);
		
		// display Dialog box
		System.out.println("=".repeat(displayCharWidth));
		System.out.println(String.format("%-" + displayCharWidth/2 + "s" + "%-" + displayCharWidth/2 + "s", 
				"GAME DIALOG", "NOTE: To use Heli Lift or Sandbag at any time, enter [HELI] or [SAND]")); // TODO: This make sense ??
		System.out.println("=".repeat(displayCharWidth));
		
		//Show who's turn it is
		showPlayerTurn(p);
//		displayGameDialog(); // TODO: gameView.addToUpcomingDialog
		
	}
	
	// TODO: maybe move to something like GameGraphics, to store all lengthy terminal image type outputs ??
	private void displayIslandBoard(GameModel gameModel) { //TODO: print associated treasure

		String outputString = "";
		String vertBars = "-".repeat(tileCharWidth);
		Map<IslandTile, Player> pawnLocations = new HashMap<IslandTile, Player>(); // TODO: change to printing Pawn info
		
		// retrieve players' current positions from model TODO: CHANGE this to getPlayerLocations method
		for (Player p : gameModel.getGamePlayers()) {
			pawnLocations.put(p.getPawn().getTile(), p);
		}
		
		// retrieve Island board state from model
		IslandTile[][] boardStructure = gameModel.getIslandBoard().getBoardStructure();
		
		// iterate of island grid rows
		for (int i = 0; i < boardStructure.length; i++) {
			
			int rowLength = boardStructure[i].length;
			int rowOffset = ((rowLength * 2) % 6) / 2; // offset needed to from island structure

			// add top bar of island tiles
			outputString += " ".repeat(tileCharWidth * rowOffset) + vertBars.repeat(rowLength)
							+ " ".repeat(tileCharWidth * rowOffset) + "\n";
			
			// add row structure offset before
			outputString += " ".repeat(tileCharWidth * rowOffset);
		
			// iterate over island grid columns
			for (int j = 0; j < boardStructure[i].length; j++) {
				
				// add specific tile name
				outputString += "| ";
				outputString += String.format("%-" + (tileCharWidth - 4) + "s",
						String.format("%" + ((tileCharWidth - 4 + (boardStructure[i][j].toString()).length()) / 2) + "s", boardStructure[i][j]));
				outputString += " |";
			}
			
			// add row structure offset before
			outputString += "\n";
			outputString += " ".repeat(tileCharWidth * rowOffset);
			
			// iterate over island grid columns
			for (int j = 0; j < boardStructure[i].length; j++) {
				
				// add flooded status String
				outputString += "| ";
				outputString += String.format("%-" + (tileCharWidth - 4) + "s",
						String.format("%" + ((tileCharWidth - 4 + (boardStructure[i][j].getFloodStatus().toString()).length()) / 2) + "s", boardStructure[i][j].getFloodStatus().toString()));
				outputString += " |";
			}
			
			// add row structure offset before
			outputString += "\n";
			outputString += " ".repeat(tileCharWidth * rowOffset);
			
			// iterate over island grid columns
			for (int j = 0; j < boardStructure[i].length; j++) {
				
				// add space
				outputString += "| ";
				outputString += String.format("%-" + (tileCharWidth - 4) + "s",
						String.format("%" + ((tileCharWidth - 4 + " ".length()) / 2) + "s", " "));
				outputString += " |";
			}

			// add row structure offset before
			outputString += "\n";
			outputString += " ".repeat(tileCharWidth * rowOffset);
			
			// iterate over island grid columns
			for (int j = 0; j < boardStructure[i].length; j++) {
				
				// add specific tile details
				outputString += "| ";
				if (pawnLocations.containsKey(boardStructure[i][j])) {
					outputString += String.format("%-" + (tileCharWidth - 4) + "s",
							String.format("%" + ((tileCharWidth - 4 + (pawnLocations.get(boardStructure[i][j]).toString().length())) / 2) + "s", pawnLocations.get(boardStructure[i][j]).toString()));
					outputString += " |";
				} else {
					outputString += String.format("%-" + (tileCharWidth - 4) + "s",
						String.format("%" + ((tileCharWidth - 4 + " ".length()) / 2) + "s", " "));
					outputString += " |";
				}
			} // TODO: check for Player names that are too long, shorten?

			// add bottom bar of island tiles
			outputString += "\n" + " ".repeat(tileCharWidth * rowOffset) + vertBars.repeat(rowLength)
							+ " ".repeat(tileCharWidth * rowOffset) + "\n";
		}
		
		System.out.print(outputString);
	}
	
	private void displayPlayerInformation(GameModel gameModel) {
		// retrieve players' current information from model
		final int maxTreasureCards = 5;
		final int playerCount = gameModel.getGamePlayers().getPlayersList().size();
		final List<Player> playerList = gameModel.getGamePlayers().getPlayersList();
//		TreasureDeckCard currentTreasureCard;
		for (Player p : gameModel.getGamePlayers()) {
			System.out.printf("%" + (-6*tileCharWidth)/playerCount + "s", p); // left alignment 
		}
		System.out.println(); // newline 
		for (Player p : gameModel.getGamePlayers()) {
			System.out.printf("%" + (-6*tileCharWidth)/playerCount + "s", "Cards in hand:"); // left alignment 
		}
		System.out.println(); // newline 
		
		// list currently held treasure cards
		for (int i = 0; i < maxTreasureCards; i++) {
			for (int j = 0; j < playerCount; j++) {
				if (i < playerList.get(j).getCards().size())
					System.out.printf("%" + (-6*tileCharWidth)/playerCount + "s", "  " + (i+1) + ". " + playerList.get(j).getCards().get(i)); // left alignment 
				else
					System.out.printf("%" + (-6*tileCharWidth)/playerCount + "s", "  " + (i+1) + ". "+ "----------"); // left alignment 
			}
			System.out.println();
		}
	}
	
	
	/**
	 * Called from within model to get player action choice
	 */
	public Action getPlayerActionChoice(int availableActions) {
		
		String prompt = "Select one of the following actions: ("+availableActions+" remaining)";
		return pickFromList( Arrays.asList(Action.values()) , prompt);
		
	}
	
	//OR just go straight to pickFromList() from ActionController???
	//This way just means you aren't passing a string
	public IslandTile pickTileDestination(List<IslandTile> tiles) {//TODO:Give chance to change mind?
		String prompt = "Which tile do you wish to move to?";
		//TODO:
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
		
		index = Integer.parseInt(scanNextLine(prompt+"\n"+options)) - 1; //TODO:proper check for invalid input
		
		if(index > items.size() - 1) {
			System.out.println("Invalid Choice, choose again");
			return pickFromList(items, "");
		}
		return items.get(index);
	}
	


	public String scanNextLine(String initialPrompt) {
		//Print prompt in here (rather than before function)??
		
		String input = userInput.nextLine();
		
		while(input.equals("HELI") || input.equals("SAND")) {
			if(input.equals("HELI")) {
				gameController.getPlaySpecialCardController().heliRequest();
			}
			if(input.equals("SAND")) {
				gameController.getPlaySpecialCardController().sandbagRequest();
			}
//			System.out.println("Press enter to return to before request...");
//			userInput.nextLine();
//			updateView(gameController.getGameModel()); //TODO: Is getModel() ok for the View to do??????????????????
			System.out.println("\n"+initialPrompt);
			input = userInput.nextLine();
		}
		 
		return input;
	}
	

	
	
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
