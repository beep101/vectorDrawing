package controller;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import ModifyInterface.ColorPicker;
import ModifyInterface.Flag;
import ModifyInterface.PickerDialog;
import ModifyInterface.PickerLabel;
import ModifyInterface.ValuePicker;
import commands.AddShape;
import commands.SendToBackCommand;
import commands.BringToFrontCommand;
import commands.Command;
import commands.ModifyShape;
import commands.RemoveShape;
import commands.BackwardCommand;
import commands.ForwardCommand;
import geometry.Circle;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;
import model.Model;
import observer.SelectObserver;
import observer.UndoRedoObserver;
import view.Frame;
import view.View;

public class Controller implements UndoRedoObserver,SelectObserver{
	private Frame frame;
	private Model model;
	private View view;
	
	private Mode mode;
	private Color border;
	private Color surface;
	
	private StringBuilder log;
	private UndoRedoManager undoRedo;
	private SaveLoadManager saveLoad;
	private Parser parser;
	private SelectManager select;
	
	private int id=0;
	private int loadStep=0;
	private boolean lineFlag=false;
	private int lineX,lineY;
	private boolean loadFlag=false;
	
	public Controller(View view, Model model,Frame frame) {
		this.frame=frame;
		this.model=model;
		this.view=view;
		
		log=new StringBuilder();
		undoRedo=new UndoRedoManager();
		saveLoad=new SaveLoadManager();
		select=new SelectManager(model,undoRedo);

		parser=null;
		
		undoRedo.addUndoRedoObserver(this);
		select.addSelectObserver(this);
		
		mode=Mode.SELECT;
		frame.btnSelectSetSelected(true);
		border=Color.BLACK;
		surface=Color.WHITE;
		
		frame.enableUndo(false);
		frame.enableRedo(false);
		frame.enableModify(false);
		frame.enableDelete(false);
		frame.disableMoveToButtons();
		frame.setBorderColor(border);
		frame.setSurfaceColor(surface);
	}
	
