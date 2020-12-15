package island.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import island.components.WaterMeter;
import island.game.GameController;
import island.game.GameModel;
import island.game.GameView;

public class GameSetupTest {
	
	private static GameModel gameModel;
	private static GameView gameView;
	private static GameController gameController;
	private static String sampleUserInput;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Enter sample use input required at game setup
	    sampleUserInput = "2\n";			// Number of players
	    sampleUserInput += "Eoghan\n";		// Name of first player
	    sampleUserInput += "Robert\n";		// Name of second player
	    sampleUserInput += "1\n";			// starting difficulty level
//	    String sampleUserInput = "2\nEoghan\nRobert\n1\n";
	    
	    InputStream sysInBackup = System.in; // backup
	    InputStream in = new ByteArrayInputStream(sampleUserInput.getBytes());
	    System.setIn(in);
	    System.setIn(sysInBackup); // Reset system input

		// Retrieve singleton instances to run the following tests on
		gameModel = GameModel.getInstance();
		gameView =  GameView.getInstance();
		gameController = GameController.getInstance(gameModel, gameView);
		
		gameController.setup();

	}
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test_IslandCreation_1() {
		
//		assertEquals()
		
	}
	
	@Test
	public void test_IslandStartedSinking_1() {
		
	}
	
	@Test
	public void test_PlayerCreation_1() {
		
	}
	
	@Test
	public void test_InitialTreasureCardHandout_1() {
		
	}
	
	@Test
	public void test_SetWaterLevel_1() {
		
		// Check if instance gets deleted
		WaterMeter wm = gameModel.getWaterMeter();
		System.out.println(wm.getWaterLevel());
		wm.incrementLevel();
		System.out.println(wm.getWaterLevel());
		wm = null;
		wm = gameModel.getWaterMeter();
		System.out.println(wm.getWaterLevel());
		
		
//		fail("Not yet implemented");
	}
	
	@After
	public void tearDown() {
		
		// Do stuff after each test? Find way to destroy singletons
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		gameModel = null;
		gameView = null;
		gameController = null;
	}
	
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

}
