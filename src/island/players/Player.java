package island.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import island.cards.FloodCard;
import island.cards.TreasureCard;
import island.cards.TreasureDeckCard;
import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.Pawn;
import island.components.Treasure;
import island.components.WaterMeter;
import island.decks.FloodDeck;
import island.decks.FloodDiscardPile;
import island.decks.TreasureDeck;
import island.decks.TreasureDiscardPile;

/**
 * Player is an abstract class to describe the common behaviour of
 * the player types present in the Forbidden Island game, for
 * example Engineer, Messenger etc.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public abstract class Player { //TODO: Make class shorter!!!!?????

	private String name;
	private IslandTile currentTile;
	private Pawn pawn; //TODO: start making use of pawn
	private List<TreasureDeckCard> treasureDeckCards; // check for null when using
	
	public Player(String name) {
		this.name = name;
		treasureDeckCards = new ArrayList<TreasureDeckCard>(); // TODO: check for overflow, set max size five
	}
	
	/**
	 * method to take a players turn
	 */
	public void takeTurn(Scanner userInput) {
		
		boolean successfullyTaken;
		int availableActions = 3;
		
		System.out.println("Do you wish to take an action? ("+Integer.toString(availableActions)+" remaining)");
		System.out.println("[Y]/[N]");
		String takeAction = userInput.nextLine();
		
		while(availableActions > 0 && takeAction.equals("Y")) {
			//take an action
			successfullyTaken = takeAction(userInput);
			
			//decrease available actions if successful
			availableActions -= successfullyTaken ? 1 : 0;
			
			System.out.println("Do you wish to take another action? ("+Integer.toString(availableActions)+" remaining)");
			System.out.println("[Y]/[N]");
			takeAction = userInput.nextLine();
		}
		
		//Draw Treasure deck cards
		System.out.println("Drawing 2 cards from Treasure Deck...");
		drawFromTreasureDeck(2); //make 2 a final value??
		
		//draw Flood cards
		System.out.println("Drawing Flood cards...");
		drawFromFloodDeck(WaterMeter.getInstance().getLevel());
		
	}
	
	/**
	 * method to choose the action to take during a turn
	 * @return whether action successfully taken
	 */
	public boolean takeAction(Scanner userInput) {
		
		boolean successfullyTaken = false;
		
		System.out.println("Select one of the following actions:");
		System.out.println("Move [M], Shore-Up [S], Give Treasure Card [G], Capture a Treasure [C]");
		String actionType = userInput.nextLine();
		
		switch(actionType) {
			case "M":
				successfullyTaken = move(userInput);
				break;
			
			case "S":
				successfullyTaken = shoreUp(userInput);
				break;
				
			case "G":
				successfullyTaken = giveTreasureCard(userInput);
				break;
				
			case "C":
				successfullyTaken = captureTreasure();
				break;
		}
		
		return successfullyTaken;
	}
	
	/**
	 * Changes players current tile to adjacent tile of their choice
	 * @returns true if successful move made
	 */
	private boolean move(Scanner userInput) {
		//TODO: print island board? - or give option to do that at any time??
		
		List<IslandTile> adjTiles = IslandBoard.getInstance().findAdjacentTiles(currentTile);
		
		if(adjTiles.size() > 0) {
			
			System.out.println("Which tile do you wish to move to?");
			
			int i = 1;
			for(IslandTile t : adjTiles) {
				System.out.print(t.name()+" ["+Integer.toString(i)+"], ");
				i++;
			}
			System.out.println();
			
			int choice = Integer.parseInt(userInput.nextLine()) - 1;
			currentTile = adjTiles.get(choice);
			return true;
			
		} else {
			System.out.println("No available tiles, Unlucky m8");
			return false;
		}
	}
	
	/**
	 * method to shore up tile of users choice
	 * @return true if shore-up action successful, false otherwise
	 */
	private boolean shoreUp(Scanner userInput) {
		
		List<IslandTile> adjTiles = IslandBoard.getInstance().findAdjacentTiles(currentTile);
		adjTiles.add(currentTile); //can shore-up current tile
		
		for(IslandTile t : adjTiles) {
			//remove tiles that aren't flooded
			if(!t.isFlooded()) {
				adjTiles.remove(t);
			}
		}
		
		if(adjTiles.size() > 0) {
			System.out.println("Which tile do you wish to shore-up?");
			
			int i = 1;
			for(IslandTile t : adjTiles) {
				System.out.print(t.name()+" ["+Integer.toString(i)+"], ");
				i++;
			}
			System.out.println();
			
			int choice = Integer.parseInt(userInput.nextLine()) - 1;
			return IslandBoard.getInstance().shoreUp(adjTiles.get(choice));
			
		} else {
			System.out.println("No available tiles to shore-up");
			return false;
		}
		//Is it better to show user what tiles they can perform action on
		//OR let them choose a tile based on map and then we tell them if its a valid choice?
	}
	
	
	/**
	 * method to give a treasure card from hand to another player on the same tile
	 * @return whether or not treasure successfully given
	 */
	private boolean giveTreasureCard(Scanner userInput) {
		
		List<Player> playersOnSameTile = new ArrayList<Player>();
		
		//Find players on same tile
		for(Player p : GamePlayers.getInstance().getPlayersList()) {
			if( currentTile.equals(p.getCurrentTile()) && !this.equals(p) ) {
				playersOnSameTile.add(p);
			}
		}
		
		if(playersOnSameTile.size() > 0) { //player found
			
			//User chooses player to give card to
			System.out.println("Which player do you wish to give a card to?");
			
			int i = 1;
			for(Player p : playersOnSameTile) {
				System.out.print(p.toString()+" ["+Integer.toString(i)+"], ");
				i++;
			}
			System.out.println();
			
			int playerChoice = Integer.parseInt(userInput.nextLine()) - 1;
			
			//User chooses card to give
			System.out.println("Which card do you wish to give");
			
			int j = 1;
			for(TreasureDeckCard c : treasureDeckCards) {
				if(c instanceof TreasureCard) {
					System.out.print(c.toString()+" ["+Integer.toString(j)+"], ");
				}
				j++;//TODO: Fix printing so not skipping numbers
			}
			System.out.println();
			
			int cardChoice = Integer.parseInt(userInput.nextLine()) - 1;
			
			//give card
			playersOnSameTile.get(playerChoice).receiveTreasureDeckCard(treasureDeckCards.get(cardChoice));
			treasureDeckCards.remove(cardChoice);
			return true;
			
		} else {
			System.out.println("Could not give card");//Explain why..
			return false;
		}
	}
	
	
	/**
	 * method to capture a treasure from current tile
	 * @return whether or not treasure successfully captured
	 */
	private boolean captureTreasure() { //Overcomplicated - improve?
		
		//If true - Collect all cards which can be used to capture treasure
		if(currentTile.getAssociatedTreasure() != null) {
			
			List<TreasureDeckCard> tradeCards = new ArrayList<TreasureDeckCard>();
			
			//Take out all relevant treasure cards
			for(TreasureDeckCard c : treasureDeckCards) {
				if(c instanceof TreasureCard) {
					if( ((TreasureCard) c).getAssociatedTreasure().equals(currentTile.getAssociatedTreasure())) {
						tradeCards.add(c);
						treasureDeckCards.remove(c);
					}
				}
			}
			
			//Capture Treasure if have enough cards
			if(tradeCards.size() >= 4) {
				//TODO: make 4 a final value
				
				//Discard 4 treasure cards
				for(int i = 0; i < 4; i++) {
					TreasureDiscardPile.getInstance().addCard(tradeCards.get(i));
					tradeCards.remove(i);
				}
				
				//capture treasure
				GamePlayers.getInstance().addTreasure(currentTile.captureAssociatedTreasure());
				
				//if there, add extra card back
				if(tradeCards.size() > 0) {treasureDeckCards.add(tradeCards.get(0));}
				//TODO: Don't assume only 1 card left? Don't add the extra one?
				
				return true;
				
			} else {
				//if can't capture treasure, return cards to player deck
				for(int i = 0; i < tradeCards.size(); i++) {
					treasureDeckCards.add(tradeCards.get(i));
				}
			}
			
		}
		return false;
	}
	
	
	public void drawFromTreasureDeck(int cardCount) {
		//draw cardCount cards
		for(int i = 0; i < cardCount; i++) {
			receiveTreasureDeckCard( TreasureDeck.getInstance().drawCard() );
		}
	}
	
	/**
	 * method to add a card to treasureDeckCards and prompt to remove if over 5 cards in hand
	 */
	public void receiveTreasureDeckCard(TreasureDeckCard c) {
		treasureDeckCards.add(c);
		//If more than 5 in hand, choose cards to discard
		while(treasureDeckCards.size() > 5) {
			//chooseCardToDiscard(userInput);
			//TODO: figure out how to get userInput to here - put this call in an observer class?
		}
	}
	
	/**
	 * method to choose a card to discard from treasureDeckCards
	 */
	public void chooseCardToDiscard(Scanner userInput) {
		
		System.out.println("Which card do you wish to discard?");
		
		int i = 1;
		for(TreasureDeckCard c : treasureDeckCards) {
			System.out.print(c.toString()+" ["+Integer.toString(i)+"], ");
			i++;
		}
		System.out.println();
		
		int iChoice = Integer.parseInt(userInput.nextLine()) - 1;
		//Have used the block of code above alot -> make into some sort of function??
		
		//add and remove
		TreasureDiscardPile.getInstance().addCard(treasureDeckCards.get(iChoice));
		treasureDeckCards.remove(iChoice);
	}
	
	
	public void drawFromFloodDeck(int cardCount) {
		for(int i = 0; i < cardCount; i++) {
			FloodCard fc = FloodDeck.getInstance().drawCard();
			IslandBoard.getInstance().floodTile(fc);
			FloodDiscardPile.getInstance().addCard(fc);
			//This is duplicated code from startSinking()!! -> implement in drawCard()?
		}
	}
	
	
	@Override
	public String toString() {
		return getName();
	}
	
	// getters and setters
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setCurrentTile(IslandTile t) {
		currentTile = t;
	}
	
	public IslandTile getCurrentTile() {
		return currentTile;
	}
	
	public List<TreasureDeckCard> getTreasureCards() {
		return treasureDeckCards;
	}
}
