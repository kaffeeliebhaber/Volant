package de.kaffeeliebhaber.tilesystem.chunk;

import java.awt.Graphics;
import java.util.List;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.EntitySystem;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.inventory.ItemManager;
import de.kaffeeliebhaber.tilesystem.transition.ITransitionListener;
import de.kaffeeliebhaber.tilesystem.transition.Transition;
import de.kaffeeliebhaber.tilesystem.transition.TransitionEvent;
import de.kaffeeliebhaber.tilesystem.transition.TransitionState;
import de.kaffeeliebhaber.tilesystem.transition.tile.ITransitionTileListener;
import de.kaffeeliebhaber.tilesystem.transition.tile.TransitionDirection;
import de.kaffeeliebhaber.tilesystem.transition.tile.TransitionTile;
import de.kaffeeliebhaber.tilesystem.transition.tile.TransitionTileEvent;

public class GameWorld implements ITransitionTileListener, ITransitionListener {

	private final Player player;
	private final ChunkSystem chunkSystem;
	private final ItemManager itemManager;
	private final Transition transition;
	private final EntitySystem entitySystem;
	
	// IS GAMEWORLD ACTIVE OR NOT.
	private boolean active = true;
	
	// TRANSITION DATA
	private int toChunkID;
	private TransitionDirection transitionDirection;
	private boolean initialized;
	
	public GameWorld(final Player player, final ChunkSystem chunkSystem, final ItemManager itemManager, final EntitySystem entitySystem, final Transition transition) {
		this.player = player;
		this.chunkSystem = chunkSystem;
		this.itemManager = itemManager;
		this.entitySystem = entitySystem;
		this.transition = transition;
		
		setChunk(0);
		transition.addTransitionListener(this);
	}
	
	public void update(float timeSinceLastFrame) {
		if (active) {
			chunkSystem.update(timeSinceLastFrame);
			itemManager.update();
			entitySystem.update(timeSinceLastFrame);
		}
	}
	
	public void render(Graphics g, Camera camera) {
		chunkSystem.render(g, camera);
		itemManager.render(g, camera);
		entitySystem.render(g, camera);
	}

	private void changeChunk(final int fromChunkID, final int toChunkID) {
		unsetChunk(fromChunkID);
		setChunk(toChunkID);
	}
	
	private void setChunk(final int chunkID) {
		chunkSystem.setChunkID(chunkID);
		entitySystem.setChunkID(chunkID);
		final List<TransitionTile> transitionList = chunkSystem.getTransitionTileList(chunkID);
		chunkSystem.addTransitionTileListener(this);
		player.addEntityUpdateListeners(transitionList);
	}
	
	private void unsetChunk(final int chunkID) {
		
		itemManager.clear();
		
		final List<TransitionTile> transitionList = chunkSystem.getTransitionTileList(chunkID);
		chunkSystem.removeTransitionTileListener(this);
		player.removeEntityUpdateListeners(transitionList);
	}
	
	@Override
	public void transitionTileEntered(TransitionTileEvent event) {
		
		if (!transition.isActive()) {
			transition.start();
			
			toChunkID = event.getChunkID();
			transitionDirection = event.getTransitionDirection();
		}
	}

	@Override
	public void transitionStateChanged(TransitionEvent event) {
		final TransitionState state = event.getState();
		
		switch (state) {
			case MAX:
				if (!initialized) {
					changeChunk(chunkSystem.getCurrentChunkID(), toChunkID);
					updatePlayerPosition();
					initialized = true;
				}
				break;
			case END:
				initialized = false;
				break;
				
			default: break;
		}
	}
	
	private void updatePlayerPosition() {
		
		float newPosX = 0;
		float newPosY = 0;
		
		final int offset = 3;
		
		// RESET PLAYER POSITION
		switch (transitionDirection) {
			case UP: 
				newPosX = player.getX();
				newPosY = chunkSystem.getChunkHeightInPixel() - player.getHeight() - offset;
				break;
			case RIGHT: 
				newPosX = offset;
				newPosY = player.getY();
				break;
			case DOWN: 
				newPosX = player.getX();
				newPosY = offset;
				break;
			case LEFT: 
				newPosX = chunkSystem.getChunkHeightInPixel() - player.getWidth() - offset;
				newPosY = player.getY();
				break;
		}
		
		player.updatePosition(newPosX, newPosY);
	}
}
