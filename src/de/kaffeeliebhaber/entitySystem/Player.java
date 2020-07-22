package de.kaffeeliebhaber.entitySystem;

import java.awt.Graphics;
import java.util.List;

import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.behavior.moving.IMovingBehavior;
import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.debug.Debug;
import de.kaffeeliebhaber.inventory.stats.PlayerStats;

public class Player extends MovingEntity {

	private final PlayerStats playerStats;

	public Player(float x, float y, int width, int height, IAnimationController animationController, IMovingBehavior movingBehavior, final PlayerStats playerStats, BoundingBox boundingBox) {
		super(x, y, width, height, animationController, movingBehavior);
		this.playerStats = playerStats;
		addBoundingBox(boundingBox);
	}
	
	public void update(float timeSinceLastFrame, final List<Entity> entities) {
		super.update(timeSinceLastFrame, entities);
	}
	
	public void render(Graphics g, Camera camera) {
		super.render(g, camera);
		renderBoundingBox(g, camera);
	}
	
	private void renderBoundingBox(Graphics g, Camera camera) {
		if (Debug.PLAYER_RENDER_SHOW_BOUNDINGBOX) {
			boundingBoxController.render(g, camera);
		}
	}
	
	public void updatePosition(float newPosX, float newPosY) {
		final int dx = (int) (newPosX - x);
		final int dy = (int) (newPosY - y);
		
		translate(dx, dy);
		adjustDistricBorder();
	}
	
	public PlayerStats getStats() {
		return playerStats;
	}
}

