package commands;

public abstract interface Command{
	public void execute();
	public void unexecute();
}
