package island.game;

import java.util.ArrayList;
import java.util.List;

import island.components.IslandTile;
import island.components.Pawn;
import island.components.WaterMeter;
import island.observers.GameOverObserver;
import island.observers.Subject;
import island.observers.FoolsLandingObserver;
import island.observers.SunkTileObserver;
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
	//private SunkTileObserver sunkTileObserver; //Do we need to attach observer to controller? Not currently using it
	
	/**
	 * Constructor to retrieve view and model instances
	 */
	private GameController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
		setupController = SetupController.getInstance(gameModel, gameView);
		actionController = ActionController.getInstance(gameModel, gameView);
		drawCardsController = DrawCardsController.getInstance(gameModel, gameView);
		//sunkTileObserver = SunkTileObserver.getInstance(gameModel);
		//Will getInstance() be needed elsewhere?? if so is it good to pass gameModel in every time?
	}
	
	/**
	 * @return single instance of GameController
	 */
	public static GameController getInstance(GameModel gameModel, GameView gameView) {
		if (gameController == null) {
			gameController = new GameController(gameModel, gameView);
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
		
		
	}
	
	/**
	 * 
	 * @param player
	 * @param currentIslandTile
	 * @return whether Player was successfully moved to safety
	 */
	public boolean movePlayerToSafety(Pawn playerPawn, IslandTile currentIslandTile) {
		
		return false;
	}
	
	/**
	 * Method called by observers that have encountered game ending conditions
	 */
	public void endGame(String message) {
		gameView.showEnding(message);	// gameView
		System.exit(0);
	}

}