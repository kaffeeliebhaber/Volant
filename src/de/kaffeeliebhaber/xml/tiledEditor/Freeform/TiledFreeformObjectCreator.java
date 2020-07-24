package de.kaffeeliebhaber.xml.tiledEditor.Freeform;

import java.util.List;

import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;

public class TiledFreeformObjectCreator {

	private final TiledFreeformGroupManager freeformGroupManager;
	private final ChunkSystem chunkSystem;
	
	public TiledFreeformObjectCreator(final TiledFreeformGroupManager freeformGroupManager, final ChunkSystem chunkSystem) {
		this.freeformGroupManager = freeformGroupManager;
		this.chunkSystem = chunkSystem;
	}
	
	public void create() {
		
		List<TiledFreeformObject> freeformObjects = freeformGroupManager.getFreeformObjects();
		
		for (final TiledFreeformObject object : freeformObjects) {
			
			final String type = object.getType();
			
			switch (type) {
				case "TransitionTile": 
					new TiledFreeformObjectTransitionTile(object, freeformGroupManager.getProperties(object.getID()), chunkSystem).create();
					break;
			}
		}
		
	}
}
