package de.kaffeeliebhaber.tilesystem.transition.tile;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.entitySystem.IEntityListener;
import de.kaffeeliebhaber.entitySystem.Player;

public class TransitionTile extends Entity implements IEntityListener {

//	private static final Color color = new Color(175, 238, 238);
	private final List<ITransitionTileListener> transitionTileListener;
	private final int toChunkID;
	private final TransitionDirection direction;
	
	public TransitionTile(float x, float y, int width, int height, final int toChunkID, TransitionDirection direction) {
		super(x, y, width, height);
		this.toChunkID = toChunkID;
		this.direction = direction;
		transitionTileListener = new ArrayList<ITransitionTileListener>();
		addBoundingBox(new BoundingBox(x, y, width, height));
	}
	
//	public void setToChunkID(final int toChunkID) {
//		this.toChunkID = toChunkID;
//	}
//	
//	public void setTransitionDirection(final TransitionDirection direction) {
//		this.direction = direction;
//	}

	// TODO: Hier muss definitiv noch etwas geändert werden.
	@Override
	public void update(float timeSinceLastFrame, final List<Entity> entities) {
		
		final int size = entities.size();
		boolean execute = true;
		
		for (int i = 0; i < size && execute; i++) {
			if (this.intersects(entities.get(i))) {
				this.notifiyAllTransitionTileListener(new TransitionTileEvent(direction, toChunkID));
				execute = false;
			}
		}
		
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		//boundingBoxes.stream().forEach(b -> b.render(g, camera));
		boundingBoxController.render(g, camera);
		
	}
	
//	private void renderBoundingBox(Graphics g, Camera camera) {
//		
//	}

	// TODO: Diese Funktion wurde in die update-Methode ausgelagert. Das Interface wie auch diese Methode später entfernen.
	@Override
	public void entityUpdated(Entity entity) {
		
//		System.out.println("(TransitionTile) | entityUpdated");
		// TODO: Refactor
		if (entity instanceof Player) {
			Player player = (Player) entity;

			// TODO: Wofür brauche ich hier das 'true'???
			//boolean executeTransition = true;
			
			if (this.intersects(player)) {
//				System.out.println("(TransitionTile) | entityUpdated & intersects ");
				this.notifiyAllTransitionTileListener(new TransitionTileEvent(direction, toChunkID));
			}
			/*
			if (executeTransition) {
				
				if (this.intersects(player)) {
					this.notifiyAllTransitionTileListener(new TransitionTileEvent(direction, toChunkID));
				}
			}
			*/
		}
		
	}
	
	public void addTransitionTileListener(final ITransitionTileListener l) {
		transitionTileListener.add(l);
	}
	
	public void removeTransitionTileListener(final ITransitionTileListener l) {
		transitionTileListener.remove(l);
	}
	
	private void notifiyAllTransitionTileListener(TransitionTileEvent event) {
		for (int i = 0; i < transitionTileListener.size(); i++) {
			transitionTileListener.get(i).transitionTileEntered(event);
		}
	}

	/*
	@Override
	public boolean intersects(Entity entity) {
		return entity.intersects(boundingBox);
	}

	@Override
	public boolean intersects(BoundingBox boundingBox) {
		return boundingBox.intersects(this.boundingBox);
	}
*/
}
