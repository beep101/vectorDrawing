package commands;

import java.util.List;

import geometry.Shape;

public class DeselectShape implements Command,Report {

	private List<Shape> shapes;
	
	public DeselectShape(List<Shape> shapes) {
		this.shapes=shapes;
	}
	
	public String details() {
		StringBuilder result=new StringBuilder();
		result.append("Deselect: ");
		for(Shape shape:shapes)
			result.append(shape.typeAndId()+", ");
		return result.toString().substring(0, result.toString().length()-2);
	}

	@Override
	public void execute() {
		for(Shape shape:shapes)
			shape.setSelected(false);
	}

	@Override
	public void unexecute() {
		for(Shape shape:shapes)
			shape.setSelected(true);
	}

}
