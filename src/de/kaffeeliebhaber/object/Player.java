package de.kaffeeliebhaber.object;

import java.awt.Graphics;
import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.inventory.stats.PlayerStats;
import de.kaffeeliebhaber.object.movingBehavior.IMovingBehavior;

public class Player extends MovingEntity {

	private final PlayerStats playerStats;

	public Player(float x, float y, int width, int height, IAnimationController animationController, IMovingBehavior movingBehavior, final PlayerStats playerStats, BoundingBox boundingBox) {
		super(x, y, width, height, animationController, movingBehavior);
		this.playerStats = playerStats;
		setBoundingBox(boundingBox);
	}
	
	@Override public void update(float timeSinceLastFrame) {
		super.update(timeSinceLastFrame);
	}
	
	@Override public void render(Graphics g, Camera camera) {
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

