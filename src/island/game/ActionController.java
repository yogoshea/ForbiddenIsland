package island.game;

import java.util.ArrayList;
import java.util.List;

import island.cards.Card;
import island.cards.TreasureCard;
import island.components.IslandTile;
import island.components.Treasure;
import island.players.Player;

/**
 * Controller class for retrieving player choices for GameView and change 
 * game model accordingly
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class ActionController { //Name PlayerActionController for clarity?
	
	// Singleton to be instantiated
	private static ActionController actionController;
	
	private GameView gameView;
	private GameModel gameModel;
	private GameController gameController;
	
	public enum Action { // TODO:  move strings to GameView
		MOVE("Move"),
		SHORE_UP("Shore Up"),
		GIVE_TREASURE_CARD("Give Treasure Card"),
		CAPTURE_TREASURE("Capture Treasure"),
		NONE("None");
		
		private String actionString;
		Action(String s) {
			this.actionString = s;
		}
		public String toString() {
			return actionString;
		}
	}
	
	/**
	 * Constructor to retrieve view and model instances
	 */
	private ActionController(GameModel gameModel, GameView gameView, GameController gameController) {
		this.gameModel = gameModel;
		this.gameView = gameView;
		this.gameController = gameController;
	}
	
	/**
	 * @return single instance of action controller
	 */
	public static ActionController getInstance(GameModel gameModel, GameView gameView, GameController gameController) {
		if (actionController == null) {
			actionController = new ActionController(gameModel, gameView, gameController);
		}
		return actionController;
	}
	
	public void takeActions(Player p) {
		
//		gameView.showPlayerTurn(p); // TODO: make sure to print this
		Action actionChoice;
		boolean actionSuccessfullyTaken = false;
		int remainingTurns = 3;
		
		do {
			
			gameView.updateView(gameModel); // display updated full game view after every action
			gameView.showPlayerTurn(p);
			actionChoice = gameView.getPlayerActionChoice(remainingTurns);
			
			switch(actionChoice) {
			
			case MOVE:
				actionSuccessfullyTaken = move(p); // TODO: check for validity in Player class, throw Exception
				break;
			
			case SHORE_UP:
				actionSuccessfullyTaken = shoreUpAction(p);
				break;
				
			case GIVE_TREASURE_CARD:
				actionSuccessfullyTaken = giveTreasureCard(p);
				break;
				
			case CAPTURE_TREASURE:
				actionSuccessfullyTaken = captureTreasure(p);
				break;

			case NONE:
				return;
					
			}
			
			remainingTurns -= actionSuccessfullyTaken ? 1 : 0;
			
		} while (remainingTurns > 0);
		
	}
	
	/**
	 * Changes players current tile to adjacent tile of their choice
	 * @returns true if successful move made
	 */
	private boolean move(Player p) {

		List<IslandTile> adjTiles = gameModel.getIslandBoard().getAdjacentTiles(p.getPawn().getTile());
		
		if(! adjTiles.isEmpty()) {
			
			p.getPawn().setTile(gameView.pickTileDestination(adjTiles)); // TODO: check for errors with simple function in GameView
			return true;
			
		} else {
			gameView.showNoMoveTiles();
			return false;
		}
	}
	
	/**
	 * method to shore up tile of users choice
	 * @return true if shore-up action successful, false otherwise
	 */
	private boolean shoreUpAction(Player p) {

		List<IslandTile> adjTiles = gameModel.getIslandBoard().getAdjacentTiles(p.getPawn().getTile());
		adjTiles.add(p.getPawn().getTile()); //can shore-up current tile
		List<IslandTile> holder = new ArrayList<>(adjTiles);
		IslandTile tileChoice;
		
		//remove tiles that aren't flooded
		for(IslandTile t : holder) {
			if(!t.isFlooded()) {
				adjTiles.remove(t);
			}
		}
		
		if(! adjTiles.isEmpty()) {
			
			tileChoice = gameView.pickShoreUpTile(adjTiles);
			tileChoice.setToSafe();
			adjTiles.remove(tileChoice);
		
			if (! adjTiles.isEmpty() && p.getShoreUpQuantity() > 1) {

				tileChoice = gameView.pickShoreUpTile(adjTiles);
				tileChoice.setToSafe();
				
			}
			return true;
		}
		gameView.showNoShoreUpTiles();
		return false;
		//Is it better to show user what tiles they can perform action on
		//OR let them choose a tile based on map and then we tell them if its a valid choice?
	}
	
	
	/**
	 * method to give a treasure card from hand to another player on the same tile
	 * @return whether or not treasure successfully given
	 */
	private boolean giveTreasureCard(Player p) {
		
		DrawCardsController drawCardsController = gameController.getDrawCardsController();
		List<Player> playersOnSameTile = new ArrayList<Player>();
		List<Card> treasureCards = new ArrayList<Card>();
		Player playerToRecieve;
		Card card;
		
		//find players on same tile
		playersOnSameTile = p.getGiveCardsPlayers(gameModel.getGamePlayers());
		
		if(playersOnSameTile.isEmpty()) {
			gameView.showNoPlayersOnSameTile();
			return false;
		}
		
		//Find treasure cards in hand
		treasureCards = p.getTreasureCards();
		System.out.println(treasureCards);
		
		if(treasureCards.isEmpty()) {
//			gameView.updateView(gameModel);
			gameView.showNoTreasureCards();
			return false;
		}
		
		//User chooses player to give card to
		playerToRecieve = gameView.pickPlayerToRecieveCard(playersOnSameTile);
		
		//User chooses card to give
		card = gameView.pickCardToGive(treasureCards);
		
		//give card
		drawCardsController.addCardToHand(playerToRecieve, card);
		p.getCards().remove(card);
		return true;
		
	}
	
	
	/**
	 * method to capture a treasure from current tile
	 * @return whether or not treasure successfully captured
	 */
	private boolean captureTreasure(Player p) { //TODO: Overcomplicated - improve?
		
		final int numCardsRequired = 4;
		Treasure treasure = p.getPawn().getTile().getAssociatedTreasure();
		
		//If true - Collect all cards which can be used to capture treasure
		if(treasure != null) {
			
			if( gameModel.getGamePlayers().getCapturedTreasures().contains(treasure) ) {
				//gameView.showAlreadyCaptured(treasure)
				return false;
			}
			
			List<Card> tradeCards = new ArrayList<Card>();
			int cardsFound = 0;
			
			//Take out all relevant treasure cards
			for(Card c : p.getTreasureCards()) {
				//TODO: Are subclasses making these treasure deck cards hard to deal with??
				if(((TreasureCard) c).getAssociatedTreasure().equals( p.getPawn().getTile().getAssociatedTreasure())) {
					tradeCards.add(c);
					p.getCards().remove(c);
					cardsFound++;
				}
				
				if(cardsFound >= numCardsRequired) {
					//Capture the treasure!!!
					//Discard 4 treasure cards
					for(int i = 0; i < 4; i++) {
						gameModel.getTreasureDiscardPile().addCard(tradeCards.get(i));
						tradeCards.remove(i);
					}
					//capture
					gameModel.getGamePlayers().addTreasure(p.getPawn().getTile().captureAssociatedTreasure());
					//TODO: alert observer that treasure has been captured/print via gameView "You captured..."
					return true;
				}
			}
			
			//if couldn't capture treasure
			gameView.showNotEnoughCards(p.getPawn().getTile().getAssociatedTreasure());
			//Return cards to player deck
			for(int i = 0; i < tradeCards.size(); i++) {
				gameModel.getTreasureDeck().addCardToDeck(tradeCards.get(i));
			}
			return false;
			
		}
		
		gameView.showNoTreasure(p.getPawn().getTile());
		return false;
	}

}

