package de.kaffeeliebhaber.xml.tiledEditor.BoundingBox;

import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;

public class TiledBoundingBox {

	private final int tileID;
	private final List<BoundingBox> boundingBoxes;
	
	public TiledBoundingBox(final int tileID) {
		this.tileID = tileID;
		boundingBoxes = new ArrayList<BoundingBox>();
	}
	
	public void addBoundingBox(float x, float y, int width, int height) {
		addBoundingBox(new BoundingBox(x, y, width, height));
	}
	
	public void addBoundingBox(BoundingBox boundingBox) {
		boundingBoxes.add(boundingBox);
	}
	
	public int getTileID() {
		return tileID;
	}
	
	@Override
	public String toString() {
		String returnValue = "";
		
		for (BoundingBox boundingBox : boundingBoxes) {
			returnValue += boundingBox.toString();
		}
		
		return returnValue;
	}
}
