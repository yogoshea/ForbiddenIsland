package island.game;

import java.util.Arrays;
import java.util.List;

import island.cards.Card;
import island.cards.HelicopterLiftCard;
import island.cards.SandbagCard;
import island.components.GameEndings;
import island.components.IslandTile;
import island.components.Treasure;
import island.players.Player;

/**
 * Controller class for implementing helicopter and sandbag card logic 
 * game model accordingly
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class PlaySpecialCardController { //TODO: better name!!
	
	private static PlaySpecialCardController playSpecialCardController;
	
	private GameView gameView;
	private GameModel gameModel;
	private GameController gameController;
	
	/**
	 * Constructor to retrieve view and model instances
	 */
	private PlaySpecialCardController(GameModel gameModel, GameView gameView, GameController gameController) {
		this.gameModel = gameModel;
		this.gameView = gameView;
		this.gameController = gameController;
	}
	
	/**
	 * @return single instance of action controller
	 */
	public static PlaySpecialCardController getInstance(GameModel gameModel, GameView gameView, GameController gameController) {
		if (playSpecialCardController == null) {
			playSpecialCardController = new PlaySpecialCardController(gameModel, gameView, gameController);
		}
		return playSpecialCardController;
	}
	
	//TODO: Some similarities between 
	/**
	 * Method to play a helicopter lift card
	 */
	public void heliRequest() {
		
		List<Player> gamePlayers = gameModel.getGamePlayers().getPlayersList();
		List<IslandTile> nonSunkTiles = gameModel.getIslandBoard().getNonSunkTiles();
		List<Player> heliPlayers;
		IslandTile destination;
		
		Player player = gameView.pickHeliPlayer(gamePlayers);
		
		for(Card card : player.getCards()) {
			
			//If helicopter card found then attempt to play it
			if(card instanceof HelicopterLiftCard) {
				
				checkForGameWin();
				
				//Prompt for user to choose destination and players who wish to move
				destination = gameView.pickHeliDestination(nonSunkTiles);
				heliPlayers = gameView.pickHeliPlayers(gamePlayers, destination);
				
				//If there are players who wish to move then move them
				if(heliPlayers.size() > 0) {
					for(Player p: heliPlayers) {
						p.getPawn().setTile(destination);
					}
					player.getCards().remove(card);
				}
				
				return;

			}
		}
		//If you reach here then there was no card in hand
		gameView.showNoHeliCard(player);
	}
	
	/**
	 * Method to play a sandbag card
	 */
	public void sandbagRequest() {
		
		IslandTile tileChoice;
		List<Player> gamePlayers = gameModel.getGamePlayers().getPlayersList();
		
		Player player = gameView.pickSandbagPlayer(gamePlayers);
		
		for(Card card : player.getCards()) {
			
			if(card instanceof SandbagCard) {
				
				List<IslandTile> floodedTiles = gameModel.getIslandBoard().getFloodedTiles();
				
				if(floodedTiles.size() > 0) {
					
					tileChoice = gameView.pickShoreUpTile(floodedTiles);
					tileChoice.setToSafe();
					player.getCards().remove(card);
					
				}
				return;
			}
		}
		//If you reach here then there was no card in hand
		gameView.showNoSandbagCard(player);
	}
	
	
	/**
	 * Method to check if game has been won when heli card played
	 */
	public void checkForGameWin() {
		//TODO: give option to not win game??
		
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
		
		gameController.endGame(GameEndings.WIN); //TODO: ENUM
		
	}
	
}
