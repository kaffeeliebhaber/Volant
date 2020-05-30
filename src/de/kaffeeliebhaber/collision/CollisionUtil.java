package de.kaffeeliebhaber.collision;

import java.util.List;

import de.kaffeeliebhaber.entitySystem.Entity;

public class CollisionUtil {

	private CollisionUtil() {}
	
	public static boolean gameObjectIntersectsGameObject(final Entity entityOne, final Entity entityTwo) {
		/*
		boolean collision = false;
		
		if(
				entityOne.getX() < entityTwo.getX() + entityTwo.getWidth() 	&&
				entityOne.getX() + entityOne.getWidth()  > entityTwo.getX() &&
				entityOne.getY() < entityTwo.getY() + entityTwo.getHeight() &&
				entityOne.getY() + entityOne.getHeight() > entityTwo.getY())
		{
			collision = true;
		}
		
		return collision;
		*/

		return entityOne.getBoundingBox().intersects(entityTwo.getBoundingBox());
	}
	
	public static boolean isCollidingWithEntities(final Entity entity, final List<Entity> entities) {
		
		
		for (int i = 0; i < entities.size(); i++) {
			
			Entity e = entities.get(i);
			
			if (entity != e && CollisionUtil.gameObjectIntersectsGameObject(entity, e)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean collides(final Entity entity, final BoundingBox boundingBox, final List<Entity> entities) {
		boolean collides = false;
		
		for (int i = 0; i < entities.size() && !collides; i++) {

			Entity currentEntity = entities.get(i);
			
			if (currentEntity != entity && boundingBox != null && boundingBox.intersects(currentEntity.getBoundingBox())) {
				collides = true;
			}
		}
		
		return collides;
	}
}