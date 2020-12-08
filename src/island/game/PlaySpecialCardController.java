package island.game;

import java.util.Arrays;
import java.util.List;

import island.cards.Card;
import island.cards.HelicopterLiftCard;
import island.cards.SandbagCard;
import island.components.IslandTile;
import island.players.GamePlayers;
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
	
	/**
	 * Constructor to retrieve view and model instances
	 */
	private PlaySpecialCardController(GameModel gameModel, GameView gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;
	}
	
	/**
	 * @return single instance of action controller
	 */
	public static PlaySpecialCardController getInstance(GameModel gameModel, GameView gameView) {
		if (playSpecialCardController == null) {
			playSpecialCardController = new PlaySpecialCardController(gameModel, gameView);
		}
		return playSpecialCardController;
	}
	
	
	public void heliRequest() {
		
		List<Player> gamePlayers = gameModel.getGamePlayers().getPlayersList();
		List<IslandTile> nonSunkTiles = gameModel.getIslandBoard().getNonSunkTiles();
		List<Player> heliPlayers;
		IslandTile destination;
		
		Player player = gameView.pickHeliPlayer(gamePlayers);
		
		for(Card card : player.getCards()) {
			
			//If helicopter card found then attempt to play it
			if(card instanceof HelicopterLiftCard) {
				
				//Prompt for user to choose destination and players who wish to move
				destination = gameView.pickHeliDestination(nonSunkTiles);
				heliPlayers = gameView.pickHeliPlayers(gamePlayers, destination);
				
				//If there are players who wish to move then move them
				if(heliPlayers.size() > 0) {
					for(Player p: heliPlayers) {
						p.getPawn().setLocation(destination);
					}
					player.getCards().remove(card);
				}
				
				return;

			}
		}
		//If you reach here then there was no card in hand
		gameView.showNoHeliCard(player);
	}
	
	
	public void sandBagRequest() {
		
		List<Player> players = gameModel.getGamePlayers().getPlayersList();
		
		Player player = gameView.pickSandbagPlayer(players);
		
		for(Card card : player.getCards()) {
			
			if(card instanceof SandbagCard) {
				
				List<IslandTile> floodedTiles = gameModel.getIslandBoard().getFloodedTiles();
				
				if(floodedTiles.size() > 0) {
					
					
					
				}
				
			}
			
		}
	}
	

	
	

}
