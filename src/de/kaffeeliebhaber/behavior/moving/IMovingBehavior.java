package de.kaffeeliebhaber.behavior.moving;

import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.entitySystem.MovingEntity;
import de.kaffeeliebhaber.math.Vector2f;

public interface IMovingBehavior {
	
	void contextMovingEntity(final MovingEntity movingEntity);
	
	Vector2f move(final KeyManager keyManager, float timeSinceLastFrame);
}
