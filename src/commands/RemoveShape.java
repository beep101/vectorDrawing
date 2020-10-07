package commands;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import geometry.Shape;
import model.Model;

public class RemoveShape implements Command, Report{
	private List<Shape> shapes;
	private List<Integer> indexes;
	private Model model;
	
	public RemoveShape(List<Shape> shapes, Model model) {
		this.shapes=shapes;
		this.model=model;
		indexes=new ArrayList<>();
		for(Shape shape :shapes)
			indexes.add(new Integer(model.indexOf(shape)));
	}

	@Override
	public String details() {
		StringBuilder result=new StringBuilder();
		result.append("Remove: ");
		for(Shape shape:shapes)
			result.append(shape.typeAndId()+", ");
		return result.toString().substring(0, result.toString().length()-2);
	}

	@Override
	public void execute() {
		for(Shape shape:shapes)
			model.remove(shape);
		
	}

	@Override
	public void unexecute() {
		ListIterator<Shape> i=shapes.listIterator(shapes.size());
		while(i.hasPrevious()) {
			Shape shape=i.previous();
			model.add(indexes.get(shapes.indexOf(shape)),shape);
		}
	}
}
