package de.kaffeeliebhaber.object.movingBehavior;

import de.kaffeeliebhaber.math.Vector2f;
import de.kaffeeliebhaber.object.MovingEntity;

public interface IMovingBehavior {
	
	void contextMovingEntity(final MovingEntity movingEntity);
	
	Vector2f move(float timeSinceLastFrame);
}
