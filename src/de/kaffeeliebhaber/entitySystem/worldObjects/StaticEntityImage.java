package de.kaffeeliebhaber.entitySystem.worldObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.core.Camera;

public class StaticEntityImage {

	private BufferedImage image;
	private float x;
	private float y;
	
	public StaticEntityImage(final BufferedImage image, final float x, final float y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	
	public void render(final Graphics g, final Camera camera) {
		g.drawImage(
				image, 
				(int) (x - camera.getX()), 
				(int) (y - camera.getY()), 
				image.getWidth(), 
				image.getHeight(), 
				null);
	}
}
