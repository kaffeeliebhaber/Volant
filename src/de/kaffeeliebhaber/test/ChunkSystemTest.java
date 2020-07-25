package de.kaffeeliebhaber.test;

public class ChunkSystemTest {

	private static final int TILESIZE = 32;
	private static final int CHUNKS_X = 25;
	
	public static void main(String[] args) {
		
		float currentPositionX = 798;
		
		float transformedCurrentPositionX = ChunkSystemTest.getChunkPositionX(currentPositionX);
		
		System.out.println("PRE: " + currentPositionX + ", POST: " + transformedCurrentPositionX);
	}
	
	// Aktuelle X-Position -> Chunk x-Position
	public static float getChunkPositionX(final float x) {
		
		float chunkPositionX = x;
		
		if (chunkPositionX > ChunkSystemTest.getChunkWidthInPixel()) {
			chunkPositionX = x % ChunkSystemTest.getChunkWidthInPixel();
		}
		
		return chunkPositionX;
	}
	
	public static int getChunkWidthInPixel() {
		return TILESIZE * CHUNKS_X;
	}
	
}
