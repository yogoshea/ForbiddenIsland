package island.components;

/**
 * Class to allow for easy representation of IslandTile locations
 * on the IslandBoard
 * @author Eoghan O'Shea and Robert McCarthy
 */
public class Coordinate {

	private int rowIndex;
	private int columnIndex;
	
	/**
	 * Constructor for Coordinate instance.
	 * @param row integer value of new Coordinate.
	 * @param column integer value of new Coordinate.
	 */
	public Coordinate(int rowIndex, int columnIndex) {
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
	}
	
	/**
	 * Computes the row positional offset seen on the island layout.
	 * @param row array index from island board 2D array structure.
	 * @return integer value of island grid row offset.
	 */
	public static int calcRowOffset(int aRowIndex) {
		if (aRowIndex <= 3)
			return (- 2 * aRowIndex) + 4;
		else
			return (2 * aRowIndex) - 6;
	}
	
	/**
	 * Computes distance between two given coordinates on island.
	 * @param First Coordinate instance.
	 * @param Second Coordinate instance.
	 * @return double value representing the distance between points.
	 */
	public static double calcDistanceBetweenCoordinates(Coordinate c1, Coordinate c2) {
		
		// Retrieve rows and columns according to island layout.
		double x1 = c1.getIslandRow();
		double x2 = c2.getIslandRow();
		double y1 = c1.getIslandColumn();
		double y2 = c2.getIslandColumn();
		
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	/**
	 * Getter method for row index value in island board array.
	 * @return integer value representing row array index.
	 */
	public int getRowIndex() {
		return rowIndex;
	}
	
	/**
	 * Getter method for column index value in island board array.
	 * @return integer value representing column array index.
	 */
	public int getColumnIndex() {
		return columnIndex;
	}
	
	/**
	 * Getter method for row value on island board grid.
	 * @return integer value representing row grid line.
	 */
	public int getIslandRow() {
		return rowIndex + calcRowOffset(rowIndex);
	}
	
	/**
	 * Getter method for column value  on island board grid.
	 * @return integer value representing column grid line.
	 */
	public int getIslandColumn() {
		return columnIndex;
	}

}
