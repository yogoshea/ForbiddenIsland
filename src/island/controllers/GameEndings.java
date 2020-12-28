package island.controllers;

/**
 * Enum class to represent finite set of possible game endings. To be used
 * by controllers, observers and game view to present appropriate
 * information to user.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public enum GameEndings {

	/*
	 * When all treasures have been collected by players, then players
	 * helicopter lifted off the island from Fools Landing.
	 */
	WIN,					
	
	/*
	 * When the Fools Landing island tile has sunk, leaving the players
	 * with no way to escape the island. 
	 */
	FOOLS_LANDING_SUNK,
	
	/*
	 * When both island tiles holding a particular treasure have sunk
	 * before the players have captured that treasure.
	 */
	TREASURE_SUNK,
	
	/*
	 * When an island tile a player is on sinks and that player is
	 * unable to move to another tile on the island.
	 */
	PLAYER_SUNK,
	
	/*
	 * When the water level of the island has risen to the maximum
	 * value of 5.
	 */
	MAX_WATER_LEVEL

}
