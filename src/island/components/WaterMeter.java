package island.components;

import island.game.GameOverObserver;

/**
 * WaterMeter, singleton design pattern, indicates water level status
 * during game play.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class WaterMeter {
	
	// Eager initialisation of singleton
	private static WaterMeter instance = new WaterMeter();
	private int waterLevel;
	
	// private constructor, initialise meter to 1
	private WaterMeter() {
		this.waterLevel = 1; // TODO: better way to set attributes?
	}
	
	public static WaterMeter getInstance() {
		return instance;
	}
	
	public void setLevel(int level) {
		// TODO: check for valid water level between 1-5
		this.waterLevel = level;
		//Alert gameOverObserver that something happened which may cause game to be over
		GameOverObserver.getInstance().checkIfGameOver();
	}
	
	public int getLevel() { 
		return waterLevel;
	}
	
	// TODO: toString() method for displaying in terminal UI

}
