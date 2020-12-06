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
	
	private WaterMeterObserver(Subject subject, GameController gc) {
//		this.subject = subject;
//		this.subject.attach(this);
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
	public void update(Subject updatedWaterMeter) {
		if (((WaterMeter) updatedWaterMeter).getWaterLevel() == 5)
			gameController.endGame("Water Level has reached Level 5");
	}

}