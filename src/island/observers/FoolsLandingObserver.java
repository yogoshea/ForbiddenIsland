package island.observers;

import island.components.GameEndings;
import island.game.GameController;

/**
 * Observer class to react to the Fools Landing IslandTile sinking
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FoolsLandingObserver implements Observer {
	
	// Singleton instance
	private static FoolsLandingObserver foolsLandingObserver;
	
	// Reference to GameController
	private GameController gameController;
	
	/**
	 * Constructor for FoolsLandingObserver
	 * @param Subject to observe
	 * @param Refernce to GameController
	 */
	private FoolsLandingObserver(Subject subject, GameController gc) {
		
		// Attach observer to subject
		subject.attach(this);
		
		// Store GameController reference
		this.gameController = gc;
	}
	
	/**
	 * Singleton instance getter method
	 * @param Subject to observe
	 * @param Reference to GameController
	 * @return single instance of FoolsLandingObserver class 
	 */
	public static FoolsLandingObserver getInstance(Subject subject, GameController gc) {
		if (foolsLandingObserver == null) {
			foolsLandingObserver = new FoolsLandingObserver(subject, gc);
		}
		return foolsLandingObserver;
	}
	
	/**
	 * Update method called when state of IslandTile changes to sunk
	 */
	@Override
	public void update(Subject subject) {
		gameController.endGame(GameEndings.FOOLS_LANDING_SUNK); // TODO: change to GameEndings enum! e.g. GameEndings.FOOLS_LANDING_SUNK
	}
}