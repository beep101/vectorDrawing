package geometry;

import java.awt.Color;
import java.awt.Graphics;

import hexagon.Hexagon;

public class HexagonAdapter extends Surface{
	private Hexagon hex;
	
	public HexagonAdapter() {
		super();
		this.hex=null;
	}
	
	public HexagonAdapter(int x,int y,int r,Color color, Color surface,int id) {
		super();
		hex=new Hexagon(x, y, r);
		hex.setBorderColor(color);
		hex.setAreaColor(surface);
		setId(id);
	}

	@Override
	public Color getSurfaceColor() {
		return hex.getAreaColor();
	}
	
	@Override
	public void setSurfaceColor(Color color) {
		hex.setAreaColor(color);;
	}

	@Override
	public void fill(Graphics g) {}

	public Point getCenter() {
		Point base=new Point(hex.getX(),hex.getY());
		return base;
	}

	public void setCenter(Point t) {
		hex.setX(t.getX());
		hex.setY(t.getY());
	}

	@Override
	public void paint(Graphics g) {
		hex.paint(g);
	}

	@Override
	public void selected(Graphics g) {
		//**********************************
		//implementirano u hexagon klasi
		//**********************************
	}

	@Override
	public boolean contains(int x, int y) {
		return hex.doesContain(x, y);
	}

	@Override
	public Shape clone() {
		HexagonAdapter clone=new HexagonAdapter(hex.getX(), hex.getY(), hex.getR(), hex.getBorderColor(),hex.getAreaColor(),-1);
		clone.setSurfaceColor(hex.getAreaColor());
		return clone;
	}
	
	@Override
	public boolean isSelected() {
		return hex.isSelected();
	}
	
	@Override
	public void setSelected(boolean selected) {
		hex.setSelected(selected);
	}
	
	@Override
	public Color getColor() {
		return hex.getBorderColor();
	}
	
	@Override
	public void setColor(Color color) {
		hex.setBorderColor(color);
	}
	
	public int getRadius() {
		return hex.getR();
	}
	
	public void setRadius(int radius) {
		hex.setR(radius);
	}

	@Override
	public void adjust(Shape shape) {
		if(!(shape instanceof HexagonAdapter))
			return;
		HexagonAdapter model=(HexagonAdapter)shape;
		hex.setX(model.getCenter().getX());
		hex.setY(model.getCenter().getY());
		hex.setR(model.getRadius());
		hex.setBorderColor(model.getColor());
		hex.setAreaColor(model.getSurfaceColor());
	}

	@Override
	public String details() {
		StringBuilder details=new StringBuilder();
		details.append("[ ");
		details.append("Center: ("+getCenter().getX()+", "+getCenter().getY()+"), ");
		details.append("Side: "+getRadius()+", ");
		details.append("Border: RGB("+getColor().getRed()+", "+getColor().getGreen()+", "+getColor().getBlue()+"), ");
		details.append("Surface: RGB("+getSurfaceColor().getRed()+", "+getSurfaceColor().getGreen()+", "+getSurfaceColor().getBlue()+") ]");
		return details.toString();
	}

	@Override
	public String typeAndId() {
		return "Hexagon[ ID: "+getId()+" ]";
	}

}
