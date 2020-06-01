package de.kaffeeliebhaber.entitySystem.worldObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.debug.Debug;
import de.kaffeeliebhaber.entitySystem.Entity;

public class SimpleBush extends Entity {

	private static final int BOUNDINGBOX_HEIGHT = 10;
	private BufferedImage image;
	
	public SimpleBush(float x, float y, int width, int height, BufferedImage image) {
		super(x, y, width, height);
		this.image = image;
		
		setBoundingBox(new BoundingBox((int) x, (int) (y + height - BOUNDINGBOX_HEIGHT), width, BOUNDINGBOX_HEIGHT));
	}

	@Override
	public boolean intersects(Entity entity) {
		return false;
	}

	@Override
	public boolean intersects(BoundingBox boundingBox) {
		return this.boundingBox.intersects(boundingBox);
	}

	@Override
	public void update(float timeSinceLastFrame, List<Entity> entities) {
		
	}

	@Override
	public void render(Graphics g, Camera camera) {
		g.drawImage(image, (int) (this.x - camera.getX()), (int) (this.y - camera.getY()), null);
		
		if (Debug.WORLDOBJECTS_RENDER_SHOW_BOUNDINGBOX) {
			boundingBox.render(g, camera);
		} 
	}

}
