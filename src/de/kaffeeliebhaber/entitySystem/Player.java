package de.kaffeeliebhaber.entitySystem;

import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.behavior.moving.IMovingBehavior;
import de.kaffeeliebhaber.inventory.stats.PlayerStats;

public class Player extends MovingEntity {

	private final PlayerStats playerStats;

	public Player(float x, float y, int width, int height, IAnimationController animationController, IMovingBehavior movingBehavior, final PlayerStats playerStats) {
		super(x, y, width, height, animationController, movingBehavior);
		this.playerStats = playerStats;
	}
	
	public PlayerStats getStats() {
		return playerStats;
	}
}

