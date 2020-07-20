package de.kaffeeliebhaber.xml.tiledEditor.EntitySystem;

import de.kaffeeliebhaber.entitySystem.EntitySystem;
import de.kaffeeliebhaber.gfx.Spritesheet;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBoxManager;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObjectGroup;

/**
 * Alle Objekte der Gruppe werden als ein Objekt erstellt.
 * @author User
 *
 */
public class EntitySystemObjectCreatorSingleGroup extends EntitySystemObjectCreator {

	public void create(final TiledObjectGroup objectGroup, final TiledBoundingBoxManager boundingBoxManager, final ChunkSystem chunkSystem, final Spritesheet spritesheet, final EntitySystem entitySystem) {
		System.out.println("(EntitySystemObjectCreatorSingleGroup.create) | single | ");
	}
}
