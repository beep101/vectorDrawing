package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import commands.AddShape;
import commands.BackwardCommand;
import commands.BringToFrontCommand;
import commands.Command;
import commands.DeselectShape;
import commands.ForwardCommand;
import commands.ModifyShape;
import commands.RemoveShape;
import commands.SelectShape;
import commands.SendToBackCommand;
import geometry.Circle;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.Square;
import model.Model;

public class Parser {
	private List<String> logList;
	private ListIterator<String> iterator;
	private Model model;
	private String currentString=null;
	private String currentMode=null;
	private Command currentCommand=null;
	
	public Parser(Model model,List<String> logList) {
		this.logList=new ArrayList<>();
		for(String s:logList) {
			if(s.split(" ")[0].equals("Log")||s.split(" ")[0].equals("Drawing"))
				continue;
			this.logList.add(s.replaceAll(" ", ""));
		}
		this.iterator=this.logList.listIterator();
		this.model=model;

	}
	
	public boolean hasNext() {
		return iterator.hasNext();
	}
	
	public boolean hasPrevious() {
		return iterator.hasPrevious();
	}
	
	public String next() {
		currentString=iterator.next();
		makeMode();
		makeCommand();
		return currentMode;
	}

	public String previous() {
		currentString=iterator.previous();
		makeMode();
		makeCommand();
		return currentMode;
	}
	
	private void makeMode() {
		currentMode=currentString.split(":")[0].toUpperCase();
	}
	
	private void makeCommand() {
		currentCommand=null;
		if(currentMode.equals("UNDONE")||currentMode.equals("REDONE")) {
			return;
		}
		String commandName=currentString.split(":")[1].toUpperCase();
		if(commandName.equals("SELECT")) {
			Shape shape=shapeById(idParse(currentString));
			currentCommand= new SelectShape(shape);
		}else if(commandName.equals("DESELECT")) {
			List<Shape> elements=new ArrayList<>();
			String idArr[]=currentString.split(":");
			for(int i=3;i<idArr.length;i++) {
				int shapeId=Integer.parseInt(idArr[i].split("\\]")[0]);
				for(int j=0;j<model.size();j++)
					if(model.get(j).getId()==shapeId)
						elements.add(model.get(j));
			}
			currentCommand= new 	DeselectShape(elements);
		}else if(commandName.equals("ADD")) {
			Shape shape=makeShape(currentString.split("with")[1], shapeType(currentString), idParse(currentString));
			currentCommand= new AddShape(shape, model);
		}else if(commandName.equals("REMOVE")) {
			List<Shape> elements=new ArrayList<>();
			String idArr[]=currentString.split(":");
			for(int i=3;i<idArr.length;i++) {
				int shapeId=Integer.parseInt(idArr[i].split("\\]")[0]);
				for(int j=0;j<model.size();j++)
					if(model.get(j).getId()==shapeId)
						elements.add(model.get(j));
			}
			currentCommand= new RemoveShape(elements,model);			
		}else if(commandName.equals("MODIFY")) {
			Shape mainShape=shapeById(idParse(currentString));
			Shape modified=makeShape(currentString.split("to")[1], shapeType(currentString), -1);
			currentCommand= new ModifyShape(mainShape, modified);
		}else if(commandName.equals("FORWARD")) {
			Shape shape=shapeById(idParse(currentString));
			currentCommand= new ForwardCommand(model,shape);
		}else if(commandName.equals("BACKWARD")) {
			Shape shape=shapeById(idParse(currentString));
			currentCommand= new BackwardCommand(model,shape);			
		}else if(commandName.equals("SENDTOBACK")) {
			Shape shape=shapeById(idParse(currentString));
			currentCommand= new SendToBackCommand(model,shape);				
		}else if(commandName.equals("BRINGTOFRONT")) {
			Shape shape=shapeById(idParse(currentString));
			currentCommand= new BringToFrontCommand(model,shape);			
		}
	}
	
	public Command getCommand() {
		return currentCommand;
	}
	
	public String getMode() {
		return currentMode;
	}
	
	private Point makePoint(String details,int id) {
		String coordinates=details.split("\\(")[1].split("\\)")[0];
		String color=details.split("\\(")[2].split("\\)")[0];
		Point point=new Point(coordinatesParse(coordinates).getX(), coordinatesParse(coordinates).getY(),colorParse(color) , id);
		return point;
	}
	
