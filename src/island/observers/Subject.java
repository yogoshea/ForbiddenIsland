package island.observers;

/**
 * Abstract class to be implemented by necessary game play subjects
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public interface Subject { 
	public void attach(Observer observer);
	public void notifyAllObservers();
}