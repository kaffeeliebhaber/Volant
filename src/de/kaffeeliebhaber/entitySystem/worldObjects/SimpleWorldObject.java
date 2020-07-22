package de.kaffeeliebhaber.entitySystem.worldObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.debug.Debug;
import de.kaffeeliebhaber.entitySystem.Entity;

public class SimpleWorldObject extends Entity {

	private BufferedImage[] images;
	
	/*
	public SimpleWorldObject(float x, float y, int width, int height, BufferedImage[] images, BoundingBox boundingBox) {
		super(x, y, width, height);
		addBoundingBox(boundingBox);
		this.images = images;
	}
	
	public SimpleWorldObject(float x, float y, int width, int height, BufferedImage image, BoundingBox boundingBox) {
		this(x, y, width, height, new BufferedImage[] {image}, boundingBox);
	}
	*/
	
	public SimpleWorldObject(float x, float y, int width, int height, BufferedImage image) {
		this(x, y, width, height, new BufferedImage[] {image});

	}
	
	public SimpleWorldObject(float x, float y, int width, int height, BufferedImage[] images) {
		super(x, y, width, height);
		this.images = images;
	}

	public void update(float timeSinceLastFrame, List<Entity> entities) {}

	@Override
	public void render(Graphics g, Camera camera) {

		for (int i = 0; i < images.length; i++) {
			g.drawImage(images[i], (int) (x - camera.getX()), (int) (y + (height / images.length) * i - camera.getY()), null);
		}
		
		if (Debug.WORLDOBJECTS_RENDER_SHOW_BOUNDINGBOX) {
			boundingBoxController.render(g, camera);
		} 
	}

}
