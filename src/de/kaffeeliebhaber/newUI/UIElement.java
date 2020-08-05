package de.kaffeeliebhaber.newUI;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class UIElement {

	protected float x;
	protected float y;
	
	protected int width;
	protected int height;
	
	protected boolean visible;
	
	public UIElement(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public UIElement(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void toggleVisibility() {
		visible = !visible;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean contains(final Point p) {
		return new Rectangle((int) x, (int) y, width, height).contains(p);
	}
	
}
