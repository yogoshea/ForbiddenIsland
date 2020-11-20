package island.components;

import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

/**
 * Data structure extending Stack and containing all IslandTiles
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
@SuppressWarnings("serial")
public class IslandTileStack extends Stack<IslandTile> {
	
	/**
	 * constructor fills stack with island tiles and randomises the order
	 */
	public IslandTileStack() {
		super(); // calling superclass Stack constructor
		
		// Add all island tiles to stack when instantiated
		this.addAll(Arrays.asList(IslandTile.values()));
		
		// Shuffle the tiles in the stack
		Collections.shuffle(this);
	}

}
