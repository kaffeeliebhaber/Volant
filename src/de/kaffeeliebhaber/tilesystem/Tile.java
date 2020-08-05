package de.kaffeeliebhaber.tilesystem;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.entitySystem.Entity;

public class Tile extends Entity {

	private BufferedImage image;
	private boolean blocked;
	private final int ID;
	//private BoundingBoxController boundingBoxController;
	
	private static final int SCALE = 2;
	
	public Tile(final int ID, float x, float y, int width, int height, BufferedImage image) {
		this(ID, x, y, width, height, image, false);
	}
	
	public Tile(final int ID, float x, float y, int width, int height, BufferedImage image, final boolean blocked) {
		super(x, y , width, height);
		this.ID = ID;
		this.image = image;
		this.blocked = blocked;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public boolean isBlocked() {
		return blocked;
	}
	
	public boolean isEmpty() {
		return ID < 0;
	}
	
	public void update(final KeyManager keyManager, float timeSinceLastFrame, List<Entity> entities) {}
	
	public String toString() {
		return "(Tile) " + super.toString();
	}
	
	public void render(Graphics g, Camera camera) {
		if (image != null) {
			g.drawImage(image, (int) (x - camera.getX()), (int) (y - camera.getY()), width, height, null);
		} 
		
//		if (blocked) {
//			g.setColor(java.awt.Color.GREEN);
//			g.drawRect((int) (x - camera.getX()), (int) (y - camera.getY()), width, height);
//		}
	}
}
