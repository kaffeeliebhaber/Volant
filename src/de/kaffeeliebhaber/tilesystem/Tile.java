package de.kaffeeliebhaber.tilesystem;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.debug.Debug;
import de.kaffeeliebhaber.entitySystem.Entity;

public class Tile extends Entity {

	private List<BoundingBox> boundingBoxes;
	private int boundingBoxesSize;
	private BufferedImage image;
	private boolean blocked;
	private final int ID;
	
	public Tile(final int ID, float x, float y, int width, int height, BufferedImage image) {
		super(x, y , width, height);
		this.ID = ID;
		this.image = image;
		boundingBoxes = new ArrayList<BoundingBox>();
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	public void update(float timeSinceLastFrame, List<Entity> entities) {}
	
	public String toString() {
		return "(Tile) " + super.toString();
	}
	
	public void render(Graphics g, Camera camera) {
		if (image != null) {
			g.drawImage(image, (int) (x - camera.getX()), (int) (y - camera.getY()), width, height, null);
		} 
		renderingBoundingBoxes(g, camera);
	}
	
	private void renderingBoundingBoxes(Graphics g, Camera camera) {
		if (Debug.TILE_RENDER_SHOW_BOUNDINGBOX) {
			if (boundingBoxes.size() > 0) {
				boundingBoxes.stream().forEach(b -> b.render(g, camera));
			}
		}
	}
	
	public void setBoundingBoxes(final List<BoundingBox> boundingBoxesList) {
		boundingBoxesList.stream().forEach(b -> boundingBoxes.add(b.createNew())); 
		
		boundingBoxesSize = boundingBoxes.size();
	}
	
	private boolean areBoundingBoxesAvailable() {
		return boundingBoxesSize > 0;
	}
	
	public void adjustBoundingBoxes() {
		boundingBoxes.stream().forEach(b -> b.translate(x, y));
	}

	@Override
	public boolean intersects(Entity entity) {
		
		boolean intersects = false;

		if (areBoundingBoxesAvailable()) {
			for (int i = 0; i < boundingBoxesSize && !intersects; i++) {
				intersects = entity.intersects(boundingBoxes.get(i));
			}
		}
		
		return intersects;
	}

	@Override
	public boolean intersects(BoundingBox boundingBox) {
		
		boolean intersects = false;
		
		if (areBoundingBoxesAvailable()) {
			for (int i = 0; i < boundingBoxesSize && !intersects; i++) {
				intersects = boundingBox.intersects(boundingBoxes.get(i));
			}
		}
		
		return intersects;
	}
}
