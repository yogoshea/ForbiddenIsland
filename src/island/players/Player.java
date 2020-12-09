package island.players;

import java.util.ArrayList;
import java.util.List;

import island.cards.Card;
import island.cards.TreasureCard;
import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.Pawn;

/**
 * Player is an abstract class to describe the common behaviour of
 * the player types present in the Forbidden Island game, for
 * example Engineer, Messenger etc.
 * @author Eoghan O'Shea and Robert McCarthy
 * 
 */
public abstract class Player { 

	private String name;
	private Pawn pawn; //TODO: start making use of pawn
	private List<Card> cards; // check for null when using
	private int shoreUpQuantity; //TODO: maybe change to final 
	
	protected Player(String name, IslandTile startingTile) {
		this.name = name;
		this.cards = new ArrayList<Card>();
		this.pawn = new Pawn(this, startingTile);
		this.shoreUpQuantity = 1;
	}
	
	@Override
	public String toString() { //TODO: add cards and current tile?
		return this.name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Pawn getPawn() {
		return this.pawn;
	}
	
	public void addCard(Card card) {
		cards.add(card); // TODO: check for TreasureCard instance
	}
	
	public List<Card> getCards() {
		return this.cards;
	}
	
	public int getShoreUpQuantity() {
		return this.shoreUpQuantity;
	}
	
	protected void setShoreUpQuantity(int quantity) {
		this.shoreUpQuantity = quantity;
	}
	
	public List<Card> getTreasureCards() {
		
		List<Card> treasureCards = new ArrayList<Card>();
	
		for(Card c : this.cards) {
			if (c instanceof TreasureCard)
				treasureCards.add(c);
		}
		return treasureCards;
	}

	public List<IslandTile> getSwimmableTiles(IslandBoard islandBoard) {
		return islandBoard.getAdjacentTiles(this.pawn.getTile());
	}

	public List<Player> getGiveCardsPlayers(GamePlayers gamePlayers) {
		List<Player> giveCardPlayers = new ArrayList<Player>();
		for(Player p : gamePlayers) {
			if (this.pawn.getTile().equals(p.getPawn().getTile())) {
				giveCardPlayers.add(p);
			}
		}
		return giveCardPlayers;
	}

	
//	public void drawFromTreasureDeck(int cardCount) {
//		//draw cardCount cards
//		Card c;
//		for(int i = 0; i < cardCount; i++) {
//			c = TreasureDeck.getInstance().drawCard();
//			if(c instanceof WaterRiseCard) {
//				WaterMeter.getInstance().incrementLevel(); //pass card into function? like a transaction?
//				TreasureDiscardPile.getInstance().addCard(c);
//			} else {
//				receiveTreasureDeckCard(c);
//			}
//		}
//	}
//	
//	/**
//	 * method to add a card to treasureDeckCards and prompt to remove if over 5 cards in hand
//	 */
//	public void receiveTreasureDeckCard(Card c) {
//		// TODO: add if instanceof card types
//		this.cards.add(c);
//		//notifyAllObservers();
//		//If more than 5 in hand, choose cards to discard
//		while(this.cards.size() > 5) {
//			chooseCardToDiscard();
//		}
//	}
//	
//	/**
//	 * method to choose a card to discard from treasureDeckCards
//	 */
//	public void chooseCardToDiscard() {
//		Scanner userInput = new Scanner(System.in);
//		System.out.println("Which card do you wish to discard?");
//		
//		int i = 1;
//		for(Card c : this.cards) { // TODO: move printing to controller
//			System.out.print(c.toString()+" ["+Integer.toString(i)+"], ");
//			i++;
//		}
//		System.out.println();
//		
//		int iChoice = Integer.parseInt(userInput.nextLine()) - 1;
//		//Have used the block of code above alot -> make into some sort of function??
//		
//		//add and remove
//		TreasureDiscardPile.getInstance().addCard(this.cards.get(iChoice));
//		this.cards.remove(iChoice);
//	}
	
	//TODO: getCard() function which finds card in player hand and returns it - to make things cleaner??

	
	
//	public void drawFromFloodDeck(int cardCount) {
//		for(int i = 0; i < cardCount; i++) {
//			FloodCard fc = FloodDeck.getInstance().drawCard();
//			IslandBoard.getInstance().floodOrSinkTile(fc.getCorrespondingIslandTile());
//			FloodDiscardPile.getInstance().addCard(fc);
//			//This is duplicated code from startSinking()!! -> implement in drawCard()?
//		}
//	}
	
//	public void playSandBagCard() {
//		
//		boolean used;
//		for(Card c : this.cards) {
//			//if card found then use it
//			if(c instanceof SandbagCard) {
//				used = ((SandbagCard) c).use();
//				if(used) {
//					TreasureDiscardPile.getInstance().addCard(c);
//					this.cards.remove(c);
//				}
//				return;
//			}
//		}
//		
//		System.out.println("No Sandbag Card in hand");
//		
//	}
//	/*
//	 * Is there a way to use Generics to combine method above and below into one? instanceof doesn't work with generic types
//	 */
//	public void playHeliCard() {
//		
//		for(Card c : this.cards) {
//			//If heli card in hand then use it
//			if(c instanceof HelicopterLiftCard) {
//				((HelicopterLiftCard) c).use(); // TODO: cahnge how this is done
//				return;
//			}
//		}
//		
//		System.out.println("No Heli Card in hand");
//		
//	}
//	

	
	

	
}
