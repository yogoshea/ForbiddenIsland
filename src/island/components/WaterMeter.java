package island.components;

import java.util.ArrayList;
import java.util.List;

import island.observers.Observer;
import island.observers.Subject;

/**
 * WaterMeter, singleton design pattern, indicates water level status
 * during game play.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class WaterMeter implements Subject {
	
	// Eager initialisation of singleton
	private static WaterMeter waterMeter;
	
	// Water meter attributes
	private int waterLevel;
	private List<Observer> observers = new ArrayList<Observer>();	
	public static final int MAX_WATER_LEVEL = 5;
	
	/**
	 * Private constructor for WaterMeter singleton.
	 */
	private WaterMeter() {
		
		// Initial water meter set to 1 by default
		this.waterLevel = 1;
	}
	
	/**
	 * Getter method for singleton instance.
	 * @return single WaterMeter instance.
	 */
	public static WaterMeter getInstance() {
		if (waterMeter == null) {
			waterMeter = new WaterMeter();
			
		}
		return waterMeter;
	}
	
	/**
	 * Sets new water meter level.
	 * @param integer value of new water meter level.
	 */
	public void setLevel(int newLevel) {
		waterLevel = newLevel;
		notifyAllObservers(); // Notify observers of WaterMeter state
	}
	
	/**
	 * Increments water level by one.
	 */
	public void incrementLevel() {
		this.waterLevel++;
		// TODO: we should be refilling FloodDeck here, or in DrawCardsController!!!
		notifyAllObservers(); // Notify observers of WaterMeter state
	}
	
	/**
	 * Getter method for the current water level.
	 * @return integer value representing water level.
	 */
	public int getWaterLevel() { 
		return waterLevel;
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
	
	// Singleton reset for JUnit testing
	public static void reset() {
		waterMeter = null;
	}
	
	// Getter method for observers, for JUnit testing
	public List<Observer> getObservers() {
		return observers;
	}
	
}
