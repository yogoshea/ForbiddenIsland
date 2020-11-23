package island.players;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import island.cards.TreasureCard;
import island.cards.TreasureDeckCard;
import island.components.IslandTile;
import island.components.Pawn;
import island.components.Treasure;
import island.decks.TreasureDiscardPile;

/**
 * Player is an abstract class to describe the common behaviour of
 * the player types present in the Forbidden Island game, for
 * example Engineer, Messenger etc.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public abstract class Player {

	private String name;
	private IslandTile currentTile;
	private Pawn pawn;
	private List<TreasureDeckCard> treasureDeckCards; // check for null when using
	
	public Player(String name) {
		this.name = name;
		treasureDeckCards = new ArrayList<TreasureDeckCard>(); // TODO: check for overflow, set max size five
	}
	
	/**
	 * method to take a players turn
	 */
	public void takeTurn(Scanner userInput) {
		int availableActions = 3;
		System.out.println("Do you wish to take an action? ("+Integer.toString(availableActions)+" remaining)");
		System.out.println("[Y]/[N]");
		String takeAction = userInput.nextLine();
		boolean successfullyTaken;
		
		while(availableActions > 0 && takeAction.equals("Y")) {
			//take an action
			successfullyTaken = takeAction(userInput);
			//decrease available actions if successful
			availableActions += successfullyTaken ? 1 : 0;
			System.out.println("Do you wish to take another action? ("+Integer.toString(availableActions)+" remaining)");
			System.out.println("[Y]/[N]");
			takeAction = userInput.nextLine();
		}
		//TODO: take remainder of turn
		//Draw Treasure deck cards
		System.out.println("Drawing 2 cards from Treasure Deck...");
		//draw Flood cards
		System.out.println("Drawing Flood cards...");
		
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
				move();
				break;
			
			case "S":
				shoreUp();
				break;
				
			case "G":
				giveTreasureCard();
				break;
				
			case "C":
				successfullyTaken = captureTreasure();
				break;
		}
		return successfullyTaken;
	}
	
	
	private void move() {
		
	}
	
	private void shoreUp() {
		
	}
	
	private void giveTreasureCard() {
		
	}
	
	
	/**
	 * method to capture a treasure
	 * @return whether treasure successfully captured
	 */
	private boolean captureTreasure() { //V.long method - improve?
		//If true - Collect all cards which can be used to capture treasure
		if(currentTile.getAssociatedTreasure() != null) {
			List<TreasureDeckCard> tradeCards = new ArrayList<TreasureDeckCard>();
			//treasureDeckCards.indexOf(o)
			//Take out all relevant cards
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
				for(int i = 0; i < 4; i++) {
					TreasureDiscardPile.getInstance().addCard(tradeCards.get(i));
					tradeCards.remove(i);
				}
				GamePlayers.getInstance().addTreasure(currentTile.captureAssociatedTreasure());
				if(tradeCards.size() > 0) {treasureDeckCards.add(tradeCards.get(0));}//add extra card back
				//TODO: Don't assume only 1 card left? Don't add the extra one?
				return true;
			}
		}
		return false;
	}
	
	
	// TODO: change this to drawFromTreasureDeck(int cardCount) {}
	public void takeTreasureCard(TreasureDeckCard newTreasureDeckCard) {
		treasureDeckCards.add(newTreasureDeckCard);
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
