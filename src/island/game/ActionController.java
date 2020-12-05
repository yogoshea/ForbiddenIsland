package island.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import island.cards.TreasureCard;
import island.cards.TreasureDeckCard;
import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.Treasure;
import island.decks.TreasureDiscardPile;
import island.players.GamePlayers;
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
	
	public enum Action {
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
	private ActionController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
	}
	
	/**
	 * @return single instance of action controller
	 */
	public static ActionController getInstance(GameModel gameModel, GameView gameView) {
		if (actionController == null) {
			actionController = new ActionController(gameModel, gameView);
		}
		return actionController;
	}
	
	public void takeActions(Player p) {
		
//		gameView.showPlayerTurn(p);
		Action actionChoice;
		boolean actionSuccessfullyTaken = false;
		int remainingTurns = 3;
		//System.out.println(p.toStringDetailed());
		
		do {
			
			gameView.updateView(gameModel); // display updated full game view after every action
			gameView.showPlayerTurn(p);
			actionChoice = gameView.getPlayerActionChoice(remainingTurns);
			
			if(actionChoice.equals(Action.NONE)) {
				
				break;
				
			} else {
				
				switch(actionChoice) {
				
				case MOVE:
					actionSuccessfullyTaken = move(p); // TODO: check for validity in Player class, throw Exception
					break;
				
				case SHORE_UP:
					actionSuccessfullyTaken = shoreUp(p);
					break;
					
				case GIVE_TREASURE_CARD:
					actionSuccessfullyTaken = giveTreasureCard(p);
					break;
					
				case CAPTURE_TREASURE:
					actionSuccessfullyTaken = captureTreasure(p);
					break;
					
				case NONE:
					
				}

				remainingTurns -= actionSuccessfullyTaken ? 1 : 0;
			}
			
		} while (remainingTurns > 0);
		
	}
	
	/**
	 * Changes players current tile to adjacent tile of their choice
	 * @returns true if successful move made
	 */
	private boolean move(Player p) {
		//TODO: print island board? - or give option to do that at any time??
		//Could just move all of this into Gameview?

		List<IslandTile> adjTiles = gameModel.getIslandBoard().findAdjacentTiles(p.getCurrentTile());
		
		if(adjTiles.size() > 0) {
			
			p.setCurrentTile( gameView.pickTileDestination(adjTiles) ); //TODO: check correct user input
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
	private boolean shoreUp(Player p) {

		List<IslandTile> adjTiles = gameModel.getIslandBoard().findAdjacentTiles(p.getCurrentTile());
		adjTiles.add(p.getCurrentTile()); //can shore-up current tile
		List<IslandTile> holder = new ArrayList<>(adjTiles);
		
		for(IslandTile t : holder) {
			//remove tiles that aren't flooded
			if(!t.isFlooded()) {
				adjTiles.remove(t);
			}
		}
		
		if(adjTiles.size() > 0) { //Check if list empty in view??
			IslandTile tileChoice = gameView.pickShoreUpTile(adjTiles);
			gameModel.getIslandBoard().shoreUp(tileChoice);
			return true;
			
		} else {
			gameView.showNoShoreUpTiles();
			return false;
		}
		//Is it better to show user what tiles they can perform action on
		//OR let them choose a tile based on map and then we tell them if its a valid choice?
	}
	
	
	/**
	 * method to give a treasure card from hand to another player on the same tile
	 * @return whether or not treasure successfully given
	 */
	private boolean giveTreasureCard(Player p) {
		
		List<Player> playersOnSameTile = new ArrayList<Player>();
		List<TreasureDeckCard> treasureCards = new ArrayList<TreasureDeckCard>();
		Player playerToRecieve;
		TreasureDeckCard card;
		
		//find players on same tile
		playersOnSameTile = p.getPlayersOnSameTile();
		
		if(playersOnSameTile.size() <= 0) {
			gameView.showNoPlayersOnSameTile();
			return false;
		}
		
		//Find treasure cards in hand
		treasureCards = p.findTreasureCards();
		
		if(treasureCards.size() <= 0) {
			gameView.showNoTreasureCards();
			return false;
		}
		
		//User chooses player to give card to
		playerToRecieve = gameView.pickPlayerToRecieveCard(playersOnSameTile);
		
		//User chooses card to give
		card = gameView.pickCardToGive(treasureCards);
		
		//give card
		playerToRecieve.receiveTreasureDeckCard(card);
		p.getTreasureDeckCards().remove(card);
		return true;
		
	}
	
	
	/**
	 * method to capture a treasure from current tile
	 * @return whether or not treasure successfully captured
	 */
	private boolean captureTreasure(Player p) { //Overcomplicated - improve?
		
		final int numCardsRequired = 4;
		
		//If true - Collect all cards which can be used to capture treasure
		if(p.getCurrentTile().getAssociatedTreasure() != null) {
			
			List<TreasureDeckCard> tradeCards = new ArrayList<TreasureDeckCard>();
			int cardsFound = 0;
			
			//Take out all relevant treasure cards
			for(TreasureDeckCard c : p.findTreasureCards()) {
				//TODO: Are subclasses making these treasure deck cards hard to deal with??
				if(((TreasureCard) c).getAssociatedTreasure().equals( p.getCurrentTile().getAssociatedTreasure())) {
					tradeCards.add(c);
					p.getTreasureDeckCards().remove(c);
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
					gameModel.getGamePlayers().addTreasure(p.getCurrentTile().captureAssociatedTreasure());
					//TODO: alert observer that treasure has been captured/print via gameView "You captured..."
					return true;
				}
			}
			
			//if couldn't capture treasure
			gameView.showNotEnoughCards(p.getCurrentTile().getAssociatedTreasure());
			//Return cards to player deck
			for(int i = 0; i < tradeCards.size(); i++) {
				gameModel.getTreasureDeck().addCardToDeck(tradeCards.get(i));
			}
			return false;
			
		}
		
		gameView.showNoTreasure(p.getCurrentTile());
		return false;
	}

}

