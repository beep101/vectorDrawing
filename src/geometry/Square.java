package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Square extends Surface{
	protected Point upperLeft;
	protected int side;

	public Square() {

	}

	public Square(Point upperLeft, int side) {
		this.upperLeft = upperLeft;
		this.side = side;
	}

	public Square(Point upperLeft, int side, Color color,Color surface,int id) {
		this(upperLeft, side);
		setColor(color);
		setSurfaceColor(surface);
		setId(id);
	}

	public Line diagonal() {
		Point lowerRight = new Point(upperLeft.getX()+side, upperLeft.getY()+side);
		return new Line(upperLeft, lowerRight);
	}

	public void paint(Graphics g) {
		g.setColor(getColor());
		g.drawRect(upperLeft.getX(), upperLeft.getY(), side, side);
		fill(g);
		if (isSelected())
			selected(g);
	}
	
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		new Line(getUpperLeft(), new Point(getUpperLeft().getX()+side, getUpperLeft().getY())).selected(g);
		new Line(getUpperLeft(), new Point(getUpperLeft().getX(), getUpperLeft().getY()+side)).selected(g);
		new Line(new Point(getUpperLeft().getX()+side, getUpperLeft().getY()), diagonal().getEndPoint()).selected(g);
		new Line(new Point(getUpperLeft().getX(), getUpperLeft().getY()+side), diagonal().getEndPoint()).selected(g);
	}
	
	public void fill(Graphics g) {
		g.setColor(getSurfaceColor());
		g.fillRect(upperLeft.getX()+1, getUpperLeft().getY()+1, side-1, side-1);
	}

	public boolean contains(int x, int y) {
		if(this.getUpperLeft().getX()<=x 
				&& x<=(this.getUpperLeft().getX() + side)
				&& this.getUpperLeft().getY()<=y 
				&& y<=(this.getUpperLeft().getY() + side))
			return true;
		else 
			return false;
	}
	public Point getUpperLeft() {
		return upperLeft;
	}
	public void setUpperLeft(Point upperLeft) {
		this.upperLeft = upperLeft;
	}
	public int getSide() {
		return side;
	}
	public void setSide(int side) {
		this.side = side;
	}

	@Override
	public Shape clone() {
		Square clone=new Square();
		clone.setUpperLeft((Point)upperLeft.clone());
		clone.setSide(getSide());
		clone.setColor(getColor());
		clone.setSurfaceColor(getSurfaceColor());
		return clone;
	}

	@Override
	public void adjust(Shape shape) {
		if(!(shape instanceof Square))
			return;
		Square square=(Square)shape;
		setUpperLeft((Point)(square.getUpperLeft().clone()));
		setSide(square.getSide());
		setColor(square.getColor());
		setSurfaceColor(square.getSurfaceColor());
	}
	
	public String details() {
		StringBuilder details=new StringBuilder();
		details.append("[ ");
		details.append("Upper Left End: ("+getUpperLeft().getX()+", "+getUpperLeft().getY()+"), ");
		details.append("Width: "+getSide()+", ");
		details.append("Border: RGB("+getColor().getRed()+", "+getColor().getGreen()+", "+getColor().getBlue()+"), ");
		details.append("Surface: RGB("+getSurfaceColor().getRed()+", "+getSurfaceColor().getGreen()+", "+getSurfaceColor().getBlue()+") ");
		details.append("]");
		return details.toString();
	}

	@Override
	public String typeAndId() {
		return "Square[ ID: "+getId()+" ]";
	}
}