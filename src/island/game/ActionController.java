package island.game;

import java.util.ArrayList;
import java.util.List;

import island.cards.Card;
import island.components.IslandTile;
import island.components.Treasure;
import island.players.Player;

/**
 * Controller class for retrieving player choices for GameView and change 
 * game model accordingly.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class ActionController { //Name PlayerActionController for clarity?
	
	// Singleton to be instantiated
	private static ActionController actionController;
	
	private GameView gameView;
	private GameModel gameModel;
	
	/**
	 * Constructor for ActionController singleton, receives view and model instances.
	 * @param Reference to GameModel.
	 * @param Reference to GameView.
	 */
	private ActionController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
	}
	
	/**
	 * Getter method for singleton instance.
	 * @param Reference to GameModel.
	 * @param Reference to GameView.
	 * @return single instance of ActionController.
	 */
	public static ActionController getInstance(GameModel gameModel, GameView gameView) {
		if (actionController == null) {
			actionController = new ActionController(gameModel, gameView);
		}
		return actionController;
	}
	
	/**
	 * Retrieves player action choices from GameView and calls relevant methods to
	 * perform requested action.
	 * @param Player instances choosing the action.
	 */
	public void takeActions(Player p, DrawCardsController drawCardsController) {
		
//		gameView.showPlayerTurn(p); // TODO: make sure to print this
		Action actionChoice;
		boolean actionSuccessfullyTaken = false;
		int remainingTurns = 3;
		
		do {
			
			gameView.showEnterToContinue();
			gameView.updateView(gameModel, p); // display updated full game view after every action
			//gameView.showPlayerTurn(p);
			actionChoice = gameView.getPlayerActionChoice(remainingTurns);
			
			switch(actionChoice) {
			
			case MOVE:
				actionSuccessfullyTaken = move(p); // TODO: check for validity in GameView, throw Exception
				break;
			
			case SHORE_UP:
				actionSuccessfullyTaken = shoreUpAction(p);
				break;
				
			case GIVE_TREASURE_CARD:
				actionSuccessfullyTaken = giveTreasureCard(p, drawCardsController);
				break;
				
			case CAPTURE_TREASURE:
				actionSuccessfullyTaken = captureTreasure(p);
				break;

			case NONE:
				gameView.showSkippingActions();
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
		IslandTile destination;
		
		if(! adjTiles.isEmpty()) {
			
			destination = gameView.pickTileDestination(adjTiles);
			p.getPawn().setTile(destination); // TODO: check for errors with simple function in GameView
			gameView.showSuccessfulMove(p, destination);
			return true;
			
		} else {
			gameView.showNoMoveTiles();
			return false;
		}
	}
	
	/**
	 * Performs shore up action on island tile of user's choice
	 * @return true if shore-up action successful, false otherwise
	 */
	private boolean shoreUpAction(Player p) {

		List<IslandTile> adjTiles = gameModel.getIslandBoard().getAdjacentTiles(p.getPawn().getTile());
		adjTiles.add(p.getPawn().getTile()); //can shore-up current tile
		List<IslandTile> holder = new ArrayList<>(adjTiles);
		IslandTile tileChoice;
		
		// Remove tiles that aren't flooded
		for(IslandTile tile : holder) {
			if(! tile.isFlooded()) {
				adjTiles.remove(tile);
			}
		}
		
		// If there are adjacent flooded tiles, prompt user for shore up choice
		if(! adjTiles.isEmpty()) {
			
			tileChoice = gameView.pickShoreUpTile(adjTiles);
			tileChoice.setToSafe();
			adjTiles.remove(tileChoice);
		
			// Check if current players role allows second shore up
			// TODO: prompt gameView; does engineer player want to shore up another!
			if (! adjTiles.isEmpty() && p.getShoreUpQuantity() == 2) {

				tileChoice = gameView.pickShoreUpTile(adjTiles);
				tileChoice.setToSafe();
				gameView.showSuccessfulShoreUp(tileChoice); // TODO: print this outside loop??
				
			}
			return true;
		}
		gameView.showNoShoreUpTiles();
		return false;
	}
	
	
	/**
	 * Gives a treasure card from hand to another player on the same island tile
	 * @return whether or not treasure successfully given
	 */
	private boolean giveTreasureCard(Player p, DrawCardsController drawCardsController) {
		
		List<Player> playersOnSameTile = new ArrayList<Player>();
		List<Card<?>> treasureCards = new ArrayList<Card<?>>();
		Player playerToRecieve;
		Card<?> card;
		
		// Find players on same tile
		playersOnSameTile = p.getCardReceivablePlayers(gameModel.getGamePlayers());
		
		if(playersOnSameTile.isEmpty()) {
			gameView.showNoPlayersOnSameTile();
			return false;
		}
		
		// Find treasure cards in hand
		treasureCards = p.getTreasureCards();
		System.out.println(treasureCards);
		
		if(treasureCards.isEmpty()) {
//			gameView.updateView(gameModel);
			gameView.showNoTreasureCards();
			return false;
		}
		
		// User chooses player to give card to
		playerToRecieve = gameView.pickPlayerToRecieveCard(playersOnSameTile);
		
		// User chooses card to give
		card = gameView.pickCardToGive(treasureCards);
		
		// Give card to other player
		drawCardsController.addCardToHand(playerToRecieve, card);

		p.removeCard(card);
		//TODO: gameView.showSuccessfullyGiven(card, player, playerToRecieve)

		return true;
		
	}
	
	
	/**
	 * Performs action of capturing a treasure from current tile.
	 * @return Boolean indicating whether or not treasure successfully captured.
	 */
	private boolean captureTreasure(Player p) {
		
		final int numCardsRequired = 4;
		Treasure treasure = p.getPawn().getTile().getAssociatedTreasure();
		
		// If true - Collect all cards which can be used to capture treasure
		if(treasure != null) {
			
			if( gameModel.getGamePlayers().getCapturedTreasures().contains(treasure) ) {
				//gameView.showAlreadyCaptured(treasure)
				return false;
			}
			
			List<Card<?>> tradeCards = new ArrayList<Card<?>>();
			int cardsFound = 0;
			
			// Take out all relevant treasure cards from player's hand
			for(Card<?> c : p.getTreasureCards()) {
				if(c.getUtility().equals(treasure)) {
					tradeCards.add(c);
					p.getCards().remove(c);
					cardsFound++;
				}
				
				if(cardsFound == numCardsRequired) {
					
					// Discard the 4 treasure cards and capture treasure
					gameModel.getTreasureDiscardPile().getAllCards().addAll(tradeCards);
					gameModel.getGamePlayers().addTreasure(treasure);
					gameView.showTreasureCaptured(treasure);
					return true;
				}
			}
			// If couldn't capture treasure, show message and return cards to deck
			gameView.showNotEnoughCards(p.getPawn().getTile().getAssociatedTreasure());
			p.getCards().addAll(tradeCards);
			return false;
			
		}
		// No treasure found on island tile
		gameView.showNoTreasure(p.getPawn().getTile());
		return false;
	}

	// Singleton reset for JUnit testing
	public static void reset() {
		actionController = null;
	}

}

