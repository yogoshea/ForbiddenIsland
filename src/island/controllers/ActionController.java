package island.controllers;

import java.util.ArrayList;
import java.util.List;

import island.cards.Card;
import island.components.GameModel;
import island.components.IslandTile;
import island.components.Treasure;
import island.players.Player;
import island.view.GameView;

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
	public static final int actionsPerTurn = 3;
	public static final int cardsRequiredForCapture = 4;
	
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
		
		Action actionChoice;
		boolean actionSuccessfullyTaken = false;
		int remainingTurns = actionsPerTurn;
		
		do {
			
			gameView.getPrompter().promptEnterToContinue();
			gameView.updateView(gameModel, p); // display updated full game view after every action
			
			actionChoice = gameView.getPrompter().pickAction(remainingTurns); //Get the players choice of action
			
			switch(actionChoice) {
			
			case MOVE:
				actionSuccessfullyTaken = move(p);
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
				gameView.getNotifier().showSkippingActions();
				return;
					
			}
			
			remainingTurns -= actionSuccessfullyTaken ? 1 : 0; //If action was successful then remaining turns decreases by 1
			
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
			
			destination = gameView.getPrompter().pickTileDestination(adjTiles);
			p.getPawn().setTile(destination);
			gameView.getNotifier().showSuccessfulMove(p, destination);
			return true;
			
		} else {
			gameView.getNotifier().showNoMoveTiles();
			return false;
		}
	}
	
	/**
	 * Performs shore up action on island tile of user's choice
	 * @return true if shore-up action successful, false otherwise
	 */
	private boolean shoreUpAction(Player p) {

		List<IslandTile> adjTiles = gameModel.getIslandBoard().getAdjacentTiles(p.getPawn().getTile()); //List of potential tiles to shore up
		adjTiles.add(p.getPawn().getTile()); //can also shore-up your current tile
		List<IslandTile> holder = new ArrayList<>(adjTiles);
		IslandTile tileChoice;
		
		// Remove tiles that aren't flooded
		for(IslandTile tile : holder) {
			if(! tile.isFlooded()) {
				adjTiles.remove(tile);
			}
		}
		
		// If there are flooded tiles available, prompt user for shore-up choice
		if(! adjTiles.isEmpty()) {
			
			tileChoice = gameView.getPrompter().pickShoreUpTile(adjTiles);
			tileChoice.setToSafe(); //shore-up tile
			adjTiles.remove(tileChoice);
			gameView.getNotifier().showSuccessfulShoreUp(tileChoice);
		
			// Check if current players role allows second shore up
			if (! adjTiles.isEmpty() && p.getShoreUpQuantity() == 2 ) {
				
				//If engineer wishes to shore-up another tile
				if(gameView.getPrompter().shoreUpAnother()) {
					
					tileChoice = gameView.getPrompter().pickShoreUpTile(adjTiles);
					tileChoice.setToSafe(); //shore-up tile
					gameView.getNotifier().showSuccessfulShoreUp(tileChoice);
				}
				
			}
			return true;
		}
		gameView.getNotifier().showNoShoreUpTiles();
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
			gameView.getNotifier().showNoAvailablePlayers();
			return false;
		}
		
		// Find treasure cards in hand
		treasureCards = p.getTreasureCards();
		
		if(treasureCards.isEmpty()) {
			gameView.getNotifier().showNoTreasureCards();
			return false;
		}
		
		// User chooses player to give card to
		playerToRecieve = gameView.getPrompter().pickPlayerToRecieveCard(playersOnSameTile);
		
		// User chooses card to give
		card = gameView.getPrompter().pickCardToGive(treasureCards);
		
		// Give card to other player
		drawCardsController.addCardToHand(playerToRecieve, card);
		
		//remove from players hand
		p.removeCard(card);
		gameView.getNotifier().showCardGiven(card, p, playerToRecieve);

		return true;
		
	}
	
	
	/**
	 * Performs action of capturing a treasure from current tile.
	 * @return Boolean indicating whether or not treasure successfully captured.
	 */
	private boolean captureTreasure(Player p) {
		
		Treasure treasure = p.getPawn().getTile().getAssociatedTreasure();
		
		// If treasure exists - Collect all cards which can be used to capture treasure
		if(treasure != null) {
			
			if( gameModel.getGamePlayers().getCapturedTreasures().contains(treasure) ) {
				gameView.getNotifier().showAlreadyCaptured(treasure);
				return false;
			}
			
			List<Card<?>> tradeCards = new ArrayList<Card<?>>();
			int cardsFound = 0;
			
			// Take out all relevant treasure cards from player's hand
			for(Card<?> c : p.getTreasureCards()) {
				if(c.getUtility().equals(treasure)) {
					tradeCards.add(c);
					p.removeCard(c);
					cardsFound++;
				}
				
				//If player has enough cards -> capture treasure
				if(cardsFound == cardsRequiredForCapture) {
					
					// Discard the 4 treasure cards and capture treasure
					gameModel.getTreasureDiscardPile().getAllCards().addAll(tradeCards);
					gameModel.getGamePlayers().addTreasure(treasure);
					gameView.getNotifier().showTreasureCaptured(treasure);
					return true;
				}
			}
			
			// If could not capture treasure, show message and return cards to deck
			gameView.getNotifier().showNotEnoughCards(p.getPawn().getTile().getAssociatedTreasure());
			p.getCards().addAll(tradeCards);
			return false;
			
		}
		// If no treasure found on island tile
		gameView.getNotifier().showNoTreasure(p.getPawn().getTile());
		return false;
	}

	// Singleton reset for JUnit testing
	public static void reset() {
		actionController = null;
	}

}

