package de.kaffeeliebhaber.newUI;

import java.awt.Graphics;
import java.awt.Point;

public abstract class UIView extends UIElement {

	protected boolean hover;
	
	public UIView(float x, float y) {
		super(x, y);
	}
	
	public UIView(float x, float y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void clicked(int buttonID, Point p) {}
	
	public abstract void render(final Graphics g);
	
	public void mouseMoved(final Point p) {
		hover = contains(p);
	}
	
}
