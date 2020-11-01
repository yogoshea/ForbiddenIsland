package island.players;

import island.board.IslandTile;
import island.board.Pawn;

/**
 * Player is an abstract class to describe the common behaviour of
 * the player types present in the Forbidden Island game, for
 * example Engineer, Messenger etc.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public abstract class Player {

	private String name;
	private IslandTile startingTile;
	private Pawn pawn;
	
	public Player(String name) {
		this.name = name;
	}
	
	public void takeAction() {
		
	}
	
	private void move() {
		
	}
	
	private void shoreUp() {
		
	}
	
	private void giveTreasureCard() {
		
	}
	
	public void receiveCard() {
		
	}
	
	// getters and setters
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public IslandTile getStartingTile() {
		return startingTile;
	}
}
