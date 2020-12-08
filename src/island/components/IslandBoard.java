package island.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import island.observers.Observer;
import island.observers.Subject;

/**
 * IslandBoard class represents island board and holds tiles
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class IslandBoard implements Subject {
	
	// Instantiate singleton
	private static IslandBoard islandBoard = new IslandBoard();
	
	// Create 2D array of IslandTiles to represent game board
	private IslandTile[][] boardStructure;
	
//	private FloodDeck floodDeck;
//	private FloodDiscardPile floodDiscardPile;
//	private Map<Pawn,IslandTile> pawnLocations;
	private List<Pawn> pawns = new ArrayList<Pawn>();

	private List<Observer> observers = new ArrayList<Observer>();
	
//	// Small inner class to store IslandTile coordinates for quicker access TODO: make sure this is okay to do!
//	private class Coordinate {
//		int row;
//		int column;
//		Coordinate(int row, int column) {
//			this.row = row;
//			this.column = column;
//		}
//	}
	private Map<IslandTile, Coordinate> tileCoordinates;
	
	// Fills structure with IslandTile Enum types
	private IslandBoard() {
		
		// get instances of required classes
//		floodDeck = FloodDeck.getInstance();
//		floodDiscardPile = FloodDiscardPile.getInstance();
		
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
	public List<IslandTile> getAdjacentTiles(IslandTile tile) { //TODO: update to make use of Coordinates
		//TODO: Give IslandTile a location variable??
		List<IslandTile> adjTiles = new ArrayList<IslandTile>();
		Coordinate currentCoord = tileCoordinates.get(tile);
		
		// TODO: change below to work with Coordinate
		int[] currentPos = new int[2];
		currentPos[0] = currentCoord.getRow();
		currentPos[1] = currentCoord.getColumn();
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
	
	public List<Pawn> getPawns() {
		return this.pawns;
	}
	
//	public void setPawnLocation(Pawn playerPawn, IslandTile islandTile) {
//		pawnLocations.put(playerPawn, islandTile);
//	}
//	
//	public boolean isPawnOnTile(IslandTile islandtile) {
//		for (IslandTile tileToCheck : pawnLocations.values()) {
//			if (tileToCheck.equals(islandtile)) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	public Pawn getPawnOnTile(IslandTile islandTile) { // TODO: might throw null pointer exception
//		return 
//	}
//	
//	public  Map<Pawn,IslandTile> getPawnLocations() {
//		return this.pawnLocations;
//	}

	/**
	 * Gets instance of specific island tile placed on board
	 * @param IslandTile to retrieve from board
	 * @return IslandTile instance present within IslandBoard
	 */
	public IslandTile getTile(IslandTile islandTile) {
		Coordinate tileCoord = tileCoordinates.get(islandTile);
		return this.boardStructure[tileCoord.getRow()][tileCoord.getColumn()];
	}
	
	/**
	 * Forms List of IslandTile instances
	 * @return instances of each IslandTile on IslandBoard
	 */
	public List<IslandTile> getAllTiles() { // TODO: maybe implement iterator for IslandBoard if time
		List<IslandTile> allTiles = new ArrayList<IslandTile>();
		for (int i = 0; i < boardStructure.length; i++) {
			for (int j = 0; j < boardStructure[i].length; j++) {
					allTiles.add(boardStructure[i][j]);
			}
		}
		return allTiles;
	}
	
	/**
	 * Forms List of FLOODED IslandTile instances
	 * @return instances of FLOODED IslandTiles on IslandBoard
	 */
	public List<IslandTile> getFloodedTiles(){
		
		List<IslandTile> floodedTiles = new ArrayList<IslandTile>();
		
		for(IslandTile tile: getAllTiles()) {
			if(tile.isFlooded()) {
				floodedTiles.add(tile);
			}
		}
		return floodedTiles;
	}
	
	/**
	 * Forms List of SAFE and FLOODED IslandTile instances
	 * @return instances of SAFE and FLOODED IslandTiles on IslandBoard
	 */
	public List<IslandTile> getNonSunkTiles(){
		
		List<IslandTile> nonSunkTiles = new ArrayList<IslandTile>();
		
		for(IslandTile tile: getAllTiles()) {
			if(!tile.isSunk()) {
				nonSunkTiles.add(tile);
			}
		}
		return nonSunkTiles;
	}
	
	/**
	 * Identifies IslandTiles where Treasures are located
	 * @return instances of IslandTile with associated Treasures
	 */
	public List<IslandTile> getTreasureTiles() { // TODO: maybe implement iterator for IslandBoard if time
		List<IslandTile> treasureTiles = new ArrayList<IslandTile>();
		for (int i = 0; i < boardStructure.length; i++) {
			for (int j = 0; j < boardStructure[i].length; j++) {
				if(boardStructure[i][j].getAssociatedTreasure() != null) {
					treasureTiles.add(boardStructure[i][j]);
				}
			}
		}
		return treasureTiles;
	}

	@Override
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	@Override
	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}

}
	
	
//	/**
//	 * Takes a flood deck card and either floods or removes corresponding tile
//	 * @return true if successful, false if tile already removed
//	 */
//	public boolean floodOrSinkTile(IslandTile tile) {
//		int[] pos = getTileLocation(tile);
//		int i = pos[0];
//		int j = pos[1];
//		if(i >= 0 && j >= 0) {
//			//If not flooded
//			if(!boardStructure[i][j].isFlooded()) {
//				//Flood tile
//				boardStructure[i][j].setToFlooded();
//			} else {
//				
//				//If player on tile, give chance to move
//				//TODO: should this happen via observer?
//				for(Player p : GamePlayers.getInstance().getPlayersList()) {
//					if(p.getCurrentTile().equals(boardStructure[i][j])) {
//						//p.move(userScanner);
//					}
//				}
//				System.out.println(boardStructure[i][j].name()+ "has sunk!!!!"); // TODO: change this to notify SunkObserver
//				boardStructure[i][j] = null; //TODO: set to enum Sunk
//				//Alert gameOverObserver that something happened which may cause game to be over
//				//notifyAllObservers();
//			}
//			return true;
//		}
//		//Action doesn't count as turn if you couldn't use card? Check before this?
//		return false;
//	}
	
