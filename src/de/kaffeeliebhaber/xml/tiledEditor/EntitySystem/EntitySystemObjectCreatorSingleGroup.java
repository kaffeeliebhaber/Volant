package de.kaffeeliebhaber.xml.tiledEditor.EntitySystem;

import java.util.List;

import de.kaffeeliebhaber.entitySystem.EntitySystem;
import de.kaffeeliebhaber.gfx.Spritesheet;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBoxManager;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObject;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObjectGroup;

/**
 * Alle Objekte der Gruppe werden als ein Objekt erstellt.
 * @author User
 *
 */
public class EntitySystemObjectCreatorSingleGroup extends EntitySystemObjectCreator {

	public void create(final TiledObjectGroup objectGroup, final TiledBoundingBoxManager boundingBoxManager, final ChunkSystem chunkSystem, final Spritesheet spritesheet, final EntitySystem entitySystem) {
		System.out.println("(EntitySystemObjectCreatorSingleGroup.create) | single | ");
		
		/*
		final List<TiledObject> objects = objectGroup.getObjects();
		
		for (TiledObject object : objects) {
			
			System.out.println("(EntitySystemObjectCreatorSingleGroup.create) | TileID: " + object.getID());
			
		}
		*/
	}
}
