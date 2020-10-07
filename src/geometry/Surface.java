package geometry;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Surface extends Shape{

	private Color surfaceColor;
	
	public Color getSurfaceColor() {
		return surfaceColor;
	}
	public void setSurfaceColor(Color color) {
		this.surfaceColor = color;
	}
	
	public abstract void fill(Graphics g);

}
