package island.components;

import island.players.Player;

/**
 * Pawns are to be place on an IslandTile on the game board
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Pawn {
	
	private int[] location; // co-ordinates of pawn on game board
	private Player player;
	
	public Pawn(Player newPlayer) {
		this.player = newPlayer;
	}
	
	public void move(IslandTile tile) {
		// TODO: call islandBoard.move() ???
	}
	
	
	// TODO: handle all of the movement checking in here !

}
