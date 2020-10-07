package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ListIterator;

import javax.swing.JPanel;

import geometry.Shape;
import model.Model;

public class View extends JPanel{
	Model model;
	public View(Model model){
		super();
		this.model=model;
		setBackground(Color.WHITE);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ListIterator<Shape> i=model.getElements().listIterator();
		while(i.hasNext()) {
			Shape el=i.next();
			el.paint(g);
		}
	}
}
