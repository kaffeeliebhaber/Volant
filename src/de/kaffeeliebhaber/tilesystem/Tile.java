package de.kaffeeliebhaber.tilesystem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.debug.Debug;
import de.kaffeeliebhaber.object.BoundingBox;
import de.kaffeeliebhaber.object.Entity;

public class Tile extends Entity {

	private BufferedImage image;
	private boolean blocked;
	private int id;
	
	public Tile(float x, float y, int width, int height) {
		super(x, y , width, height);
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
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
	
	@Override
	public void update(float timeSinceLastFrame) {
		
	}
	
	@Override
	public String toString() {
		return "(Tile) " + super.toString();
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		if (image != null) {
			g.drawImage(image, (int) (x - camera.getX()), (int) (y - camera.getY()), width, height, null);
		} 
		
		if (Debug.TILE_RENDER_SHOW_BOUNDINGBOX) {
			
			final BoundingBox boundingBox = getBoundingBox();
			
			if (boundingBox != null) {
				g.setColor(new Color(0, 255, 0, 100));
				g.fillRect((int) (boundingBox.getX() - camera.getX()), (int) (boundingBox.getY() - camera.getY()), boundingBox.getWidth(), boundingBox.getHeight());
			}
		}
	}
	
	
	@Override
	public BoundingBox getBoundingBox() {
		BoundingBox localBoundingBox = null;
		
		/*
		
		TODO: Add this code again.
		if (boundingBox != null) {
			localBoundingBox = new BoundingBox(
					(int) (this.x + boundingBox.getX()),
					(int) (this.y + boundingBox.getY()),
					(int) (boundingBox.getWidth()),
					(int) (boundingBox.getHeight())
					);
		} else {
			localBoundingBox = super.getBoundingBox();
		}
		*/
		
		
		
		return localBoundingBox;
	}
}
