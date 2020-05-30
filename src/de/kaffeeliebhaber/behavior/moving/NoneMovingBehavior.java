package de.kaffeeliebhaber.behavior.moving;

import de.kaffeeliebhaber.entitySystem.MovingEntity;
import de.kaffeeliebhaber.math.Vector2f;

public class NoneMovingBehavior implements IMovingBehavior {
	
	@Override public Vector2f move(float timeSinceLastFrame) {
		return new Vector2f(0f, 0f);
	}

	@Override public void contextMovingEntity(MovingEntity movingEntity) {}
	
}
