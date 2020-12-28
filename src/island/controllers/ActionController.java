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
 * Controller class for retrieving player action choices from GameView and changing 
 * the game model accordingly.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class ActionController {
	
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
			gameView.updateView(gameModel, p); // display updated game view after every action
			
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
		
		// If there are available tiles to move to
		if(! adjTiles.isEmpty()) {
			
			destination = gameView.getPrompter().pickTileDestination(adjTiles);
			p.getPawn().setTile(destination);
			gameView.getNotifier().showSuccessfulMove(p, destination);
			return true;
			
		} else {
			gameView.getNotifier().showNoMoveTiles();
			return false; // Move has not been made as no tiles available
		}
	}
	
	/**
	 * Performs shore up action on island tile of user's choice
	 * @return true if a shore-up action is made, false otherwise
	 */
	private boolean shoreUpAction(Player p) {

		List<IslandTile> adjTiles = gameModel.getIslandBoard().getAdjacentTiles(p.getPawn().getTile()); // List of potential tiles to shore up
		adjTiles.add(p.getPawn().getTile()); // Current tile is added to shore-up list
		List<IslandTile> holder = new ArrayList<>(adjTiles);
		IslandTile tileChoice;
		
		// Remove tiles that aren't flooded
		for(IslandTile tile : holder) {
			if(! tile.isFlooded()) {
				adjTiles.remove(tile);
			}
		}
		
		// If there are flooded tiles available, prompt user for a shore-up choice
		if(! adjTiles.isEmpty()) {
			
			tileChoice = gameView.getPrompter().pickShoreUpTile(adjTiles);
			tileChoice.setToSafe(); // shore-up tile choice
			adjTiles.remove(tileChoice);
			gameView.getNotifier().showSuccessfulShoreUp(tileChoice);
		
			// Check if current player's role allows second shore up
			if ((! adjTiles.isEmpty()) && p.getShoreUpQuantity() == 2 ) {
				
				// If engineer wishes to shore-up another tile
				if(gameView.getPrompter().shoreUpAnother()) {
					
					tileChoice = gameView.getPrompter().pickShoreUpTile(adjTiles);
					tileChoice.setToSafe(); // shore-up tile choice
					gameView.getNotifier().showSuccessfulShoreUp(tileChoice);
				}
				
			}
			return true;
		}
		// If no shore-up action can be made, return false
		gameView.getNotifier().showNoShoreUpTiles();
		return false;
	}
	
	
	/**
	 * Method to gives a treasure card from hand to another player during a turn
	 * @return Boolean indicating whether or not treasure card successfully given
	 */
	private boolean giveTreasureCard(Player p, DrawCardsController drawCardsController) {
		
		List<Player> receivablePlayers = new ArrayList<Player>();
		List<Card<?>> treasureCards = new ArrayList<Card<?>>();
		Player playerToRecieve;
		Card<?> card;
		
		// Find players that card can be given to
		receivablePlayers = p.getCardReceivablePlayers(gameModel.getGamePlayers());
		
		if(receivablePlayers.isEmpty()) {
			gameView.getNotifier().showNoAvailablePlayers();
			return false;
		}
		
		// Get players treasure cards
		treasureCards = p.getTreasureCards();
		
		if(treasureCards.isEmpty()) {
			gameView.getNotifier().showNoTreasureCards();
			return false;
		}
		
		// User chooses player to give card to
		playerToRecieve = gameView.getPrompter().pickPlayerToRecieveCard(receivablePlayers);
		
		// User chooses card to give
		card = gameView.getPrompter().pickCardToGive(treasureCards);
		
		// Give card to chosen player
		drawCardsController.addCardToHand(playerToRecieve, card);
		
		//remove card from current players hand
		p.removeCard(card);
		gameView.getNotifier().showCardGiven(card, p, playerToRecieve);
		return true;
	}
	
	
	/**
	 * Method to perform a treasure capture action.
	 * @return Boolean indicating whether or not treasure successfully captured.
	 */
	private boolean captureTreasure(Player p) {
		
		Treasure treasure = p.getPawn().getTile().getAssociatedTreasure();
		
		// If treasure exists -> Attempt to capture it
		if(treasure != null) {
			
			// If treasure already captured, return null
			if( gameModel.getGamePlayers().getCapturedTreasures().contains(treasure) ) {
				gameView.getNotifier().showAlreadyCaptured(treasure);
				return false;
			}
			
			List<Card<?>> tradeCards = new ArrayList<Card<?>>();
			
			// Take out all relevant treasure cards from player's hand
			for(Card<?> c : p.getTreasureCards()) {
				
				if(c.getUtility().equals(treasure)) {
					tradeCards.add(c); // Add relevant card to 'tradeCards' and remove from hand
					p.removeCard(c);
				}
				
				//If enough cards are found -> capture the treasure
				if(tradeCards.size() == cardsRequiredForCapture) {
					
					// Discard the 4 treasure cards and capture the treasure
					gameModel.getTreasureDiscardPile().getAllCards().addAll(tradeCards);
					gameModel.getGamePlayers().addTreasure(treasure);
					gameView.getNotifier().showTreasureCaptured(treasure);
					return true;
				}
			}
			
			// If not enough cards to capture treasure, return cards to players hand
			gameView.getNotifier().showNotEnoughCards(p.getPawn().getTile().getAssociatedTreasure());
			p.getCards().addAll(tradeCards);
			return false;
			
		}
		// If no treasure found on island tile, return false
		gameView.getNotifier().showNoTreasure(p.getPawn().getTile());
		return false;
	}

	
	
	// Singleton reset for JUnit testing
	public static void reset() {
		actionController = null;
	}

}

