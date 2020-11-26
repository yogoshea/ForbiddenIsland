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
import island.game.GameOverObserver;
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
	private GamePlayers players;
	
	// Fills structure with IslandTile Enum types
	private IslandBoard() {
		
		// get instances of required classes
		floodDeck = FloodDeck.getInstance();
		floodDiscardPile = FloodDiscardPile.getInstance();
		players = GamePlayers.getInstance();
		
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
		
	}
	
	
	public static IslandBoard getInstance() {
		return islandBoard;
	}
	
	/**
	 * method to start island sinking at beginning of game
	 */
	public void startSinking() {
		
		for (int i = 0; i < 6; i++) {
			
			// Draw FloodCard from deck
			FloodCard fc = floodDeck.drawCard();
			//TODO: Flood tile within drawCard method??
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
	// TODO: move this to GameView
	@Override
	public String toString() { //TODO: print players, treasures and flood status of each tile
		String outputString = "";
		int tileCharWidth = 25; // change to make tiles wider
		String vertBars = "-".repeat(tileCharWidth);
		Map<IslandTile, Player> playerLocations = new HashMap<IslandTile, Player>();
		
		
		// get player positions TODO: move this to getPlayerLocations method
		for (Player p : players.getPlayersList()) {
			playerLocations.put(p.getCurrentTile(), p);
		}
		
		
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
				
				// add specific tile status TODO: change to Enum values with Strings "--- Not Flooded ---", "~~~ Flooded ~~~", "XXX Sank XXX"
				outputString += "| ";
				outputString += String.format("%-" + (tileCharWidth - 4) + "s",
						String.format("%" + ((tileCharWidth - 4 + (boardStructure[i][j].isFlooded().toString()).length()) / 2) + "s", boardStructure[i][j].isFlooded().toString()));
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
			}
			

			// add bottom bar of island tiles
			outputString += "\n" + " ".repeat(tileCharWidth * rowOffset) + vertBars.repeat(rowLength)
							+ " ".repeat(tileCharWidth * rowOffset) + "\n";
		}
		
		return outputString;
	}
	
	/**
	 * Takes a tile and returns a list of adjacent island tiles (which methods should be static in singleton?)
	 */
	public List<IslandTile> findAdjacentTiles(IslandTile tile) {
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
	public boolean floodTile(FloodCard fc) {
		int[] pos = findTileLocation(fc.getCorrespondingIslandTile());
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