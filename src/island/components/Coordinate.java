package island.components;

/**
 * Class to allow for easy representation of IslandTile locations
 * on the IslandBoard
 * @author Eoghan O'Shea and Robert McCarthy
 */
public class Coordinate {

	private int row;
	private int column;
	
	Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
}