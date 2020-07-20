package de.kaffeeliebhaber.xml.tiledEditor.EntitySystem;

import de.kaffeeliebhaber.entitySystem.EntitySystem;
import de.kaffeeliebhaber.gfx.Spritesheet;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBoxManager;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObjectGroup;

public abstract class EntitySystemObjectCreator {
	
	public static EntitySystemObjectCreator construct(boolean isSingle) {
		EntitySystemObjectCreator objectCreator = null;
		
		if (isSingle) {
			objectCreator = new EntitySystemObjectCreatorSingleGroup();
		} else {
			objectCreator = new EntitySystemObjectCreatorMultiGroup();
		}
		
		return objectCreator;
	}
	
	public abstract void create(final TiledObjectGroup objectGroup, final TiledBoundingBoxManager boundingBoxManager, final ChunkSystem chunkSystem, final Spritesheet spritesheet, final EntitySystem entitySystem);
}
