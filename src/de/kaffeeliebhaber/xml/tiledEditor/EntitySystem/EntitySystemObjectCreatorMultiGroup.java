package de.kaffeeliebhaber.xml.tiledEditor.EntitySystem;

import java.util.List;

import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.entitySystem.EntitySystem;
import de.kaffeeliebhaber.entitySystem.worldObjects.SimpleWorldObject;
import de.kaffeeliebhaber.gfx.Spritesheet;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBox;
import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBoxManager;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObject;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObjectGroup;

/**
 * Jedes Objekte der Gruppe wird als eingenständiges Objekt erstellt.
 * @author User
 *
 */
public class EntitySystemObjectCreatorMultiGroup extends EntitySystemObjectCreator {

	public void create(final TiledObjectGroup objectGroup, final TiledBoundingBoxManager boundingBoxManager, final ChunkSystem chunkSystem, final Spritesheet spritesheet, final EntitySystem entitySystem) {
		
		final List<TiledObject> objects = objectGroup.getObjects();
		
		for (TiledObject object : objects) {
			Entity simpleWorldObject = new SimpleWorldObject(object.getX(), object.getY(), object.getWidth(), object.getHeight(), spritesheet.getImageByIndex(object.getID() - 1));
			
			final TiledBoundingBox tiledBoundingBox = boundingBoxManager.getTiledBoundingBox(object.getID() - 1);
			
			if (tiledBoundingBox != null) {
				simpleWorldObject.addBoundingBoxes(tiledBoundingBox.getBoundingBoxes());
				simpleWorldObject.translateBoundingBoxes(object.getX(), object.getY());
			}
			
			final int chunkID = chunkSystem.getChunkID((int) object.getX(), (int) object.getY());
			
			entitySystem.add(chunkID, simpleWorldObject);
		}
	}

}
