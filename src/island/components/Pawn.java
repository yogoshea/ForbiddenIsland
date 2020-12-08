package island.components;

import island.players.Player;

/**
 * Pawns are to be place on an IslandTile on the game board
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Pawn {
	
	private Player player;
	private IslandTile currentLocation;
	
	public Pawn(Player newPlayer, IslandTile startingLocation) {
		this.player = newPlayer;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public IslandTile getLocation() {
		return this.currentLocation;
	}
	
	public void setLocation(IslandTile newIslandTile) {
		this.currentLocation = newIslandTile;
	}

}
