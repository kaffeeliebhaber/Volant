package de.kaffeeliebhaber.entitySystem.worldObjects;

import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.collision.BoundingBox;

public class SimpleBush extends SimpleWorldObject {

	private static final int BOUNDINGBOX_HEIGHT = 10;
	
	public SimpleBush(float x, float y, int width, int height, BufferedImage image) {
		super(x, y, width, height, image, new BoundingBox((int) x, (int) (y + height - BOUNDINGBOX_HEIGHT), width, BOUNDINGBOX_HEIGHT));
	}

}
