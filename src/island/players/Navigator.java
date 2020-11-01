package island.players;

public class Navigator extends Player {

	public Navigator(String name) {
		super(name);
		System.out.println(name + " is the Navigator");
	}

	@Override
	public String toString() {
		return super.toString() + " (Navigator)";
	}
}
