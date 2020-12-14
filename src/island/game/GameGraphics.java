package island.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import island.components.IslandTile;
import island.players.Player;

/**
 * Class populated with static methods for displaying the game play
 * through the console or terminal window.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameGraphics {

	// display component dimensions
	private final static int tileCharWidth = 25; // change to make tiles wider
	private final static int displayCharWidth = 6 * tileCharWidth; // display width in characters
	
	public static void refreshDisplay(GameModel gameModel) {
		
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
	//	displayGameDialog(); // TODO: gameView.addToUpcomingDialog
	}
	
	// TODO: maybe move to something like GameGraphics, to store all lengthy terminal image type outputs ??
	private static void displayIslandBoard(GameModel gameModel) { //TODO: print associated treasure

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
						String.format("%" + ((tileCharWidth - 4 + (boardStructure[i][j].getName()).length()) / 2) + "s", boardStructure[i][j]));
				outputString += " |";
			}
			
			// add row structure offset before
			outputString += "\n";
			outputString += " ".repeat(tileCharWidth * rowOffset);
			
			// iterate over island grid columns
			for (int j = 0; j < boardStructure[i].length; j++) {
				
			//	private enum FloodStatus { SAFE("--- Safe ---"), FLOODED("~~~ Flooded ~~~"), SUNK("XXX Sunk XXX"); // TODO: move to GameView
				// add flooded status String
				outputString += "| ";
				if (boardStructure[i][j].isSafe())
						outputString += String.format("%-" + (tileCharWidth - 4) + "s", String.format("%" + ((tileCharWidth - 4 + (" ").length()) / 2) + "s", " "));
				else if (boardStructure[i][j].isFlooded())
						outputString += String.format("%-" + (tileCharWidth - 4) + "s", String.format("%" + ((tileCharWidth - 4 + ("~~~ FLOODED ~~~").length()) / 2) + "s", "~~~ FLOODED ~~~"));
				else
						outputString += String.format("%-" + (tileCharWidth - 4) + "s", String.format("%" + ((tileCharWidth - 4 + ("XXX SUNK XXX").length()) / 2) + "s", "XXX SUNK XXX"));
					
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
	
	private static void displayPlayerInformation(GameModel gameModel) {
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

}
