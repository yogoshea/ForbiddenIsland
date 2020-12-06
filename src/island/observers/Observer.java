package island.observers;

/**
 * Interface to be implemented by required game play observers
 * @author Eoghan O'Shea and Robert McCarthy
 * @param <E> argument to store updated game component
 */
public interface Observer {
//	protected Subject subject;
	public abstract void update(Subject updatedSubject);
}
