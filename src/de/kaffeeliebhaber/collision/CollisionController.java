package de.kaffeeliebhaber.collision;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.entitySystem.MovingEntity;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;

public class CollisionController {

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

}
