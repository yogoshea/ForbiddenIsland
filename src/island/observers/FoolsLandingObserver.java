package island.observers;

import island.components.IslandTile;
import island.components.IslandTile.FloodStatus;
import island.game.GameController;

/**
 * Observer class to react to island tiles sinking
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class FoolsLandingObserver extends Observer {
	
	private static FoolsLandingObserver foolsLandingObserver;
	private GameController gameController;
	
	private FoolsLandingObserver(Subject subject, GameController gc) {
		this.subject = subject;
		this.subject.attach(this);
		this.gameController = gc;
	}
	
	/**
	 * @param IslandBoard, GameController
	 * @return single instance of IslandBoardObserver class 
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
	public void update() {
		// TODO: check instance of to cast??
		if (((IslandTile) this.subject).getFloodStatus().equals(FloodStatus.SUNK)) { // TODO: implement equals method
			gameController.endGame("Fools Landing has sunk!"); // TODO: change to GameEndings enum! e.g. GameEndings.FOOLS_LANDING_SUNK
		}	
	}
}
	
//	} else if (checkTreasures) {
//	
//	
//} else if (islandBoard.getPawnLocations().containsValue(updatedIslandTile)) {
////	ActionController.move(player)...
//	
//}