package de.kaffeeliebhaber.xml.tiledEditor.BoundingBox;

import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;

public class TiledBoundingBox {

	private int tileID;
	private final List<BoundingBox> boundingBoxes;
	
	public TiledBoundingBox(final int tileID) {
		setTileID(tileID);
		boundingBoxes = new ArrayList<BoundingBox>();
	}
	
	public void setTileID(final int tileID) {
		this.tileID = tileID;
	}
	
	public void addBoundingBox(float x, float y, int width, int height) {
		addBoundingBox(new BoundingBox(x, y, width, height));
	}
	
	public void addBoundingBox(BoundingBox boundingBox) {
		boundingBoxes.add(boundingBox.createNew());
	}
	
	public int getTileID() {
		return tileID;
	}
	
	public List<BoundingBox> getBoundingBoxes() {
		return new ArrayList<BoundingBox>(boundingBoxes);
	}
	
	@Override
	public String toString() {
		String returnValue = "TileID: " + tileID + "\n";
		
		for (BoundingBox boundingBox : boundingBoxes) {
			returnValue += boundingBox.toString();
		}
		
		return returnValue;
	}
	
	@Override
	public TiledBoundingBox clone() {
		
		TiledBoundingBox tiledBoundingBox = new TiledBoundingBox(this.getTileID());
		
		
		
		return tiledBoundingBox;
	}
}
