package de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem;

import java.util.ArrayList;
import java.util.List;

public class TiledObjectGroupManager {

	private List<TiledObjectGroup> objectGroups;
	
	public TiledObjectGroupManager()
	{
		objectGroups = new ArrayList<TiledObjectGroup>();	
	}
	
	public void addObjectGroup(final int objectGroupID, final boolean isSingle) {
		addObjectGroup(new TiledObjectGroup(objectGroupID, isSingle));
	}
	
	public void addObjectGroup(final TiledObjectGroup objectGroup) {
		if (!objectGroups.contains(objectGroup)) {
			objectGroups.add(objectGroup);
		} else {
			System.out.println("Die ObjectGroup wurde bereits erstellt und hinzugefügt.");
		}
	}
	
	public void addObject(final int objectGroupID, final int objectID, float x, float y, int width, int height) {
		final TiledObjectGroup objectGroup = objectGroups.stream().filter(e -> e.getObjectGroupID() == objectGroupID).findFirst().get();
		
		if (objectGroup != null) {
			objectGroup.addObject(objectID, x, y, width, height);
		} else {
			System.out.println("Es wurde keine passenden ObjectGroup mit der ID: " + objectGroupID + " gefunden.");
		}
	}
	
	public List<TiledObjectGroup> getObjectGroups() {
		return objectGroups;
	}
	
	@Override
	public String toString() {
		String returnValue = "";
		
		for (TiledObjectGroup objectGroup : objectGroups) {
			returnValue += objectGroup.toString();
		}
		
		return returnValue;
	}
}
