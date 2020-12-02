package island.observers;

import java.util.List;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.Treasure;
import island.components.WaterMeter;
import island.game.ActionController;
import island.game.GameModel;
import island.game.GameView;
import island.players.GamePlayers;
import island.players.Player;

//TODO: Split into a tile observer (observing island board), and a watermeter observer?

/**
 * Class which should be alerted at appropriate times so it can check if the game is over
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public abstract class GameOverObserver extends Observer {
	
	protected abstract void checkIfGameOver();
	
	@Override
	public void update() {
		checkIfGameOver();
		// tell GameController to endGame() ??
	}
	
	protected void setGameOver() {
		//gameOver = true;
		//gameView.showEnding()
	}
	
}

//public class GameOverObserver extends Observer {
//	
//	private static GameOverObserver gameOverObserver;
//	
//	//Used to check if treasure tile are sunk
//	private static IslandTile[][] pairedTreasureTiles;
//	
//	private GameOverObserver(GameModel gameModel) {
//		
//		subject = gameModel;
//		subject.attach(this);
//		
//		//This array is used for checkTreasureTiles() method
//		pairedTreasureTiles = new IslandTile[4][];
//		pairedTreasureTiles[0] = new IslandTile[] {IslandTile.CAVE_OF_EMBERS, IslandTile.CAVE_OF_SHADOWS};
//		pairedTreasureTiles[1] = new IslandTile[] {IslandTile.CORAL_PALACE, IslandTile.TIDAL_PALACE};
//		pairedTreasureTiles[2] = new IslandTile[] {IslandTile.HOWLING_GARDEN, IslandTile.WHISPERING_GARDEN};
//		pairedTreasureTiles[3] = new IslandTile[] {IslandTile.TEMPLE_OF_THE_MOON, IslandTile.TEMPLE_OF_THE_SUN};
//	}
//	
//	/**
//	 * @return single instance of action controller
//	 */
//	public static GameOverObserver getInstance(GameModel gameModel) {
//		if (gameOverObserver == null) {
//			gameOverObserver = new GameOverObserver(gameModel);
//		}
//		return gameOverObserver;
//	}
//	
//	
//	/*
//	 * Method to check all 4 ways that game could be over. Sets gameOver if it is
//	 */
//	public void checkIfGameOver() {
//		boolean gameOver = checkTreasureTiles() || checkFoolsLanding() || checkPlayerTiles() || checkWaterLevel();
//		if(gameOver) {
//			//Game.getInstance().setGameOver();
//		}
//	}
//	// TODO: make separate observers?
//	//Unnecessary to check every thing each time??
//	//Have memory and/or only check applicable ones???
//	
//	
//	/*
//	 * Method to check if game is over as one of the treasures can no longer be captured 
//	 */
//	public static boolean checkTreasureTiles() {
//		
//		for(int i = 0; i < 4; i++) {
//			int[] pos1 = IslandBoard.getInstance().findTileLocation( pairedTreasureTiles[i][1] );
//			int[] pos2 = IslandBoard.getInstance().findTileLocation( pairedTreasureTiles[i][2] );
//			List<Treasure> capturedTreasures = GamePlayers.getInstance().getCapturedTreasures();
//			boolean alreadyCaptured = capturedTreasures.contains(pairedTreasureTiles[i][1].getAssociatedTreasure());
//			
//			//If both tiles are sunk and associated treasure not captured
//			if( (pos1[0] < 0 && pos2[0] < 0) && !alreadyCaptured) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	/*
//	 * Method to check if game is over due to FoolsLanding being sunkeded
//	 */
//	public static boolean checkFoolsLanding() {
//		int[] pos = IslandBoard.getInstance().findTileLocation(IslandTile.FOOLS_LANDING);
//		if(pos[0] < 0) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//	
//	/*
//	 * Method to check if game is over due to player being on a sunken tile
//	 * Should player be an observer here? check via player?
//	 */
//	public static boolean checkPlayerTiles() {
//		for(Player p : GamePlayers.getInstance().getPlayersList()) {
//			int[] pos = IslandBoard.getInstance().findTileLocation( p.getCurrentTile() );
//			if(pos[0] < 0) {
//				return true;				
//			}
//		}
//		return false;
//	}
//	
//	/*
//	 * Method to check if Game Over due to water level above threshold
//	 */
//	public static boolean checkWaterLevel() {
//		return WaterMeter.getInstance().getLevel() > 5;
//		//TODO: make make level a final game variable
//	}
//
//	@Override
//	public void update() {
//		checkIfGameOver();
//		// tell GameController to endGame() ??
//		
//	}
//
//}