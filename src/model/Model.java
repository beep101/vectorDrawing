package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import geometry.Shape;

public class Model implements Serializable{
	private List<Shape> elements;
	
	public Model() {
		elements=new ArrayList<>();
	}

	public List<Shape> getElements() {
		return elements;
	}

	public void setElements(List<Shape> elements) {
		this.elements = elements;
	}
	
	public void add(Shape element) {
		this.elements.add(element);
	}
	
	public void add(int index, Shape element) {
		this.elements.add(index, element);
	}
	
	public void set(int index,Shape shape) {
		this.elements.set(index, shape);
	}
	
	public void remove(Shape shape) {
		elements.remove(shape);
	}
	
	public int indexOf(Shape shape) {
		return elements.indexOf(shape);
	}
	
	public Shape get(int index) {
		return elements.get(index);
	}
	
	public void clear() {
		elements.clear();
	}
	
	public int size() {
		return elements.size();
	}
}
