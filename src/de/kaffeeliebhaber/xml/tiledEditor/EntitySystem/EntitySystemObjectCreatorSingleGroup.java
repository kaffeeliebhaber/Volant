package de.kaffeeliebhaber.xml.tiledEditor.EntitySystem;

import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.entitySystem.EntitySystem;
import de.kaffeeliebhaber.entitySystem.worldObjects.StaticEntity;
import de.kaffeeliebhaber.gfx.Spritesheet;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBox;
import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBoxManager;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObject;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObjectGroup;

/**
 * Alle Objekte der Gruppe werden als ein Objekt erstellt.
 * @author User
 *
 */
public class EntitySystemObjectCreatorSingleGroup extends EntitySystemObjectCreator {

	public void create(final TiledObjectGroup objectGroup, final TiledBoundingBoxManager boundingBoxManager, final ChunkSystem chunkSystem, final Spritesheet spritesheet, final EntitySystem entitySystem) {
		
		final List<TiledObject> objects = objectGroup.getObjects();
		
		final float x = objectGroup.getMaxX();
		final float y = objectGroup.getMaxY();
		
		final StaticEntity staticEntity = new StaticEntity(x, y);
		
		for (final TiledObject object : objects) {
			
			final int ID = object.getID() - 1;
			
			staticEntity.addBufferedImage(spritesheet.getImageByIndex(ID), object.getX(), object.getY());
			
			if (!boundingBoxManager.isEmpty()) {
				final TiledBoundingBox tiledBoundingBox = boundingBoxManager.getTiledBoundingBox(ID);
				
				if (tiledBoundingBox != null) {
					
					List<BoundingBox> currentBoundingBoxes = tiledBoundingBox.getBoundingBoxes();
					currentBoundingBoxes.stream().forEach(b -> b.translate(object.getX(), object.getY()));
					staticEntity.addBoundingBoxes(currentBoundingBoxes);
				}
			}

			
			final int chunkID = chunkSystem.getChunkID((int) object.getX(), (int) object.getY());
			
			entitySystem.add(chunkID, staticEntity);
		}
	}
}
