package de.kaffeeliebhaber.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import de.kaffeeliebhaber.core.Camera;

public class BoundingBox extends GameObject {

	public BoundingBox(float x, float y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void translate(final float dx, final float dy) {
		x += dx;
		y += dy;
	}
	
	public void translateX(final float dx) {
		translate(dx, 0);
	}
	
	public void translateY(final float dy) {
		translate(0, dy);
	}

	private Rectangle createRectangle() {
		return new Rectangle((int) x, (int) y, width, height);
	}
	
	public boolean intersects(final BoundingBox boundingBox) {
		return createRectangle().intersects(boundingBox.createRectangle());
	}
	
	public void render(final Graphics g, final Camera camera ) {
		g.setColor(new Color(0, 255, 0, 128));
		g.fillRect((int) (x - camera.getX()), (int) (y - camera.getY()), width, height);
	}
	
	public static BoundingBox createTranslatedBoundingBox(final BoundingBox boundingBox, final float dx, final float dy) {
		return new BoundingBox(boundingBox.getX() + dx, boundingBox.getY() + dy, boundingBox.getWidth(), boundingBox.getHeight());
	}
}
