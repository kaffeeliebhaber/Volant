package de.kaffeeliebhaber.xml.tiledEditor.BoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TiledBoundingBoxManager {

	private List<TiledBoundingBox> tiledBoundingBoxes;
	
	public TiledBoundingBoxManager() {
		tiledBoundingBoxes = new ArrayList<TiledBoundingBox>();
	}
	
	public void addBoundingBox(final int tileID, float x, float y, int width, int height) {
		
		System.out.println("(TiledBoundingBoxManager.addBoundingBox) | TileID: " + tileID);
		final long count = tiledBoundingBoxes.stream().filter(b -> b.getTileID() == tileID).count();
		
		if (count > 0) {
			tiledBoundingBoxes.stream().filter(b -> b.getTileID() == tileID).findFirst().get().addBoundingBox(x, y, width, height);
		} else {
			TiledBoundingBox tileBoundingBox = new TiledBoundingBox(tileID);
			tileBoundingBox.addBoundingBox(x, y, width, height);
			tiledBoundingBoxes.add(tileBoundingBox);
		}
	}
	
	public TiledBoundingBox getTiledBoundingBox(final int tileID) {
		TiledBoundingBox tiledBoundingBoxOrig = tiledBoundingBoxes.stream().filter(b -> b.getTileID() == tileID).findFirst().get();
		TiledBoundingBox tiledBoundingBoxCopy = tiledBoundingBoxOrig.clone();
		tiledBoundingBoxOrig.getBoundingBoxes().stream().forEach(b -> tiledBoundingBoxCopy.addBoundingBox(b.createNew()));
		return tiledBoundingBoxCopy;
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
