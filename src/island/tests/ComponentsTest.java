package island.tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.game.GameController;

public class ComponentsTest {
	
	private static IslandBoard islandBoard;
	private static IslandTile[][] boardStructure;
	private static IslandTile tile00;
	private static IslandTile tile22;
	private static IslandTile tile43;
	
	private static final double DELTA = 0.001;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		islandBoard = IslandBoard.getInstance();
		boardStructure = islandBoard.getBoardStructure();
		
		tile00 = boardStructure[0][0];
		tile22 = boardStructure[2][2];
		tile43 = boardStructure[4][3];
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		GameController.reset();
	}

	@Test
	public void testGetAdjacentTiles() {
		
		List<IslandTile> adjTiles00 = islandBoard.getAdjacentTiles(tile00);
		List<IslandTile> expectedTiles00 = Arrays.asList(boardStructure[0][1], boardStructure[1][1]);
		assertTrue("Correct expected number of Adjacent tiles (tile11)", adjTiles00.size()==expectedTiles00.size());
		assertTrue("Correct adjacent tiles found (tile00)", adjTiles00.containsAll(expectedTiles00) && expectedTiles00.containsAll(adjTiles00));
		
		List<IslandTile> adjTiles22 = islandBoard.getAdjacentTiles(tile22);
		List<IslandTile> expectedTiles22 = Arrays.asList(boardStructure[2][1], boardStructure[2][3], boardStructure[1][1], boardStructure[3][2]);
		assertTrue("Correct expected number of Adjacent tiles (tile33)", adjTiles22.size()==expectedTiles22.size());
		assertTrue("Correct adjacent tiles found (tile33)", adjTiles22.containsAll(expectedTiles22) && expectedTiles22.containsAll(adjTiles22));
	}

	@Test
	public void testCalcDistanceBetweenTiles() {
		
		double expectedDistance00to22 = 2;
		double expectedDistance00to43 = 4.472136;
		double expectedDistance22to43 = 2.828427;
		
		assertEquals("Distance between tiles 00 and 22", expectedDistance00to22, islandBoard.calcDistanceBetweenTiles(tile00, tile22), DELTA);
		assertEquals("Distance between tiles 00 and 43", expectedDistance00to43, islandBoard.calcDistanceBetweenTiles(tile00, tile43), DELTA);
		assertEquals("Distance between tiles 22 and 43", expectedDistance22to43, islandBoard.calcDistanceBetweenTiles(tile22, tile43), DELTA);
	}

}
