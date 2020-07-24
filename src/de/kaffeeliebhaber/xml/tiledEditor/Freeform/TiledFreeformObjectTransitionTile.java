package de.kaffeeliebhaber.xml.tiledEditor.Freeform;

import java.util.List;

import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.tilesystem.transition.tile.TransitionDirection;
import de.kaffeeliebhaber.tilesystem.transition.tile.TransitionTile;

public class TiledFreeformObjectTransitionTile {

	private final TiledFreeformObject freeformObject;
	private final List<TiledFreeformObjectProperty> properties;
	private final ChunkSystem chunkSystem;
	
	public TiledFreeformObjectTransitionTile(final TiledFreeformObject freeformObject, final List<TiledFreeformObjectProperty> properties, final ChunkSystem chunkSystem) {
		this.freeformObject = freeformObject;
		this.properties = properties;
		this.chunkSystem = chunkSystem;
	}
	
	public void create() {
		System.out.println("(TiledFreeformObjectTransitionTile.create) | create method was called.");
		
		final float x = freeformObject.getX();
		final float y = freeformObject.getY();
		final int width = freeformObject.getWidth();
		final int height = freeformObject.getHeight();
		
		final int propertiesCnt = properties.size();
		
		int toChunkID = 0;
		int fromChunkID = 0;
		TransitionDirection transitionDirection = TransitionDirection.LEFT;
		
		for (int i = 0; i < propertiesCnt; i++) {
			
			final TiledFreeformObjectProperty property = properties.get(i);
			final String name = property.getProperty();
			final String value = property.getValue();
			
			//System.out.println("(TiledFreeformObjectTransitionTile.create) | name: " + name + ", value: " + value);
			
			switch (name) {
				case "Direction": transitionDirection = getTransitionDirection(value); break;
				case "ToChunkID": toChunkID = Integer.parseInt(value); break;
				case "FromChunkID": fromChunkID = Integer.parseInt(value); break;
			}
			
		}
		
		System.out.println("(TiledFreeformObjectTransitionTile.create) | toChunkID: " + toChunkID + ", fromChunkID: " + fromChunkID + ", TransitionDirection: " + transitionDirection);
		
		chunkSystem.addTransitionTile(fromChunkID, new TransitionTile(x, y, width, height, toChunkID, transitionDirection));
		
		// chunkSystem.addTransitionTile(0, new TransitionTile(25 * 32 - 2, 300, 2, 100, 1, TransitionDirection.RIGHT));
		
		
	}
	
	public TransitionDirection getTransitionDirection(final String direction) {
		TransitionDirection transitionDirection = null;
		
		switch (direction) {
			case "left": transitionDirection = TransitionDirection.LEFT; break;
			case "up": transitionDirection = TransitionDirection.UP; break;
			case "right": transitionDirection = TransitionDirection.RIGHT; break;
			case "down": transitionDirection = TransitionDirection.DOWN; break;
		}
		
		return transitionDirection;
	}
}
