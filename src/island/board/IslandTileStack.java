package island.board;

import java.util.Stack;

public class IslandTileStack {
	
	Stack<IslandTile> islandTileStack = new Stack<IslandTile>();
	
	public IslandTileStack() {
		
		for(IslandTileName tileName : IslandTileName.values()) {
			islandTileStack.add(new IslandTile(tileName));
		}
		
	}
	
	public Stack<IslandTile> getIslandTileStack(){
		return islandTileStack;
	}

}
