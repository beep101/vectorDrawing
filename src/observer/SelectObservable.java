package observer;

public interface SelectObservable {
	public void addSelectObserver(SelectObserver o);
	public void removeSelectObserver(SelectObserver o);
	public void notifySelectObserver();
}
