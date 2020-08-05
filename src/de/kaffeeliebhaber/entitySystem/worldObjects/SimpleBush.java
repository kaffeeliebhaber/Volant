package de.kaffeeliebhaber.entitySystem.worldObjects;

import java.awt.image.BufferedImage;


public class SimpleBush extends SimpleWorldObject {

	public SimpleBush(float x, float y, int width, int height, BufferedImage image) {
		super(x, y, width, height, image);
	}

	private static final int BOUNDINGBOX_HEIGHT = 10;
	/*
	public SimpleBush(float x, float y, int width, int height, BufferedImage image) {
		super(x, y, width, height, image, new BoundingBox((int) x, (int) (y + height - BOUNDINGBOX_HEIGHT), width, BOUNDINGBOX_HEIGHT));
		
		
	}
	
	public SimpleBush(float x, float y, int width, int height, BufferedImage image, final BoundingBox boundingBox) {
		super(x, y, width, height, image, boundingBox);
		
	}
	*/


}
