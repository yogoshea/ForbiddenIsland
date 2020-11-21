package island.cards;

/**
 * Abstract class to be extended by different card types
 * used throughout game.
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public abstract class Card {
	
	// TODO: remove name attribute if unnecessary
	private String name;
	
	public String getName() {
		return name;
	}
	
	protected void setName(String name) {
		this.name = name;
	}
	
	// insert any common functionality here

}
