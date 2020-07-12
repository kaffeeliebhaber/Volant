package de.kaffeeliebhaber.xml.tiledEditor;

public interface ChunkSystemCreatorModel {

	LayerDataContainer getLayerDataContainer();
	
	BoundingBox2DContainer getBoundingBox2DContainer();
	
	int getTileWidth();
	
	int getTileHeight();
	
	int getTilesX();
	
	int getTilesY();
	
	int getChunkWidth();
	
	int getChunkHeight();
	
	int getObjectLayerID();
	
}