//	/**
//	 * Takes an IslandTile and shores-up corresponding tile on board
//	 * @return true if successful, false if not
//	 */
//	public boolean shoreUp(IslandTile t) {
//		int[] pos = getTileLocation(t);
//		int i = pos[0];
//		int j = pos[1];
//		if(i >= 0 && j >= 0) {
//			return boardStructure[i][j].shoreUp();
//		}
//		return false;
//	}
	
//	/**
//	 * Takes an island tile and finds its position on board
//	 * @return position in array: [x,y]. returns [-1,-1] if not found
//	 */
//	public int[] getTileLocation(IslandTile tile) {
//		// TODO: save tile locations at start instead of searching each time?
//		int[] pos = {-1,-1}; // [row, column]
//		for (int i = 0; i < boardStructure.length; i++) {
//			for (int j = 0; j < boardStructure[i].length; j++) {
//				
//				if (boardStructure[i][j].equals(tile)) {
//					// TODO: implement equals() method in IslandTile to compare enum value
//					pos[0] = i;
//					pos[1] = j;
//				}	
//			}
//		}
//		return pos;
//	}
//	
	
//	public List<IslandTile> getFloodedTiles() {
//		List<IslandTile> floodedTiles = new ArrayList<IslandTile>();//Arraylist good?
//		
//		for (int i = 0; i < boardStructure.length; i++) {
//			for (int j = 0; j < boardStructure[i].length; j++) {
//				
//				if(boardStructure[i][j].isFlooded()) {
//					floodedTiles.add(boardStructure[i][j]);
//				}
//			}
//		}
//		
//		return floodedTiles;
//	}

