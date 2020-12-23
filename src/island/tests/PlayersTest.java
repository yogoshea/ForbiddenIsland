package island.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import island.components.IslandTile;
import island.players.Diver;
import island.players.Engineer;
import island.players.Explorer;
import island.players.Messenger;
import island.players.Navigator;
import island.players.Pilot;
import island.players.Player;

public class PlayersTest {

	private Player diver;
	private Player engineer;
	private Player explorer;
	private Player messenger;
	private Player navigator;
	private Player pilot;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		// Create new Player instances
		diver = new Diver("Alice");
		engineer = new Engineer("Bob");
		explorer = new Explorer("Cian");
		messenger = new Messenger("Dave");
		navigator = new Navigator("Ellen");
		pilot = new Pilot("Fred");
	}

	@After
	public void tearDown() throws Exception {
		diver = null;
		engineer = null;
		explorer = null;
		messenger = null;
		navigator = null;
		pilot = null;
	}
	
	@Test
	public void test_startingTiles() {
		assertEquals("Diver starting tile", IslandTile.IRON_GATE, diver.getStartingTile());
		assertEquals("Engineer starting tile", IslandTile.BRONZE_GATE, engineer.getStartingTile());
		assertEquals("Explorer starting tile", IslandTile.COPPER_GATE, explorer.getStartingTile());
		assertEquals("Messenger starting tile", IslandTile.SILVER_GATE, messenger.getStartingTile());
		assertEquals("Navigator starting tile", IslandTile.GOLD_GATE, navigator.getStartingTile());
		assertEquals("Pilot starting tile", IslandTile.FOOLS_LANDING, pilot.getStartingTile());
	}

	@Test
	public void test_swimmableTiles() {
		
		// test specific player traits, that are expected for each
		
		fail("Not yet implemented");
	}

}
