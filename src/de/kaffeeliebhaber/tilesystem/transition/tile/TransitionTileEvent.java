package de.kaffeeliebhaber.tilesystem.transition.tile;

public class TransitionTileEvent {

	private TransitionDirection direction;
	private int chunkID;
	
	public TransitionTileEvent(final TransitionDirection direction, final int chunkID) {
		this.direction = direction;
		this.chunkID = chunkID;
	}
	
	public int getChunkID() {
		return chunkID;
	}
	
	public TransitionDirection getTransitionDirection() {
		return direction;
	}
}
