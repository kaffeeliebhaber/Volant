package de.kaffeeliebhaber.collision;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.kaffeeliebhaber.object.BoundingBox;
import de.kaffeeliebhaber.object.Entity;
import de.kaffeeliebhaber.object.MovingEntity;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;

public class CollisionController {

	public static void collision(final ChunkSystem chunkSystem, final List<Entity> entities) {

		final List<MovingEntity> movingEntities = CollisionController.filterListForMovingEntities(entities);
		// TODO: Ich bin der Meinung, dass die 'entities' an dieser Stelle nicht mehr benötigt werden.
		// TODO: Bitte einmal testen.
		final List<Entity> contextEntities = CollisionController.collectAllMovingEntityContextEntities(movingEntities, chunkSystem, entities);
		
		CollisionController.checkCollisionAndUpdateMovingEntity(movingEntities, contextEntities);
	}

	public static List<MovingEntity> filterListForMovingEntities(final List<Entity> entities) {
		return entities.stream()
				.filter(e -> e instanceof MovingEntity)
				.map(me -> (MovingEntity) me)
				.collect(Collectors.toList());
	}
	
	public static List<Entity> collectAllMovingEntityContextEntities(final List<MovingEntity> movingEntities, final ChunkSystem chunkSystem, final List<Entity> entities) {
		final List<Entity> contextEntities = new ArrayList<Entity>();
		movingEntities.forEach(e -> contextEntities.addAll(chunkSystem.getContextEntities(e)));
		contextEntities.addAll(entities);
		
		return contextEntities;
	}
	
	public static void checkCollisionAndUpdateMovingEntity(final List<MovingEntity> movingEntities, final List<Entity> contextEntities) {
		final int size = movingEntities.size();

		for (int i = 0; i < size; i++) {
			final MovingEntity movingEntity = movingEntities.get(i);

			if (!CollisionUtil.collides(movingEntity, BoundingBox.createTranslatedBoundingBox(movingEntity.getBoundingBox(), movingEntity.getDx(), 0), contextEntities)) {
				movingEntity.moveX();
			}

			if (!CollisionUtil.collides(movingEntity, BoundingBox.createTranslatedBoundingBox(movingEntity.getBoundingBox(), 0, movingEntity.getDy()), contextEntities)) {
				movingEntity.moveY();
			}
		}
	}
}
