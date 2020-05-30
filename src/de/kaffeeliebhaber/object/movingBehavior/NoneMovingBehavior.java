package de.kaffeeliebhaber.object.movingBehavior;

import de.kaffeeliebhaber.math.Vector2f;
import de.kaffeeliebhaber.object.MovingEntity;

public class NoneMovingBehavior implements IMovingBehavior {
	
	@Override public Vector2f move(float timeSinceLastFrame) {
		return new Vector2f(0f, 0f);
	}

	@Override public void contextMovingEntity(MovingEntity movingEntity) {}
	
}
