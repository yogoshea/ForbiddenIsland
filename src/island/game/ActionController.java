package island.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import island.cards.TreasureCard;
import island.cards.TreasureDeckCard;
import island.components.IslandBoard;
import island.components.IslandTile;
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
	
	// Instantiate singleton
	private static ActionController actionController;
	
	private GameView gameView;
	private GameModel gameModel;
	
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
		//TODO: print which player turn it is and possibly move all into gameView
		String choice;
		boolean actionSuccessfullyTaken = false;
		int remainingTurns = 3;
		
		String[] actions = new String[]{"M", "S", "G", "C"}; //TODO: private static final String MOVE = "M" etc.???
		List<String> actionList = Arrays.asList(actions);
//		System.out.println(toStringDetailed());
		
		do {
			
			choice = gameView.getPlayerActionChoice(remainingTurns);
			
			if(actionList.contains(choice)) {
				
				actionSuccessfullyTaken = takeAction(p, choice);
				remainingTurns -= actionSuccessfullyTaken ? 1 : 0;
				
			} else {break;}
			
		} while (remainingTurns > 0);
		
	}
	
	/**
	 * method to choose the action to take during a turn
	 * @return whether action successfully taken
	 */
	public boolean takeAction(Player p, String choice) {
		
		boolean successfullyTaken = false;
		
		switch(choice) {
			case "M":
				successfullyTaken = move(p); // TODO: check for validity in Player class, throw Exception
				break;
			
			case "S":
				successfullyTaken = shoreUp(p);
				break;
				
			case "G":
				successfullyTaken = giveTreasureCard(p);
				break;
				
			case "C":
				successfullyTaken = captureTreasure(p);
				break;
		}
		
		return successfullyTaken;
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
			gameView.printToUser("No available tiles, Unlucky m8"); //If this is ok to do, can use promptUser() more
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
			gameView.printToUser("No available tiles to shore-up");//TODO: Is this ok??
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
		String prompt;
		
		//find players on same tile
		playersOnSameTile = p.getPlayersOnSameTile();
		
		if(playersOnSameTile.size() <= 0) {
			gameView.printToUser("No players on your tile :(");
			return false;
		}
		
		//Find treasure cards in hand
		treasureCards = p.findTreasureCards();
		
		if(treasureCards.size() <= 0) {
			gameView.printToUser("No treasure cards in hand :(");
			return false;
		}
		
		//User chooses player to give card to
		prompt = "Which player do you wish to give a card to?";
		playerToRecieve = gameView.pickFromList(playersOnSameTile, prompt); //OR create choose player method in gameView???
		
		//User chooses card to give
		prompt = "Which card do you wish to give";
		card = gameView.pickFromList(treasureCards, prompt);
		
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
		String prompt;
		
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
			
			//if couldn't capture treasure, return cards to player deck
			prompt = "Not enough "+p.getCurrentTile().getAssociatedTreasure().toString()+" cards to capture the treasure!!";
			gameView.printToUser(prompt); //???????????
			for(int i = 0; i < tradeCards.size(); i++) {
				gameModel.getTreasureDeck().addCardToDeck(tradeCards.get(i));
			}
			return false;
			
		}
		
		prompt = "No treasure found at " + p.getCurrentTile().toString();
		gameView.printToUser(prompt);
		return false;
	}

}

