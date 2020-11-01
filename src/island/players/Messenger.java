package island.players;

public class Messenger extends Player {

	public Messenger(String name) {
		super(name);
		System.out.println(name + " is the Messenger");
	}

	@Override
	public String toString() {
		return super.toString() + " (Messenger)";
	}
}
