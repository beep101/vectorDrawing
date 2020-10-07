package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape{
	private int x;
	private int y;

	public Point() {

	}
	public Point(int x, int y) {
		this.x = x;
		setY(y);
	}
	
	public Point(int x, int y, Color color, int id) {
		this(x, y);
		setColor(color);
		setId(id);
	}

	public double distance(Point t) {
		int dX = x - t.x;
		int dY = y - t.y;
		double d = Math.sqrt(dX*dX + dY*dY);
		return d;
	}

	public void paint(Graphics g) {
		if(isSelected())
			selected(g);
		g.setColor(getColor());
		g.drawLine(x-2, y, x+2, y);
		g.drawLine(x, y-2, x, y+2);
	}
	
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		g.drawRect(x-3, y-3, 6, 6);
	}
	
	public boolean contains(int x, int y) {
		if(new Point(x,y).distance(this) < 2 )
			return true;
		return false;
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public Shape clone() {
		Point clone=new Point();
		clone.setX(getX());
		clone.setY(getY());
		clone.setColor(getColor());
		return clone;
	}
	@Override
	public void adjust(Shape shape) {
		if(!(shape instanceof Point))
			return;
		Point point=(Point)shape;
		setX(point.getX());
		setY(point.getY());
		setColor(point.getColor());
		
	}
	
	public String details() {
		StringBuilder details=new StringBuilder();
		details.append("[ ");
		details.append("Location: ("+getX()+", "+getY()+"), ");
		details.append("Border: RGB("+getColor().getRed()+", "+getColor().getGreen()+", "+getColor().getBlue()+") ");
		details.append("]");
		return details.toString();
	}
	@Override
	public String typeAndId() {
		return "Point[ ID: "+getId()+" ]";
	}
}
