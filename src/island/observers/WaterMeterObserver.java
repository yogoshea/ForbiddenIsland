package island.observers;

import island.components.WaterMeter;

public class WaterMeterObserver extends GameOverObserver {
	
	private static WaterMeterObserver waterMeterObserver;
	
	private WaterMeterObserver() {
		subject = WaterMeter.getInstance(); //This way (use getInstance()) or SunkTileObserver constructor way (pass in gameModel)?
		subject.attach(this);
	}
	
	/**
	 * @return single instance of water meter observer
	 */
	public static WaterMeterObserver getInstance() {
		if (waterMeterObserver == null) {
			waterMeterObserver = new WaterMeterObserver();
		}
		return waterMeterObserver;
	}
	
	@Override
	public void checkIfGameOver() {
		boolean gameOver = checkWaterLevel();
		if(gameOver) {
			setGameOver();
		}
	}
	
	/*
	 * Method to check if Game Over due to water level above threshold
	 */
	public static boolean checkWaterLevel() {
		return WaterMeter.getInstance().getLevel() > 5;
		//TODO: make make level a final game variable
	}

}
