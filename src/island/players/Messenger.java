package island.players;

import java.util.ArrayList;
import java.util.List;

import island.components.IslandTile;

/**
 * Class to represent Messenger role of a player in the game.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public class Messenger extends Player {

	/**
	 * Constructor for Messenger instance.
	 * @param String representing name of player.
	 */
	public Messenger(String name) {
		
		// Messenger's starting island tile set to Silver Gate.
		super(name, "Messenger", IslandTile.SILVER_GATE);
	}

	@Override
	public List<Player> getCardReceivablePlayers(GamePlayers gamePlayers) {
		
		// Messenger can give cards to players without having to be on the same tile
		List<Player> recievablePlayers = new ArrayList<Player>(gamePlayers.getPlayersList());
		recievablePlayers.remove(this);
		return recievablePlayers;
	}
}
