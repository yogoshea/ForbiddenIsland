package island.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * IslandBoard class represents island board and the island tiles it consists of.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class IslandBoard {
	
	// Singleton instance
	private static IslandBoard islandBoard;
	
	private IslandTile[][] boardStructure; // 2D array of IslandTiles to represent game board
	private Map<IslandTile, Coordinate> tileCoordinates;
	
	/**
	 * Private constructor for IslandBoard singleton.
	 */
	private IslandBoard() {
		
		// Source all IslandTiles form Enum values
		Stack<IslandTile> islandTiles = new Stack<>();
		
		// Add all island tiles to stack when instantiated
		islandTiles.addAll(Arrays.asList(IslandTile.values()));
		
		// Shuffle the tiles in the stack
		Collections.shuffle(islandTiles);
		
		// Assign appropriate lengths to 2D structure
		boardStructure = new IslandTile[6][];
		boardStructure[0] = new IslandTile[2];	//		  [][]
		boardStructure[1] = new IslandTile[4];	//		[][][][]
		boardStructure[2] = new IslandTile[6];	//	  [][][][][][]	
		boardStructure[3] = new IslandTile[6];	//	  [][][][][][]
		boardStructure[4] = new IslandTile[4];	//		[][][][]
		boardStructure[5] = new IslandTile[2];	//		  [][]
		
		// Store locations in HashMap
		tileCoordinates = new HashMap<IslandTile, Coordinate>();
		for (int i = 0; i < boardStructure.length; i++) {
			for (int j = 0; j < boardStructure[i].length; j++) {
				boardStructure[i][j] = islandTiles.pop();
				tileCoordinates.put(boardStructure[i][j], new Coordinate(i,j));
			}
		}
		
	}
	
	/**
	 * Getter method for singleton instance
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
	
	/**
	 * Finds the island tiles adjacent to a given island tile. Does not return sunk tiles
	 * @param Specific IslandTile to retrieve adjacent tiles for.
	 * @return List containing relevant IslandTile instances.
	 */
	public List<IslandTile> getAdjacentTiles(IslandTile tile) {
		
		// Error threshold when equating doubles
		final double ERROR_THRESHOLD = 0.0001;

		List<IslandTile> adjTiles = new ArrayList<IslandTile>();
		for (IslandTile otherTile : this.getNonSunkTiles()) {
			if (Math.abs(Coordinate.calcDistanceBetweenCoordinates(tileCoordinates.get(tile), tileCoordinates.get(otherTile)) - 1.0) < ERROR_THRESHOLD) {
				adjTiles.add(otherTile);
			}
		}
		return adjTiles;
	}
	
	/**
	 * Forms List of IslandTile instances
	 * @return instances of each IslandTile on IslandBoard
	 */
	public List<IslandTile> getAllTiles() {
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
			if(! tile.isSunk()) {
				nonSunkTiles.add(tile);
			}
		}
		return nonSunkTiles;
	}
	
	/**
	 * Identifies IslandTiles where Treasures are located
	 * @return instances of IslandTile with associated Treasures
	 */
	public List<IslandTile> getTreasureTiles() {
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

	/**
	 * Computes distance between two specified island tiles.
	 * @param First IslandTile instance.
	 * @param Second IslandTile instance.
	 * @return double value representing the distance between island tiles.
	 */
	public double calcDistanceBetweenTiles(IslandTile aTile, IslandTile otherTile) {
		return Coordinate.calcDistanceBetweenCoordinates(tileCoordinates.get(aTile), tileCoordinates.get(otherTile));
	}
	
	// Singleton reset for JUnit testing
	public static void reset() {
		for (IslandTile tile : IslandTile.values())
			tile.setToSafe();
		islandBoard = null;
	}

}