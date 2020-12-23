package island.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
import island.decks.FloodDeck;
import island.decks.FloodDiscardPile;
import island.decks.TreasureDeck;
import island.decks.TreasureDiscardPile;

public class DecksTest {
	
	private FloodDeck floodDeck;
	private FloodDiscardPile floodDiscardPile;
	private TreasureDeck treasureDeck;
	private TreasureDiscardPile treasureDiscardPile;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		floodDiscardPile = FloodDiscardPile.getInstance();
		floodDeck = FloodDeck.getInstance(floodDiscardPile);
		treasureDiscardPile = TreasureDiscardPile.getInstance();
		treasureDeck = TreasureDeck.getInstance(treasureDiscardPile);
	}

	@After
	public void tearDown() throws Exception {
		FloodDeck.reset();
		FloodDiscardPile.reset();
		TreasureDeck.reset();
		TreasureDiscardPile.reset();
	}

	@Test
	public void test_FloodDeck_drawCard() {
		
		FloodCard drawnCard = floodDeck.drawCard();
		assertFalse("Drawing card from deck", floodDeck.getAllCards().contains(drawnCard));
	}

	@Test
	public void test_FloodDeck_addCard() {

		FloodCard drawnCard = floodDeck.drawCard();
		assertFalse("Drawing card from deck", floodDeck.getAllCards().contains(drawnCard));
		
		floodDeck.addCard(drawnCard);
		assertTrue("Adding card back to deck", floodDeck.getAllCards().contains(drawnCard));
	}
	
	@Test
	public void test_FloodDeck_discardAndRefill() {
		
		assertTrue("Getting discard pile contents", floodDiscardPile.getAllCards().isEmpty());
		
		int initialDeckSize = floodDeck.getAllCards().size();
		List<FloodCard> drawnCardsList = new ArrayList<FloodCard>();
		FloodCard drawnCard;
		
		for (int i = 0; i < initialDeckSize; i++) {
			drawnCard = floodDeck.drawCard();
			drawnCardsList.add(drawnCard);
			floodDiscardPile.addCard(drawnCard);
		}
		
		assertTrue("Emptying the deck", floodDeck.getAllCards().isEmpty());
		assertEquals("Getting size of discard pile",
				drawnCardsList.size(), floodDiscardPile.getAllCards().size());
		
		for (FloodCard expectedFloodCard : drawnCardsList) {
			assertTrue("Getting discard pile contents", floodDiscardPile.getAllCards().contains(expectedFloodCard));
		}
		
		drawnCard = floodDeck.drawCard(); // should cause Deck to refill
		floodDeck.addCard(drawnCard); // add car back to deck and check deck is as expected

		assertFalse("Refilling the deck", floodDeck.getAllCards().isEmpty());
		assertTrue("Getting deck contents", floodDeck.getAllCards().containsAll(drawnCardsList));
		assertTrue("Getting discard pile contents", floodDiscardPile.getAllCards().isEmpty());
	}
	
	@Test
	public void test_FloodDeck_contents() {
		
		assertEquals("Getting size of deck", IslandTile.values().length, floodDeck.getAllCards().size());
		
		for (FloodCard fc : floodDeck.getAllCards()) {
			assertTrue("Getting cards associated utility", fc.getUtility() instanceof IslandTile);
		}
		
		List<Object> cardUtilities = floodDeck.getAllCards().stream().map( FloodCard::getUtility ).collect( Collectors.toList() );
		assertTrue("Getting deck contents", cardUtilities.containsAll(Arrays.asList(IslandTile.values())));
	}

	@Test
	public void test_TreasureDeck_drawCard() {
		
		Card<?> drawnCard = treasureDeck.drawCard();
		assertFalse("Drawing card from deck", treasureDeck.getAllCards().contains(drawnCard));
		
		treasureDeck.addCard(drawnCard);
		assertTrue("Adding card back to deck", treasureDeck.getAllCards().contains(drawnCard));
	}

	@Test
	public void test_TreasureDeck_addCard() {

		Card<?> drawnCard = treasureDeck.drawCard();
		assertFalse("Drawing card from deck", treasureDeck.getAllCards().contains(drawnCard));
		
		treasureDeck.addCard(drawnCard);
		assertTrue("Adding card back to deck", treasureDeck.getAllCards().contains(drawnCard));
	}
	
	@Test
	public void test_TresureDeck_discardAndRefill() {

		assertTrue("Getting discard pile contents", treasureDiscardPile.getAllCards().isEmpty());
		
		int initialDeckSize = treasureDeck.getAllCards().size();
		List<Card<?>> drawnCardsList = new ArrayList<Card<?>>();
		Card<?> drawnCard;
		
		for (int i=0; i < initialDeckSize; i++) {
			drawnCard = treasureDeck.drawCard();
			drawnCardsList.add(drawnCard);
			treasureDiscardPile.addCard(drawnCard);
		}
		
		assertTrue("Emptying the deck", treasureDeck.getAllCards().isEmpty());
		assertEquals("Getting size of discard pile",
				drawnCardsList.size(), treasureDiscardPile.getAllCards().size());
		
		for (Card<?> expectedCard : drawnCardsList) {
			assertTrue("Getting discard pile contents", treasureDiscardPile.getAllCards().contains(expectedCard));
		}
		
		drawnCard = treasureDeck.drawCard(); // should cause Deck to refill
		treasureDeck.addCard(drawnCard); // add car back to deck and check deck is as expected
		
		assertFalse("Refilling the deck", treasureDeck.getAllCards().isEmpty());
		assertTrue("Getting deck contents", treasureDeck.getAllCards().containsAll(drawnCardsList));
		assertTrue("Getting discard pile contents", treasureDiscardPile.getAllCards().isEmpty());
	}
	
	@Test
	public void test_TreasureDeck_contents() {
		
		int expectedDeckSize = 5*Treasure.values().length + 8;
		assertEquals("Getting size of deck", expectedDeckSize, treasureDeck.getAllCards().size());

		final int expectedTreasureCardCount = 5;
		final int expectedHelicopterLiftCardCount = 3;
		final int expectedSandbagCardCount = 2;
		final int expectedWaterRiseCardCount = 3;
		
		for (Card<?> c : treasureDeck.getAllCards()) {
			assertTrue(c instanceof TreasureCard || c instanceof SpecialCard);
			assertTrue("Getting cards associated utility", c.getUtility() instanceof SpecialCardAbility || c.getUtility() instanceof Treasure);
		}
		
		List<Object> cardUtilities = treasureDeck.getAllCards().stream().map( Card<?>::getUtility ).collect( Collectors.toList() );
		
		assertEquals("Getting Treasure Card count", expectedTreasureCardCount, Collections.frequency(cardUtilities, Treasure.THE_CRYSTAL_OF_FIRE));
		assertEquals("Getting Treasure Card count", expectedTreasureCardCount, Collections.frequency(cardUtilities, Treasure.THE_EARTH_STONE));
		assertEquals("Getting Treasure Card count", expectedTreasureCardCount, Collections.frequency(cardUtilities, Treasure.THE_OCEANS_CHALICE));
		assertEquals("Getting Treasure Card count", expectedTreasureCardCount, Collections.frequency(cardUtilities, Treasure.THE_STATUE_OF_THE_WIND));
		assertEquals("Getting Helicopter Lift Card count", expectedHelicopterLiftCardCount, Collections.frequency(cardUtilities, SpecialCardAbility.HELICOPTER_LIFT));
		assertEquals("Getting Sandbag Card count", expectedSandbagCardCount, Collections.frequency(cardUtilities, SpecialCardAbility.SANDBAG));
		assertEquals("Getting Water Rise Card count", expectedWaterRiseCardCount, Collections.frequency(cardUtilities, SpecialCardAbility.WATER_RISE));
	}
	
}
