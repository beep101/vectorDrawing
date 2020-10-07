package commands;

import geometry.Shape;
import model.Model;

public class SendToBackCommand implements Command, Report {
	private Shape shape;
	private Model model;
	private int index;
	
	public SendToBackCommand(Model model,Shape shape) {
		this.model=model;
		this.shape=shape;
		index=model.indexOf(shape);
	}
	@Override
	public void execute() {
		model.remove(shape);
		model.add(0,shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.add(index, shape);
	}
	@Override
	public String details() {
		return "SendToBack: "+shape.typeAndId();
	}

}
