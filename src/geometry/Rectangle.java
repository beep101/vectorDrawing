package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Square{
	private int width;

	public Rectangle() {

	}
	public Rectangle(Point upperLeft, int height, int width) {
		this.upperLeft = upperLeft;
		side = height;
		this.width = width;
	}
	
	public Rectangle(Point upperLeft, int height, int width, Color color,Color surface,int id) {
		this(upperLeft, height, width);
		setColor(color);
		setSurfaceColor(surface);
		setId(id);
	}

	public void paint(Graphics g) {
		g.setColor(getColor());
		g.drawRect(upperLeft.getX(), upperLeft.getY(), width, side);
		fill(g);
		if (isSelected())
			selected(g);
	}
	public void selected(Graphics g) {
		g.setColor(Color.BLUE);
		new Line(getUpperLeft(), new Point(getUpperLeft().getX()+width, getUpperLeft().getY())).selected(g);
		new Line(getUpperLeft(), new Point(getUpperLeft().getX(), getUpperLeft().getY()+side)).selected(g);
		new Line(new Point(getUpperLeft().getX()+width, getUpperLeft().getY()),new Point(getUpperLeft().getX()+width, getUpperLeft().getY()+side)).selected(g);
		new Line(new Point(getUpperLeft().getX(), getUpperLeft().getY()+side), new Point(getUpperLeft().getX()+width, getUpperLeft().getY()+side)).selected(g);
	}
	
	public boolean contains(int x, int y) {
		if(this.getUpperLeft().getX()<=x 
				&& x<=(this.getUpperLeft().getX() + width)
				&& this.getUpperLeft().getY()<=y 
				&& y<=(this.getUpperLeft().getY() + side))
			return true;
		else 
			return false;
	}
	
	public void fill(Graphics g) {
		g.setColor(getSurfaceColor());
		g.fillRect(upperLeft.getX()+1, getUpperLeft().getY()+1, width-1, side-1);
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	@Override
	public Shape clone() {
		Rectangle clone=new Rectangle();
		clone.setUpperLeft((Point)getUpperLeft().clone());
		clone.setSide(getSide());
		clone.setWidth(getWidth());
		clone.setColor(getColor());
		clone.setSurfaceColor(getSurfaceColor());
		return clone;
	}
	
	public void adjust(Shape shape) {
		if(!(shape instanceof Rectangle))
			return;
		super.adjust(shape);
		setWidth(((Rectangle)shape).getWidth());
	}
	public String details() {
		StringBuilder details=new StringBuilder();
		details.append("[ ");
		details.append("Upper Left End: ("+getUpperLeft().getX()+", "+getUpperLeft().getY()+"), ");
		details.append("Width: "+getWidth()+", ");
		details.append("Height: "+getSide()+", ");
		details.append("Border: RGB("+getColor().getRed()+", "+getColor().getGreen()+", "+getColor().getBlue()+"), ");
		details.append("Surface: RGB("+getSurfaceColor().getRed()+", "+getSurfaceColor().getGreen()+", "+getSurfaceColor().getBlue()+") ");
		details.append("]");
		return details.toString();
	}
	
	@Override
	public String typeAndId() {
		return "Rectangle[ ID: "+getId()+" ]";
	}
}
