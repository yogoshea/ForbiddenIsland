package island.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import island.cards.Card;
import island.cards.FloodCard;
import island.cards.SpecialCard;
import island.cards.SpecialCardAbility;
import island.cards.TreasureCard;
import island.components.IslandTile;
import island.components.Treasure;

public class CardsTest {
	
	private Card<IslandTile> floodCard;
	private Card<SpecialCardAbility> specialCard;
	private Card<Treasure> treasureCard;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test_FloodCard() {
		
		floodCard = new FloodCard(IslandTile.BREAKERS_BRIDGE);
		
		String expectedName = "Breakers Bridge Flood Card";
		assertEquals("Accessing name of card", expectedName, floodCard.getName());
		
		IslandTile expectedIslandTile = IslandTile.BREAKERS_BRIDGE;
		assertEquals("Accessing card utility", expectedIslandTile, floodCard.getUtility());
		
	}

	@Test
	public void test_SpecialCard() {
		
		specialCard = new SpecialCard(SpecialCardAbility.HELICOPTER_LIFT);

		String expectedName = "Helicopter Lift Card";
		assertEquals("Accessing name of card", expectedName, specialCard.getName());
		
		SpecialCardAbility expectedAbility = SpecialCardAbility.HELICOPTER_LIFT;
		assertEquals("Accessing card utility", expectedAbility, specialCard.getUtility());
	}
	
	@Test
	public void test_TreasureCard() {
		
		treasureCard = new TreasureCard(Treasure.THE_CRYSTAL_OF_FIRE);
		
		String expectedName = "Crystal of Fire Card";
		assertEquals("Accessing name of card", expectedName, treasureCard.getName());
		
		Treasure expectedTreasure = Treasure.THE_CRYSTAL_OF_FIRE;
		assertEquals("Accessing card utility", expectedTreasure, treasureCard.getUtility());
	}
	
}
