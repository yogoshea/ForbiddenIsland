package island.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.Permission;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import island.components.GameModel;
import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.Treasure;
import island.components.WaterMeter;
import island.controllers.GameController;
import island.observers.FoolsLandingObserver;
import island.observers.PlayerSunkObserver;
import island.observers.Subject;
import island.observers.TreasureTilesObserver;
import island.observers.WaterMeterObserver;
import island.players.Engineer;
import island.players.GamePlayers;
import island.players.Player;
import island.view.GameView;

public class ObserversTest {
	
	private GameModel gameModel;
	private GameView gameView;
	private GameController gameController;
	
	// Subclasses to check game exit with exceptions
	@SuppressWarnings("serial")
	private static class GameExitException extends SecurityException {
		public final int status;
        public GameExitException(int status) {
            super("Testing GameExitException");
            this.status = status;
        }
    }

	private static class CheckGameExitSecurityManager extends SecurityManager {
		@Override
		public void checkPermission(Permission p) {} 
		@Override
		public void checkPermission(Permission p, Object c) {}
        @Override
        public void checkExit(int status) {
            super.checkExit(status);
            throw new GameExitException(status);
        }
    }
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Ensure components reset from previous test cases
		GameModel.reset();
		GameView.reset();
		GameController.reset();
		FoolsLandingObserver.reset();
		PlayerSunkObserver.reset();
		TreasureTilesObserver.reset();
		WaterMeterObserver.reset();
	}

	@Before
	public void setUp() throws Exception {
		gameModel = GameModel.getInstance();
        System.setSecurityManager(new CheckGameExitSecurityManager());
	}

	@After
	public void tearDown() throws Exception {
		GameModel.reset();
		GameView.reset();
		GameController.reset();
		FoolsLandingObserver.reset();
		PlayerSunkObserver.reset();
		TreasureTilesObserver.reset();
		WaterMeterObserver.reset();
        System.setSecurityManager(null);
	}
	
	@Test
	public void test_FoolsLandingObserver_gameExit() {
		
		// Create view without specified user input
		gameView =  GameView.getInstance();
		gameController = GameController.getInstance(gameModel, gameView);
		
		// Attach Observer to Fool'sLanding island tile
		FoolsLandingObserver.getInstance(IslandTile.FOOLS_LANDING, gameController);
		
		// Allow flooding of tile
		IslandTile.FOOLS_LANDING.setToFlooded();
		assertTrue("Island tile flooded status", IslandTile.FOOLS_LANDING.isFlooded());
		
		// Sink the Fool's Landing island tile
		try {
			
			assertFalse("Island tile sunk status", IslandTile.FOOLS_LANDING.isSunk());
			IslandTile.FOOLS_LANDING.setToSunk();
			
        } catch (GameExitException e) {
           assertEquals("Check game exit status", 0, e.status);
        }
		assertTrue("Island tile sunk status", IslandTile.FOOLS_LANDING.isSunk());
	}
	
	@Test
	public void test_PlayerSunkObserver_playerEscape() {
		
		// Provide necessary user input for test
		String sampleUserInput = "1\n1\n"; 
	    InputStream backup = System.in; // backup
	    InputStream in = new ByteArrayInputStream(sampleUserInput.getBytes());
	    System.setIn(in);
	    
	    // Setup GameView user specified input
		gameView =  GameView.getInstance();
		gameController = GameController.getInstance(gameModel, gameView);

		GamePlayers players = gameModel.getGamePlayers();
		IslandBoard islandBoard = gameModel.getIslandBoard();
		
		// Attach Observer to each island tile
		PlayerSunkObserver psObs = PlayerSunkObserver.getInstance(gameController, players, gameView);
		for (Subject subject : gameModel.getIslandBoard().getAllTiles()) {
			subject.attach(psObs);
		}

		// Check successful escape
		Player player1 = new Engineer("Tom");
		players.addPlayer(player1);
		IslandTile player1Tile = player1.getPawn().getTile();
		IslandTile safeTile = islandBoard.getAdjacentTiles(player1Tile).get(0);
		
		// Sink surrounding tiles; only swimmable tiles for Engineer
		for (IslandTile tile : islandBoard.getAdjacentTiles(player1Tile)) {
			tile.setToSunk();
			assertTrue("Checking tiles sunk", tile.isSunk());
		}
		
		// Set safe tile back to flooded, so player can escape to it
		assertTrue("Checking tile sunk", safeTile.isSunk());
		safeTile.setToFlooded();
		assertTrue("Checking tile flooded", safeTile.isFlooded());
		
		
//		try {
		// Set current tile to sunk; calls observer method
		player1Tile.setToSunk();
//		} catch(GameExitException e) {
//			System.out.println("Exiting");
//		}
		
		assertEquals("Checking player new location", safeTile, player1.getPawn().getTile());
	    System.setIn(backup); // Reset system input
	}

	@Test
	public void test_PlayerSunkObserver_gameExit() {
		
		// Create view without specified user input
		gameView =  GameView.getInstance();
		gameController = GameController.getInstance(gameModel, gameView);
		
		GamePlayers players = gameModel.getGamePlayers();
		IslandBoard islandBoard = gameModel.getIslandBoard();

		// Attach Observer to each island tile
		PlayerSunkObserver psObs = PlayerSunkObserver.getInstance(gameController, players, gameView);
		for (Subject subject : gameModel.getIslandBoard().getAllTiles()) {
			subject.attach(psObs);
		}
		
		// Set game ending condition, sunk player and surrounding tiles
		try {
			// Add player to game
			Player player2 = new Engineer("Gerry");
			players.addPlayer(player2);
			IslandTile player2Tile = player2.getPawn().getTile();
			
			// Sink surrounding tiles; only swimmable tiles for Engineer
			for (IslandTile tile : islandBoard.getAdjacentTiles(player2Tile)) {
				tile.setToSunk();
				assertTrue("Checking tiles sunk", tile.isSunk());
			}
			
			// Sink players current tile
			player2Tile.setToSunk();
			player2 = null;
			
        } catch (GameExitException e) {
           assertEquals("Check game exit status", 0, e.status);
        }
	}
	
	@Test
	public void test_TeasureTilesObserver_gameExit() {

		// Create view without specified user input
		gameView =  GameView.getInstance();
		gameController = GameController.getInstance(gameModel, gameView);
		
		GamePlayers players = gameModel.getGamePlayers();
		
		// Attach Observer to each tile with treasure on it
		TreasureTilesObserver ttObs = TreasureTilesObserver.getInstance(gameController, gameModel.getIslandBoard(), gameModel.getGamePlayers(), gameView);
		for (Subject subject : gameModel.getIslandBoard().getTreasureTiles()) {
			subject.attach(ttObs);
		}
		
		// Sink two tiles of the same treasure; after players have captured it
		assertTrue("Captured treasures", players.getCapturedTreasures().isEmpty());
		players.addTreasure(Treasure.THE_CRYSTAL_OF_FIRE);
		assertTrue("Captured treasures", players.getCapturedTreasures().contains(Treasure.THE_CRYSTAL_OF_FIRE));
		IslandTile.CAVE_OF_EMBERS.setToSunk();
		IslandTile.CAVE_OF_SHADOWS.setToSunk();
		assertTrue("Checking tiles sunk", IslandTile.CAVE_OF_EMBERS.isSunk());
		assertTrue("Checking tiles sunk", IslandTile.CAVE_OF_SHADOWS.isSunk());
		
		// Sink two tiles of the same treasure; before players have captured it
		try {
			
			assertFalse("Captured treasures", players.getCapturedTreasures().contains(Treasure.THE_EARTH_STONE));
			IslandTile.TEMPLE_OF_THE_MOON.setToSunk();
			IslandTile.TEMPLE_OF_THE_SUN.setToSunk();
			
        } catch (GameExitException e) {
           assertEquals("Check game exit status", 0, e.status);
        }
	}

	@Test
	public void test_WaterMeterObserver_stateUpdate() {
		
		// Subclass observer and add tracker for subject state
		class TestWMO extends WaterMeterObserver {
			int updatedWaterLevel;
			boolean gameOverCondition;
			TestWMO(WaterMeter wm, GameController gc) {
				super(wm,gc);
				updatedWaterLevel = wm.getWaterLevel();
				gameOverCondition = false;
			}
			@Override
			public void update(Subject subject) {
				updatedWaterLevel = ((WaterMeter) subject).getWaterLevel();
				if (((WaterMeter) subject).getWaterLevel() >= 5)
					gameOverCondition = true;
			}
		}
		
		WaterMeter wm = gameModel.getWaterMeter();
		TestWMO wmObs = new TestWMO(wm, gameController);
		
		assertEquals("Getting water level", 1, wm.getWaterLevel());
		assertEquals("Getting water level", wmObs.updatedWaterLevel, wm.getWaterLevel());
		assertTrue("Getting observers", wm.getObservers().contains(wmObs));
		assertFalse("Game over status", wmObs.gameOverCondition);

		wm.incrementLevel();
		assertEquals("Getting water level", 2, wm.getWaterLevel());
		assertEquals("Getting water level", wmObs.updatedWaterLevel, wm.getWaterLevel());
		assertFalse("Game over status", wmObs.gameOverCondition);
		
		wm.setLevel(3);
		assertEquals("Getting water level", 3, wm.getWaterLevel());
		assertEquals("Getting water level", wmObs.updatedWaterLevel, wm.getWaterLevel());
		assertFalse("Game over status", wmObs.gameOverCondition);
		
		// Test system exit
		wm.setLevel(5);
		assertEquals("Getting water level", 5, wm.getWaterLevel());
		assertEquals("Getting water level", wmObs.updatedWaterLevel, wm.getWaterLevel());
		assertTrue("Game over status", wmObs.gameOverCondition);
	}
	
	@Test
	public void test_WaterMeterObserver_gameExit() {
		
		// Create view without specified user input
		gameView =  GameView.getInstance();
		gameController = GameController.getInstance(gameModel, gameView);

		WaterMeter wm = gameModel.getWaterMeter();
		WaterMeterObserver.getInstance(wm, gameController);
		
		// set game ending condition, increase water level
		try {
			wm.incrementLevel();
			wm.incrementLevel();
			wm.incrementLevel();
			wm.incrementLevel();
			wm.incrementLevel();
            // OR wm.setLevel(5);
        } catch (GameExitException e) {
           assertEquals("Check game exit status", 0, e.status);
        }
		assertTrue("Checking final water level", wm.getWaterLevel() >= WaterMeter.MAX_WATER_LEVEL);
	}
	
}
