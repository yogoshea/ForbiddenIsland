package island.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import island.board.IslandBoard;
import island.board.IslandTile;
import island.board.IslandTileName;
import island.board.IslandTileStack;
import island.players.Diver;
import island.players.Engineer;
import island.players.Explorer;
import island.players.Messenger;
import island.players.Navigator;
import island.players.Pilot;
import island.players.Player;

/**
 * Game class describes in full the status of the game
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Game {
	
	// Game attributes describing the current state of the game
	private int playerCount;
	private List<Player> players;
	private IslandBoard islandBoard;
	private IslandTileStack islandTileStack;
	
	/**
	 * Game constructor, instantiates all required game components
	 */
	public Game() {

		createIsland();
		addPlayers();

	}
	
	/**
	 * method to handle the creation of the island tiles and board
	 */
	private void createIsland() {
		
		// Instantiate stack of island tiles
		IslandTileStack islandTiles = new IslandTileStack(); // maybe just put this in IslandBoard constructor?
//		for (IslandTile t : test) {
//			System.out.println(t);
//		}
		
		// Create island board with this stack of island tiles
		IslandBoard islandBoard = new IslandBoard(islandTiles);
		
		// draw cards from stack into data structure IslandBoard...
	}
	
	/**
	 * method to handle the creation of the game's players
	 */
	private void addPlayers() {

		// create scanner to read input from players
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("How many players are there?");
		playerCount = Integer.parseInt(userInput.nextLine());
		
		// instantiate players as ArrayList with initial capacity playerCount
		players = new ArrayList<Player>(playerCount);
		
		// create list of possible roles players can have
		List<String> possibleRoles = Arrays.asList("Diver", "Engineer", "Explorer", 
				"Messenger", "Navigator", "Pilot");
		
		// randomise list order
		Collections.shuffle(possibleRoles); 
		
		// iterate over number of players
		for (int i = 0; i < playerCount; i++) {
			
			System.out.println("Enter the name of Player " + (i+1) + ":");
			String playerName = userInput.nextLine();
			
			// instantiate specific player subclasses 
			switch (possibleRoles.get(i)) {

				case "Diver":
					players.add(new Diver(playerName));
					break;
					
				case "Engineer":
					players.add(new Engineer(playerName));
					break;
				
				case "Explorer":
					players.add(new Explorer(playerName));
					break;
				
				case "Messenger":
					players.add(new Messenger(playerName));
					break;
					
				case "Navigator":
					players.add(new Navigator(playerName));
					break;
					
				case "Pilot":
					players.add(new Pilot(playerName));
					break;
				
			}
		}
		
		// close Scanner
		userInput.close();
	}
	
	// getters and setters
	
	/**
	 * @return List of Player's playing the game
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	
}
