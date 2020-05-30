package de.kaffeeliebhaber.ui;

import java.awt.Color;
import java.awt.Graphics;

import de.kaffeeliebhaber.ui.inventory.UIElement;

public class UILine extends UIElement {

	private static final Color LINE_COLOR_GRAY = new Color(209, 209, 209);
	private float startX;
	
	public UILine(float x, float y, int width, int height) {
		super(x, y, width, height);
		this.startX = x;
	}

	public void render(Graphics g) {
		g.setColor(LINE_COLOR_GRAY);
		g.drawLine((int) startX, (int) y, (int) x, (int) y);
	}
	
	@Override
	protected void calcDimension() {}

}
