package de.kaffeeliebhaber.xml.tiledEditor.ChunkSystem;

import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBoxManager;
import de.kaffeeliebhaber.xml.tiledEditor.Freeform.TiledFreeformGroupManager;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObjectGroupManager;

public interface ChunkSystemCreatorModel {

	LayerDataContainer getLayerDataContainer();
	
	TiledBoundingBoxManager getTiledBoundingBoxManager();
	
	TiledObjectGroupManager getTiledObjectGroupManager();
	
	TiledFreeformGroupManager getTiledFreeformGroupManager();
	
	int getTileWidth();
	
	int getTileHeight();
	
	int getTilesX();
	
	int getTilesY();
	
	int getChunkWidth();
	
	int getChunkHeight();
	
	int getObjectLayerID();
	
}
