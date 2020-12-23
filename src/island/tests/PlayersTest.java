package island.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import island.game.GameModel;
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
	private IslandBoard islandBoard;
	GamePlayers players;
	List<IslandTile> islandTiles;
	
	@Before
	public void setUp() throws Exception {

		// Create new Player instances
		diver = new Diver("Alice");
		engineer = new Engineer("Bob");
		explorer = new Explorer("Cian");
		messenger = new Messenger("Dave");
		navigator = new Navigator("Ellen");
		pilot = new Pilot("Fred");
		
		islandBoard = IslandBoard.getInstance();
		players = GamePlayers.getInstance();
		islandTiles = islandBoard.getAllTiles();
		
		// Add all new players to game
		players.addPlayer(diver);
		players.addPlayer(engineer);
		players.addPlayer(explorer);
		players.addPlayer(messenger);
		players.addPlayer(navigator);
		players.addPlayer(pilot);
	}

	@After
	public void tearDown() throws Exception {
		diver = null;
		engineer = null;
		explorer = null;
		messenger = null;
		navigator = null;
		pilot = null;
		players.getPlayersList().clear();
		GameModel.reset();
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
		
		// Place all players on tile first tile; top row on the left
		IslandTile playersTile = islandTiles.get(0);
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
		List<IslandTile> pilotExpectedTiles = new ArrayList<IslandTile>(); // all tiles
		for (IslandTile tile : islandTiles) {
			if (! tile.equals(playersTile)) { // except current tile
				pilotExpectedTiles.add(tile); 
			}
		}
		Collections.sort(pilotExpectedTiles);
		
		assertEquals("Swimmable tiles", diverExpectedTiles, diverTiles);
		assertEquals("Swimmable tiles", engineerExpectedTiles, engineerTiles);
		assertEquals("Swimmable tiles", explorerExpectedTiles, explorerTiles);
		assertEquals("Swimmable tiles", messengerExpectedTiles, messengerTiles);
		assertEquals("Swimmable tiles", navigatorExpectedTiles, navigatorTiles);
		assertEquals("Swimmable tiles", pilotExpectedTiles, pilotTiles);
	}

	@Test
	public void test_cardReceivablePlayers() {
		
		// Place all players on tile first tile; top row on the left
		IslandTile playersTile = islandTiles.get(0);
		for (Player p : players) {
			p.getPawn().setTile(playersTile);
		}
		
		List<Player> diverPlayers = diver.getCardReceivablePlayers(players);
		List<Player> engineerPlayers = engineer.getCardReceivablePlayers(players);
		List<Player> explorerPlayers = explorer.getCardReceivablePlayers(players);
		List<Player> messengerPlayers = messenger.getCardReceivablePlayers(players);
		List<Player> navigatorPlayers = navigator.getCardReceivablePlayers(players);
		List<Player> pilotPlayers = pilot.getCardReceivablePlayers(players);
		
		// All players should be able to give to each other if on same tile
		assertTrue("Players that can be given to", players.getPlayersList().containsAll(diverPlayers));
		assertTrue("Players that can be given to", players.getPlayersList().containsAll(engineerPlayers));
		assertTrue("Players that can be given to", players.getPlayersList().containsAll(explorerPlayers));
		assertTrue("Players that can be given to", players.getPlayersList().containsAll(messengerPlayers));
		assertTrue("Players that can be given to", players.getPlayersList().containsAll(navigatorPlayers));
		assertTrue("Players that can be given to", players.getPlayersList().containsAll(pilotPlayers));
		
		// Put on different tile, check expected
		diver.getPawn().setTile(islandTiles.get(0));
		engineer.getPawn().setTile(islandTiles.get(5));
		explorer.getPawn().setTile(islandTiles.get(6));
		messenger.getPawn().setTile(islandTiles.get(11));
		navigator.getPawn().setTile(islandTiles.get(14));
		pilot.getPawn().setTile(islandTiles.get(23));
		
		// Only Messenger can give to players not on tile
		assertTrue("Players that can be given to", diver.getCardReceivablePlayers(players).isEmpty());
		assertTrue("Players that can be given to", engineer.getCardReceivablePlayers(players).isEmpty());
		assertTrue("Players that can be given to", explorer.getCardReceivablePlayers(players).isEmpty());
		assertTrue("Players that can be given to", players.getPlayersList().containsAll(messenger.getCardReceivablePlayers(players)));
		assertTrue("Players that can be given to", navigator.getCardReceivablePlayers(players).isEmpty());
		assertTrue("Players that can be given to", pilot.getCardReceivablePlayers(players).isEmpty());
	}
}
