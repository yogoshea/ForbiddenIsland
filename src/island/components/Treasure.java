package island.components;

public enum Treasure {
	THE_CRYSTAL_OF_FIRE("The Crystal of Fire"),
	THE_EARTH_STONE("The Earth Stone"),
	THE_OCEANS_CHALICE("The Oceans Chalice"),
	THE_STATUE_OF_THE_WIND("The Statue of the Wind");
	
	private String name;
	
	Treasure(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
