package island.components;

import island.observers.GameOverObserver;
import island.observers.Subject;

/**
 * WaterMeter, singleton design pattern, indicates water level status
 * during game play.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class WaterMeter extends Subject {
	
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
	
	public void incrementLevel() { //Is there a situation where specific level needed/level can go down?
		this.waterLevel++;
		//Alert gameOverObserver that something happened which may cause game to be over
		notifyAllObservers();
	}
	
	public int getWaterLevel() { 
		return waterLevel;
	}
	
	// TODO: toString() method for displaying in terminal UI

}
