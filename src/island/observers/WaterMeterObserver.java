package island.observers;

import island.components.WaterMeter;
import island.game.GameController;
import island.game.GameEndings;

/**
 * Observer class to act on updates to state of WaterMeter class
 * @author Eoghan O'Shea and Robert McCarthy
 */
public class WaterMeterObserver implements Observer {
	
	private static WaterMeterObserver waterMeterObserver;
	private GameController gameController;
	private static int maxWaterLevel;
	
	/**
	 * Constructor of observer for WaterMeter
	 * @param WaterMeter subject to attach observer to
	 * @param Reference to GameController
	 */
	private WaterMeterObserver(Subject subject, GameController gc) {
		
		// Attach this observer to subject
		subject.attach(this);
		
		// Store GameController reference
		this.gameController = gc;
	}
	
	/**
	 * Singleton instance getter method
	 * @return single instance of WaterMeterObserver
	 */
	public static WaterMeterObserver getInstance(Subject subject, GameController gc) {
		if (waterMeterObserver == null) {
			waterMeterObserver = new WaterMeterObserver(subject, gc);
		}
		return waterMeterObserver;
	}
	
	/**
	 * Update methods called when Water Meter changes water level
	 */
	@Override
	public void update(Subject subject) {
		// TODO: add call to GameView whenever water level increases!
		if (((WaterMeter) subject).getWaterLevel() == maxWaterLevel)
			gameController.endGame(GameEndings.MAX_WATER_LEVEL);
	}

	// Singleton reset for JUnit testing
	public void reset() {
		waterMeterObserver = null;
	}

}