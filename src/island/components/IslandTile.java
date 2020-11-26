package island.components;

/**
 * IslandTile enum for island tiles
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public enum IslandTile {
	
	// Maybe add another String attribute so can print like "Cave of Embers" instead of "CAVE_OF_EMBERS"
	BREAKERS_BRIDGE,
	BRONZE_GATE,
	CAVE_OF_EMBERS(Treasure.THE_CRYSTAL_OF_FIRE),
	CAVE_OF_SHADOWS(Treasure.THE_CRYSTAL_OF_FIRE),
	CLIFFS_OF_ADANDON,
	COPPER_GATE,
	CORAL_PALACE(Treasure.THE_OCEANS_CHALICE),
	CRIMSON_FOREST,
	DUNES_OF_DECEPTION,
	FOOLS_LANDING,
	GOLD_GATE,
	HOWLING_GARDEN(Treasure.THE_STATUE_OF_THE_WIND),
	IRON_GATE,
	LOST_LAGOON,
	MISTY_MARSH,
	OBSERVATORY,
	PHANTOM_ROCK,
	SILVER_GATE,
	TEMPLE_OF_THE_MOON(Treasure.THE_EARTH_STONE),
	TEMPLE_OF_THE_SUN(Treasure.THE_EARTH_STONE),
	TIDAL_PALACE(Treasure.THE_OCEANS_CHALICE),
	TWILIGHT_HOLLOW,
	WATCHTOWER,
	WHISPERING_GARDEN(Treasure.THE_STATUE_OF_THE_WIND);
	
	private Treasure associatedTreasure;
	private boolean floodedStatus;
	
	/**
	 * no argument constructor sets associatedTreasure to null
	 */
	private IslandTile() {
		this(null); // check for null when using enums at later stage
	}
	// TODO: make floodStatus Enum {"notFlooded", "Flooded", "Sank"}
	
	/**
	 * constructor to set associatedTreasure and floodedStatus
	 * @param treasure specifies treasure associated with island tile
	 */
	private IslandTile(Treasure treasure) {
		associatedTreasure = treasure;
		floodedStatus = false; // all island tile are initially not flooded
	}
	
	public Treasure captureAssociatedTreasure() {
		Treasure temp = associatedTreasure;
		associatedTreasure = null;
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
