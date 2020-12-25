package island.components;

import java.util.ArrayList;
import java.util.List;

import island.observers.Observer;
import island.observers.Subject;

/**
 * IslandTile enum describing the island tiles used in game.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public enum IslandTile implements Subject {
	
	BREAKERS_BRIDGE("Breakers Bridge"),
	BRONZE_GATE("Bronze Gate"),
	CAVE_OF_EMBERS("Cave of Embers",Treasure.THE_CRYSTAL_OF_FIRE),
	CAVE_OF_SHADOWS("Cave of Shadows", Treasure.THE_CRYSTAL_OF_FIRE),
	CLIFFS_OF_ADANDON("Cliffs of Adandon"),
	COPPER_GATE("Copper Gate"),
	CORAL_PALACE("Coral Palace", Treasure.THE_OCEANS_CHALICE),
	CRIMSON_FOREST("Crimson Forest"),
	DUNES_OF_DECEPTION("Dunes of Deception"),
	FOOLS_LANDING("Fools Landing"),
	GOLD_GATE("Gold Gate"),
	HOWLING_GARDEN("Howling Garden", Treasure.THE_STATUE_OF_THE_WIND),
	IRON_GATE("Iron Gate"),
	LOST_LAGOON("Lost Lagoon"),
	MISTY_MARSH("Misty Marsh"),
	OBSERVATORY("Observatory"),
	PHANTOM_ROCK("Phantom Rock"),
	SILVER_GATE("Silver Gate"),
	TEMPLE_OF_THE_MOON("Temple of the Moon", Treasure.THE_EARTH_STONE),
	TEMPLE_OF_THE_SUN("Temple of the Sun", Treasure.THE_EARTH_STONE),
	TIDAL_PALACE("Tidal Palace", Treasure.THE_OCEANS_CHALICE),
	TWILIGHT_HOLLOW("Twilight Hallow"),
	WATCHTOWER("Watchtower"),
	WHISPERING_GARDEN("Whispering Garden", Treasure.THE_STATUE_OF_THE_WIND);
	
	// IslandTile attributes
	private String name;
	private Treasure associatedTreasure;
	private List<Observer> observers = new ArrayList<Observer>();
	
	// Private Enum to store flood status of each tile.
	private enum FloodStatus { SAFE, FLOODED, SUNK }
	private FloodStatus status;
	
	/**
	 * Constructor sets associatedTreasure to null if not provided.
	 * @param String representing name of island tile.
	 */
	private IslandTile(String name) {
		this(name, null);
	}
	
	/**
	 * Constructor to set associatedTreasure and floodedStatus
	 * @param String representing name of island tile.
	 * @param treasure specifies treasure associated with island tile
	 */
	private IslandTile(String name, Treasure treasure) {
		this.name = name;
		this.associatedTreasure = treasure;
		this.status = FloodStatus.SAFE; // all island tile are initially not flooded
	}
	
	/**
	 * Getter method for name of island tile.
	 * @return String representation of island tile name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * getter method for treasure associated with island tile.
	 * @return associated Treasure enum type.
	 */
	public Treasure getAssociatedTreasure() {
		return associatedTreasure;
	}
	
	/**
	 * @return boolean corresponding IslandTile being safe.
	 */
	public Boolean isSafe() {
		return this.status.equals(FloodStatus.SAFE);
	}
	
	/**
	 * @return boolean corresponding IslandTile being flooded.
	 */
	public Boolean isFlooded() {
		return this.status.equals(FloodStatus.FLOODED);
	}
	
	/**
	 * @return boolean corresponding IslandTile having sunk.
	 */
	public Boolean isSunk() {
		return this.status.equals(FloodStatus.SUNK);
	}
	
	/**
	 * Sets flood status to safe.
	 */
	public void setToSafe() {
		this.status = FloodStatus.SAFE;
	}
	
	/**
	 * Sets flood status to flooded.
	 */
	public void setToFlooded() {
		this.status = FloodStatus.FLOODED;
	}
	
	/**
	 * Sets flood status to sunk.
	 */
	public void setToSunk() {
		this.status = FloodStatus.SUNK;
		notifyAllObservers();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	@Override
	public void detach(Observer observer) {
		observers.remove(observer);
	}
	
	@Override
	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}
	
}
