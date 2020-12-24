package island.observers;

import island.components.WaterMeter;
import island.controllers.GameController;
import island.controllers.GameEndings;

/**
 * Observer class to act on updates to state of WaterMeter class
 * @author Eoghan O'Shea and Robert McCarthy
 */
public class WaterMeterObserver implements Observer {
	
	private static WaterMeterObserver waterMeterObserver;
	private GameController gameController;
	
	/**
	 * Constructor of observer for WaterMeter
	 * @param WaterMeter subject to attach observer to
	 * @param Reference to GameController
	 */
	protected WaterMeterObserver(Subject subject, GameController gc) {
		
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
		WaterMeter updatedWaterMeter = (WaterMeter) subject;
		if (updatedWaterMeter.getWaterLevel() >= WaterMeter.MAX_WATER_LEVEL)
			gameController.endGame(GameEndings.MAX_WATER_LEVEL);
	}

	// Singleton reset for JUnit testing
	public static void reset() {
		WaterMeter.getInstance().detach(waterMeterObserver);
		waterMeterObserver = null;
	}

}