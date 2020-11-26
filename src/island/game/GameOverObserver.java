package island.game;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.components.WaterMeter;
import island.players.GamePlayers;
import island.players.Player;

//Is this a good way to do it??
//Is this a proper observer???
//See IslandBoard line 214 for how it would work

/**
 * Class which should be alerted at appropriate times so it can check if the game is over
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GameOverObserver {
	
	private static GameOverObserver gameOverObserver = new GameOverObserver();
	
	private IslandTile[][] treasureTiles;
	
	private GameOverObserver() {
		treasureTiles = new IslandTile[4][];
		treasureTiles[0] = new IslandTile[] {IslandTile.CAVE_OF_EMBERS, IslandTile.CAVE_OF_SHADOWS};
		treasureTiles[1] = new IslandTile[] {IslandTile.CORAL_PALACE, IslandTile.TIDAL_PALACE};
		treasureTiles[2] = new IslandTile[] {IslandTile.CAVE_OF_EMBERS, IslandTile.CAVE_OF_SHADOWS};
		treasureTiles[3] = new IslandTile[] {IslandTile.CAVE_OF_EMBERS, IslandTile.CAVE_OF_SHADOWS};
	}
	
	public static GameOverObserver getInstance() {
		return gameOverObserver;
	}
	
	/*
	 * Method to check all 4 ways that game could be over. Sets gameOver if it is
	 */
	public void checkIfGameOver() {
		boolean gameOver = checkTreasureTiles() || checkFoolsLanding() || checkPlayerTiles() || checkWaterLevel();
		if(gameOver) {
			//TODO: make game singleton
			//Game.getInstance().setGameOver();
		}
	}
	// TODO: make separate observers?
	//Unnecessary to check every thing each time??
	//Have memory and/or only check applicable ones???
	
	
	
	public boolean checkTreasureTiles() {
		
		for(int i = 0; i < 4; i++) {
			int[] pos1 = IslandBoard.getInstance().findTileLocation( treasureTiles[i][1] );
			int[] pos2 = IslandBoard.getInstance().findTileLocation( treasureTiles[i][2] );
			if(pos1[0] < 0 || pos2[0] < 0) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean checkFoolsLanding() {
		int[] pos = IslandBoard.getInstance().findTileLocation(IslandTile.FOOLS_LANDING);
		if(pos[0] < 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkPlayerTiles() {
		for(Player p : GamePlayers.getInstance().getPlayersList()) {
			int[] pos = IslandBoard.getInstance().findTileLocation( p.getCurrentTile() );
			if(pos[0] < 0) {
				return true;				
			}
		}
		return false;
	}
	
	public static boolean checkWaterLevel() {
		return WaterMeter.getInstance().getLevel() > 5;
		//TODO: make max level a game variable
	}

}
