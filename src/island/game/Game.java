package island.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import island.board.IslandBoard;
import island.board.IslandTileStack;
import island.players.Diver;
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
	
	public Game() {

		//Create a stack of all the island tiles
		IslandTileStack islandTileStack = new IslandTileStack();//Do this inside IslandBoard constructor?
		//Shuffle it - do this inside IslandTileStack??
		islandTileStack.shuffle();
		//Create island board with this stack of tiles
		IslandBoard islandBoard = new IslandBoard(islandTileStack.getIslandTileStack());
		
		addPlayers();
		
	}
	
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
			
			System.out.println("Enter name of Player " + i);
			String playerName = userInput.nextLine();
			
			// instantiate specific player subclasses 
			switch (possibleRoles.get(i)) {

				case "Diver":
					players.add(new Diver(playerName));
					break;
					
				// add rest of player subclasses here
				
			}
				
			
		}
		
	}
}
