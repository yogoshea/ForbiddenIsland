package island.game;

import java.util.Arrays;
import java.util.List;

import island.cards.Card;
import island.cards.SpecialCardAbility;
import island.components.IslandTile;
import island.components.Treasure;
import island.players.Player;

/**
 * Controller class for implementing helicopter and sandbag card logic 
 * game model accordingly
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class SpecialCardController {
	
	private static SpecialCardController specialCardController;
	
	private GameView gameView;
	private GameModel gameModel;
	private GameController gameController;
	
	/**
	 * Constructor for SpecialCardController singleton.
	 * @param Reference to GameModel.
	 * @param Reference to GameView.
	 */
	private SpecialCardController(GameModel gameModel, GameView gameView, GameController gameController) {
		this.gameModel = gameModel;
		this.gameView = gameView;
		this.gameController = gameController;
	}
	
	/**
	 * Getter method for singleton instance.
	 * @param Reference to GameModel.
	 * @param Reference to GameView.
	 * @return single instance of SpecialCardController.
	 */
	public static SpecialCardController getInstance(GameModel gameModel, GameView gameView, GameController gameController) {
		if (specialCardController == null) {
			specialCardController = new SpecialCardController(gameModel, gameView, gameController);
		}
		return specialCardController;
	}
	
	
