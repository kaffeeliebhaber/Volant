package de.kaffeeliebhaber.xml.tiledEditor.Freeform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TiledFreeformGroupManager {

	private List<TiledFreeformObject> freeformObjects;
	private Map<Integer, List<TiledFreeformObjectProperty>> freeformObjectProperties;
	
	public TiledFreeformGroupManager() {
		freeformObjects = new ArrayList<TiledFreeformObject>();
		freeformObjectProperties = new HashMap<Integer, List<TiledFreeformObjectProperty>>();
	}
	
	public void addFreeformObject(final int ID, final String type, final float x, final float y, final int width, final int height) {
		
		TiledFreeformObject freeformObject = new TiledFreeformObject(ID, type, x, y, width, height);
		freeformObjects.add(freeformObject);
	}
	
	public void addFreeformObjectProperties(final int ID, final String property, final String value) {
		
		if (!freeformObjectProperties.containsKey(ID)) {
			freeformObjectProperties.put(ID, new ArrayList<TiledFreeformObjectProperty>());
		}
		
		freeformObjectProperties.get(ID).add(new TiledFreeformObjectProperty(property, value));
	}
	/*
	public TiledFreeformObject getFreeformObject(final int ID) {
		return freeformObjects.stream().filter(o -> o.getID() == ID).findFirst().get();
	}
	*/
	
	public List<TiledFreeformObject> getFreeformObjects() {
		return freeformObjects;
	}
	
	public List<TiledFreeformObjectProperty> getProperties(final int ID) {
		return freeformObjectProperties.get(ID);
	}
	
}
