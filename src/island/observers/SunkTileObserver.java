//package island.observers;
//
//import java.util.List;
//
//import island.components.IslandBoard;
//import island.components.IslandTile;
//import island.components.Treasure;
//import island.game.GameModel;
//import island.players.GamePlayers;
//import island.players.Player;
//
//public class SunkTileObserver extends GameOverObserver { //or extends abstract class GameOverObserver??? -> with method setGameOver()
//	
//	private static SunkTileObserver sunkTileObserver;
//	
//	//Used to check if treasure tile are sunk
//	private static IslandTile[][] pairedTreasureTiles;
//	
//	private SunkTileObserver(GameModel gameModel) {
//		
//		subject = gameModel.getIslandBoard(); //OR just use IslandBoard.getInstance()?? like in waterMeterObserver
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
//	 * @return single instance of SUnkTileObserver
//	 */
//	public static SunkTileObserver getInstance(GameModel gameModel) {
//		if (sunkTileObserver == null) {
//			sunkTileObserver = new SunkTileObserver(gameModel);
//		}
//		return sunkTileObserver;
//	}
//	
//	
//	/*
//	 * Method to check all 4 ways that game could be over. Sets gameOver if it is
//	 */
//	@Override
//	public void checkIfGameOver() {
//		boolean gameOver = checkTreasureTiles() || checkFoolsLanding() || checkPlayerTiles();
//		if(gameOver) {
//			setGameOver(); // TODO: change to GameController.endGame() I think
//		}
//	}
//	
//	
//	/*
//	 * Method to check if game is over as one of the treasures can no longer be captured 
//	 */
//	public static boolean checkTreasureTiles() {
//		
//		//Can't use subject.findTileLocation() as subject has no method findTileLocation()???
//		for(int i = 0; i < 4; i++) {
//			int[] pos1 = IslandBoard.getInstance().getTileLocation( pairedTreasureTiles[i][1] ); 
//			int[] pos2 = IslandBoard.getInstance().getTileLocation( pairedTreasureTiles[i][2] );
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
//		int[] pos = IslandBoard.getInstance().getTileLocation(IslandTile.FOOLS_LANDING);
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
//			int[] pos = IslandBoard.getInstance().getTileLocation( p.getCurrentTile() );
//			if(pos[0] < 0) {
//				return true;				
//			}
//		}
//		return false;
//	}
//	
//}
