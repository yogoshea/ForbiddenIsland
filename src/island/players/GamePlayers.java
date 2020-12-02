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
	
	/**
	 * Constructor to instantiate list attributes
	 */
	private GamePlayers() {
		playersList = new ArrayList<Player>();
		capturedTreasures = new ArrayList<Treasure>();
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
	 * Allow iteration over game players
	 */
	@Override
	public Iterator<Player> iterator() {
		return playersList.iterator();
	}
	
	/**
	 * Assigns in-game roles to each new player
	 * @param String list containing names of new players
	 */
	public void assignPlayerRoles(List<String> playerNames) {

		// create stack of possible roles players can have
		Stack<String> possibleRoles = new Stack<String>();
		possibleRoles.addAll(Arrays.asList("Diver", "Engineer", "Explorer", 
				"Messenger", "Navigator", "Pilot"));
		
		// randomise stack order
		Collections.shuffle(possibleRoles); 
		
		// iterate over number of players
		for (String playerName : playerNames) {
			
			// TODO: check if player name already given
			
			// instantiate specific player subclasses 
			switch (possibleRoles.pop()) {

				case "Diver":
					playersList.add(new Diver(playerName));
					break;
					
				case "Engineer":
					playersList.add(new Engineer(playerName));
					break;
				
				case "Explorer":
					playersList.add(new Explorer(playerName));
					break;
				
				case "Messenger":
					playersList.add(new Messenger(playerName));
					break;
					
				case "Navigator":
					playersList.add(new Navigator(playerName));
					break;
					
				case "Pilot":
					playersList.add(new Pilot(playerName));
					break;
			}
		}
	}
	

	// TODO: change this to go inside Player subclasses
	public List<Treasure> getCapturedTreasures() {
		return capturedTreasures;
	}
	
	/*
	 * Method to check if all treasures have been captured
	 * @return true if they all have, false otherwise
	 */
	public boolean allTreasuresCaptured() {
		
		for(Treasure t : Arrays.asList(Treasure.values())) {
			if( !capturedTreasures.contains(t) ) {
				return false;
			}
		}
		return true;
	}
	

//	public void setInitialPositions() {
//		for(Player p : playersList) {
//			p.setCurrentTile(IslandTile.FOOLS_LANDING);
//		}
//	}
	
	public void addTreasure(Treasure t) {
		capturedTreasures.add(t);
	}
	
	public List<Player> getPlayersList() {
		return playersList;
	}



}
