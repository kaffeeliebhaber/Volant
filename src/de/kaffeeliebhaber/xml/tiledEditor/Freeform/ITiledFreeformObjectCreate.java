package de.kaffeeliebhaber.xml.tiledEditor.Freeform;

import java.util.List;

import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;

public interface ITiledFreeformObjectCreate {

	void create(final TiledFreeformObject freeformObject, final List<TiledFreeformObjectProperty> properties, final ChunkSystem chunkSystem);
}
