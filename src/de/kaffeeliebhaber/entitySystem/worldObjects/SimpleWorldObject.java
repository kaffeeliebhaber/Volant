package de.kaffeeliebhaber.entitySystem.worldObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.debug.Debug;
import de.kaffeeliebhaber.entitySystem.Entity;

public class SimpleWorldObject extends Entity {

	private BufferedImage[] images;
	
	public SimpleWorldObject(float x, float y, int width, int height, BufferedImage[] images, BoundingBox boundingBox) {
		super(x, y, width, height);
		setBoundingBox(boundingBox);
		this.images = images;
	}
	
	public SimpleWorldObject(float x, float y, int width, int height, BufferedImage image, BoundingBox boundingBox) {
		this(x, y, width, height, new BufferedImage[] {image}, boundingBox);
	}

	@Override
	public boolean intersects(Entity entity) {
		return false;
	}

	// TODO: Kann diese Methode nicht auch direkt in der 'Entity' Class verschoeben werden.
	@Override
	public boolean intersects(BoundingBox entityBoundingBox) {
		return boundingBox != null ? boundingBox.intersects(entityBoundingBox) : false;
	}

	public void update(float timeSinceLastFrame, List<Entity> entities) {}

	@Override
	public void render(Graphics g, Camera camera) {

		for (int i = 0; i < images.length; i++) {
			g.drawImage(images[i], (int) (x - camera.getX()), (int) (y + (height / images.length) * i - camera.getY()), null);
			
			//System.out.println("(SimpleWorldObject) render | rendering...");
		}
		
		if (Debug.WORLDOBJECTS_RENDER_SHOW_BOUNDINGBOX) {
			boundingBox.render(g, camera);
		} 
	}

}
