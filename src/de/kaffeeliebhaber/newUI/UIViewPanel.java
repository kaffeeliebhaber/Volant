package de.kaffeeliebhaber.newUI;

import java.awt.Color;
import java.awt.Graphics;

public class UIViewPanel extends UIView {

	public UIViewPanel(float x, float y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(Graphics g) {

		// DRAW: BACKGROUND
		renderBackground(g);
		
		// DRAW: BORDER
		renderBorder(g);
	}
	
	private void renderBackground(final Graphics g) {
		
		g.setColor(new Color(0, 0, 0, 128));
		g.fillRect((int) x, (int) y, width, height);
		
	}
	
	private void renderBorder(final Graphics g) {
		g.setColor(new Color(255, 255, 255, 128));
		g.drawRect((int) x, (int) y, width, height);
	}

}