	public void clicked(int x,int y) {
		if(loadFlag)
			return;
		Command c;
		if(lineFlag) {
			Line line=new Line(new Point(lineX,lineY),new Point(x, y),border,id++);
			c=new AddShape(line,model);
			log.append(undoRedo.doCommand(c));
			lineFlag=false;
			select.notifySelectObserver();
			view.repaint();
			frame.setLogText(log.toString());
			//****************************************************************
			//bez return linije kontinualne, pritisnuti line za restart linije
			return;
			//****************************************************************
		}
		switch(mode) {
		case SELECT:
			String retVal=select.select(x, y);
			if(retVal!=null)
				log.append(retVal);
			break;
		case POINT:
			Point point=new Point(x,y,border,id++);
			c=new AddShape(point, model);
			log.append(undoRedo.doCommand(c));
			break;
		case LINE:
			lineX=x;
			lineY=y;
			lineFlag=true;
			break;
		case SQUARE:
			Flag squareFlag=new Flag();
			PickerDialog squareDialog=new PickerDialog(squareFlag,"Square dimmensions");
			squareDialog.setLocation(x, y);
			ValuePicker squareSide=new ValuePicker("Side: ",-1); 
			squareDialog.add(squareSide);
			squareDialog.start();
			do {
				if(!squareFlag.get())
					return;
				if(squareSide.getValue()!=0)
					break;
				JOptionPane.showMessageDialog(squareDialog, "Square side must have length", "Warning",JOptionPane.WARNING_MESSAGE);
				squareFlag.set(false);
				squareDialog.restart();
			}while(squareSide.getValue()==0);
			Square square=new Square(new Point(x,y),squareSide.getValue(), border, surface,id++);
			c=new AddShape(square, model);
			log.append(undoRedo.doCommand(c));
			break;
		case RECTANGLE:
			Flag rectFlag=new Flag();
			PickerDialog rectDialog=new PickerDialog(rectFlag,"Rectectangle dimmensions");
			rectDialog.setLocation(x, y);
			ValuePicker rectWidth=new ValuePicker("Width: ",-1); 
			ValuePicker rectHeight=new ValuePicker("Height: ", -1);
			rectDialog.add(rectWidth);
			rectDialog.add(rectHeight);
			rectDialog.start();
			do {
				if(!rectFlag.get())
					return;
				if(rectWidth.getValue()!=0&&rectHeight.getValue()!=0)
					break;
				JOptionPane.showMessageDialog(rectDialog, "Rectangle sides must have length", "Warning",JOptionPane.WARNING_MESSAGE);
				rectFlag.set(false);
				rectDialog.restart();
			}while(rectWidth.getValue()==0||rectHeight.getValue()==0);
			Rectangle rect=new Rectangle(new Point(x,y),rectHeight.getValue(),rectWidth.getValue(),border,surface,id++);
			c=new AddShape(rect, model);
			log.append(undoRedo.doCommand(c));
			break;
		case CIRCLE:
			Flag circleFlag=new Flag();
			PickerDialog circleDialog=new PickerDialog(circleFlag,"Circle dimmensions");
			circleDialog.setLocation(x, y);
			ValuePicker circleRadius=new ValuePicker("Radius: ",-1);
			circleDialog.add(circleRadius);
			circleDialog.start();
			do {
				if(!circleFlag.get())
					return;
				if(circleRadius.getValue()!=0)
					break;
				JOptionPane.showMessageDialog(circleDialog, "Circle radius must have length", "Warning",JOptionPane.WARNING_MESSAGE);
				circleFlag.set(false);
				circleDialog.restart();
			}while(circleRadius.getValue()==0);
			Circle circle=new Circle(new Point(x,y),circleRadius.getValue(),border,surface,id++);
			c=new AddShape(circle, model);
			log.append(undoRedo.doCommand(c));
			break;
		case HEXAGON:
			Flag hexagonFlag=new Flag();
			PickerDialog hexagonDialog=new PickerDialog(hexagonFlag,"Hexagon dimmensions");
			hexagonDialog.setLocation(x, y);
			ValuePicker hexagonSide=new ValuePicker("Side: ",-1);
			hexagonDialog.add(hexagonSide);
			hexagonDialog.start();
			do {
				if(!hexagonFlag.get())
					return;
				if(hexagonSide.getValue()!=0)
					break;
				JOptionPane.showMessageDialog(hexagonDialog, "Hexagon side must have length", "Warning",JOptionPane.WARNING_MESSAGE);
				hexagonFlag.set(false);
				hexagonDialog.restart();
			}while(hexagonSide.getValue()==0);
			HexagonAdapter hex=new HexagonAdapter(x,y,hexagonSide.getValue(),border,surface,id++);
			c=new AddShape(hex, model);
			log.append(undoRedo.doCommand(c));
			break;
		default:
			break;
		}
		select.notifySelectObserver();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void undo() {
		lineFlag=false;
		log.append(undoRedo.undoCommand());
		select.notifySelectObserver();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void redo() {
		lineFlag=false;
		log.append(undoRedo.redoCommand());
		select.notifySelectObserver();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void modify() {
		lineFlag=false;
		Command c=null;
		Flag flag=new Flag();
		Shape element=select.getSelected().get(0);
		PickerDialog dialog;
		if(element instanceof Point) {
			dialog=new PickerDialog(flag,"Modify point");
			Point p=(Point)element;
			ValuePicker x=new ValuePicker("X:", p.getX());
			ValuePicker y=new ValuePicker("Y: ", p.getY());
			ColorPicker color=new ColorPicker("Color: ", p.getColor());
			dialog.add(x);
			dialog.add(y);
			dialog.add(color);
			dialog.start();
			if(flag.get()) {
				Point newPoint=new Point(x.getValue(), y.getValue(), color.getColor(),-1);
				c=new ModifyShape(element, newPoint);
			}
		}else if(element instanceof Line) {
			dialog=new PickerDialog(flag,"Modify line");
			Line l=(Line)element;
			ValuePicker x1=new ValuePicker("X:", l.getStartPoint().getX());
			ValuePicker y1=new ValuePicker("Y: ", l.getStartPoint().getY());
			ValuePicker x2=new ValuePicker("X:", l.getEndPoint().getX());
			ValuePicker y2=new ValuePicker("Y: ", l.getEndPoint().getY());
			ColorPicker color=new ColorPicker("Color: ", l.getColor());
			dialog.add(new PickerLabel("Start point:"));
			dialog.add(x1);
			dialog.add(y1);
			dialog.add(new PickerLabel("End point:"));
			dialog.add(x2);
			dialog.add(y2);
			dialog.add(color);
			dialog.start();
			if(flag.get()) {
				Line newLine=new Line(new Point(x1.getValue(), y1.getValue()),new Point(x2.getValue(),y2.getValue()), color.getColor(),-1);
				c=new ModifyShape(element,newLine);
			}			
		}else if(element instanceof Rectangle) {
			dialog=new PickerDialog(flag,"Modify rectangle");
			Rectangle r=(Rectangle)element;
			ValuePicker x1=new ValuePicker("X: ", r.getUpperLeft().getX());
			ValuePicker y1=new ValuePicker("Y: ", r.getUpperLeft().getY());
			ValuePicker width=new ValuePicker("Width: ",r.getWidth()); 
			ValuePicker height=new ValuePicker("Height: ", r.getSide());
			ColorPicker border=new ColorPicker("BorderColor: ", r.getColor());
			ColorPicker surface=new ColorPicker("Surface color: ", r.getSurfaceColor());
			dialog.add(new PickerLabel("Upper left point:"));
			dialog.add(x1);
			dialog.add(y1);
			dialog.add(width);
			dialog.add(height);
			dialog.add(border);
			dialog.add(surface);
			dialog.start();
			do {
				if(!flag.get())
					return;
				if(width.getValue()!=0&&height.getValue()!=0)
					break;
				JOptionPane.showMessageDialog(dialog, "Rectangle sides must have length", "Warning",JOptionPane.WARNING_MESSAGE);
				flag.set(false);
				dialog.restart();
			}while(width.getValue()==0||height.getValue()==0);
			Rectangle newRect=new Rectangle(new Point(x1.getValue(), y1.getValue()),height.getValue(),width.getValue(), border.getColor(), surface.getColor(),-1);
			c=new ModifyShape(element, newRect);
		}else if(element instanceof Square) {
			dialog=new PickerDialog(flag,"Modify square");
			Square s=(Square)element;
			ValuePicker x1=new ValuePicker("X: ", s.getUpperLeft().getX());
			ValuePicker y1=new ValuePicker("Y: ", s.getUpperLeft().getY());
			ValuePicker side=new ValuePicker("Side: ",s.getSide()); 
			ColorPicker border=new ColorPicker("BorderColor: ", s.getColor());
			ColorPicker surface=new ColorPicker("Surface color: ", s.getSurfaceColor());
			dialog.add(new PickerLabel("Upper left point:"));
			dialog.add(x1);
			dialog.add(y1);
			dialog.add(side);
			dialog.add(border);
			dialog.add(surface);
			dialog.start();
			do {
				if(!flag.get())
					return;
				if(side.getValue()!=0)
					break;
				JOptionPane.showMessageDialog(dialog, "Square side must have length", "Warning",JOptionPane.WARNING_MESSAGE);
				flag.set(false);
				dialog.restart();
			}while(side.getValue()==0);
			Square newSquare=new Square(new Point(x1.getValue(), y1.getValue()), side.getValue(), border.getColor(), surface.getColor(),-1);
			c=new ModifyShape(element,newSquare);
		}else if(element instanceof Circle) {
			dialog=new PickerDialog(flag,"Modify circle");
			Circle cr=(Circle)element;
			ValuePicker x1=new ValuePicker("X: ", cr.getCenter().getX());
			ValuePicker y1=new ValuePicker("Y: ", cr.getCenter().getY());
			ValuePicker radius=new ValuePicker("Width: ",cr.getRadius()); 
			ColorPicker border=new ColorPicker("BorderColor: ", cr.getColor());
			ColorPicker surface=new ColorPicker("Surface color: ", cr.getSurfaceColor());
			dialog.add(new PickerLabel("Center point:"));
			dialog.add(x1);
			dialog.add(y1);
			dialog.add(radius);
			dialog.add(border);
			dialog.add(surface);
			dialog.start();
			do {
				if(!flag.get())
					return;
				if(radius.getValue()!=0)
					break;
				JOptionPane.showMessageDialog(dialog, "Circle radius must have length", "Warning",JOptionPane.WARNING_MESSAGE);
				flag.set(false);
				dialog.restart();
			}while(radius.getValue()==0);
			Circle newCircle=new Circle(new Point(x1.getValue(), y1.getValue()),radius.getValue(), border.getColor(), surface.getColor(),-1);
			c=new ModifyShape(element,newCircle);
		}else if(element instanceof HexagonAdapter) {
			dialog=new PickerDialog(flag,"Modify hexagon");
			HexagonAdapter h=(HexagonAdapter)element;
			ValuePicker x1=new ValuePicker("X: ", h.getCenter().getX());
			ValuePicker y1=new ValuePicker("Y: ", h.getCenter().getY());
			ValuePicker radius=new ValuePicker("Side: ",h.getRadius());
			ColorPicker border=new ColorPicker("BorderColor: ", h.getColor());
			ColorPicker surface=new ColorPicker("Surface color: ", h.getSurfaceColor());
			dialog.add(new PickerLabel("Center point:"));
			dialog.add(x1);
			dialog.add(y1);
			dialog.add(radius);
			dialog.add(border);
			dialog.add(surface);
			dialog.start();
			do {
				if(!flag.get())
					return;
				if(radius.getValue()!=0)
					break;
				JOptionPane.showMessageDialog(dialog, "Hexagon side must have length", "Warning",JOptionPane.WARNING_MESSAGE);
				flag.set(false);
				dialog.restart();
			}while(radius.getValue()==0);
			HexagonAdapter newHex=new HexagonAdapter(x1.getValue(), y1.getValue(), radius.getValue(), border.getColor(), surface.getColor(),-1);
			c=new ModifyShape(element, newHex);
		}
		if(c!=null) {
			log.append(undoRedo.doCommand(c));
		}
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void delete() {
		lineFlag=false;
		List<Shape> removeList=new ArrayList<>();
		for(int i=model.size()-1;i>=0;i--) {
			if(model.get(i).isSelected()) {
				removeList.add(model.get(i));
			}
		}
		log.append(undoRedo.doCommand(new RemoveShape(removeList, model)));
		select.notifySelectObserver();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void borderColor() {
		lineFlag=false;
		JColorChooser colorChooser = new JColorChooser();
		Color color=colorChooser.showDialog(frame, "Boja za ivicu elementa", Color.BLACK);
		if(color!=null) {
			border=color;
			frame.setBorderColor(border);
		}
	}
	
	public void surfaceColor() {
		lineFlag=false;
		JColorChooser colorChooser = new JColorChooser();
		Color color=colorChooser.showDialog(frame, "Boja za povrsinu elementa", Color.WHITE);
		if(color!=null) {
			surface=color;
			frame.setSurfaceColor(surface);
		}		
	}

	@Override
	public void updateUndoRedo(boolean undo, boolean redo) {
		frame.enableUndo(undo);
		frame.enableRedo(redo);
	}

	public void mode(Mode mode) {
		this.mode = mode;
		lineFlag=false;
		view.repaint();
	}

	@Override
	public void updateSelect(int n) {
		if(n==0) {
			frame.enableModify(false);
			frame.enableDelete(false);
			frame.disableMoveToButtons();
		}else if(n==1) {
			frame.enableModify(true);
			frame.enableDelete(true);
			adjustMoveToButtons();
		}else if(n>1) {
			frame.enableModify(false);
			frame.enableDelete(true);
			frame.disableMoveToButtons();
		}
	}
	
	
	public void forward() {
		lineFlag=false;
		log.append(undoRedo.doCommand(new ForwardCommand(model, select.getSelected().get(0))));
		adjustMoveToButtons();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void backward() {
		lineFlag=false;
		log.append(undoRedo.doCommand(new BackwardCommand(model, select.getSelected().get(0))));
		adjustMoveToButtons();		
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void bringToFront() {
		lineFlag=false;
		log.append(undoRedo.doCommand(new BringToFrontCommand(model, select.getSelected().get(0))));
		adjustMoveToButtons();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void sendToBack() {
		lineFlag=false;
		log.append(undoRedo.doCommand(new SendToBackCommand(model, select.getSelected().get(0))));
		adjustMoveToButtons();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void adjustMoveToButtons() {
		frame.disableMoveToButtons();
		if(select.getSelected().size()==0||model.size()==1)
			return;
		if(model.indexOf(select.getSelected().get(0))==0) {
			frame.enableMoveForwardButtons();
		}else if(model.indexOf(select.getSelected().get(0))==model.size()-1) {
			frame.enableMoveBackwardButtons();
		}else {
			frame.enableMoveBackwardButtons();
			frame.enableMoveForwardButtons();
		}
	}
	
	
	public void save(){
		lineFlag=false;
		JFileChooser chooser = new JFileChooser();
	    chooser.setSelectedFile(new File("drawing1"));
	    chooser.setFileFilter(new FileNameExtensionFilter(null,"rko","rlg"));
	    int retVal = chooser.showSaveDialog(frame);
	    if (retVal == JFileChooser.APPROVE_OPTION) {
	    	String filelocation=chooser.getCurrentDirectory().toString()+"\\"+chooser.getSelectedFile().getName();
	    	if(filelocation.substring(filelocation.length()-4, filelocation.length()).equals(".rko")) {
	    		filelocation=filelocation.substring(0, filelocation.length()-4);
	    	}else if(filelocation.substring(filelocation.length()-4, filelocation.length()).equals(".rlg")) {
	    		filelocation=filelocation.substring(0, filelocation.length()-4);
	    	}
	    	boolean logCheck=saveLoad.saveLog(filelocation+".rlg", log.toString());
	    	boolean modelCheck=saveLoad.saveModel(filelocation+".rko", model);
	    	if(logCheck)
	    		log.append("Log saved to "+filelocation+".rlg\n");
	    	if(modelCheck) 
	    		log.append("Drawing saved to "+filelocation+".rko\n");
	    	
	    }else if (retVal == JFileChooser.CANCEL_OPTION) {
	    	return;
	    }
	    view.repaint();
    	frame.setLogText(log.toString());
	}
	
	public void load() {
		lineFlag=false;
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter(null,"rko","rlg"));
	    int retVal = chooser.showOpenDialog(frame);
	    if (retVal == JFileChooser.APPROVE_OPTION) {
	    	String filelocation=chooser.getCurrentDirectory().toString()+"\\"+chooser.getSelectedFile().getName();
	    	if(filelocation.substring(filelocation.length()-4).equals(".rko")){
	    		if(saveLoad.loadModel(filelocation)) {
	    			model.setElements(saveLoad.getLoadedModel().getElements());
	    			saveLoad.modelDone();
	    			undoRedo.getDone().clear();
	    			undoRedo.getUndone().clear();
	    			select.notifySelectObserver();
	    			undoRedo.notifyUndoRedoObserver();
	    		}
	    	}else if(filelocation.substring(filelocation.length()-4).equals(".rlg")) {
	    		if(saveLoad.loadLog(filelocation)) {
	    			List<String> logList=saveLoad.getLoadedLog();
	    			model.clear();
	    			saveLoad.logDone();
	    			undoRedo.getDone().clear();
	    			undoRedo.getUndone().clear();
	    			parser=new Parser(model,logList);
	    			id=parser.getNextId();
	    			log=new StringBuilder();
	    			frame.showLoadButtons();
	    			loadFlag=true;
	    			loadStep=0;
	    			frame.adjustLoadButtons(true);
	    			frame.adjustUnloadButtons(false);
	    		}
	    	}
		}else if (retVal == JFileChooser.CANCEL_OPTION) {
	    	return;
	    }
	    view.repaint();
    	frame.setLogText(log.toString());
	}
	
	public void loadDone() {
		parser=null;
		loadFlag=false;
		loadStep=0;
    	undoRedo.notifyUndoRedoObserver();
    	select.notifySelectObserver();
		frame.hideLoadButtons();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void loadNone() {
		while(parser.hasPrevious()) {
			parser.previous();
		}
		loadStep=0;
		undoRedo.getDone().clear();
		undoRedo.getUndone().clear();
		model.clear();
		log=new StringBuilder();
		adjustLoadPanel();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void loadPrevious() {
		if(parser.hasPrevious()) {
			while(parser.hasPrevious()) {
				parser.previous();
			}
			undoRedo.getDone().clear();
			undoRedo.getUndone().clear();
			model.clear();
			log=new StringBuilder();
			for(int i=0;i<loadStep-1;i++)
				if(parser.hasNext()) {
					parser.next();
					if(parser.getMode().equals("DONE")) {
						log.append(undoRedo.doCommand(parser.getCommand()));
					}else if(parser.getMode().equals("UNDONE")) {
						log.append(undoRedo.undoCommand());
					}else if(parser.getMode().equals("REDONE")) {
						log.append(undoRedo.redoCommand());
					}
				}
			loadStep--;
		}
		adjustLoadPanel();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void loadNext() {
		if(parser.hasNext()) {
			parser.next();
			loadStep++;
			if(parser.getMode().equals("DONE")) {
				log.append(undoRedo.doCommand(parser.getCommand()));
			}else if(parser.getMode().equals("UNDONE")) {
				log.append(undoRedo.undoCommand());
			}else if(parser.getMode().equals("REDONE")) {
				log.append(undoRedo.redoCommand());
			}
		}
		adjustLoadPanel();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	
	public void loadAll() {
		while(parser.hasNext()) {
			parser.next();
			loadStep++;
			if(parser.getMode().equals("DONE")) {
				log.append(undoRedo.doCommand(parser.getCommand()));
			}else if(parser.getMode().equals("UNDONE")) {
				log.append(undoRedo.undoCommand());
			}else if(parser.getMode().equals("REDONE")) {
				log.append(undoRedo.redoCommand());
			}
		}
		adjustLoadPanel();
		view.repaint();
		frame.setLogText(log.toString());
	}
	
	public void adjustLoadPanel() {
		frame.adjustLoadButtons(parser.hasNext());
		frame.adjustUnloadButtons(parser.hasPrevious());
	}

	
	public void select() {
		mode(Mode.SELECT);
	}
	
	public void point() {
		mode(Mode.POINT);
	}	
	public void line() {
		mode(Mode.LINE);
	}
	
	public void square() {
		mode(Mode.SQUARE);
	}
	
	public void rectangle() {
		mode(Mode.RECTANGLE);
	}
	
	public void circle() {
		mode(Mode.CIRCLE);
	}
	
	public void hexagon() {
		mode(Mode.HEXAGON);
	}
}
