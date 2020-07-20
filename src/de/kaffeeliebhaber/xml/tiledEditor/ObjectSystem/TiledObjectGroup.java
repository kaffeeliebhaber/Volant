package de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem;

import java.util.ArrayList;
import java.util.List;

public class TiledObjectGroup {

	private final int objectGroupID;
	private final boolean single;
	private final List<TiledObject> tiledObjects;
	
	public TiledObjectGroup(final int objectGroupID, final boolean single) {
		this.objectGroupID = objectGroupID;
		this.single = single;
		this.tiledObjects = new ArrayList<TiledObject>();
	}
	
	public void addObject(final int objectID, float x, float y, int width, int height) {
		addObject(new TiledObject(objectID, x, y, width, height));
	}
	
	public void addObject(final TiledObject tiledObject) {
		tiledObjects.add(tiledObject);
	}
	
	public int getObjectGroupID() {
		return objectGroupID;
	}
	
	public boolean isSingle() {
		return single;
	}
	
	public List<TiledObject> getObjects() {
		return this.tiledObjects;
	}
	
	@Override
	public String toString() {
		
		String returnValue = "-------------------------------------\n";
		returnValue += "ObjectGroupID: " + objectGroupID + ", Single: " + single + "\n";
		
		
		for (TiledObject tiledObject : tiledObjects) {
			returnValue += tiledObject.toString();
		}
		
		//returnValue += "-------------------------------------\n";
		return returnValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + objectGroupID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TiledObjectGroup other = (TiledObjectGroup) obj;
		if (objectGroupID != other.objectGroupID)
			return false;
		return true;
	}
	
}
