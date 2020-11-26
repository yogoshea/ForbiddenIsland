package island.components;

/**
 * IslandTile enum for island tiles
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public enum IslandTile {
	
	// Maybe add another String attribute so can print like "Cave of Embers" instead of "CAVE_OF_EMBERS"
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
	private boolean floodedStatus;
	
	/**
	 * no argument constructor sets associatedTreasure to null
	 */
	private IslandTile(String name) {
		this(name, null); // check for null when using enums at later stage
	}
	// TODO: make floodStatus Enum {"notFlooded", "Flooded", "Sank"}
	
	/**
	 * constructor to set associatedTreasure and floodedStatus
	 * @param treasure specifies treasure associated with island tile
	 */
	private IslandTile(String name, Treasure treasure) {
		this.name = name;
		this.associatedTreasure = treasure;
		this.floodedStatus = false; // all island tile are initially not flooded
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public Treasure captureAssociatedTreasure() {
		Treasure temp = associatedTreasure;
		associatedTreasure = null; // TODO: create NullTreasure class?
		return temp;
	}
	
	/**
	 * getter method for treasure associated with island tile
	 * @return associated Treasure enum type
	 */
	public Treasure getAssociatedTreasure() {
		return associatedTreasure;
	}

	/**
	 * getter method for flooded status of an island tile
	 * @return boolean corresponding to flood status
	 */
	public Boolean isFlooded() {
		return floodedStatus;
	}
	
	/**
	 * setter method for flooded status of island tile
	 */
	public void setToFlooded() {
		System.out.println(name()+" has been flooded!!!");
		floodedStatus = true;
	}
	
	public boolean shoreUp() {
		if(!floodedStatus) {
			System.out.println(name()+" already shored-up");
			return false;
		} else {
			floodedStatus = false;
			System.out.println(name()+" has been shored-up");
			return true;
		}
	}

}
