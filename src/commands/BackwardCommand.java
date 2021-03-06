package commands;

import geometry.Shape;
import model.Model;

public class BackwardCommand implements Command, Report {
	private Model model;
	private Shape shape;
	
	public BackwardCommand(Model model,Shape shape) {
		this.model=model;
		this.shape=shape;
	}
	@Override
	public void execute() {
		int index=model.indexOf(shape);
		Shape temp=model.get(index);
		model.set(index, model.get(index-1));
		model.set(index-1, temp);
	}

	@Override
	public void unexecute() {
		int index=model.indexOf(shape);
		Shape temp=model.get(index);
		model.set(index, model.get(index+1));
		model.set(index+1, temp);
	}
	@Override
	public String details() {
		return "Backward: "+shape.typeAndId();
	}
}
