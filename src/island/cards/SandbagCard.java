package island.cards;

import java.util.List;

import island.components.IslandBoard;
import island.components.IslandTile;
import island.game.GameScanner;

/**
 * Class to represent Sandbag cards.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public class SandbagCard extends Card {
	
	public SandbagCard() {
//		super();
	}
	
	public String toString() {
		return "*** Sandbag Card ***";
	}

//	/**
//	 * Method to use card and de-flood a tile
//	 */
//	public boolean use() { // TODO: should this throw a NoFloodedTiles Exception
//		
//		List<IslandTile> floodedTiles = IslandBoard.getInstance().getFloodedTiles(); //TODO: implement getFloodedTiles()
//		
//		if(floodedTiles.size() > 0) {
//			
//			String prompt;
//			IslandTile tile;
//			prompt = "Which tile would you like to shore-up?";
//			tile = GameScanner.getInstance().pickFromList(floodedTiles, prompt);
//			tile.setToFlooded();
//			return true;
//			
//		} else {
//			System.out.println("No flooded tiles");
//			return false;
//		}
//	}

}
