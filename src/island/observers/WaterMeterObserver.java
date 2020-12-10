package island.observers;

import island.components.GameEndings;
import island.components.WaterMeter;
import island.game.GameController;

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
	private WaterMeterObserver(Subject subject, GameController gc) {
		
		// Attach this observer subject
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
		if (((WaterMeter) subject).getWaterLevel() == 5)
			gameController.endGame(GameEndings.WATER_LEVEL_EXCEEDED); // TODO: change to GameEndings enum
	}

}