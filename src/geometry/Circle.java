package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Circle extends Surface{
	private Point center;
	private int radius;

	public Circle() {
	}

	public Circle(Point center, int radius) {
		this.center = center;
		this.radius = radius;
	}

	public Circle(Point center, int radius, Color color, Color surface,int id) {
		this.center = center;
		this.radius = radius;
		setColor(color);
		setSurfaceColor(surface);
		setId(id);
	}

	public void paint(Graphics g) {
		g.setColor(getColor());
		g.drawOval(center.getX() - radius, center.getY() - radius, 2*radius, 2*radius);
		fill(g);
		if(isSelected())
			selected(g);
	}

	public void selected(Graphics g) {
		new Line(new Point(center.getX(), center.getY()-radius), 
				new Point(center.getX(), center.getY() + radius)).selected(g);
		new Line(new Point(center.getX()-radius, center.getY()), 
				new Point(center.getX()+radius, center.getY())).selected(g);
	}
	public void fill(Graphics g) {
		g.setColor(getSurfaceColor());
		g.fillOval(center.getX()-radius+1, center.getY()-radius+1, 2*radius-2, 2*radius-2);
	}

	public boolean contains(int x, int y) {
		if(new Point(x, y).distance(getCenter()) <= radius)
			return true;
		else
			return false;
	}
	public Point getCenter() {
		return center;
	}
	public void setCenter(Point center) {
		this.center = center;
	}
	public int getRadius() {
		return radius;
	}
	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public Shape clone() {
		Circle clone=new Circle();
		clone.setCenter((Point)center.clone());
		clone.setRadius(getRadius());
		clone.setColor(getColor());
		clone.setSurfaceColor(getSurfaceColor());
		return clone;
	}

	@Override
	public void adjust(Shape shape) {
		if(!(shape instanceof Circle))
			return;
		Circle circle=(Circle)shape;
		setCenter((Point)(circle.getCenter().clone()));
		setRadius(circle.getRadius());
		setColor(circle.getColor());
		setSurfaceColor(circle.getSurfaceColor());
	}

	@Override
	public String details() {
		StringBuilder details=new StringBuilder();
		details.append("[ ");
		details.append("Center: ("+getCenter().getX()+", "+getCenter().getY()+"), ");
		details.append("Radius: "+getRadius()+", ");
		details.append("Border: RGB("+getColor().getRed()+", "+getColor().getGreen()+", "+getColor().getBlue()+"), ");
		details.append("Surface: RGB("+getSurfaceColor().getRed()+", "+getSurfaceColor().getGreen()+", "+getSurfaceColor().getBlue()+") ");
		details.append("]");
		return details.toString();
	}

	@Override
	public String typeAndId() {
		return "Circle[ ID: "+getId()+" ]";
	}
}
