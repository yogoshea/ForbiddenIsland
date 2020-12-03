package island.game;

import java.util.Scanner;

import island.cards.TreasureDeckCard;
import island.cards.WaterRiseCard;
import island.components.WaterMeter;
import island.decks.TreasureDeck;
import island.decks.TreasureDiscardPile;
import island.players.Player;

//TODO: Split into DrawTreasure and DrawFlood???

public class DrawCardsController {
	
	private static DrawCardsController drawCardsController;
	
	private GameView gameView;
	private GameModel gameModel;
	
	
	/**
	 * Constructor to retrieve view and model instances
	 */
	private DrawCardsController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
	}
	
	/**
	 * @return single instance of draw cards controller
	 */
	public static DrawCardsController getInstance(GameModel gameModel, GameView gameView) {
		if (drawCardsController == null) {
			drawCardsController = new DrawCardsController(gameModel, gameView);
		}
		return drawCardsController;
	}
	
	public void drawTreasureCards(Player p) {
		
		drawFromTreasureDeck(p);

		//Could leave these draw methods in Player class and notify an observer when they are executed
		//Then if player has too many cards, the observer can prompt to choose which one to discard
		//OR
		//Implement all draw methods in this controller class, as I've started below
		
	}
	
	public void drawFloodCards() {
		
	}
	

	public void drawFromTreasureDeck(Player p) {
		//draw cardCount cards
		final int cardCount = 2;
		TreasureDeckCard c;
		
		for(int i = 0; i < cardCount; i++) {
			
			c = TreasureDeck.getInstance().drawCard();
			
			if(c instanceof WaterRiseCard) {
				WaterMeter.getInstance().incrementLevel(); //pass card into function? like a transaction?
				TreasureDiscardPile.getInstance().addCard(c);
			} else {
				receiveTreasureDeckCard(p, c);
			}
		}
	}
	
	
	public void receiveTreasureDeckCard(Player p, TreasureDeckCard c) {
		
		p.getTreasureDeckCards().add(c);
		//notifyAllObservers();
		//If more than 5 in hand, choose cards to discard
		while(p.getTreasureDeckCards().size() > 5) {
			chooseCardToDiscard(p);
		}
	}
	
	
	public void chooseCardToDiscard(Player p) {
		Scanner userInput = new Scanner(System.in);
		System.out.println("Which card do you wish to discard?");
		
		int i = 1;
		for(TreasureDeckCard c : p.getTreasureDeckCards()) {
			System.out.print(c.toString()+" ["+Integer.toString(i)+"], ");
			i++;
		}
		System.out.println();
		
		int iChoice = Integer.parseInt(userInput.nextLine()) - 1;
		//Have used the block of code above alot -> make into some sort of function??
		
		//add and remove
		TreasureDiscardPile.getInstance().addCard(p.getTreasureDeckCards().get(iChoice));
		p.getTreasureDeckCards().remove(iChoice);
	}

}
