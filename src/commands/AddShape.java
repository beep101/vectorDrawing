package commands;

import geometry.Shape;
import model.Model;

public class AddShape implements Command, Report {
	private Shape shape;
	private Model model;
	
	public AddShape(Shape shape,Model model) {
		this.shape=shape;
		this.model=model;
	}
	
	@Override
	public void execute() {
		model.add(shape);
	}
	@Override
	public void unexecute() {
		model.remove(shape);
	}

	@Override
	public String details() {
		return "Add: "+shape.typeAndId()+" with "+shape.details();
	}
}
