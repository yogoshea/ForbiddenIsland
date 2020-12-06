package island.observers;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class to be implemented by necessary gameplay subjects
 * @author Eoghan O'Shea and Robert McCarthy
 *
 */
public interface Subject { // TODO: must be interface as it's applied to subclasses and enums
	public void attach(Observer observer);
	public void notifyAllObservers();
}
	
//	private List<Observer> observers = new ArrayList<Observer>();
//	
//	public void attach(Observer observer) {
//		observers.add(observer);
//	}
//	
//	public void notifyAllObservers() {
//		for (Observer observer : observers) {
//			observer.update();
//		}
//	}
	
//	public abstract E getState();
//	public abstract void setState();

