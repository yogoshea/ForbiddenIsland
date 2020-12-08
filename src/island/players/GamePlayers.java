package island.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import island.components.IslandTile;
import island.components.Treasure;

/**
 * Class to represent the group of Players currently
 * playing the game.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GamePlayers implements Iterable<Player> {
	
	// Instantiate singleton
	private static GamePlayers gamePlayers;
	
	private List<Player> playersList;
	private List<Treasure> capturedTreasures; //TODO: Better for each player to have individually? Think they're just pooled in rules
	private int playerCount;
	
	/**
	 * Constructor to instantiate GamePlayers class
	 */
	private GamePlayers() {
		playersList = new ArrayList<Player>();
		capturedTreasures = new ArrayList<Treasure>();
		playerCount = 0; // set player count to zero at beginning
	}
	
	/**
	 * @return single instance of game players
	 */
	public static GamePlayers getInstance() {
		if (gamePlayers == null) {
			gamePlayers = new GamePlayers();
		}
		return gamePlayers;
	}
	
	/**
	 * Allows iteration over game players
	 */
	@Override
	public Iterator<Player> iterator() {
		return playersList.iterator();
	}
	
	/**
	 * Getter method for Treasure captured by Players
	 * @return List of Treasures already captured by gamem players
	 */
	public List<Treasure> getCapturedTreasures() {
		return capturedTreasures;
	}
	
	/**
	 * Method to check if all treasures have been captured
	 * @return true if they all have, false otherwise
	 */
	public boolean allTreasuresCaptured() {
		
		// Iterate of all Treasure values
		for (Treasure t : Arrays.asList(Treasure.values())) {
			if (! capturedTreasures.contains(t)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Add treasure to List of captured treasures
	 * @param Treasure to add to GamePlayer's collection
	 */
	public void addTreasure(Treasure treasure) {
		capturedTreasures.add(treasure);
	}
	
	/**
	 * Getter method for the List of Players playing the game
	 * @return List of Players of game
	 */
	public List<Player> getPlayersList() {
		return playersList;
	}

}
