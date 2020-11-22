package island.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

import island.cards.FloodCard;
import island.decks.FloodDeck;
import island.decks.FloodDiscardPile;

/**
 * IslandBoard class represents island board and holds tiles
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class IslandBoard {
	
	// Instantiate singleton
	private static IslandBoard islandBoard = new IslandBoard();
	
	// Create 2D array of IslandTiles to represent game board
	private IslandTile[][] boardStructure;
	
	private FloodDeck floodDeck;
	private FloodDiscardPile floodDiscardPile;
	
	// Fills structure with IslandTile Enum types
	private IslandBoard() {
		
		// get instances of required classes
		floodDeck = FloodDeck.getInstance();
		floodDiscardPile = FloodDiscardPile.getInstance();
		
		// Source all IslandTiles form Enum values
		Stack<IslandTile> islandTiles = new Stack<>();
		
		// Add all island tiles to stack when instantiated
		islandTiles.addAll(Arrays.asList(IslandTile.values()));
		
		// Shuffle the tiles in the stack
		Collections.shuffle(islandTiles);
		
		// Assign appropriate lengths to 2D structure, can change this to for loop if needed
		boardStructure = new IslandTile[6][];
		boardStructure[0] = new IslandTile[2];	//		  [][]
		boardStructure[1] = new IslandTile[4];	//		[][][][]
		boardStructure[2] = new IslandTile[6];	//	  [][][][][][]	
		boardStructure[3] = new IslandTile[6];	//	  [][][][][][]
		boardStructure[4] = new IslandTile[4];	//		[][][][]
		boardStructure[5] = new IslandTile[2];	//		  [][]
		
		for (int i = 0; i < boardStructure.length; i++) {
			for (int j = 0; j < boardStructure[i].length; j++) {
				boardStructure[i][j] = islandTiles.pop();
				// TODO: ADD to TreeMap / HashMap tile location reference
			}
		}
		
//		//Add sea tiles
//		for(int i=0; i<4; i++) {
//			for(int j=3-i; j>0; j--) {
//				boardStructure[i][j] = new SeaTile();
//				boardStructure[i][7-j] = new SeaTile();
//				boardStructure[7-i][j] = new SeaTile();
//				boardStructure[7-i][7-j] = new SeaTile();
//			}
//		}
//		//Add Island tiles
//		for(int i=0; i<4; i++) {
//			for(int j=3; j>3-i; j--) {
//				boardStructure[i][j] = tiles.pop();
//				boardStructure[i][7-j] = tiles.pop();
//				boardStructure[7-i][j] = tiles.pop();
//				boardStructure[7-i][7-j] = tiles.pop();
//			}
//		}
	}
	
	
	public static IslandBoard getInstance() {
		return islandBoard;
	}
	
	/**
	 * method to start island sinking at beginning of game
	 */
	public void startSinking() {
		
		for (int i = 0; i < 6; i++) {
			
			// Draw FloddCard from deck
			FloodCard fc = floodDeck.drawCard();
			
			// Flood corresponding IslandTile on board
			islandBoard.floodTile(fc);

			// Add card to flood discard pile
			floodDiscardPile.addCard(fc);
		}
		//TODO: Print out tiles that were flooded
	}
	
	/**
	 * toString method to display current state of IslandBoard
	 * and to visualise the island in the correct format
	 */
	@Override
	public String toString() {
		String outputString = "";
		int tileCharWidth = 25; // change to make tiles wider
		String vertBars = "-".repeat(tileCharWidth);
		
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
				
				// add specific tile details
				outputString += "| ";
				outputString += String.format("%-" + (tileCharWidth - 4) + "s",
						String.format("%" + ((tileCharWidth - 4 + (boardStructure[i][j].toString()).length()) / 2) + "s", boardStructure[i][j]));
				outputString += " |";
			}

			// add bottom bar of island tiles
			outputString += "\n" + " ".repeat(tileCharWidth * rowOffset) + vertBars.repeat(rowLength)
							+ " ".repeat(tileCharWidth * rowOffset) + "\n";
		}
		return outputString;
	}
	
	/**
	 * Takes a flood deck card and either floods or removes corresponding tile
	 * @return true if successful, false if tile already removed
	 */
	public boolean floodTile(FloodCard fc) {

		// TODO: save tile locations at start instead of searching each time?
		for (int i = 0; i < boardStructure.length; i++) {
			for (int j = 0; j < boardStructure[i].length; j++) {
				
//				if(boardStructure[i][j] != null) {
				
				//If island tile has same name as card (could also have associated tile for card)
//				if ( boardStructure[i][j].toString().equals(card.toString()) ) {
				if (boardStructure[i][j].equals(fc.getCorrespondingIslandTile())) {
					// TODO: implement equals() method in IslandTile to compare enum value
					
					//If not flooded
					if(!boardStructure[i][j].isFlooded()) {
						//Flood tile
						boardStructure[i][j].setToFlooded();
					} else {
						//Else remove - does setting to null work?????
						boardStructure[i][j] = null;
					}
					return true;
				}
				
			}
			
		}
		//Action doesn't count as turn if you couldn't use card? Check before this?
		return false;
	}
	
	
	
}
