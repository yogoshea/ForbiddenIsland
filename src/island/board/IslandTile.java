package island.board;

/**
 * IslandTile class for island tiles
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */

//Make just an Enum???

// Yeah, I think this should be an enum with the attribute isFlooded

public class IslandTile extends Tile {
	
	//name is Enum
	private IslandTileName name;
	
	private Boolean isFlooded;
	
	//Takes name Enum as input and construct based on that
	public IslandTile(IslandTileName name) {
		this.name = name;
		this.isFlooded = false;
		//Add an associated treasure???
	}
	
	public Boolean getIsFlooded() {
		return this.isFlooded;
	}
	
	public void setIsFlooded(Boolean isFlooded) {
		this.isFlooded = isFlooded;
	}

}
