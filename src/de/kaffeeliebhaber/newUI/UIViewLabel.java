package de.kaffeeliebhaber.newUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import de.kaffeeliebhaber.assets.AssetsLoader;

public class UIViewLabel extends UIView {

	private String label;
	private Color color;
	private Font font;
	
	public UIViewLabel(String label, float x, float y) {
		super(x, y);
		setLabel(label);
	}
	
	public UIViewLabel(String label, float x, float y, Color color) {
		super(x, y);
		setLabel(label);
		setColor(color);
	}

	public void setLabel(final String label) {
		this.label = label;
	}
	
	public void setColor(final Color color) {
		this.color = color;
	}
	
	public void setFont(final Font font) {
		this.font = font;
	}
	
	public void render(final Graphics g) {
		
		if (font != null) {
//			g.setFont(font);
			g.setFont(AssetsLoader.inventoryFont);
		}
		
		g.setColor(color);
		g.drawString(label, (int) x, (int) y); 
	}
	
}
