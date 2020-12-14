package island.game;

import java.util.List;

import island.components.IslandTile;
import island.components.Pawn;
import island.observers.PlayerSunkObserver;
import island.observers.Subject;
import island.observers.FoolsLandingObserver;
import island.observers.TreasureTilesObserver;
import island.observers.WaterMeterObserver;
import island.players.Player;

/**
 * Controls the flow of the game play and obtains player choices
 * through GameView
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameController {
	
	// Singleton instance
	private static GameController gameController;
	 
	// References for model, view and sub-controllers
	private GameView gameView;
	private GameModel gameModel;
	private SetupController setupController;
	private ActionController actionController;
	private DrawCardsController drawCardsController;
	private PlaySpecialCardController playSpecialCardController;
	
	/**
	 * Constructor to retrieve model, view and sub-controller instances
	 */
	private GameController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
		setupController = SetupController.getInstance(gameModel, gameView);
		actionController = ActionController.getInstance(gameModel, gameView, this);
		drawCardsController = DrawCardsController.getInstance(gameModel, gameView);
		playSpecialCardController = PlaySpecialCardController.getInstance(gameModel, gameView, this);
	}
	
	/**
	 * Singleton instance getter method
	 * @return single instance of GameController
	 */
	public static GameController getInstance(GameModel gameModel, GameView gameView) {
		if (gameController == null) {
			gameController = new GameController(gameModel, gameView);
			gameView.setController(gameController);
		}
		return gameController;
	}
	
	/**
	 * Initialises game components for start of game
	 */
	public void setup() {
		
		// Show gameView welcome screen
		gameView.showWelcome();
		
		// Setup game components with new players obtain form user through GameView
		setupController.setupGame();
		
		// Create game observers
		createObservers();
		
		// Update user view
//		gameView.updateView(gameModel);
	}
	
	/**
	 * Controls the overall flow of game play 
	 */
	public void playGame() {
		
		// Repeat player turns until winning/losing conditions observed
		while(true) {
			
			// Iterate over players in game
			for (Player p : gameModel.getGamePlayers()) {
				
				// Take a number of actions
				actionController.takeActions(p);
				
				// Draw two cards from Treasure Deck
				drawCardsController.drawTreasureCards(p);
				
				// Draw two cards from Treasure Deck
				drawCardsController.drawFloodCards();
			}
		}
	}
	
	/**
	 * Calls instances of observer classes to create observers
	 */
	private void createObservers() {
		
		// Instantiate observer for WaterMeter
		WaterMeterObserver.getInstance(gameModel.getWaterMeter(), this);
		
		// Instantiate observer for Fools Landing IslandTile
		FoolsLandingObserver.getInstance(gameModel.getIslandBoard().getTile(IslandTile.FOOLS_LANDING), this);
		
		// Instantiate observer for IslandTiles with Treasure
		TreasureTilesObserver newTreasureTilesObserver = TreasureTilesObserver.getInstance(this, gameModel.getIslandBoard(), gameModel.getGamePlayers());
		for (Subject subject : gameModel.getIslandBoard().getTreasureTiles()) {
			subject.attach(newTreasureTilesObserver); // Attach observer to each IslandTile that holds Treasure
		}
		
		// Instantiate observer for IslandTiles that sink with Players on them
		PlayerSunkObserver newPlayerSunkObserver = PlayerSunkObserver.getInstance(this, gameModel.getGamePlayers());
		for (Subject subject : gameModel.getIslandBoard().getAllTiles()) {
			subject.attach(newPlayerSunkObserver); // Attach observer to every IslandTile
		}
	}
	
	/**
	 * Attempts to move game players to another IslandTile when their current
	 * IslandTile has sunk.
	 * @param Pawn instance of Player on sunk IslandTile
	 * @return whether Player was successfully moved to safety
	 */
	public boolean movePlayerToSafety(Pawn pawn) {
		
		// Obtain player-role specific IslandTiles that player can swim to
		List<IslandTile> swimmableTiles = pawn.getPlayer().getSwimmableTiles(gameModel.getIslandBoard());
		
		// Move player pawn to new IslandTile if possible
		if (swimmableTiles.isEmpty()) {
			return false;
		} else {
			pawn.setTile(gameView.pickSwimmableTile(swimmableTiles));
			return true;
		}
	}
	
	/**
	 * Method called by observers that have encountered game ending conditions
	 */
	public void endGame(GameEndings ending) {
		
		// Display ending message to user and exit application
		gameView.showEnding(ending);
		System.exit(0);
	}
	
	/**
	 * PlaySpecialCardController getter method
	 * @return single instance of PlaySpecialCardController
	 */
	public PlaySpecialCardController getPlaySpecialCardController() {
		return playSpecialCardController;
	}

	/**
	 * DrawCardsController getter method
	 * @return single instance of DrawCardsController
	 */
	public DrawCardsController getDrawCardsController() {
		return drawCardsController;
	}
	
	/**
	 * ActionController getter method
	 * @return single instance of ActionController
	 */
	public ActionController getActionController() {
		return actionController;
	}
}