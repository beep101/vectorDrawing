package controller;

import java.util.ArrayList;
import java.util.List;

import commands.Command;
import commands.DeselectShape;
import commands.SelectShape;
import geometry.Shape;
import model.Model;
import observer.SelectObservable;
import observer.SelectObserver;

public class SelectManager implements SelectObservable{
	private Model model;
	private SelectObserver selectObserver;
	private UndoRedoManager undoRedo;
	
	public SelectManager(Model model, UndoRedoManager undoRedo) {
		this.model=model;
		this.undoRedo=undoRedo;
		this.selectObserver=null;
	}
	
	public String select(int x, int y) {
		Command c;
		for(int i=model.size()-1;i>=0;i--) {
			if(model.get(i).contains(x, y)) {
				if(model.get(i).isSelected()) {
					List<Shape> list=new ArrayList<Shape>();
					list.add(model.get(i));
					c=new DeselectShape(list);
				}
				else
					c=new SelectShape(model.get(i));
				return undoRedo.doCommand(c);
			}
		}
		List<Shape> selected=getSelected();
		if(selected.size()>0) {
			return undoRedo.doCommand(new DeselectShape(getSelected()));
		}
		else {
			return null;
		}
	}
	
	private int count() {
		int count=0;
		for(int i=0;i<model.size();i++)
			if(model.get(i).isSelected())
				count++;
		return count;
	}
	
	public List<Shape> getSelected(){
		List<Shape> list=new ArrayList<>();
		for(int i=0;i<model.size();i++)
			if(model.get(i).isSelected())
				list.add(model.get(i));
		return list;
	}
	
	@Override
	public void addSelectObserver(SelectObserver o) {
		this.selectObserver=o;
	}
	@Override
	public void removeSelectObserver(SelectObserver o) {
		this.selectObserver=null;
	}
	@Override
	public void notifySelectObserver() {
		selectObserver.updateSelect(count());
	}
}
