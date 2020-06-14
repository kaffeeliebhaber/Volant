package de.kaffeeliebhaber.tilesystem.chunk;

import java.util.List;

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

/**
 * Die Aufgabe dieses Controllers liegt darin, Übergänge zu definieren, steuern und die Transition zu steuern. 
 * Entsprechende Informationen müssen nach Außen gegeben werden.
 * @author User
 *
 */
public class DEL_ChunkSystemController implements ITransitionTileListener, ITransitionListener {

	private ChunkSystem chunkSystem;
	private Player player;
	private Transition transition;
//	private EntityManager entityHandler;
	private ItemManager itemManager;
	private boolean initialized;
	private TransitionDirection direction;
	private int toChunkID;
	
	public DEL_ChunkSystemController(final ChunkSystem chunkSystem, final Player player, final Transition transition, final ItemManager itemManager) {
		this.chunkSystem = chunkSystem;
		this.player = player;
		this.transition = transition;
//		this.entityHandler = entityHandler;
		this.itemManager = itemManager;
		
		transition.addTransitionListener(this);
	}
	
	public void setChunkID(final int chunkID) {
		
		chunkSystem.setChunkID(chunkID);

		// Die Entitäten werden nur deswegen in den EntityHandler eingefügt, um sie später durch den Y-SOrt zu sortieren.
		// Kann das nicht direkt auch im ChunkSystem passieren?
		//entityHandler.clear();
		//entityHandler.addAll(chunkSystem.getEntityList());
		//entityHandler.add(player);
		
//		chunkSystem.addEntity(chunkID, player);
		
		final List<TransitionTile> transitionList = chunkSystem.getTransitionTileList(chunkID);
		
		if (transitionList != null && transitionList.size() > 0) {
			//registerTransitionTileListener(transitionList);
			player.addEntityUpdateListeners(transitionList);
		}
	}
	
	private void unsetChunkID(final int chunkID) {
		final List<TransitionTile> transitionList = chunkSystem.getTransitionTileList(chunkID);
		
//		entityHandler.clear();
		itemManager.clear();
//		chunkSystem.removeEntity(chunkID, player);
		
		if (transitionList != null && transitionList.size() > 0) {
			//unregisterTransitionTileListener(transitionList);
			player.removeEntityUpdateListeners(transitionList);
		}
	}
	
	private void changeChunk(final int fromChunkID, final int toChunkID) {
		unsetChunkID(fromChunkID);
		setChunkID(toChunkID);
	}
	
	@Override
	public void transitionTileEntered(TransitionTileEvent event) {
		
		if (!transition.isActive()) {
			transition.start();
			
			toChunkID = event.getChunkID();
			direction = event.getTransitionDirection();
		}
	}
	

//	private void registerTransitionTileListener(final List<? extends TransitionTile> transitionTiles) {
//		transitionTiles.forEach(e -> e.addTransitionTileListener(this));
//	}
//	
//	private void unregisterTransitionTileListener(final List<? extends TransitionTile> transitionTiles) {
//		transitionTiles.forEach(e -> e.removeTransitionTileListener(this));
//	}

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
		
		final int offset = 2;
		
		// RESET PLAYER POSITION
		switch (direction) {
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
