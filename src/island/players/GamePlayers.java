package island.players;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import island.components.IslandTile;
import island.components.Treasure;

/**
 * Class to represent the group of Players currently
 * playing the game.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GamePlayers {
	
	// Instantiate singleton
	private static GamePlayers gamePlayers = new GamePlayers();
	private int playerCount;
	private List<Player> playersList;
	private List<Treasure> capturedTreasures; //Better for each player to have individually? Think they're just pooled in rules
	
	private GamePlayers() {
		playersList = new ArrayList<Player>();
		capturedTreasures = new ArrayList<Treasure>();
		playerCount = 0;
	}
	
	public static GamePlayers getInstance() {
		return gamePlayers;
	}
	
	public void addPlayers(Scanner userInput) {

		System.out.println("How many players are there?");
		playerCount = Integer.parseInt(userInput.nextLine());
		// TODO: check for max allowed player count
		
		// create list of possible roles players can have
		List<String> possibleRoles = Arrays.asList("Diver", "Engineer", "Explorer", 
				"Messenger", "Navigator", "Pilot");
		
		// randomise list order
		Collections.shuffle(possibleRoles); 
		
		// iterate over number of players
		for (int i = 0; i < playerCount; i++) {
			
			System.out.println("Enter the name of Player " + (i+1) + ":");
			String playerName = userInput.nextLine();
			
			// TODO: check if player name already given
			
			// instantiate specific player subclasses 
			switch (possibleRoles.get(i)) {

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
	
	public void setInitialPositions() {
		for(Player p : playersList) {
			p.setCurrentTile(IslandTile.FOOLS_LANDING);
		}
	}
	
	public void addTreasure(Treasure t) {
		capturedTreasures.add(t);
	}
	
	public List<Player> getPlayersList() {
		return playersList;
	}

}
