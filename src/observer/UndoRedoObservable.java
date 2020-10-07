package observer;

public interface UndoRedoObservable {
	public void addUndoRedoObserver(UndoRedoObserver observer);
	public void removeUndoRedoObserver(UndoRedoObserver observer);
	public void notifyUndoRedoObserver();
}
