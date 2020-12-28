package island.observers;

/**
 * Interface to be implemented by required game play observers
 * @author Eoghan O'Shea and Robert McCarthy
 */
public interface Observer {
	public abstract void update(Subject subject);
}
