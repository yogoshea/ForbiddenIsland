package island.players;

public class Pilot extends Player {
	
	public Pilot(String name) {
		super(name);
		System.out.println(name + " is the Pilot");
	}

	@Override
	public String toString() {
		return super.toString() + " (Pilot)";
	}
}
