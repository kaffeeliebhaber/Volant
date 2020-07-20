package de.kaffeeliebhaber.xml.tiledEditor.BoundingBox;

import java.util.ArrayList;
import java.util.List;

public class TiledBoundingBoxManager {

	private List<TiledBoundingBox> tiledBoundingBoxes;
	
	public TiledBoundingBoxManager() {
		tiledBoundingBoxes = new ArrayList<TiledBoundingBox>();
	}
	
	public void addBoundingBox(final int tileID, float x, float y, int width, int height) {
		
		final long count = tiledBoundingBoxes.stream().filter(b -> b.getTileID() == tileID).count();
		
		if (count > 0) {
			tiledBoundingBoxes.stream().filter(b -> b.getTileID() == tileID).findFirst().get().addBoundingBox(x, y, width, height);
		} else {
			TiledBoundingBox tileBoundingBox = new TiledBoundingBox(tileID);
			tileBoundingBox.addBoundingBox(x, y, width, height);
			tiledBoundingBoxes.add(tileBoundingBox);
		}

	}
	
	@Override
	public String toString() {
		String returnValue = "";
		
		for (TiledBoundingBox tiledBoundingBox : tiledBoundingBoxes) {
			returnValue += tiledBoundingBox.toString();
		}
		
		return returnValue;
	}
}
