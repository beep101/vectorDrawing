package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Line extends Shape{
	private Point startPoint;
	private Point endPoint;
	
	public Line() {}
	public Line (Point start, Point end) {
		this.startPoint = start;
		this.endPoint = end;
	}

	
	public Line (Point start, Point end, Color color,int id) {
		this(start, end);
		setColor(color);
		setId(id);
	}

	public double length() {
		return startPoint.distance(endPoint);
	}
	
	public Point midPoint(){
		int xMid = (startPoint.getX() + endPoint.getX())/2;
		int yMid = (startPoint.getY() + endPoint.getY())/2;
		Point midPoint = new Point(xMid, yMid);
		return midPoint;
	}

	public void paint(Graphics g) {
		if(isSelected())
			selected(g);
		g.setColor(getColor());
		g.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
	}
	
	public void selected(Graphics g){
		g.setColor(Color.BLUE);
		startPoint.selected(g);
		endPoint.selected(g);
		midPoint().selected(g);
	}

	public boolean contains(int x, int y){
		Point temp = new Point(x, y);
		if((startPoint.distance(temp)+endPoint.distance(temp))-length()<=1)
			return true;
		else
			return false;
	}
	public Point getStartPoint() {
		return startPoint;
	}
	public void setStartPoint(Point start) {
		this.startPoint = start;
	}
	public Point getEndPoint() {
		return endPoint;
	}
	public void setEndPoint(Point end) {
		this.endPoint = end;
	}
		
	@Override
	public Shape clone() {
		Line clone=new Line();
		clone.setStartPoint(new Point(getStartPoint().getX(),getStartPoint().getY()));
		clone.setEndPoint(new Point(getEndPoint().getX(), getEndPoint().getY()));
		clone.setColor(getColor());
		return clone;
	}
	@Override
	public void adjust(Shape shape) {
		if(!(shape instanceof Line))
			return;
		Line line=(Line)shape;
		getStartPoint().setX(line.getStartPoint().getX());
		getStartPoint().setY(line.getStartPoint().getY());
		getEndPoint().setX(line.getEndPoint().getX());
		getEndPoint().setY(line.getEndPoint().getY());
		setColor(line.getColor());
	}
	public String details() {
		StringBuilder details=new StringBuilder();
		details.append("[ ");
		details.append("Start: ("+getStartPoint().getX()+", "+getStartPoint().getY()+"), ");
		details.append("End: ("+getEndPoint().getX()+", "+getEndPoint().getY()+"), ");
		details.append("Border: RGB("+getColor().getRed()+", "+getColor().getGreen()+", "+getColor().getBlue()+") ");
		details.append("]");
		return details.toString();
	}
	@Override
	public String typeAndId() {
		return "Line[ ID: "+getId()+" ]";
	}
	
}