	private Line makeLine(String details, int id) {
		String start=details.split("\\(")[1].split("\\)")[0];
		String end=details.split("\\(")[2].split("\\)")[0];
		String color=details.split("\\(")[3].split("\\)")[0];
		Line line=new Line(coordinatesParse(start), coordinatesParse(end), colorParse(color), id);
		return line;
	}
	
	private Square makeSquare(String details,int id) {
		String upperLeft=details.split("\\(")[1].split("\\)")[0];
		String color=details.split("\\(")[2].split("\\)")[0];
		String surface=details.split("\\(")[3].split("\\)")[0];
		String width=details.split(":")[2].split(",")[0];
		Square square=new Square(coordinatesParse(upperLeft),Integer.parseInt(width),colorParse(color),colorParse(surface),id);
		return square;
	}
	
	private Rectangle makeRect(String details,int id) {
		String upperLeft=details.split("\\(")[1].split("\\)")[0];
		String color=details.split("\\(")[2].split("\\)")[0];
		String surface=details.split("\\(")[3].split("\\)")[0];
		String width=details.split(":")[2].split(",")[0];
		String height=details.split(":")[3].split(",")[0];
		Rectangle rect=new Rectangle(coordinatesParse(upperLeft),Integer.parseInt(height),Integer.parseInt(width),colorParse(color),colorParse(surface),id);
		return rect;		
	}
	
	private Circle makeCircle(String details, int id) {
		String center=details.split("\\(")[1].split("\\)")[0];
		String color=details.split("\\(")[2].split("\\)")[0];
		String surface=details.split("\\(")[3].split("\\)")[0];
		String radius=details.split(":")[2].split(",")[0];
		Circle circle=new Circle(coordinatesParse(center), Integer.parseInt(radius), colorParse(color), colorParse(surface), id);
		return circle;
	}
	
	private HexagonAdapter makeHex(String details, int id) {
			String center=details.split("\\(")[1].split("\\)")[0];
			String color=details.split("\\(")[2].split("\\)")[0];
			String surface=details.split("\\(")[3].split("\\)")[0];
			String side=details.split(":")[2].split(",")[0];
			HexagonAdapter hex=new HexagonAdapter(coordinatesParse(center).getX(),coordinatesParse(center).getY(), Integer.parseInt(side), colorParse(color), colorParse(surface), id);
			return hex;
	}
	
	private Point coordinatesParse(String coordinates) {
		return new Point(Integer.parseInt(coordinates.split(",")[0]),Integer.parseInt(coordinates.split(",")[1]));
	}
	
	private Color colorParse(String color) {
		return new Color(Integer.parseInt(color.split(",")[0]),
				Integer.parseInt(color.split(",")[1]),
				Integer.parseInt(color.split(",")[2]));
	}
	
	private int idParse(String fullLine) {
		String idString=fullLine.split(":")[3].split("\\]")[0];
		return Integer.parseInt(idString);
	}
	
	private Shape shapeById(int id) {
		Shape shape=null;
		for(int i=0;i<model.size();i++)
			if(model.get(i).getId()==id)
				shape=model.get(i);
		return shape;
	}
	
	private String shapeType(String fullLine) {
		return fullLine.split(":")[2].split("\\[")[0].toUpperCase();
	}
	
	private Shape makeShape(String details,String shapeType,int id) {
		Shape shape=null;
		if(shapeType.equals("POINT")) {
			shape=makePoint(details, id);
		}else if(shapeType.equals("LINE")) {
			shape=makeLine(details,id);
		}else if(shapeType.equals("RECTANGLE")) {
			shape=makeRect(details, id);
		}else if(shapeType.equals("SQUARE")) {
			shape=makeSquare(details, id);
		}else if(shapeType.equals("CIRCLE")) {
			shape=makeCircle(details, id);
		}else if(shapeType.equals("HEXAGON")) {
			shape=makeHex(details, id);
		}
		return shape;
	}
	
	public int getNextId() {
		int nextId=-1;
		for(String line:logList)
			if(idParse(line)>nextId)
				nextId=idParse(line);
		nextId++;
		return nextId;
	}
}
