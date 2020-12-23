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
	
	//TODO: Some similarities between heliRequest and Sandbag request -> can definitely streamline/reduce duplicated code
	
	/**
	 * Performs actions enabled by Helicopter Lift Card usage
	 */
	public void heliRequest() {
		
		List<Player> gamePlayers = gameModel.getGamePlayers().getPlayersList();
		List<IslandTile> nonSunkTiles = gameModel.getIslandBoard().getNonSunkTiles();
		List<Player> heliPlayers;
		IslandTile destination;
		
		Player player = gameView.pickHeliPlayer(gamePlayers);
		
		for(Card<?> card : player.getCards()) {
			
			// If helicopter card found then attempt to play it
			if(card.getUtility().equals(SpecialCardAbility.HELICOPTER_LIFT)) {
				
				checkForGameWin();
				
				// Prompt for user to choose destination and players who wish to move
				destination = gameView.pickHeliDestination(nonSunkTiles);
				heliPlayers = gameView.pickHeliPlayers(gamePlayers, destination);
				
				// If there are players who wish to move then move them
				if(heliPlayers.size() > 0) {
					for(Player p: heliPlayers) {
						p.getPawn().setTile(destination);
						gameView.showSuccessfulMove(p, destination);
					}
					player.getCards().remove(card);
				}
				returnToBefore();
				return;

			}
		}
		// If you reach here then there was no card in hand
		gameView.showNoHeliCard(player);
		returnToBefore();
	}
	
	/**
	 * Performs actions enabled by Sandbag Card usage
	 */
	public void sandbagRequest() {
		
		IslandTile tileChoice;
		List<Player> gamePlayers = gameModel.getGamePlayers().getPlayersList();
		
		Player player = gameView.pickSandbagPlayer(gamePlayers);
		
		for(Card<?> card : player.getCards()) {
			
			if(card.getUtility().equals(SpecialCardAbility.SANDBAG)) {
				
				List<IslandTile> floodedTiles = gameModel.getIslandBoard().getFloodedTiles();
				
				if(floodedTiles.size() > 0) {
					
					tileChoice = gameView.pickShoreUpTile(floodedTiles);
					tileChoice.setToSafe();
					player.getCards().remove(card);
					
				}
				returnToBefore();
				return;
			}
		}
		// If you reach here then there was no card in hand
		gameView.showNoSandbagCard(player);
		returnToBefore();
	}
	
	
	/**
	 * Determines whether players have won the game buy using current Helicopter Lift Card
	 */
	private void checkForGameWin() {
		
		for(Player p : gameModel.getGamePlayers().getPlayersList()) {
			if(!p.getPawn().getTile().equals(IslandTile.FOOLS_LANDING)) {
				return;
			}
		}
		
		for(Treasure t : Arrays.asList(Treasure.values())) {
			if(!gameModel.getGamePlayers().getCapturedTreasures().contains(t)) {
				return;
			}
		}
		gameController.endGame(GameEndings.WIN);
	}
	
	/**
	 * Returns game to previous flow after special cards have been used.
	 */
	private void returnToBefore() {
		gameView.showSpecialCardDone();
		gameView.updateView(gameModel, gameController.getCurrentPlayer());
	}
	
	// Singleton reset for JUnit testing
	public static void reset() {
		specialCardController = null;
	}
	
}
