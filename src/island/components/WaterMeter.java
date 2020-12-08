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
	private static WaterMeter instance = new WaterMeter();
	private int waterLevel;
	private List<Observer> observers = new ArrayList<Observer>();	
	
	// private constructor, initialise meter to 1
	private WaterMeter() {
		this.waterLevel = 1; // TODO: better way to set attributes?
	}
	
	public static WaterMeter getInstance() {
		return instance;
	}
	
	public void incrementLevel() {
		this.waterLevel++;
		notifyAllObservers(); // Notify observers of WaterMeter state
	}
	
	public int getWaterLevel() { 
		return waterLevel;
	}

	@Override
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	@Override
	public void notifyAllObservers() {
		for (Observer observer : observers) {
			observer.update();
		}
	}
	
	// TODO: toString() method for displaying in terminal UI

}
