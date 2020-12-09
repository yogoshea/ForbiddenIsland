package island.game;

import island.cards.Card;
import island.cards.FloodCard;
import island.cards.WaterRiseCard;
import island.components.IslandTile;
import island.players.Player;

public class DrawCardsController {
	
	private static DrawCardsController drawCardsController;
	
	private GameView gameView;
	private GameModel gameModel;
	
	
	/**
	 * Constructor to retrieve view and model instances
	 */
	private DrawCardsController(GameModel gameModel, GameView gameView) {
		//TODO: Do we need to be passing in gameModel when constructing? Can just use getInstance() for everything (as they are all singletons)
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
	
	
	/**
	 * Method to draw 2 treasure cards during a players turn
	 */
	public void drawTreasureCards(Player player) {

		//Could leave these draw methods in Player class and notify an observer when they are executed
		//Then if player has too many cards, the observer can prompt to choose which one to discard
		
		final int cardCount = 2; //TODO: should this be final static or something at start of class? 
		Card card;
		
		for(int i = 0; i < cardCount; i++) {
			
			card = gameModel.getTreasureDeck().drawCard();
			gameView.showTreasureCardDrawn(card);
			
			if(card instanceof WaterRiseCard) {
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
	 */
	public void drawFloodCards() {
		//TODO: use this function for initial flooding as well?
		FloodCard card;
		IslandTile boardTile;
		int cardCount = gameModel.getWaterMeter().getWaterLevel();
		
		for(int i = 0; i < cardCount; i++) {
			
			//draw a card
			card = gameModel.getFloodDeck().drawCard();
			boardTile = gameModel.getIslandBoard().getTile(card.getCorrespondingIslandTile());
			
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
	 * Method to add a Treasure deck card to the players hand
	 */
	public void addCardToHand(Player player, Card card) {
		
		player.addCard(card);
		
		//If more than 5 in hand, choose cards to discard
		while( player.getCards().size() > 5 ) {
			chooseCardToDiscard(player);
		}
	}
	
	/**
	 * Method for players to choose a card to discard if they have too many in their hand
	 */
	public void chooseCardToDiscard(Player player) {
		
		Card card;
		
		//choose card
		card = gameView.pickCardToDiscard(player);
		
		//remove chosen card from hand and discard it
		player.getCards().remove(card);
		gameModel.getTreasureDiscardPile().addCard(card);
		
	}

}
