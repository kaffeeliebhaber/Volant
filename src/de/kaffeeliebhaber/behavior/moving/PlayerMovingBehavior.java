package de.kaffeeliebhaber.behavior.moving;

import java.awt.event.KeyEvent;

import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.entitySystem.MovingEntity;
import de.kaffeeliebhaber.math.Vector2f;

public class PlayerMovingBehavior implements IMovingBehavior {

	private final float movingSpeed;
	
	public PlayerMovingBehavior(float movingSpeed) {
		this.movingSpeed = movingSpeed;
	}
	
	@Override
	public Vector2f move(final KeyManager keyManager, float timeSinceLastFrame) {
		
		float dx = 0;
		float dy = 0;
		
		if (keyManager.isKeyPressed(KeyEvent.VK_A)) {
			dx -= movingSpeed * timeSinceLastFrame;
		}
		if (keyManager.isKeyPressed(KeyEvent.VK_D)) {
			dx += movingSpeed * timeSinceLastFrame;
		}
		
		if (keyManager.isKeyPressed(KeyEvent.VK_W)) {
			dy -= movingSpeed * timeSinceLastFrame;
		}
		if (keyManager.isKeyPressed(KeyEvent.VK_S)) {
			dy += movingSpeed * timeSinceLastFrame;
		}
		
		return new Vector2f(dx, dy);
	}

	@Override public void contextMovingEntity(MovingEntity movingEntity) {}
	
}
