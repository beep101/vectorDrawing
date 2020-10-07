package controller;

import java.util.Stack;

import commands.Command;
import commands.Report;
import observer.UndoRedoObservable;
import observer.UndoRedoObserver;

public class UndoRedoManager implements UndoRedoObservable{
	private Stack<Command> done;
	private Stack<Command> undone;
	private UndoRedoObserver undoRedoObserver;
	
	public UndoRedoManager() {
		done=new Stack<Command>();
		undone=new Stack<Command>();
	}
	
	public String doCommand(Command c) {
		undone.clear();
		c.execute();
		done.push(c);
		notifyUndoRedoObserver();
		return "Done: "+((Report)c).details()+"\n";
	}
	
	public String undoCommand() {
		if(!done.isEmpty()) {
			done.peek().unexecute();
			undone.push(done.pop());
			notifyUndoRedoObserver();
			return "Undone: "+((Report)undone.peek()).details()+"\n";
		}
		return null;
	}
	
	public String redoCommand() {
		if(!undone.isEmpty()) {
			undone.peek().execute();
			done.push(undone.pop());
			notifyUndoRedoObserver();
			return "Redone: "+((Report)done.peek()).details()+"\n";
		}
		return null;
	}

	protected Stack<Command> getDone() {
		return done;
	}

	protected void setDone(Stack<Command> done) {
		this.done = done;
	}

	protected Stack<Command> getUndone() {
		return undone;
	}

	protected void setUndone(Stack<Command> undone) {
		this.undone = undone;
	}

	@Override
	public void addUndoRedoObserver(UndoRedoObserver observer) {
		this.undoRedoObserver=observer;			
	}

	@Override
	public void removeUndoRedoObserver(UndoRedoObserver observer) {
		this.undoRedoObserver=null;
	}

	@Override
	public void notifyUndoRedoObserver() {
		undoRedoObserver.updateUndoRedo(!done.isEmpty(), !undone.isEmpty());
	}

}
