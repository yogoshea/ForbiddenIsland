package island.game;

import java.util.ArrayList;
import java.util.List;

import island.components.IslandTile;
import island.components.Pawn;
import island.components.WaterMeter;
import island.observers.PlayerSunkObserver;
import island.observers.Subject;
import island.observers.FoolsLandingObserver;
import island.observers.TreasureTilesObserver;
import island.observers.WaterMeterObserver;
import island.players.Player;

/**
 * Controls the flow of the gameplay and obtains player choices
 * through GameView
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameController {
	
	// Instantiate singleton
	private static GameController gameController;
	
	private GameView gameView;
	private GameModel gameModel;
	private SetupController setupController;
	private ActionController actionController;
	private DrawCardsController drawCardsController;
	private PlaySpecialCardController playSpecialCardController;
	
	/**
	 * Constructor to retrieve view and model instances
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
	 * Initialise game components and add players to game
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
	 * Control the overall flow of gameplay 
	 */
	public void playGame() {
		
		// repeat player turns until winning/losing conditions observed
		while(true) {
			for (Player p : gameModel.getGamePlayers()) {
				
				// take a number of actions
				actionController.takeActions(p);
//				System.out.println("Finished Actions!"); //TODO: move to view
				
				// Draw two cards from Treasure Deck
				drawCardsController.drawTreasureCards(p);
				
				// Draw two cards from Treasure Deck
				drawCardsController.drawFloodCards();
			}
		}
	}
	
	/**
	 * Call instances of observer classes to create observers
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
		PlayerSunkObserver.getInstance(this, gameModel.getIslandBoard(), gameModel.getGamePlayers());
	}
	
	/**
	 * 
	 * @param player
	 * @param currentIslandTile
	 * @return whether Player was successfully moved to safety
	 */
	public boolean movePlayerToSafety(Pawn pawn) {
		
		List<IslandTile> swimmableTiles = pawn.getPlayer().getSwimmableTiles(gameModel.getIslandBoard());
		
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
	public void endGame() {
		gameView.showEnding();	// gameView
		System.exit(0);
	}
	
	
	public PlaySpecialCardController getPlaySpecialCardController() {
		return playSpecialCardController;
	}

	public DrawCardsController getDrawCardsController() {
		return drawCardsController;
	}
	
	public ActionController getActionController() {
		return actionController;
	}
}