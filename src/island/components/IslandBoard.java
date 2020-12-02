package island.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import island.cards.FloodCard;
import island.decks.FloodDeck;
import island.decks.FloodDiscardPile;
import island.observers.GameOverObserver;
import island.players.GamePlayers;
import island.players.Player;

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
	private Map<Pawn,IslandTile> pawnLocations;
	
	// Small inner class to store IslandTile coordinates for quicker access TODO: make sure this is okay to do!
	private class Coordinate {
		int row;
		int column;
		Coordinate(int row, int column) {
			this.row = row;
			this.column = column;
		}
	}
	private Map<IslandTile, Coordinate> tileCoordinates;
	
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
		
		// Assign appropriate lengths to 2D structure TODO: can change this to for loop if needed
		boardStructure = new IslandTile[6][];
		boardStructure[0] = new IslandTile[2];	//		  [][]
		boardStructure[1] = new IslandTile[4];	//		[][][][]
		boardStructure[2] = new IslandTile[6];	//	  [][][][][][]	
		boardStructure[3] = new IslandTile[6];	//	  [][][][][][]
		boardStructure[4] = new IslandTile[4];	//		[][][][]
		boardStructure[5] = new IslandTile[2];	//		  [][]
		
		// create HashMap to store locations
		tileCoordinates = new HashMap<IslandTile, Coordinate>();
		
		for (int i = 0; i < boardStructure.length; i++) {
			for (int j = 0; j < boardStructure[i].length; j++) {
				boardStructure[i][j] = islandTiles.pop();
				tileCoordinates.put(boardStructure[i][j], new Coordinate(i,j));
			}
		}
		
	}
	
	/**
	 * @return single instance of IslandBoard
	 */
	public static IslandBoard getInstance() {
		if (islandBoard == null) {
			islandBoard = new IslandBoard();
		}
		return islandBoard;
	}
	
	/**
	 * Getter method for Island board structure
	 * @return current 2D array of IslandTiles
	 */
	public IslandTile[][] getBoardStructure() {
		return boardStructure;
	}
	
	// TODO: the methods below are too long, maybe add getTileAbove(), getTileBelow()... shorter methods
	// Then these should be called from Controller classes
	
	/**
	 * Takes a tile and returns a list of adjacent island tiles (which methods should be static in singleton?)
	 */
	public List<IslandTile> findAdjacentTiles(IslandTile tile) { //TODO: update to make use of Coordinates
		//TODO: Give IslandTile a location variable??
		List<IslandTile> adjTiles = new ArrayList<IslandTile>();
		int[] currentPos = findTileLocation(tile);
		if(currentPos[0] < 0 || currentPos[1] < 0) {
			return adjTiles;
		}
		int curRowLength = boardStructure[currentPos[0]].length;
		int curRowOffset = ((curRowLength * 2) % 6) / 2;
		
		//First search left and right
		for(int i = -1; i <= 1; i += 2) {
			int searchCol = currentPos[1] + i;
			if(searchCol >= 0 && searchCol <= 5 - (2*curRowOffset)) { //if within bounds of board
				if(boardStructure[currentPos[0]][searchCol] != null) { //or use instanceof IslandTile??
					adjTiles.add(boardStructure[currentPos[0]][searchCol]);
				}
			}
		}
		
		//Then search up and down
		for(int i = -1; i <= 1; i += 2) {
			int searchRow = currentPos[0] + i;
			if(searchRow >= 0 && searchRow <= 5) {
				//takes offsets into account with 'shift'
				int searchRowLength = boardStructure[searchRow].length;
				int searchRowOffset = ((searchRowLength * 2) % 6) / 2;
				int shift = curRowOffset - searchRowOffset;
				int searchCol = currentPos[1] + shift;
				if(searchCol >= 0 && searchCol <= 5 - (2*searchRowOffset)) {
					if(boardStructure[searchRow][searchCol] != null) {
						adjTiles.add(boardStructure[searchRow][searchCol]);
					}
				}
			}
		}
		return adjTiles;
	}
	
	
	/**
	 * Takes a flood deck card and either floods or removes corresponding tile
	 * @return true if successful, false if tile already removed
	 */
	public boolean floodTile(IslandTile tile) {
		int[] pos = findTileLocation(tile);
		int i = pos[0];
		int j = pos[1];
		if(i >= 0 && j >= 0) {
			//If not flooded
			if(!boardStructure[i][j].isFlooded()) {
				//Flood tile
				boardStructure[i][j].setToFlooded();
			} else {
				//Else sink tile - does setting to null work?????
				//If player on tile, give chance to move
				for(Player p : GamePlayers.getInstance().getPlayersList()) {
					if(p.getCurrentTile().equals(boardStructure[i][j])) {
						//p.move(userScanner);
					}
				}
				System.out.println(boardStructure[i][j].name()+ "has sunk!!!!"); // TODO: change this to notify SunkObserver
				boardStructure[i][j] = null;
				//Alert gameOverObserver that something happened which may cause game to be over
				GameOverObserver.getInstance().checkIfGameOver();
			}
			return true;
		}
		//Action doesn't count as turn if you couldn't use card? Check before this?
		return false;
	}
	
	/**
	 * Takes an IslandTile and shores-up corresponding tile on board
	 * @return true if successful, false if not
	 */
	public boolean shoreUp(IslandTile t) {
		int[] pos = findTileLocation(t);
		int i = pos[0];
		int j = pos[1];
		if(i >= 0 && j >= 0) {
			return boardStructure[i][j].shoreUp();
		}
		return false;
	}
	
	/**
	 * Takes an island tile and finds its position on board
	 * @return position in array: [x,y]. returns [-1,-1] if not found
	 */
	public int[] findTileLocation(IslandTile tile) {
		// TODO: save tile locations at start instead of searching each time?
		int[] pos = {-1,-1}; // [row, column]
		for (int i = 0; i < boardStructure.length; i++) {
			for (int j = 0; j < boardStructure[i].length; j++) {
				
				if (boardStructure[i][j].equals(tile)) {
					// TODO: implement equals() method in IslandTile to compare enum value
					pos[0] = i;
					pos[1] = j;
				}	
			}
		}
		return pos;
	}
	
	
	public List<IslandTile> getFloodedTiles() {
		List<IslandTile> floodedTiles = new ArrayList<IslandTile>();//Arraylist good?
		
		for (int i = 0; i < boardStructure.length; i++) {
			for (int j = 0; j < boardStructure[i].length; j++) {
				
				if(boardStructure[i][j].isFlooded()) {
					floodedTiles.add(boardStructure[i][j]);
				}
			}
		}
		
		return floodedTiles;
	}
	


}