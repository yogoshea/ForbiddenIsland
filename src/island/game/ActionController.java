package island.game;

import island.players.Player;

/**
 * Controller class for retrieving player choices for GameView and change 
 * game model accordingly
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class ActionController {
	
	// Instantiate singleton
	private static ActionController actionController = new ActionController();
	
	private GameView gameView;
	private GameModel gameModel;
	
	/**
	 * Constructor to retrieve view and model instances
	 */
	private ActionController() {
		gameView = GameView.getInstance();
		gameModel = GameModel.getInstance();
	}
	
	/**
	 * @return single instance of action controller
	 */
	public static ActionController getInstance() {
		return actionController;
	}
	
	public void takeActions(Player p) {
		gameView.promptUser("...");
	}

}
