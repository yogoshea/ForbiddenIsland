package island.tests;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import island.cards.FloodCard;
import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.WaterMeter;
import island.decks.FloodDeck;
import island.decks.FloodDiscardPile;
import island.game.GameController;
import island.game.GameModel;
import island.game.GameView;

public class GameSetupTest {
	
	private static GameModel gameModel;
	private static GameView gameView;
	private static GameController gameController;
	private static String sampleUserInput;
	private static int startingDifficulty = 2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		// Enter sample use input required at game setup
	    sampleUserInput = "2\n";			// Number of players
	    sampleUserInput += "Eoghan\n";		// Name of first player
	    sampleUserInput += "Robert\n";		// Name of second player
	    sampleUserInput += startingDifficulty + "\n";			// starting difficulty level
//	    String sampleUserInput = "2\nEoghan\nRobert\n1\n";
	    
	    InputStream sysInBackup = System.in; // backup
	    InputStream in = new ByteArrayInputStream(sampleUserInput.getBytes());
	    System.setIn(in);

		// Retrieve singleton instances to run the following tests on
		gameModel = GameModel.getInstance();
		gameView =  GameView.getInstance();
		gameController = GameController.getInstance(gameModel, gameView);
		
		gameController.setup();
	    System.setIn(sysInBackup); // Reset system input
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		gameModel = null;
		gameView = null;
		gameController = null;
	}
	
	@Test
	public void test_IslandCreation() {
		
		final int expectedNumberOfTiles = 24;
		IslandBoard islandBoard = gameModel.getIslandBoard();
		assertEquals("Number of island tiles", expectedNumberOfTiles, islandBoard.getAllTiles().size());
		assertTrue("Getting island tiles", islandBoard.getAllTiles().containsAll(Arrays.asList(IslandTile.values())));
	}
	
	@Test
	public void test_IslandStartedSinking() {
		
		final int expectedNumberOfTiles = 24;
		final int expectedFloodCardsDrawn = 6;
		
		int safeTileCount = 0;
		int floodedTileCount = 0;
		int sunkTileCount = 0;
		List<IslandTile> floodedTiles = new ArrayList<IslandTile>();
		IslandBoard islandBoard = gameModel.getIslandBoard();
		for (IslandTile tile : islandBoard.getAllTiles()) {
			if (tile.isSafe()) {
				safeTileCount++;
			} else if (tile.isFlooded()) {
				floodedTiles.add(tile);
				floodedTileCount++;
			} else if (tile.isSunk()) {
				sunkTileCount++;
			}
		}
		
		// Check expected number of island tiles have flooded
		assertEquals("Number of safe island tiles", expectedNumberOfTiles - expectedFloodCardsDrawn, safeTileCount);
		assertEquals("Number of flooded island tiles", expectedFloodCardsDrawn, floodedTileCount);
		assertEquals("Number of sunk island tiles", 0, sunkTileCount);
		
		// Check flood deck and flood discard pile are as expected
		FloodDeck floodDeck = gameModel.getFloodDeck();
		FloodDiscardPile floodDiscardPile = gameModel.getFloodDiscardPile();
		assertEquals("Size of flood deck", expectedNumberOfTiles - expectedFloodCardsDrawn, floodDeck.getAllCards().size());
		assertEquals("Size of flood discard pile", expectedFloodCardsDrawn, floodDiscardPile.getAllCards().size());
		for (FloodCard fc : floodDeck.getAllCards()) {
			assertFalse("Contents of flood deck", floodedTiles.contains(fc.getUtility()));
		}
		for (FloodCard fc : floodDiscardPile.getAllCards()) {
			assertTrue("Contents of flood discard pile", floodedTiles.contains(fc.getUtility()));
		}
	}
	
	@Test
	public void test_PlayerCreation() {
		
		// Test acceptable number of players
		// no duplicates
		// pawns created in correct locations
	}
	
	@Test
	public void test_InitialTreasureCardHandout() {
		
		// Test sizes of Treasure deck after handout
		// Test remaining contents of deck
		// Test cards held by players do not appear in deck
	}
	
	@Test
	public void test_SetWaterLevel() {
		
		WaterMeter wm = gameModel.getWaterMeter();
		assertEquals("Getting initial water level", startingDifficulty, wm.getWaterLevel());
		
		// test increment method; add 1 to water level
		wm.incrementLevel();
		assertEquals("Getting new water level", startingDifficulty + 1, wm.getWaterLevel());
	}
	
}
