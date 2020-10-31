package island.main;

import island.board.*;

/**
 * GamePlay class controls the game events at the highest level
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class GamePlay {
	
	public static void main(String[] args) {
		//Create a stack of all the island tiles
		IslandTileStack islandTileStack = new IslandTileStack();//Do this inside IslandBoard constructor?
		//Shuffle it - do this inside IslandTileStack??
		islandTileStack.shuffle();
		//Create island board with this stack of tiles
		IslandBoard islandBoard = new IslandBoard(islandTileStack.getIslandTileStack());
		
	}

}
