package de.kaffeeliebhaber.entitySystem;

import java.awt.Graphics;
import java.util.List;

import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.behavior.moving.IMovingBehavior;
import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.inventory.stats.PlayerStats;

public class Player extends MovingEntity {

	// Macht es nicht Sinn, dass Inventory als Bestandteil des Spielers zu nehmen?
	// private final Inventory inventory;
	private final PlayerStats playerStats;

	public Player(float x, float y, int width, int height, IAnimationController animationController, IMovingBehavior movingBehavior, final PlayerStats playerStats, BoundingBox boundingBox) {
		super(x, y, width, height, animationController, movingBehavior);
		this.playerStats = playerStats;
		setBoundingBox(boundingBox);
	}
	
	public void update(float timeSinceLastFrame, final List<Entity> entities) {
		super.update(timeSinceLastFrame, entities);
	}
	
	public void render(Graphics g, Camera camera) {
		super.render(g, camera);
		getBoundingBox().render(g, camera);
	}
	
	public void updatePosition(float newPosX, float newPosY) {
		int dx = (int) (newPosX - x);
		int dy = (int) (newPosY - y);
		
		translateX(dx);
		translateY(dy);
		
		adjustDistricBorder();
	}
	
	public PlayerStats getStats() {
		return playerStats;
	}

	@Override
	public boolean isCollidable() {
		return true;
	}

}

