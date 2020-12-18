package island.tests;

import static org.junit.Assert.*;

import java.security.Permission;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import island.components.WaterMeter;
import island.game.GameController;
import island.game.GameModel;
import island.game.GameView;
import island.observers.Subject;
import island.observers.WaterMeterObserver;

public class ObserversTest {
	
	private GameModel gameModel;
	private GameView gameView;
	private GameController gameController;
	
	// Subclasses to check game exit with exceptions
	@SuppressWarnings("serial")
	private static class GameExitException extends SecurityException {
		public final int status;
        public GameExitException(int status) {
            super("Testing ExitException");
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
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		gameModel = GameModel.getInstance();
		gameView =  GameView.getInstance();
		gameController = GameController.getInstance(gameModel, gameView);
        System.setSecurityManager(new CheckGameExitSecurityManager());
	}

	@After
	public void tearDown() throws Exception {
        System.setSecurityManager(null);
		gameModel.reset();
		gameView.reset();
		gameController.reset();
	}
	
	@Test
	public void test_FoolsLandingObserver() {
		
	}
	
	@Test
	public void test_PlayerSunkObserver_playerMoves() {
		
		// Just set starting island tiles to sunk
		// Provide choice of adjacent tile to move to
		// Check if player moved successfully
		
	}
	
	@Test
	public void test_PlayersunkObserver_gameExit() {
		
	}
	
	@Test
	public void test_TeasureTilesObserver() {
		
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
		
		wmObs.gameOverCondition = false;
		wm.setLevel(7); // TODO: don't allow entries > 5 ?
		assertEquals("Getting water level", 7, wm.getWaterLevel());
		assertEquals("Getting water level", wmObs.updatedWaterLevel, wm.getWaterLevel());
		assertTrue("Game over status", wmObs.gameOverCondition);
	}
	
	@Test
	public void test_WaterMeterObserver_gameExit() {
		
		WaterMeter wm = gameModel.getWaterMeter();
		WaterMeterObserver.getInstance(wm, gameController);
		
		// set game ending condition, increase water level
		try {
//            wm.setLevel(5);
			wm.incrementLevel();
			wm.incrementLevel();
			wm.incrementLevel();
			wm.incrementLevel();
			wm.incrementLevel();
        } catch (GameExitException e) {
           assertEquals("Check game exit status", 0, e.status);
        }
		System.out.println(wm.getWaterLevel());
	}
	
//	@Test
//    public void testNoExit() throws Exception 
//    {
//        System.out.println("Printing works");
//    }
//
//    @Test
//    public void testExit() throws Exception {
//        try {
//            System.exit(0);
//        } catch (ExitException e) {
//           assertEquals("Exit status", 1, e.status);
//        }
//    }

}
