package de.kaffeeliebhaber.xml.tiledEditor.EntitySystem;

import java.util.List;

import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.entitySystem.EntitySystem;
import de.kaffeeliebhaber.entitySystem.worldObjects.SimpleBush;
import de.kaffeeliebhaber.gfx.Spritesheet;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBoxManager;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObject;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObjectGroup;

/**
 * Jedes Objekte der Gruppe wird als eingenständiges Objekt erstellt.
 * @author User
 *
 */
public class EntitySystemObjectCreatorMultiGroup extends EntitySystemObjectCreator {


	// void createObjects(final TiledObjectGroup objectGroup, final TiledBoundingBox boundingBoxes, final ChunkSystem chunkSystem, final Spritesheet spritesheet);
	public void create(final TiledObjectGroup objectGroup, final TiledBoundingBoxManager boundingBoxManager, final ChunkSystem chunkSystem, final Spritesheet spritesheet, final EntitySystem entitySystem) {
		
		final List<TiledObject> objects = objectGroup.getObjects();
		
		for (TiledObject object : objects) {
			
			Entity simpleBush = new SimpleBush(
					object.getX(), 
					object.getY(), 
					object.getWidth(), 
					object.getHeight(), 
					spritesheet.getImageByIndex(object.getID() - 1));
			
			final int chunkID = chunkSystem.getChunkID((int) object.getX(), (int) object.getY());
			
			entitySystem.add(chunkID, simpleBush);
		}
	}

}
