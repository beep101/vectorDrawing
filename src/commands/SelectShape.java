package commands;

import geometry.Shape;

public class SelectShape implements Command, Report{

	private Shape shape;
	
	public SelectShape(Shape shape) {
		this.shape=shape;
	}
	
	@Override
	public String details() {
		return "Select: "+shape.typeAndId();
	}

	@Override
	public void execute() {
		shape.setSelected(true);
	}

	@Override
	public void unexecute() {
		shape.setSelected(false);
	}

}
