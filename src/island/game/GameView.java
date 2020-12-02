package island.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import island.cards.TreasureDeckCard;
import island.components.IslandBoard;
import island.components.IslandTile;
import island.players.GamePlayers;
import island.players.Player;

/**
 * Class to represent the user interface view
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameView {
	
	private static GameView gameView;
	private Scanner userInput;
	
	// display component dimensions
	final int tileCharWidth = 25; // change to make tiles wider
	
	
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

	/**
	 * Displays welcome view
	 */
	public void showWelcome() {
		System.out.println("Welcome to Forbidden Island!");
		// TODO: add ASCII art
	}
	
	// Request players from user TODO: check for valid user input
	public List<String> getPlayers() {
		String temp = promptUser("How many players are there?");
		int playerCount = Integer.parseInt(temp);
		List<String> playerNames = new ArrayList<String>();
		
		// iterate over number of players
		for (int i = 1; i <= playerCount; i++) {
			playerNames.add(promptUser("Please enter the name of Player " + i)); // TODO: check for valid name input
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
	
	public void printToUser(String string) { //TODO:Make private? Should model be allowed pass string??
		System.out.println(string);		
	}
	
	/**
	 * Called from within model to provide latest game status to display
	 */
	public void updateView(GameModel gameModel) {
		// similar to observer to model changes
		//TODO: add time delays between prints? make more readable rather than whole bunch of text suddenly appearing?
		// TODO: better way to update screen?
		System.out.println("\n".repeat(20));
		
		int displayCharWidth = 6 * tileCharWidth;
		System.out.println("=".repeat(displayCharWidth));
		System.out.println("FORBIDDEN ISLAND");
		System.out.println("=".repeat(displayCharWidth));
		displayIslandBoard(gameModel);
		
		// display current player information
		System.out.println("=".repeat(displayCharWidth));
		System.out.println("PLAYER INFORMATION");
		System.out.println("=".repeat(displayCharWidth));
		displayPlayerInformation(gameModel);
		
		// display Dialog box
		System.out.println("=".repeat(displayCharWidth));
		System.out.println("GAME DIALOG");
		System.out.println("=".repeat(displayCharWidth));
//		displayGameDialog();
		
	}
	
	private void displayIslandBoard(GameModel gameModel) {

		String outputString = "";
		String vertBars = "-".repeat(tileCharWidth);
		Map<IslandTile, Player> playerLocations = new HashMap<IslandTile, Player>();
		
		// retrieve players' current positions from model TODO: move this to getPlayerLocations method
		for (Player p : gameModel.getGamePlayers()) {
			playerLocations.put(p.getCurrentTile(), p);
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
				if (playerLocations.containsKey(boardStructure[i][j])) {
					outputString += String.format("%-" + (tileCharWidth - 4) + "s",
							String.format("%" + ((tileCharWidth - 4 + (playerLocations.get(boardStructure[i][j]).toString().length())) / 2) + "s", playerLocations.get(boardStructure[i][j]).toString()));
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
		for (Player p : gameModel.getGamePlayers()) {
			System.out.print("\n" + p.toString() + "\nTreasure Cards:\t");
			for (TreasureDeckCard tdc : p.findTreasureCards()) {
				System.out.print(tdc + "\t");
			}
			System.out.print("\n\n");
		}
	}
	
	
	public String getPlayerActionChoice(int availableActions) {
		
		System.out.println("\nDo you wish to take an action? ("+Integer.toString(availableActions)+" remaining)");
		System.out.println("[Y]/[N]");
		
		if(userInput.nextLine().equals("Y")) {
			
			System.out.println("Select one of the following actions:");
			System.out.println("Move [M], Shore-Up [S], Give Treasure Card [G], Capture a Treasure [C]");
			return userInput.nextLine();
			//TODO: what if user doesn't select a proper action -> give another chance or return "NO ACTION"??
			
		} else { return "NO ACTION";}
		
	}
	
	//OR just go straight to pickFromList() from ActionController???
	//This way just means you aren't passing a string
	public IslandTile pickTileDestination(List<IslandTile> tiles) {//TODO:Give chance to change mind?
		String prompt = "Which tile do you wish to move to?";
		//System.out.println("Which tile do you wish to move to?");
		return pickFromList(tiles, prompt);
	}
	
	public IslandTile pickShoreUpTile(List<IslandTile> tiles) {
		String prompt = "Which tile do you wish to shore up?";
		return pickFromList(tiles, prompt);
	}
	
	
	
	public <E> E pickFromList(List<E> items, String prompt){
		//TODO: check for correct user input, check list isn't empty
		int index;
		System.out.println(prompt);
		
		int i = 1;
		String options = "";
		for(E item : items) {
			options += item.toString()+" ["+Integer.toString(i)+"]";
			i++;
		}
		System.out.println(options);
		prompt += "\n"+options;
		index = Integer.parseInt(scanNextLine(prompt)) - 1;
		
		if(index > items.size() - 1) {
			System.out.println("Invalid Choice, choose again");
			pickFromList(items, prompt);
		}
		return items.get(index);
	}
	
/////////////////////////////////////////////////////////////////////////////
//Game scanner pasted in between these lines
//TODO: Figure out whether to keep separate game scanner class -> probably neater to
//TODO: Uses scanNextLine() in all appropriate scanning cases (when heli and sand cards van be played)
	
	public String scanNextLine(String initialPrompt) {
		//Print prompt in here (rather than before function)??
		
		String input = userInput.nextLine();
		
		while(input.equals("Heli") || input.equals("Sandbag")) {
			if(input.equals("Heli")) {
				heliRequest();
			}
			if(input.equals("Sandbag")) {
				sandbagRequest();
			}
			
			System.out.println(initialPrompt);
			input = userInput.nextLine();
		}
		 
		return input;
	}
	
	public void heliRequest() {
		String prompt = "Which player wishes to play a heli card?";
		Player p = pickFromList(GamePlayers.getInstance().getPlayersList(), prompt);
		p.playHeliCard();
	}
	
	public void sandbagRequest() {
		String prompt = "Which player wishes to play a sandbag card?";
		Player p = pickFromList(GamePlayers.getInstance().getPlayersList(), prompt); //bad practice to instantiate here?
		p.playSandBagCard();
	}
	
	//End of GameScanner
	///////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
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
	 * Displays ending view, explains why win or lose?
	 */
	public void showEnding() {
		// TODO Auto-generated method stub
		//System.out.exit(0);
	}		

}
