package de.kaffeeliebhaber.entitySystem.worldObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.debug.Debug;
import de.kaffeeliebhaber.entitySystem.Entity;

public class StaticEntity extends Entity {

	private final StaticEntityImageController imageController;
	
	public StaticEntity(final float x, final float y) {
		this(x, y, 0, 0);
	}
	
	public StaticEntity(float x, float y, int width, int height) {
		super(x, y, width, height);
		imageController = new StaticEntityImageController();
	}
	
	public void addBufferedImage(final BufferedImage image, final float x, final float y) {
		imageController.add(image, x, y);
	}

	@Override
	public void update(float timeSinceLastFrame, List<Entity> entities) {}

	@Override
	public void render(Graphics g, Camera camera) {
		
		imageController.render(g, camera);
		
		if (Debug.WORLDOBJECTS_RENDER_SHOW_BOUNDINGBOX) {
			boundingBoxController.render(g, camera);
		} 
		
	}
}
