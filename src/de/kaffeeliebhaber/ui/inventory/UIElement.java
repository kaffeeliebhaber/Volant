package de.kaffeeliebhaber.ui.inventory;

import java.awt.Point;
import java.awt.Rectangle;

public abstract class UIElement {

	// position
	protected float x;
	protected float y;
	
	// dimension
	protected int width;
	protected int height;
	
	// visibility
	protected boolean visible;
	
	public UIElement(int x, int y) {
		
		this.x = x;
		this.y = y;
		
		calcDimension();
	}
	
	public UIElement(float x, float y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	protected abstract void calcDimension();
	
	public void toggleVisibility() {
		visible = !visible;
	}
	
	public boolean contains(final Point p) {
		return new Rectangle((int) x, (int) y, width, height).contains(p);
	}
	
	public void setX(final float x) {
		this.x = x;
	}
	
	public void setY(final float y) {
		this.y = y;
	}
	
	public void setWidth(final int width) {
		this.width = width;
	}
	
	public void setHeight(final int height) {
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isVisible() {
		return visible;
	}
	
}
