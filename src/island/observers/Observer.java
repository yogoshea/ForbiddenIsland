package island.observers;

/**
 * Abstract class to be implemented by required gameplay observers
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public abstract class Observer { //Interface?
	protected Subject subject;
	public abstract void update();

}
