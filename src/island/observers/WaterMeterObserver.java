package island.observers;

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
	 * Private constructor of observer for WaterMeter
	 * @param WaterMeter subject to attach observer to
	 * @param reference to GameController
	 */
	private WaterMeterObserver(Subject subject, GameController gc) {
		subject.attach(this);
		this.gameController = gc;
	}
	
	/**
	 * @return single instance of water meter observer
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
			gameController.endGame(GameEnding.WATER_METER); // TODO: change to GameEndings enum
	}

}