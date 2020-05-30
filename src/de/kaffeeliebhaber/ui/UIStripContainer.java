package de.kaffeeliebhaber.ui;

import java.awt.Color;
import java.awt.Graphics;

import de.kaffeeliebhaber.ui.inventory.UIElement;

/**
 * Diese Klass dient zur Darstellung eines konfigurierten Rechtecks.
 * @author User
 *
 */
public class UIStripContainer extends UIElement {

	private static final Color COLOR_BACKGROUND_GRAY_LIGHT = new Color(80, 80, 80);
//	private static final Color COLOR_BACKGROUND_BLACK_TRANSPARENT_128 = new Color(0, 0, 0, 128);
	
	public UIStripContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void render(Graphics g) {
		g.setColor(COLOR_BACKGROUND_GRAY_LIGHT);
		g.fillRect((int) x, (int) y, width, height);
		
	}
	
	@Override protected void calcDimension() {}

}
