package de.kaffeeliebhaber.behavior.moving;

import de.kaffeeliebhaber.entitySystem.MovingEntity;
import de.kaffeeliebhaber.math.Vector2f;

public class FollowEntityMovingBehavior implements IMovingBehavior {

	private MovingEntity sourceEntity;
	private MovingEntity targetEntity;
	private float speed;
	
	public FollowEntityMovingBehavior(final MovingEntity sourceEntity, final MovingEntity targetEntity, float speed) {
		this.sourceEntity = sourceEntity;
		this.targetEntity = targetEntity;
		this.speed = speed;
	}
	
	@Override
	public Vector2f move(float timeSinceLastFrame) {
		float targetX = targetEntity.getX();
		float targetY = targetEntity.getY();
		
		float newX = sourceEntity.getX() + (targetX - sourceEntity.getX()) * timeSinceLastFrame * speed;
		float newY = sourceEntity.getY() + (targetY - sourceEntity.getY()) * timeSinceLastFrame * speed;
		
		sourceEntity.setX(newX);
		sourceEntity.setY(newY);
		
		return new Vector2f(newX, newY);
	}

	@Override
	public void contextMovingEntity(MovingEntity movingEntity) {
		
	}

}
