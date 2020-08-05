package de.kaffeeliebhaber.entitySystem;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JTable;

import de.kaffeeliebhaber.collision.CollisionController;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;

public class EntitySystem {

	// CHUNKID - ENTITYMANAGER
	private final Map<Integer, List<Entity>> entitySystem;
	private int currentChunkID;
	private final ChunkSystem chunkSystem;
	private final Comparator<Entity> comparator;
	private final Player player;
	
	public EntitySystem(final ChunkSystem chunkSystem, final Player player, final Comparator<Entity> comparator) {
		this.chunkSystem = chunkSystem;
		this.player = player;
		this.comparator = comparator;
		entitySystem = new HashMap<Integer, List<Entity>>();
		initEntitySystem();
	}
	
	public EntitySystem(final ChunkSystem chunkSystem, final Player player) {
		this(chunkSystem, player, new EntityComparator());
	}
	
	private void initEntitySystem() {
		
		final int chunks = chunkSystem.chunks();
		
		for (int i = 0; i < chunks; i++) {
			addEmptyEntityList(i);
		}
	}
	
	public void add(final int chunkID, final Entity entity) {
		
		if (!containsID(chunkID)) {
			addEmptyEntityList(chunkID);
		}
		
		entitySystem.get(chunkID).add(entity);
	}
	
	public void remove(final int chunkID, final Entity entity) {
		entitySystem.get(currentChunkID).remove(entity);
	}
	
	public void update(final KeyManager keyManager, float timeSinceLastFrame) {
		
		final List<Entity> currentEntities = entitySystem.get(currentChunkID);
		
		if (currentEntities != null && currentEntities.size() > 0) {
			final List<MovingEntity> movingEntities = CollisionController.filterListForMovingEntities(currentEntities);
			final List<Entity> contextEntities = CollisionController.collectAllContextEntities(movingEntities, chunkSystem, currentEntities);
			entitySystem.get(currentChunkID).stream().forEach(e -> e.update(keyManager, timeSinceLastFrame, contextEntities));
			entitySystem.get(currentChunkID).sort(comparator);
		}
	}
	
	public void render(Graphics g, Camera camera) {
		
		List<Entity> currentEntites = entitySystem.get(currentChunkID);
		
		if (currentEntites != null && currentEntites.size() > 0) {
			currentEntites.stream().forEach(e -> e.render(g, camera));
		}
	}
	
	public List<Entity> getCurrentEntities() {
		return entitySystem.get(currentChunkID);
	}
	
	public void setChunkID(final int chunkID) {
		entitySystem.get(currentChunkID).remove(player);
		currentChunkID = chunkID;
		entitySystem.get(currentChunkID).add(player);
	}
	
	private boolean containsID(final int chunkID) {
		return entitySystem.containsKey(chunkID);
	}
	
	private void addEmptyEntityList(final int chunkID) {
		if (!containsID(chunkID)) {
			entitySystem.put(chunkID, new ArrayList<Entity>());
		}
	}
	
}
