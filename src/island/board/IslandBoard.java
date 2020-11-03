package island.board;

import java.util.Stack;

import island.cards.FloodDeckCard;

/**
 * IslandBoard class represents island board and holds tiles
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */

//I created the board as just a 2D array of tiles here.
//Thought it would be better to have tiles in a proper grid -> keep moving between tiles simple
//Possibly not a good way to do it

//Potential problems: Duplicates, ?

//Alternative method: Multidimensional Collection -> would this be hard to move player around?
//Better to not use SeaTiles? Yeah, I don't think we need them

public class IslandBoard {
	
	// Create 2D array of IslandTiles to represent game board
	private IslandTile[][] gameBoard;
	
	//Takes stack of island tiles as input and and puts them in island positions.
	//Assumes stack already shuffled
	//Puts sea tiles in remaining positions - 8x8 array so island surrounded by sea
	public IslandBoard(Stack<IslandTile> islandTiles) {
		
		// Assign appropriate lengths to 2D structure, can change this to for loop if needed
		gameBoard = new IslandTile[6][];
		gameBoard[0] = new IslandTile[2];	//		  [][]
		gameBoard[1] = new IslandTile[4];	//		[][][][]
		gameBoard[2] = new IslandTile[6];	//	  [][][][][][]	
		gameBoard[3] = new IslandTile[6];	//	  [][][][][][]
		gameBoard[4] = new IslandTile[4];	//		[][][][]
		gameBoard[5] = new IslandTile[2];	//		  [][]
		
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[i].length; j++) {
				gameBoard[i][j] = islandTiles.pop();
			}
		}
		
//		//Add sea tiles
//		for(int i=0; i<4; i++) {
//			for(int j=3-i; j>0; j--) {
//				gameBoard[i][j] = new SeaTile();
//				gameBoard[i][7-j] = new SeaTile();
//				gameBoard[7-i][j] = new SeaTile();
//				gameBoard[7-i][7-j] = new SeaTile();
//			}
//		}
//		//Add Island tiles
//		for(int i=0; i<4; i++) {
//			for(int j=3; j>3-i; j--) {
//				gameBoard[i][j] = tiles.pop();
//				gameBoard[i][7-j] = tiles.pop();
//				gameBoard[7-i][j] = tiles.pop();
//				gameBoard[7-i][7-j] = tiles.pop();
//			}
//		}
	}
	
	/* Describe relationship between 2D arrays and island shape:
	 *
	 * row length % 3 
	 */
	
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
		for (int i = 0; i < gameBoard.length; i++) {
			
			int rowLength = gameBoard[i].length;
			int rowOffset = ((rowLength * 2) % 6) / 2; // offset needed to from island structure

			// add top bar of island tiles
			outputString += " ".repeat(tileCharWidth * rowOffset) + vertBars.repeat(rowLength)
							+ " ".repeat(tileCharWidth * rowOffset) + "\n";
			
			// add row structure offset before
			outputString += " ".repeat(tileCharWidth * rowOffset);
			
			// iterate over island grid columns
			for (int j = 0; j < gameBoard[i].length; j++) {
				
				// add specific tile details
				outputString += "| ";
				outputString += String.format("%-" + (tileCharWidth - 4) + "s",
						String.format("%" + ((tileCharWidth - 4 + (gameBoard[i][j].toString()).length()) / 2) + "s", gameBoard[i][j]));
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
	public boolean floodTile(FloodDeckCard card) {
		//Search through game board - should we save tile locations at start instead of searching each time?
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[i].length; j++) {
				
				if(gameBoard[i][j] != null) {
				
					//If island tile has same name as card (could also have associated tile for card)
					if ( gameBoard[i][j].toString().equals(card.toString()) ) {
						
						//If not flooded
						if(!gameBoard[i][j].isFlooded()) {
							//Flood tile
							gameBoard[i][j].setToFlooded();
						} else {
							//Else remove - does setting to null work?????
							gameBoard[i][j] = null;
						}
						return true;
					}
				}
				
			}
			
		}
		//Action doesn't count as turn if you couldn't use card? Check before this?
		return false;
	}
	
	
	
}
