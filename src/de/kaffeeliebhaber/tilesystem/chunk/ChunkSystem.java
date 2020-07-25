package de.kaffeeliebhaber.tilesystem.chunk;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.test.ChunkSystemTest;
import de.kaffeeliebhaber.tilesystem.Tilemap;
import de.kaffeeliebhaber.tilesystem.TilemapHandler;
import de.kaffeeliebhaber.tilesystem.transition.tile.ITransitionTileListener;
import de.kaffeeliebhaber.tilesystem.transition.tile.TransitionTile;

public class ChunkSystem {
	
	private int tileWidth;
	private int tileHeight;
	private int tilesX;
	private int tilesY;
	private int chunkWidth;
	private int chunkHeight;
	private Map<Integer, TilemapHandler> chunkSystem;
	private Map<Integer, List<TransitionTile>> transitionTiles;
	private int currentChunkID;
	private int objectLayerID;
	
	public ChunkSystem(int tileWidth, int tileHeight, int tilesX, int tilesY, int chunkWidth, int chunkHeight) {
		
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.tilesX = tilesX;
		this.tilesY = tilesY;
		this.chunkWidth = chunkWidth;
		this.chunkHeight = chunkHeight;
		
		chunkSystem = new TreeMap<Integer, TilemapHandler>();
		transitionTiles = new TreeMap<Integer, List<TransitionTile>>();
	}
	
	public void update(float timeSinceLastFrame) {
		getChunk(currentChunkID).update(timeSinceLastFrame);
	}
	
	public void render(Graphics g, Camera camera) {
		getChunk(currentChunkID).render(g, camera);
		getTransitionTileList(currentChunkID).forEach(e -> e.render(g, camera));
	}
	
	public void addTransitionTile(final int chunkID, final TransitionTile tile) {
		getTransitionTileList(chunkID).add(tile);
	}
	
	public void addChunk(final int chunkID, final TilemapHandler handler) {
		chunkSystem.put(chunkID, handler);
		transitionTiles.put(chunkID, new ArrayList<TransitionTile>());
	}
	
	/**
	 * Registers the current PlayState as TransitionTileListener to each TransitionTile in the given list.
	 * @param transitionTiles
	 */
	public void addTransitionTileListener(final ITransitionTileListener l) {
		final Set<Integer> keys = transitionTiles.keySet();
		
		for (int key : keys) {
			transitionTiles.get(key).stream().forEach(e -> e.addTransitionTileListener(l));
		}
	}
	
	/**
	 * Unregisters the current PlayState as TransitionTileListener to each TransitionTile in the given list.
	 * @param transitionTiles
	 */
	public void removeTransitionTileListener(final ITransitionTileListener l) {
		final Set<Integer> keys = transitionTiles.keySet();
		
		for (int key : keys) {
			transitionTiles.get(key).stream().forEach(e -> e.removeTransitionTileListener(l));
		}
	}
	
	public void setChunkID(final int chunkId) {
		currentChunkID = chunkId;
	}
	
	public void setObjectLayerID(final int objectLayerID) {
		this.objectLayerID = objectLayerID; 
	}
	
	public void setTileWidth(int tileWidth) {
		this.tileWidth = tileWidth;
	}

	public void setTileHeight(int tileHeight) {
		this.tileHeight = tileHeight;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}
	
	public List<TransitionTile> getTransitionTileList(final int chunkID) {
		return transitionTiles.get(chunkID);
	}

	public int getChunkWidthInPixel() {
		return getChunkWidthInTile() * getTileWidth();
	}
	
	public int getChunkHeightInPixel() {
		return getChunkHeightInTile() * getTileHeight();
	}
	
	public TilemapHandler getChunk(final int chunkId) {
		return chunkSystem.get(chunkId);
	}
	
	public TilemapHandler getCurrentChunk() {
		return getChunk(currentChunkID);
	}
	
	public int getCurrentChunkID() {
		return currentChunkID;
	}
	
	public int getChunkWidthInTile() {
		return chunkWidth;
	}

	public int getChunkHeightInTile() {
		return chunkHeight;
	}
	
	public List<Entity> getContextEntities(final Entity entity) {
		
		List<Entity> contextEntities = new ArrayList<Entity>();
		
		if (this.getCurrentChunk() != null) {
			final Tilemap objTilemap = this.getCurrentChunk().getTilemap(objectLayerID);
			
			if (objTilemap != null) {
				final int centerX = (int) (entity.getCenterPosition().x / objTilemap.getTileWidth());
				final int centerY = (int) (entity.getCenterPosition().y / objTilemap.getTileHeight());

				contextEntities.addAll(objTilemap.getAdjacentTiles(centerX, centerY));
			}
		}
		
		return contextEntities;
	}

	public int getObjectLayerID() {
		return objectLayerID;
	}
	
	public boolean isOpenWorld() {
		return chunkSystem.size() == 1;
	}
	
	public int chunks() {
		return chunkSystem.keySet().size();
	}
	
	public int getChunkID(int worldPosX, int worldPosY) {
		int chunkX = getChunkX(worldPosX);
		int chunkY = getChunkY(worldPosY);
		return chunkX + chunksX() * chunkY;
	}
	
	private int getChunkX(int worldPosX) {
		int tileXID = worldPosX / this.tileWidth;
		int chunkXID = tileXID / this.chunkWidth;
		return chunkXID;
	}
	
	private int getChunkY(int worldPosY) {
		int tileYID = worldPosY / this.tileHeight;
		int chunkYID = tileYID / this.chunkHeight;
		return chunkYID;
	}
	
	private int chunksX() {
		return this.tilesX / this.chunkWidth;
	}
	
	// Aktuelle X-Position -> Chunk x-Position
	public float getChunkPositionX(final float x) {
		
		float chunkPositionX = x;
		
		if (chunkPositionX >= ChunkSystemTest.getChunkWidthInPixel()) {
			chunkPositionX = x % ChunkSystemTest.getChunkWidthInPixel();
		}
		
		return chunkPositionX;
	}
	
	public float getChunkPositionY(final float y) {
		
		float chunkPositiony = y;
		
		if (chunkPositiony >= getChunkHeightInPixel()) {
			chunkPositiony = y % getChunkHeightInPixel();
		}
		
		return chunkPositiony;
	}
	
}
