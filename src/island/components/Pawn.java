package island.components;

import island.players.Player;

/**
 * Pawns are to be place on an IslandTile on the game board
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Pawn {
	
	// Pawn attributes
	private Player player;
	private IslandTile currentTile;
	
	/**
	 * Constructor for Pawn instances.
	 * @param Player who is using this Pawn instance.
	 * @param Starting island tile of player
	 */
	public Pawn(Player newPlayer, IslandTile startingTile) {
		this.player = newPlayer;
		this.currentTile = startingTile;
	}
	
	/**
	 * Getter method for player associated with given pawn.
	 * @return Player instance associated with thie Pawn.
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * Getter method for tile where Pawn is currently situated.
	 * @return IslandTile instance Pawn is located at.
	 */
	public IslandTile getTile() {
		return this.currentTile;
	}
	
	/**
	 * Sets the location of pawn.
	 * @param New IslandTile to place Pawn on.
	 */
	public void setTile(IslandTile newIslandTile) {
		this.currentTile = newIslandTile;
	}

}
