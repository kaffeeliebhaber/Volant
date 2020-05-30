package de.kaffeeliebhaber.object.movingBehavior;

import java.awt.event.KeyEvent;

import de.kaffeeliebhaber.core.Keyboard;
import de.kaffeeliebhaber.math.Vector2f;
import de.kaffeeliebhaber.object.MovingEntity;

public class PlayerMovingBehavior implements IMovingBehavior {

	private final float movingSpeed;
	
	public PlayerMovingBehavior(float movingSpeed) {
		this.movingSpeed = movingSpeed;
	}
	
	@Override
	public Vector2f move(float timeSinceLastFrame) {
		
		float dx = 0;
		float dy = 0;
		
		if (Keyboard.isKeyPressed(KeyEvent.VK_A)) {
			dx -= movingSpeed * timeSinceLastFrame;
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_D)) {
			dx += movingSpeed * timeSinceLastFrame;
		}
		
		if (Keyboard.isKeyPressed(KeyEvent.VK_W)) {
			dy -= movingSpeed * timeSinceLastFrame;
		}
		if (Keyboard.isKeyPressed(KeyEvent.VK_S)) {
			dy += movingSpeed * timeSinceLastFrame;
		}
		
		return new Vector2f(dx, dy);
	}

	@Override public void contextMovingEntity(MovingEntity movingEntity) {}
	
}
