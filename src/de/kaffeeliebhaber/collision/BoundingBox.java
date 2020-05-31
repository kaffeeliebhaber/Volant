package de.kaffeeliebhaber.collision;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.GameObject;

public class BoundingBox extends GameObject {

	public BoundingBox(float x, float y, int width, int height) {
		super(x, y, width, height);
	}
	
	public BoundingBox translate(final float dx, final float dy) {
		x += dx;
		y += dy;
		return this;
	}
	
	public BoundingBox translateX(final float dx) {
		translate(dx, 0);
		return this;
	}
	
	public BoundingBox translateY(final float dy) {
		translate(0, dy);
		return this;
	}

	private Rectangle createRectangle() {
		return new Rectangle((int) x, (int) y, width, height);
	}
	
	public boolean intersects(final BoundingBox boundingBox) {
		return boundingBox != null ? createRectangle().intersects(boundingBox.createRectangle()) : false;
	}
	
	public void render(final Graphics g, final Camera camera ) {
		g.setColor(new Color(255, 0, 0, 255));
		g.drawRect((int) (x - camera.getX()), (int) (y - camera.getY()), width, height);
		
//		System.out.println(this);
	}
	
	public static BoundingBox createTranslatedBoundingBox(final BoundingBox boundingBox, final float dx, final float dy) {
		return new BoundingBox(boundingBox.getX() + dx, boundingBox.getY() + dy, boundingBox.getWidth(), boundingBox.getHeight());
	}
	
	@Override
	public String toString() {
		return "x: " + x + ", y: " + y + ", width: " + width + ", height: " + height;
	}
}
