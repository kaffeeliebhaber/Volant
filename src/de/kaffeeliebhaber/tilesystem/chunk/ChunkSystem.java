package de.kaffeeliebhaber.tilesystem.chunk;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.kaffeeliebhaber.collision.CollisionController;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.entitySystem.EntityComparator;
import de.kaffeeliebhaber.entitySystem.MovingEntity;
import de.kaffeeliebhaber.tilesystem.Tilemap;
import de.kaffeeliebhaber.tilesystem.TilemapHandler;
import de.kaffeeliebhaber.tilesystem.transition.tile.TransitionTile;

public class ChunkSystem {
	
	private int tileWidth;
	private int tileHeight;
	private int chunkWidth;
	private int chunkHeight;
	private Map<Integer, TilemapHandler> chunkSystem;
	private Map<Integer, List<Entity>> chunkEntities;
	private Map<Integer, List<TransitionTile>> transitionTiles;
	private final Comparator<Entity> entityComparator;
	private int currentChunkID;
	private int objectLayerID;
	
	public ChunkSystem(int chunkWidth, int chunkHeight) {
		
		this.chunkWidth = chunkWidth;
		this.chunkHeight = chunkHeight;
		
		chunkSystem = new TreeMap<Integer, TilemapHandler>();
		chunkEntities = new TreeMap<Integer, List<Entity>>();
		transitionTiles = new TreeMap<Integer, List<TransitionTile>>();
		
		entityComparator = new EntityComparator();
	}
	
	public void update(float timeSinceLastFrame) {
		
		getChunk(currentChunkID).update(timeSinceLastFrame);
		
		final List<Entity> currentChunkEntities = getEntityList();
		final List<MovingEntity> movingEntities = CollisionController.filterListForMovingEntities(currentChunkEntities);
		final List<Entity> contextEntities = CollisionController.collectAllMovingEntityContextEntities(movingEntities, this, currentChunkEntities);
		
		currentChunkEntities.forEach(e -> e.update(timeSinceLastFrame, contextEntities));
		currentChunkEntities.sort(entityComparator);
	}
	
	public void render(Graphics g, Camera camera) {
		getChunk(currentChunkID).render(g, camera);
		getEntityList().stream().forEach(e -> e.render(g, camera));
		getTransitionTileList(currentChunkID).forEach(e -> e.render(g, camera));
	}
	
	public void removeEntity(final int chunkID, final Entity e) {
		getEntityList(chunkID).remove(e);
	}
	
	public void addEntity(final int chunkID, final Entity e) {
		getEntityList(chunkID).add(e);
	}
	
	public void addTransitionTile(final int chunkID, final TransitionTile tile) {
		getTransitionTileList(chunkID).add(tile);
	}
	
	public void addChunk(final int chunkID, final TilemapHandler handler) {
		chunkSystem.put(chunkID, handler);
		chunkEntities.put(chunkID, new ArrayList<Entity>());
		transitionTiles.put(chunkID, new ArrayList<TransitionTile>());
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
	
	public List<Entity> getEntityList() {
		return getEntityList(currentChunkID);
	}
	
	public List<Entity> getEntityList(final int chunkID) {
		return chunkEntities.get(chunkID);
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

}
