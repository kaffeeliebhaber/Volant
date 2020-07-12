package de.kaffeeliebhaber.xml.tiledEditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.kaffeeliebhaber.collision.BoundingBox;

public class BoundingBox2DContainer {

	private final Map<Integer, List<BoundingBox>> boundingBoxes;
	
	public BoundingBox2DContainer() {
		boundingBoxes = new HashMap<Integer, List<BoundingBox>>();
	}
	
	 public void put(final int tileID, final BoundingBox boundingBox2D) {
		 getBoundingBoxList(tileID).add(boundingBox2D);
	 } 
	 
	 public List<BoundingBox> get(final int tileID) {
		 return getBoundingBoxList(tileID);
	 }
	 
	 private List<BoundingBox> getBoundingBoxList(final int tileID) {
		 List<BoundingBox> boundingBoxList = null;
		 
		 if (boundingBoxes.containsKey(tileID)) {
			 boundingBoxList = boundingBoxes.get(tileID);
		 } else {
			 boundingBoxList = new ArrayList<BoundingBox>();
		 }
		 
		 return boundingBoxList;
	 }
}
