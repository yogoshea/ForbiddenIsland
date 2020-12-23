package island.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.game.GameGraphics;
import island.game.GameModel;
import island.game.GameView;
import island.players.Diver;
import island.players.Engineer;
import island.players.Explorer;
import island.players.GamePlayers;
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
		
		IslandBoard islandBoard = IslandBoard.getInstance();
		GamePlayers players = GamePlayers.getInstance();
		List<IslandTile> islandTiles = islandBoard.getAllTiles();
		
		GameGraphics.refreshDisplay(GameModel.getInstance());
		
		// Add all new players to game
		players.addPlayer(diver);
		players.addPlayer(engineer);
		players.addPlayer(explorer);
		players.addPlayer(messenger);
		players.addPlayer(navigator);
		players.addPlayer(pilot);
		
		
		// Place all players on tile first tile; top row on the left
		IslandTile playersTile = islandTiles.get(0);
		System.out.println(playersTile);
		for (Player p : players) {
			p.getPawn().setTile(playersTile);
		}
		
		// Get and sort returned lists
		List<IslandTile> diverTiles = diver.getSwimmableTiles(islandBoard);
		List<IslandTile> engineerTiles = engineer.getSwimmableTiles(islandBoard);
		List<IslandTile> explorerTiles = explorer.getSwimmableTiles(islandBoard);
		List<IslandTile> messengerTiles = messenger.getSwimmableTiles(islandBoard);
		List<IslandTile> navigatorTiles = navigator.getSwimmableTiles(islandBoard);
		List<IslandTile> pilotTiles = pilot.getSwimmableTiles(islandBoard);
		Collections.sort(diverTiles);
		Collections.sort(engineerTiles);
		Collections.sort(explorerTiles);
		Collections.sort(messengerTiles);
		Collections.sort(navigatorTiles);
		Collections.sort(pilotTiles);
		
		// Construct expected lists and sort
		List<IslandTile> engineerExpectedTiles = Arrays.asList(islandTiles.get(1), islandTiles.get(3));
		Collections.sort(engineerExpectedTiles);
		List<IslandTile> diverExpectedTiles = engineerExpectedTiles; // diver can swim to nearest tile
		List<IslandTile> messengerExpectedTiles = engineerExpectedTiles;
		List<IslandTile> navigatorExpectedTiles = engineerExpectedTiles;
		
		// Explorer can swim diagonally
		List<IslandTile> explorerExpectedTiles = Arrays.asList(islandTiles.get(1),
				islandTiles.get(2), islandTiles.get(3), islandTiles.get(4));
		Collections.sort(explorerExpectedTiles);
		
		// Pilot can fly anywhere
		List<IslandTile> pilotExpectedTiles = islandTiles; // all tiles
		pilotExpectedTiles.remove(playersTile); // except one they are on
		Collections.sort(pilotExpectedTiles);
		
		System.out.println(islandTiles.get(4).isSafe());
		System.out.println(islandBoard.calcDistanceBetweenTiles(islandTiles.get(0), islandTiles.get(1)));
		System.out.println(islandBoard.calcDistanceBetweenTiles(islandTiles.get(0), islandTiles.get(4)));
		System.out.println(islandBoard.calcDistanceBetweenTiles(islandTiles.get(0), islandTiles.get(2)));
		
		assertEquals("Swimmable tiles", diverExpectedTiles, diverTiles);
		assertEquals("Swimmable tiles", engineerExpectedTiles, engineerTiles);
		assertEquals("Swimmable tiles", explorerExpectedTiles, explorerTiles);
		assertEquals("Swimmable tiles", messengerExpectedTiles, messengerTiles);
		assertEquals("Swimmable tiles", navigatorExpectedTiles, navigatorTiles);
		assertEquals("Swimmable tiles", pilotExpectedTiles, pilotTiles);

		// Set safe tile back to floode
		// Identify safe tile to move to
//		IslandTile safeTile = null;
//		safeTile = islandTiles.get(1);
//		safeTile.setToSafe();
//		assertTrue("Island tile safe", safeTile.isSafe());
		
		
		
		
		// sink adjacent tiles and check getSwimmableTiles is empty for someplayers
		// Sink surrounding tiles except for one
//		for (IslandTile adjTile : islandBoard.getAdjacentTiles(tile)) {
//			adjTile.setToSunk();
//		}

	}

		// test specific player traits, that are expected for each
}
