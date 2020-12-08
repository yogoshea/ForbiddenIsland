package island.components;

import java.util.ArrayList;
import java.util.List;

import island.observers.Observer;
import island.observers.Subject;

/**
 * IslandTile enum for island tiles
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
	
	private String name;
	private Treasure associatedTreasure;
	private List<Observer> observers = new ArrayList<Observer>();
	
	// Enum to store flood status of each tile TODO:  move this to individual class?
	public enum FloodStatus { SAFE("--- Safe ---"), FLOODED("~~~ Flooded ~~~"), SUNK("XXX Sunk XXX"); // TODO: move to GameView
		private final String sign;
		private FloodStatus(String sign) { this.sign = sign; }
		public String toString() { return sign; }
	}
	private FloodStatus status;
	
	/**
	 * no argument constructor sets associatedTreasure to null
	 */
	private IslandTile(String name) {
		this(name, null); // check for null when using enums at later stage
	}
	
	/**
	 * constructor to set associatedTreasure and floodedStatus
	 * @param treasure specifies treasure associated with island tile
	 */
	private IslandTile(String name, Treasure treasure) {
		this.name = name;
		this.associatedTreasure = treasure;
		this.status = FloodStatus.SAFE; // all island tile are initially not flooded
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public Treasure captureAssociatedTreasure() {
		Treasure temp = associatedTreasure;
		// TODO: create NullTreasure class?
		return temp;
	}
	
	/**
	 * getter method for treasure associated with island tile
	 * @return associated Treasure enum type
	 */
	public Treasure getAssociatedTreasure() {
		return associatedTreasure;
	}
	
	public FloodStatus getFloodStatus() {
		return status;
	}

	/**
	 * @return boolean corresponding IslandTile being safe
	 */
	public Boolean isSafe() {
		return this.status.equals(FloodStatus.SAFE);
	}
	
	/**
	 * @return boolean corresponding IslandTile being flooded
	 */
	public Boolean isFlooded() {
		return this.status.equals(FloodStatus.FLOODED);
	}
	
	/**
	 * @return boolean corresponding IslandTile having sunk
	 */
	public Boolean isSunk() {
		return this.status.equals(FloodStatus.SUNK);
	}
	
	/**
	 */
	public void setToSafe() {
		this.status = FloodStatus.SAFE;
	}
	
	/**
	 * Sets flood status to flooded
	 */
	public void setToFlooded() {
		this.status = FloodStatus.FLOODED;
	}
	
	/**
	 * Sets flood status to sunk
	 */
	public void setToSunk() {
		this.status = FloodStatus.SUNK;
	}
	
	
	
//	/**
//	 * Setter method for flooded status of island tile
//	 */
//	public void floodTile() {
//		if (this.isSafe()) {
//			status = FloodStatus.FLOODED;
////			System.out.println(name() + " has been flooded !!");
//		} else if (this.isFlooded()) {
//			status = FloodStatus.SUNK;
////			System.out.println(name() + " has been sunk !!!");
//		}
//		notifyAllObservers(); // When a tile has sunk, notify observers
//	}
	
//	/**
//	 * Reinstate an IslandTile as safe
//	 */
//	public boolean shoreUp() {
//		if(!this.isFlooded()) { // TODO: change to check for Sunk also 
//			System.out.println(name()+" already shored-up");
//			return false;
//		} else {
//			status = FloodStatus.SAFE;
//			System.out.println(name()+" has been shored-up");
//			return true;
//		}
//	}

	@Override
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	@Override
	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update(this);
		}
	}

}
