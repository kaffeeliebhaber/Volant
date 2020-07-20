package de.kaffeeliebhaber.xml.tiledEditor.EntitySystem;

import de.kaffeeliebhaber.gfx.Spritesheet;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBox;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObjectGroup;

public interface IEntitySystemObjectCreator {

	void createObjects(final TiledObjectGroup objectGroup, final TiledBoundingBox boundingBoxes, final ChunkSystem chunkSystem, final Spritesheet spritesheet);
}
