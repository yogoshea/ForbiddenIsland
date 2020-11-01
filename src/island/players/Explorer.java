package island.players;

public class Explorer extends Player {

	public Explorer (String name) {
		super(name);
		System.out.println(name + " is the Explorer");
	}

	@Override
	public String toString() {
		return super.toString() + " (Explorer)";
	}
}
