package island.game;

import island.cards.Card;
import island.cards.FloodCard;
import island.cards.SpecialCard;
import island.cards.SpecialCardAbility;
import island.components.IslandTile;
import island.players.Player;

public class DrawCardsController {
	
	private static DrawCardsController drawCardsController;
	
	private GameView gameView;
	private GameModel gameModel;
	
	
	/**
	 * Constructor for DrawCardsController singleton.
	 * @param Reference to GameModel.
	 * @param Reference to GameView.
	 */
	private DrawCardsController(GameModel gameModel, GameView gameView) {
		//TODO: Do we need to be passing in gameModel when constructing? Can just use getInstance() for everything (as they are all singletons)
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
	 * Method to draw 2 treasure cards during a players turn
	 * @param Reference to player to draw cards.
	 */
	public void drawTreasureCards(Player player) {

		//Could leave these draw methods in Player class and notify an observer when they are executed
		//Then if player has too many cards, the observer can prompt to choose which one to discard
		gameView.showEnterToContinue();
		gameView.updateView(gameModel, player);
		gameView.showDrawTreasureCards();
		
		final int cardCount = 2; //TODO: should this be final static or something at start of class? 
		Card card;
		
		for(int i = 0; i < cardCount; i++) {
			
			card = gameModel.getTreasureDeck().drawCard();
			gameView.showTreasureCardDrawn(card);
			
			if(card instanceof SpecialCard && card.getUtility().equals(SpecialCardAbility.WATER_RISE)) { // TODO: check casting is correct with getUtility()
				gameModel.getWaterMeter().incrementLevel(); //OR pass card into function? like a transaction?
				gameModel.getTreasureDiscardPile().addCard(card);
				gameView.showWaterRise( gameModel.getWaterMeter().getWaterLevel() );
				//TODO: OR have an observer of the WaterMeter call gameView.showWaterRise() when a rise occurs?? Probably the proper way to do it
			} else {
				addCardToHand(player, card);
			}
			
		}
		
	}
	
	
	/**
	 * Method to draw 2 flood cards during a players turn
	 * @param Reference to player to draw cards.
	 */
	public void drawFloodCards(Player player) {
		
		gameView.showEnterToContinue();
		gameView.updateView(gameModel, player);
		gameView.showDrawFloodCards();
		
		//TODO: use this function for initial flooding as well?
		FloodCard card;
		IslandTile boardTile;
		int cardCount = gameModel.getWaterMeter().getWaterLevel();
		
		for(int i = 0; i < cardCount; i++) {
			
			//draw a card
			card = gameModel.getFloodDeck().drawCard();
			boardTile = gameModel.getIslandBoard().getTile(card.getUtility());
			
			//Perform action on appropriate tile
			if (boardTile.isSafe()) {
				gameView.showTileFlooded(boardTile);
				boardTile.setToFlooded();
			} else if (boardTile.isFlooded()) {
				gameView.showTileSunk(boardTile);
				boardTile.setToSunk();
			}
			
			//Add card to discard pile
			gameModel.getFloodDiscardPile().addCard(card);
			
		}
		
	}
	
	
	/**
	 * Method to add a Treasure deck card to the players hand.
	 * @param Reference to player to give cards to.
	 * @param Card instance to give to player.
	 */
	public void addCardToHand(Player player, Card card) {
		final int maxAllowedCards = 5;//TODO: move this to constructor?
		player.addCard(card);
		
		//If more than 5 in hand, choose cards to discard
		while( player.getCards().size() > maxAllowedCards ) {
			chooseCardToDiscard(player);
		}
	}
	
	/**
	 * Method for players to choose a card to discard if they have too many in their hand.
	 * @param Player to choose card.
	 */
	public void chooseCardToDiscard(Player player) {
		
		Card card;
		
		//choose card
		card = gameView.pickCardToDiscard(player);
		
		//remove chosen card from hand and discard it
		player.getCards().remove(card);
		gameModel.getTreasureDiscardPile().addCard(card);
		
	}
	
	// Singleton reset for JUnit testing
	public void reset() {
		drawCardsController = null;
	}

}
