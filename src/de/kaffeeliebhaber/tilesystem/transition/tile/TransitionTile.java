package de.kaffeeliebhaber.tilesystem.transition.tile;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.entitySystem.IEntityListener;
import de.kaffeeliebhaber.entitySystem.Player;

public class TransitionTile extends Entity implements IEntityListener {

	private static final Color color = new Color(175, 238, 238);
	private List<ITransitionTileListener> transitionTileListener;
	private int toChunkID;
	private TransitionDirection direction;
	
	public TransitionTile(float x, float y, int width, int height) {
		super(x, y, width, height);
		transitionTileListener = new ArrayList<ITransitionTileListener>();
	}
	
	public TransitionTile(float x, float y, int width, int height, final int toChunkID, TransitionDirection direction) {
		this(x, y, width, height);
		this.toChunkID = toChunkID;
		this.direction = direction;
	}
	
	public void setToChunkID(final int toChunkID) {
		this.toChunkID = toChunkID;
	}
	
	public void setTransitionDirection(final TransitionDirection direction) {
		this.direction = direction;
	}

	// TODO: Hier muss definitiv noch etwas geändert werden.
	@Override
	public void update(float timeSinceLastFrame, final List<Entity> entities) {
		
	}
	
	@Override
	public void render(Graphics g, Camera camera) {
		g.setColor(color);
		g.fillRect((int) (x - camera.getX()), (int) (y - camera.getY()), width, height);
		
		getBoundingBox().render(g, camera);
	}

	// TODO: Hier einfach direkt der Player übergeben.
	@Override
	public void entityUpdated(Entity entity) {
		
		// TODO: Refactor
		if (entity instanceof Player) {
			Player player = (Player) entity;

			boolean executeTransition = true;
			
			if (executeTransition) {
				
				final BoundingBox boundingBox = player.getBoundingBox();
				
				if (this.getBoundingBox().intersects(boundingBox)) {
					this.notifiyAllTransitionTileListener(new TransitionTileEvent(direction, toChunkID));
				}
			}
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

}
