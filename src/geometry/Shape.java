package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Serializable{
	private boolean selected;
	private Color color;
	private int id;
	public Shape() {}
	
	public Shape(Color color,int id) {
		this.color = color;
		this.id=id;
	}

	public abstract void paint(Graphics g);
	public abstract void selected(Graphics g);
	public abstract boolean contains(int x, int y);
	public abstract Shape clone();
	public abstract void adjust(Shape shape);
	public abstract String details();
	public abstract String typeAndId();
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
