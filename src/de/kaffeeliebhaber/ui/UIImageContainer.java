package de.kaffeeliebhaber.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.ui.inventory.UIElement;

public class UIImageContainer extends UIElement {

	private BufferedImage image;
	@SuppressWarnings("unused")
	private int indentX;
	@SuppressWarnings("unused")
	private int indentY;
	private int imageWidth;
	private int imageHeight;
	
	public UIImageContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void render(Graphics g) {
		
		g.setColor(Color.BLACK);
		g.fillRect((int) x, (int) y, width, height);
		
		if (image != null) {
			g.drawImage(image, (int) (getCenterX()), (int) (getCenterY()), imageWidth, imageHeight, null);
		} 
		
		g.setColor(new Color(255, 255, 255, 125));
		g.drawRect((int) x, (int) y, width, height);
		
	}
	
	private float getCenterX() {
		return x + (width - imageWidth) / 2;
	}
	
	private float getCenterY() {
		return y + (height - imageHeight) / 2;
	}

	public void setImage(final BufferedImage image) {
		this.image = image;
	}
	
	public void setIndent(int indentX, int indentY) {
		this.indentX = indentX;
		this.indentY = indentY;
	}
	
	public void setImageDimension(final int imageWidth, final int imageHeight) {
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}

	@Override protected void calcDimension() {}
}