//	/**
//	 * Performs actions enabled by Helicopter Lift Card usage
//	 */
//	public void heliRequest() {
//		
//		List<Player> gamePlayers = gameModel.getGamePlayers().getPlayersList();
//		List<IslandTile> nonSunkTiles = gameModel.getIslandBoard().getNonSunkTiles();
//		List<Player> heliPlayers;
//		IslandTile destination;
//		
//		//Determine which player made the request
//		Player player = gameView.pickHeliPlayer(gamePlayers);
//		
//		for(Card<?> card : player.getCards()) {
//			
//			// If request player has a Helicopter Lift card
//			if(card.getUtility().equals(SpecialCardAbility.HELICOPTER_LIFT)) {
//				
//				checkForGameWin();
//				
//				// Prompt user to pick destination and players who wish to move
//				destination = gameView.pickHeliDestination(nonSunkTiles);
//				heliPlayers = gameView.pickHeliPlayers(gamePlayers, destination);
//				
//				// If there are players who wish to move -> move them
//				if(heliPlayers.size() > 0) {
//					for(Player p: heliPlayers) {
//						p.getPawn().setTile(destination);
//						gameView.showSuccessfulMove(p, destination);
//					}
//					player.getCards().remove(card); //As card has been used, remove from players hand and add to discard pile
//					gameModel.getTreasureDiscardPile().addCard(card);
//				}
//				//return to before request was made
//				showReturnToBefore();
//				return;
//
//			}
//		}
//		// If you reach here then there was no heli card in your hand
//		gameView.showNoHeliCard(player);
//		showReturnToBefore();
//	}
//	
//	/**
//	 * Performs actions enabled by Sandbag Card usage
//	 */
//	public void sandbagRequest() {
//		
//		IslandTile tileChoice;
//		List<Player> gamePlayers = gameModel.getGamePlayers().getPlayersList();
//		
//		Player player = gameView.pickSandbagPlayer(gamePlayers);
//		
//		for(Card<?> card : player.getCards()) {
//			
//			if(card.getUtility().equals(SpecialCardAbility.SANDBAG)) {
//				
//				List<IslandTile> floodedTiles = gameModel.getIslandBoard().getFloodedTiles();
//				
//				if(floodedTiles.size() > 0) {
//					
//					tileChoice = gameView.pickShoreUpTile(floodedTiles);
//					tileChoice.setToSafe();
//					player.getCards().remove(card);
//					gameModel.getTreasureDiscardPile().addCard(card);
//					
//				}
//				showReturnToBefore();
//				return;
//			}
//		}
//		// If you reach here then there was no card in hand
//		gameView.showNoSandbagCard(player);
//		showReturnToBefore();
//	}
	
	/**
	 * Invokes allows the user to play a helicopter lift or sandbag card depending on request made and whether they have the appropriate card
	 * @param The input request string made by user
	 */
	public void specialCardRequest(String input) {
		
		List<Player> gamePlayers = gameModel.getGamePlayers().getPlayersList();
		Player player;
		SpecialCardAbility ability;
		boolean successfullyPlayed;
		
		//Determine which request was made
		if(input.equals(GameView.HELI)) {
			ability = SpecialCardAbility.HELICOPTER_LIFT;
		} else {
			ability = SpecialCardAbility.SANDBAG;
		}
		
		//Determine which player made the request
		player = gameView.pickRequestPlayer(gamePlayers, ability);
		
		for(Card<?> card : player.getCards()) {
			
			//If heli or sandbag card found in players hand
			if(card.getUtility().equals(ability)) {
				
				//Attempt to play card
				if(ability.equals(SpecialCardAbility.HELICOPTER_LIFT)) {
					successfullyPlayed = playHeliCard(gamePlayers);
				} else {
					successfullyPlayed = playSandCard(gamePlayers);
				}
				
				//If card used, remove from hand and put in discard pile
				if(successfullyPlayed) {
					player.removeCard(card);
					gameModel.getTreasureDiscardPile().addCard(card);
				}
				//return to before request was made
				showReturnToBefore();
				return;
			}	
		}
		
		//If no card found, return to before request was made
		gameView.showNoSpecialCard(player, ability);
		showReturnToBefore();
	}
	
	/**
	 * Implements Helicopter Lift card logic if called
	 * @param Boolean of whether or not the card was successfully played
	 */
	private boolean playHeliCard(List<Player> gamePlayers) {
		
		List<IslandTile> nonSunkTiles = gameModel.getIslandBoard().getNonSunkTiles();
		List<Player> heliPlayers;
		IslandTile destination;
		
		// Check if game is won due to the card play
		checkForGameWin();
		
		// Prompt user to pick destination and players who wish to move
		destination = gameView.pickHeliDestination(nonSunkTiles);
		heliPlayers = gameView.pickHeliPlayers(gamePlayers, destination);
		
		// If there are players who wish to move -> move them
		if(heliPlayers.size() > 0) {
			for(Player p: heliPlayers) {
				p.getPawn().setTile(destination);
				gameView.showSuccessfulMove(p, destination);
			}
			return true;
		} else {
			return false; //Nobody took the lift so card has not been played
		}		
	}
	
	/**
	 * Implements Sandbag card logic if called
	 * @param Boolean of whether or not the card was successfully played
	 */
	private boolean playSandCard(List<Player> gamePlayers) {
		
		IslandTile tileChoice;
		List<IslandTile> floodedTiles = gameModel.getIslandBoard().getFloodedTiles();
		
		// If there are tiles available to shore-up then prompt user to choose
		if(floodedTiles.size() > 0) {
			tileChoice = gameView.pickShoreUpTile(floodedTiles);
			tileChoice.setToSafe();
			gameView.showSuccessfulShoreUp(tileChoice);
			return true;
		} else {
			gameView.showNoShoreUpTiles();
			return false; //If no tiles to shore-up then card has not been used
		}		
	}
	
	
	/**
	 * Determines whether players have won the game. Only called when Helicopter Lift Card played
	 */
	private void checkForGameWin() {
		
		// If all players are on Fools Landing
		for(Player p : gameModel.getGamePlayers().getPlayersList()) {
			if(!p.getPawn().getTile().equals(IslandTile.FOOLS_LANDING)) {
				return; //If not on Foolslanding return as game is not won
			}
		}
		
		// And all treasures have been captured
		for(Treasure t : Arrays.asList(Treasure.values())) {
			if(!gameModel.getGamePlayers().getCapturedTreasures().contains(t)) {
				return; //If treasure not captured, return as game has not been won
			}
		}
		
		//Then the game has been won
		gameController.endGame(GameEndings.WIN);
	}
	
	/**
	 * Updates view to before special card was requested.
	 */
	private void showReturnToBefore() {
		gameView.showSpecialCardDone();
		gameView.updateView(gameModel, gameController.getCurrentPlayer());
	}
	
	// Singleton reset for JUnit testing
	public static void reset() {
		specialCardController = null;
	}
	
}
