package island.observers;

/**
 * Interface to be implemented by necessary game play classes and enums
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public interface Subject { 
	public void attach(Observer observer);
	public void notifyAllObservers();
}