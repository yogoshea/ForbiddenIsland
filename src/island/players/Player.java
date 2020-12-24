package island.players;

import java.util.ArrayList;
import java.util.List;

import island.cards.Card;
import island.cards.TreasureCard;
import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.Pawn;

/**
 * Player is an abstract class to describe the common behaviour of
 * the player types present in the Forbidden Island game, for
 * example Engineer, Messenger etc.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public abstract class Player { 

	// Player attributes
	private final String name;
	private final String role;
	private final Pawn pawn;
	private List<Card<?>> cards; // TODO: check for empty when using
	private final IslandTile startingTile;
	private final int shoreUpQuantity;
	
	/**
	 * Constructor to be called when Player subclasses instantiated. 
	 * @param String representation of player name.
	 * @param String representation of player role.
	 * @param IslandTile a player will start the game on.
	 * @param The number of tiles a player can shore up per action.
	 */
	Player(String name, String role, IslandTile startingTile, int shoreUpQuantity) {
		this.name = name;
		this.role = role;
		this.pawn = new Pawn(startingTile);
		this.cards = new ArrayList<Card<?>>();
		this.startingTile = startingTile;
		this.shoreUpQuantity = shoreUpQuantity;
	}
	
	/**
	 * Constructor to be called when Player subclasses instantiated. 
	 * @param String representation of player name.
	 * @param String representation of player role.
	 * @param IslandTile a player will start the game on.
	 */
	Player(String name, String role, IslandTile startingTile) {
		this(name, role, startingTile, 1); // Sets shoreUpQuantity to 1 if not given
	}
	
	/**
	 * Getter method for player name.
	 * @return String representation of player name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Getter method for player role.
	 * @return String representation of player role.
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * Getter method for player's pawn instance.
	 * @return Player's corresponding Pawn instance.
	 */
	public Pawn getPawn() {
		return pawn;
	}
	
	/**
	 * Adds a card to the list of cards held by player.
	 * @param Card to be added to player hand.
	 */
	public void addCard(Card<?> card) {
		cards.add(card); // TODO: check for TreasureCard instance
	}
	
	/**
	 * Removes card from player hand.
	 * @param Card instances to be removed from hand.
	 */
	public void removeCard(Card<?> card) {
		cards.remove(card);
	}
	
	/**
	 * Getter method for list of player's cards.
	 * @return List of player's Card instances.
	 */
	public List<Card<?>> getCards() {
		return cards;
	}
	
	/**
	 * Getter method for player's starting island tile
	 * @return IslandTile instance that player starts game on.
	 */
	public IslandTile getStartingTile() {
		return startingTile;
	}
	
	/**
	 * Getter method for shore up quantity of player.
	 * @return number of island tiles player can shore up per action.
	 */
	public int getShoreUpQuantity() {
		return shoreUpQuantity;
	}
	
	/**
	 * Getter method for treasure cards currently held by player.
	 * @return List of TreasureCard instances held by player.
	 */
	public List<Card<?>> getTreasureCards() {
		
		List<Card<?>> treasureCards = new ArrayList<Card<?>>();
	
		for(Card<?> c : this.cards) {
			if (c instanceof TreasureCard)
				treasureCards.add(c);
		}
		return treasureCards;
	}

	/**
	 * Gets the collection of island tiles a particular player can swim to when current
	 * tile has sunk.
	 * @param Refernce to the IslandBoard for the game.
	 * @return List of IslandTile instances that player can swim to.
	 */
	public List<IslandTile> getSwimmableTiles(IslandBoard islandBoard) {
		return islandBoard.getAdjacentTiles(this.pawn.getTile());
	}

	/**
	 * Gets the collection of players that this player can give cards to.
	 * @param GamePlayers instance describing the current game's players.
	 * @return List of Player instances this player can give cards to.
	 */
	public List<Player> getCardReceivablePlayers(GamePlayers gamePlayers) {
		List<Player> giveCardPlayers = new ArrayList<Player>();
		for(Player p : gamePlayers) {
			if ( this.pawn.getTile().equals(p.getPawn().getTile()) && !this.equals(p) ) {
				giveCardPlayers.add(p);
			}
		}
		return giveCardPlayers;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
