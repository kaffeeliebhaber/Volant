package de.kaffeeliebhaber.xml.tiledEditor.ChunkSystem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import de.kaffeeliebhaber.entitySystem.EntitySystem;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.gfx.Spritesheet;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.xml.tiledEditor.EntitySystem.EntitySystemCreator;
import de.kaffeeliebhaber.xml.tiledEditor.Freeform.TiledFreeformGroupManager;
import de.kaffeeliebhaber.xml.tiledEditor.Freeform.TiledFreeformObjectCreator;

public class ChunkSystemCreator {

	private final ChunkSystemCreatorModel model;
	private final Collection<Chunk> chunks;
	private final Spritesheet spritesheet;
	private ChunkSystem chunkSystem = null;

	public ChunkSystemCreator(final ChunkSystemCreatorModel model, final Spritesheet spritesheet) {
		this.model = model;
		this.spritesheet = spritesheet;
		
		checkChunkSystemCreatorModel(model);
		chunks = new ArrayList<Chunk>();
		init();
	}

	private void checkChunkSystemCreatorModel(final ChunkSystemCreatorModel model) {}
	
	private void init() {
		
		final LayerDataContainer layerDataMapper = model.getLayerDataContainer();
		final int numberOfChunks = calcNumberOfChunks(model);
		
		final Set<Integer> layerIDs = layerDataMapper.getLayerIDs();

		for (int layerID : layerIDs) {
			//System.out.println("(ChunkSystemCreator.init) | LayerID: " + layerID);
			int[][] layerData = layerDataMapper.get(layerID);
			
			for (int chunkID = 0; chunkID < numberOfChunks; chunkID++) {
				
				createEmptyChunkIfNotExist(chunkID);
				
				final int[][] extractedLayerData = createExtractedLayerData(chunkID, layerData, model.getTilesX(), model.getTilesY(), model.getChunkWidth(), model.getChunkHeight());
				
				getChunk(chunkID).addLayer(layerID, extractedLayerData, model.getChunkWidth(), model.getChunkHeight(), model.getTileWidth(), model.getTileHeight(), spritesheet, layerID == model.getObjectLayerID());
			}
		}
	}
	
	private int[][] createExtractedLayerData(final int chunkID, final int[][] layerData, final int tilesX, final int tilesY, final int chunkWidth, final int chunkHeight) {
		
		int chunkStartX = calcChunkStartPositionX(chunkID, tilesX, chunkWidth);
		int chunkStartY = calcChunkStartPositionY(chunkID, tilesY, chunkHeight);
		int chunkEndX = chunkStartX + chunkWidth;
		int chunkEndY = chunkStartY + chunkHeight;
		
		return extractLayerData(layerData, chunkStartX, chunkStartY, chunkEndX, chunkEndY);
	}
	
	private int calcNumberOfChunks(ChunkSystemCreatorModel model) {
		return (model.getTilesX() / model.getChunkWidth()) * (model.getTilesY() / model.getChunkHeight());
	}
	
	private Chunk getChunk(final int chunkID) {
		return findChunk(chunkID).get();
	}
	
	private Optional<Chunk> findChunk(final int chunkID) {
		return chunks.stream().filter(e -> e.getChunkID() == chunkID).findAny();
	}
	
	private void createEmptyChunkIfNotExist(final int chunkID) {
		if (!findChunk(chunkID).isPresent()) {
			chunks.add(new Chunk(chunkID));
		}
	}
	
	private int calcChunkStartPositionX(final int chunkID, final int tilesX, final int chunkWidth) {
		final int chunkX = tilesX / chunkWidth;
		final int x = chunkID % chunkX;
		return x * chunkWidth;
	}
	
	private int calcChunkStartPositionY(final int chunkID, final int tilesY, final int chunkHeight) {
		final int chunkY = tilesY / chunkHeight;
		final int y = chunkID / chunkY;
		return y * chunkHeight;
	}
	
	private int[][] extractLayerData(final int[][] layerData, final int startX, final int startY, final int endX, final int endY) {
		int[][] subLayer = create2DArray(startX, startY, endX, endY);

		for (int x = startX; x < endX; x++) {
			for (int y = startY; y < endY; y++) {
				subLayer[x - startX][y - startY] = layerData[x][y];
			}
		}
		
		return subLayer;
	}
	
	private int getArrayLenghtByIntervall(final int intervallStart, final int intervallEnd) {
		return (intervallEnd - intervallStart);
	}
	
	private int[][] create2DArray(final int intervallStartX, final int intervallStartY, final int intervallEndX, final int intervallEndY) {
		final int arrayDimX = getArrayLenghtByIntervall(intervallStartX, intervallEndX);
		final int arrayDimY = getArrayLenghtByIntervall(intervallStartY, intervallEndY);
		return new int[arrayDimX][arrayDimY];
	}
	
	public ChunkSystem createChunkSystem() {
		
		chunkSystem = new ChunkSystem(model.getTileWidth(), model.getTileHeight(), model.getTilesX(), model.getTilesY(), model.getChunkWidth(), model.getChunkHeight());
		chunkSystem.setObjectLayerID(model.getObjectLayerID());
		
		chunks.stream().forEach(c -> chunkSystem.addChunk(c.getChunkID(), c.createTilemapHandler()));
		
		return chunkSystem;
	}
	
	public void createFreeformObjects() {
		new TiledFreeformObjectCreator(model.getTiledFreeformGroupManager(), chunkSystem).create();
	}
	
	public EntitySystem createEntitySystem(final Player player) {
		
		if (chunkSystem == null) {
			throw new IllegalArgumentException("ChunkSystem not available. Call createEntitySystem method first and retry.");
		}
		
		return new EntitySystemCreator(model, chunkSystem, player, spritesheet).createEntitySystem();
	}
	
}
