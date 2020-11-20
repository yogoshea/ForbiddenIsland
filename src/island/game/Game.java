package island.game;

import island.cards.*;
import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.WaterMeter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import island.players.Diver;
import island.players.Engineer;
import island.players.Explorer;
import island.players.Messenger;
import island.players.Navigator;
import island.players.Pilot;
import island.players.Player;

/**
 * Game class describes in full the status of the game and 
 * acts as Facade for client code to interact with game
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class Game { // TODO: rename to GameFacade?
	
	// Game attributes describing the current state of the game
	private int playerCount;
	private List<Player> players;
	private IslandBoard islandBoard;
//	private IslandTileStack islandTileStack;
	
	// TODO: maybe make these Singletons that extend Stack, this will also make this class less bloated
	private Stack<FloodDeckCard> floodDeck;
	private Stack<FloodDeckCard> floodDiscardPile;
	private Stack<TreasureDeckCard> treasureDeck;
	private Stack<TreasureDeckCard> treasureDiscardPile;
	
	// WaterMeter singleton to be used in the game
	private WaterMeter waterMeter;
	
	/**
	 * Game constructor, instantiates all required game components
	 */
	public Game() {

		// TODO: Maybe move some creation methods to new classes ro prevent this class being to big?
		createIsland();
		createFloodDeck();
		startSinking();
		addPlayers();
		
		// TODO: handout TreasureCards and setup water meter
		createTreasureDeck(); // and TreasureDiscardPile
		handOutTreasureCards();
		
		waterMeter = WaterMeter.getInstance(); 
//		e.g. waterMeter.setLevel(3); // TODO: give user option to make higher for added difficulty

	}
	
	/**
	 * method to handle the creation of the island tiles and board
	 */
	private void createIsland() {
		
		// Instantiate stack of island tiles using IslandTileStack
//		IslandTileStack islandTiles = new IslandTileStack(); // maybe just put this in IslandBoard constructor?
		
		// I don't think we need the IslandTileStack class really

		// OR GET RID OF IslandTileStack AND JUST DO THE FOLLOWING
		// Create stack of IslandTiles
		Stack<IslandTile> islandTiles = new Stack<>();
		
		// Add all island tiles to stack when instantiated
		islandTiles.addAll(Arrays.asList(IslandTile.values()));
		
		// Shuffle the tiles in the stack
		Collections.shuffle(islandTiles);
		
		// Instantiate island board with this stack of island tiles
		islandBoard = new IslandBoard(islandTiles);
		
		// display newly generated island board
		System.out.println("Welcome to the Forbidden Island!");
		System.out.println(islandBoard);
	}
	
	/**
	 * method to handle the creation of the game's players
	 */
	private void addPlayers() {

		// create scanner to read input from players
		Scanner userInput = new Scanner(System.in);
		
		System.out.println("How many players are there?");
		playerCount = Integer.parseInt(userInput.nextLine());
		
		// instantiate players as ArrayList with initial capacity playerCount
		players = new ArrayList<Player>(playerCount);
		
		// create list of possible roles players can have
		List<String> possibleRoles = Arrays.asList("Diver", "Engineer", "Explorer", 
				"Messenger", "Navigator", "Pilot");
		
		// randomise list order
		Collections.shuffle(possibleRoles); 
		
		// iterate over number of players
		for (int i = 0; i < playerCount; i++) {
			
			System.out.println("Enter the name of Player " + (i+1) + ":");
			String playerName = userInput.nextLine();
			
			// TODO: check if player name already given
			
			// instantiate specific player subclasses 
			switch (possibleRoles.get(i)) {

				case "Diver":
					players.add(new Diver(playerName));
					break;
					
				case "Engineer":
					players.add(new Engineer(playerName));
					break;
				
				case "Explorer":
					players.add(new Explorer(playerName));
					break;
				
				case "Messenger":
					players.add(new Messenger(playerName));
					break;
					
				case "Navigator":
					players.add(new Navigator(playerName));
					break;
					
				case "Pilot":
					players.add(new Pilot(playerName));
					break;
				
			}
		}
		
		// close Scanner
		userInput.close();
	}
	
	/**
	 * method to handle creation of flood deck
	 */
	private void createFloodDeck() {
		
		// instantiate flood card container classes
		floodDeck = new Stack<FloodDeckCard>();
		floodDiscardPile = new Stack<FloodDeckCard>();
		
		// Add all island cards to stack when instantiated
		floodDeck.addAll(Arrays.asList(FloodDeckCard.values()));
		// Shuffle the cards in the stack
		Collections.shuffle(floodDeck);
	}
	
	/**
	 * method to flood 6 tiles at start of game
	 */
	private void startSinking() {
		for (int i = 0; i < 6; i++) {
			FloodDeckCard temp = floodDeck.pop();
			//Flood corresponding tile
			islandBoard.floodTile(temp);
			//Add card to discard pile
			floodDiscardPile.push(temp);
		}
		//Print out tiles that were flooded
	}
	
	
	/**
	 * method to instantiate and populate the Treasure Deck
	 */
	private void createTreasureDeck() {
		
		// instantiate treasure deck card container classes
		treasureDeck = new Stack<TreasureDeckCard>();
		treasureDiscardPile = new Stack<TreasureDeckCard>();
		
		// TODO: make these 'private final' in new class?
		int heliCardCount = 3;
		int sandCardCount = 2;
		int watCardCount = 3;
		
		// TODO: move this to separate class?
		
		// add treasure cards to deck, 5 cards for each treasure
		for (int i = 0; i < 5; i++) {
			treasureDeck.addAll(Arrays.asList(TreasureCard.values()));
		}
		
		// add helicopter lift cards
		for (int i = 0; i < heliCardCount; i++) {
			treasureDeck.add(new HelicopterLiftCard());
		}
		
		// add sandbag cards
		for (int i = 0; i < sandCardCount; i++) {
			treasureDeck.add(new SandbagCard());
		}

		// add water rise cards
		for (int i = 0; i < watCardCount; i++) {
			treasureDeck.add(new WaterRiseCard());
		}
		
		// shuffle treasure deck
		Collections.shuffle(treasureDeck);
		
	}
	
	/**
	 * method to initially give players cards from the Treasure Deck
	 */
	private void handOutTreasureCards() {

		int cardsDrawnCount;
		final int numberOfCardsPerPlayer = 2;
		TreasureDeckCard drawnCard;
		
		// iterate of players in game
		for (Player p : players) {
			
			cardsDrawnCount = 0;
			
			do {
				drawnCard = treasureDeck.pop();
				System.out.println("Drawn card: " + drawnCard);
				if (drawnCard instanceof WaterRiseCard) {
					// push card back into deck and shuffle
					treasureDeck.push(drawnCard);
					Collections.shuffle(treasureDeck);
				} else {
					cardsDrawnCount++;
					p.takeTreasureCard(drawnCard);
				}
			} while (cardsDrawnCount < numberOfCardsPerPlayer);
		}
		System.out.println("Finished handing out cards!");
	}
	
	
	/**
	 * @return List of Player's playing the game
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	// TODO: getters and setters for Game info
	
}
