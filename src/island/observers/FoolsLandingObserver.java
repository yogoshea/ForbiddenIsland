package island.observers;

import island.components.IslandTile;
import island.game.GameController;

/**
 * Observer class to react to island tiles sinking
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FoolsLandingObserver implements Observer {
	
	private static FoolsLandingObserver foolsLandingObserver;
	private GameController gameController;
	
	private FoolsLandingObserver(Subject subject, GameController gc) {
		subject.attach(this);
		this.gameController = gc;
	}
	
	/**
	 * @param IslandTile, GameController
	 * @return single instance of FoolsLandingObserver class 
	 */
	public static FoolsLandingObserver getInstance(IslandTile islandTile, GameController gc) {
		if (foolsLandingObserver == null) {
			foolsLandingObserver = new FoolsLandingObserver(islandTile, gc);
		}
		return foolsLandingObserver;
	}
	
	/**
	 * Update method called when state of IslandTile changes
	 */
	@Override
	public void update(Subject subject) {
		gameController.endGame(); // TODO: change to GameEndings enum! e.g. GameEndings.FOOLS_LANDING_SUNK
	}
}