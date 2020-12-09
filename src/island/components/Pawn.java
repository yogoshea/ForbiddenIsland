package island.components;

import island.players.Player;

/**
 * Pawns are to be place on an IslandTile on the game board
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Pawn {
	
	private Player player;
	private IslandTile currentTile;
	
	public Pawn(Player newPlayer, IslandTile startingTile) {
		this.player = newPlayer;
		this.currentTile = startingTile;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public IslandTile getTile() {
		return this.currentTile;
	}
	
	public void setTile(IslandTile newIslandTile) {
		this.currentTile = newIslandTile;
	}

}
