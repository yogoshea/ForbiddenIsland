package island.controllers;

import java.util.List;

import island.cards.Card;
import island.cards.FloodCard;
import island.cards.SpecialCardAbility;
import island.cards.TreasureCard;
import island.components.GameModel;
import island.components.IslandTile;
import island.players.Player;
import island.view.GameView;
import island.view.Messages;

public class DrawCardsController {
	
	private static DrawCardsController drawCardsController;
	
	private GameView gameView;
	private GameModel gameModel;
	
	// Static card to draw counts
	final static int treasureCardsPerTurn = 2;
	final static int maxCardsAllowedPerPlayer = 5;
	
	/**
	 * Constructor for DrawCardsController singleton.
	 * @param Reference to GameModel.
	 * @param Reference to GameView.
	 */
	private DrawCardsController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
	}
	
	/**
	 * Getter method for singleton instance.
	 * @return single instance of draw cards controller.
	 */
	public static DrawCardsController getInstance(GameModel gameModel, GameView gameView) {
		if (drawCardsController == null) {
			drawCardsController = new DrawCardsController(gameModel, gameView);
		}
		return drawCardsController;
	}
	
	
	/**
	 * Method to draw 2 treasure cards during a players turn.
	 * @param Reference to player to draw cards.
	 */
	public void drawTreasureCards(Player player) {

		// Show information to user through game view
		Prompts.promptEnterToContinue();
		gameView.updateView(gameModel, player);
		Prompts.promptDrawTreasureCards();

		// Draw set number of cards per turn
		Card<?> drawnCard;
		for(int i = 0; i < treasureCardsPerTurn; i++) {
			
			drawnCard = gameModel.getTreasureDeck().drawCard();
			Messages.showTreasureCardDrawn(drawnCard);
			
			// Increment water level if Waters Rise card drawn
			if(drawnCard.getUtility().equals(SpecialCardAbility.WATER_RISE)) {
				
				//Increment level
				gameModel.getWaterMeter().incrementLevel();
				gameModel.getTreasureDiscardPile().addCard(drawnCard);
				drawnCard = null;
				
				//Refill flood deck
				gameModel.getFloodDeck().refill();
				Messages.showWaterRise( gameModel.getWaterMeter().getWaterLevel() );
			
			//If the drawn card is a treasure card, offer chance to give it to another player
			} else if(drawnCard instanceof TreasureCard) {
				
				boolean keepCard = Prompts.pickKeepOrGive();
				
				//If player wishes to give away card
				if(!keepCard) {
					
					List<Player> availablePlayers = player.getCardReceivablePlayers(gameModel.getGamePlayers());
					
					if(availablePlayers.isEmpty()) {
						Messages.showNoAvailablePlayers();
					} else {
						//If players available, prompt to pick one, then give card
						Player reciever = Prompts.pickPlayerToRecieveCard(availablePlayers);
						addCardToHand(reciever, drawnCard);
						Messages.showCardGiven(drawnCard, player, reciever);
						drawnCard = null;
					}	
				}
				
			} 
			
			//If card has not been used yet, add to hand
			if(drawnCard != null) {
				addCardToHand(player, drawnCard);
			}
			
		}
		
	}
	
	
	/**
	 * Method to draw flood cards during a players turn.
	 * @param Reference to player to draw cards.
	 */
	public void drawFloodCards(Player player) {
		
		Prompts.promptEnterToContinue();
		gameView.updateView(gameModel, player);
		Prompts.promptsDrawFloodCards();
		
		FloodCard card;
		IslandTile boardTile;
		int cardCount = gameModel.getWaterMeter().getWaterLevel();
		
		// Iterate over appropriate card count
		for(int i = 0; i < cardCount; i++) {
			
			// Draw a card from flood deck
			card = gameModel.getFloodDeck().drawCard();
			boardTile = card.getUtility();
			
			// Perform appropriate action on tile
			if (boardTile.isSafe()) {
				boardTile.setToFlooded();
				Messages.showTileFlooded(boardTile);
			} else if (boardTile.isFlooded()) {
				Messages.showTileSunk(boardTile); //Print first so player can see that their tile has sunk
				boardTile.setToSunk();
			}
			
			// Add card to discard pile
			gameModel.getFloodDiscardPile().addCard(card);
		}
	}
	
	
	/**
	 * Method to add a Treasure deck card to the players hand.
	 * @param Reference to player to give cards to.
	 * @param Card instance to give to player.
	 */
	public void addCardToHand(Player player, Card<?> card) {
		player.addCard(card);
		
		// If more than 5 cards in hand, choose cards to discard
		while( player.getCards().size() > maxCardsAllowedPerPlayer ) {
			chooseCardToDiscard(player);
		}
	}
	
	/**
	 * Method for players to choose a card to discard if they have too many in their hand.
	 * @param Player to choose card.
	 */
	public void chooseCardToDiscard(Player player) {
		
		// Remove chosen card from hand and discard it
		Card<?> card = Prompts.pickCardToDiscard(player);
		player.getCards().remove(card);
		gameModel.getTreasureDiscardPile().addCard(card);
		
	}
	
	// Singleton reset for JUnit testing
	public static void reset() {
		drawCardsController = null;
	}

}
