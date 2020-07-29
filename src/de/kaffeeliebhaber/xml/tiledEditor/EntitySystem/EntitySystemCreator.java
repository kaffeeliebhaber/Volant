package de.kaffeeliebhaber.xml.tiledEditor.EntitySystem;

import de.kaffeeliebhaber.entitySystem.EntitySystem;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.gfx.Spritesheet;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.xml.tiledEditor.ChunkSystem.ChunkSystemCreatorModel;

public class EntitySystemCreator {

	private final ChunkSystemCreatorModel chunkSystemCreatorModel;
	private final ChunkSystem chunkSystem;
	private final Player player;
	private final Spritesheet spritesheet;
	
	public EntitySystemCreator(final ChunkSystemCreatorModel chunkSystemCreatorModel, final ChunkSystem chunkSystem, final Player player, final Spritesheet spritesheet) {
		this.player = player;
		this.chunkSystem = chunkSystem; 
		this.chunkSystemCreatorModel = chunkSystemCreatorModel;
		this.spritesheet = spritesheet;
	}
	
	public EntitySystem createEntitySystem() {
		EntitySystem entitySystem = new EntitySystem(chunkSystem, player);

		chunkSystemCreatorModel
			.getTiledObjectGroupManager()
			.getObjectGroups()
			.stream()
			.forEach(o -> EntitySystemObjectCreator.construct(o.isSingle()).create(o, chunkSystemCreatorModel.getTiledBoundingBoxManager(), chunkSystem, spritesheet, entitySystem));
		
		return entitySystem;
	}
	
	private void printData() {
		System.out.println("###############################################################");
		System.out.println("(EntitySystemCreator.createEntitySystem) | ObjectGroupManager\n" + chunkSystemCreatorModel.getTiledObjectGroupManager());
		System.out.println("###############################################################");
		System.out.println("(EntitySystemCreator.createEntitySystem) | BoundingBoxManager\n" + chunkSystemCreatorModel.getTiledBoundingBoxManager());
		System.out.println("###############################################################");
	}
	
}
