package commands;

import geometry.Shape;

public class ModifyShape implements Command, Report {
	private Shape mainShape;
	private Shape unmodified;
	private Shape modified;
	
	public ModifyShape(Shape mainShape,Shape modified) {
		this.mainShape=mainShape;
		unmodified=mainShape.clone();
		this.modified=modified;
	}
	
	@Override
	public void execute() {
		mainShape.adjust(modified);		
	}

	@Override
	public void unexecute() {
		mainShape.adjust(unmodified);
	}

	@Override
	public String details() {
		return "Modify: "+mainShape.typeAndId()+" to "+modified.details();
	}
	
}
