package island.board;

import java.util.Stack;

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
		gameBoard[0] = new IslandTile[2];
		gameBoard[1] = new IslandTile[4];
		gameBoard[2] = new IslandTile[6];
		gameBoard[3] = new IslandTile[6];
		gameBoard[4] = new IslandTile[4];
		gameBoard[5] = new IslandTile[2];
		
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
	
	@Override
	public String toString() {
		String outputString = "";
		for (int i = 0; i < gameBoard.length; i++) {
			for (int j = 0; j < gameBoard[i].length; j++) {
				outputString += gameBoard[i][j].toString();
				outputString += "\t";
			}
			outputString += "\n";
		}
		return outputString;
	}
	
}
