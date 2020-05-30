package de.kaffeeliebhaber.tilesystem;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Entity;

public class Tilemap {

	private List<Entity> entities;
	private int rows;
	private int cols;
	private int tileHeight;
	private int tileWidth;
	private Tile[][] tiles;
	
	public Tilemap(int cols, int rows, int tileHeight, int tileWidth) {
		
		this.rows = rows;
		this.cols = cols;
		
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;
		
		tiles = new Tile[cols][rows];
		entities = new LinkedList<Entity>();
		
	}
	
	public void setTiles(Tile[][] tiles) {
		if (tiles.length == this.tiles.length && tiles[0].length == this.tiles[0].length) {
			this.tiles = tiles;
		}
	}
	
	public void addEntity(final Entity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(final Entity entity) {
		entities.remove(entity);
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public void update(float timeSinceLastFrame) {}
	
	public void render(Graphics g, Camera camera) {
		
		// HOW MANY TILES MUST BE DRAWN: X-DIRECTION
		int startX = (int) camera.getX() / tileWidth; 
		int endX = (int) (camera.getX() + camera.getWidth()) / tileWidth + 1;
		
		// HOW MANY TILES MUST BE DRAWN: Y-DIRECTION
		int startY = (int) camera.getY() / tileHeight; 
		int endY = (int) (camera.getY() + camera.getHeight()) / tileHeight + 1;
		
		if (startX < 0) {
			startX = 0;
		} else if (endX >= cols) {
			endX = cols - 1;
		}
		
		if (startY < 0) {
			startY = 0;
		} else if (endY >= rows) {
			endY = rows - 1;
		}
		
		for (int x = startX; x <= endX; x++) {
			for (int y = startY; y <= endY; y++) {
			
				if (tiles[x][y] != null) {
					tiles[x][y].render(g, camera);
				}
			}
		}
	}
	
	public void addTile(final Tile tile, final int y, final int x) {
		if (tile != null && y >= 0 && y < rows && x >= 0 && x < cols) {
			tiles[y][x] = tile;
		}
	}
	
	public Tile getTile(final int x, final int y) {
		
		Tile tile = null;
		
		if (x >= 0 && x < cols && y >= 0 && y < rows) {
			tile = tiles[x][y];
		}
		
		return tile;
	}
	
	public Tile getTilePixel(final int pixelsX, final int pixelsY) {
		return getTile( (int) (pixelsX / tileWidth), (int) (pixelsY / tileHeight));
	}
	
	/*
	 Diese Funktion liefert alle benachbarten Tiles um die Tile-Position (x, y). Dabei wird das Tile an der Stelle (x, y) selbst 
	 nicht berücksichtigt.
	 */	
	public List<Tile> getAdjacentTiles(final int x, final int y) {
		List<Tile> adjacentTiles = new ArrayList<Tile>();
		//System.out.println("y: " + y + ", x: " + x + ", rows: " + rows + ", cols: " + cols);
		// CHECK FOR FIRST POSSIBLE X POSITIONS (LEFT - RIGHT)
		int startX = x - 1 < 0 ? 0 : x - 1;
		int endX = x + 1 >= cols ? (cols-1) : x + 1;
		
		// CHECK FOR FIRST POSSIBLE Y POSITIONS (TOP - BOTTOM)
		int startY = y - 1 < 0 ? 0 : y - 1;
		int endY = y + 1 >= (rows-1) ? y : y + 1;
		
		Tile localTile = null;
		
		//System.out.println("SX: " + startX + ", EX: " + endX + ", SY: " + startY + ", EY: " + endY);
		for (int newX = startX; newX <= endX; newX++) {
			for (int newY = startY; newY <= endY; newY++) {
				//System.out.println("newY: " + newY + ", newX: " + newX);
				localTile = tiles[newX][newY];
				
				if (localTile != null) {
					adjacentTiles.add(localTile);
				}
			}
		}
		//System.out.println("-------------------------------------");
		
		return adjacentTiles;
	}
	
	@Override
	public String toString() {
		String tileMap = "";
		for (int x = 0; x < cols; x++) {
			for (int y = 0; y < rows; y++) {
				tileMap += tiles[x][y] + ", ";
			}
			tileMap += "\n";
		}
		
		return tileMap;
	}
}
