package de.kaffeeliebhaber.tilesystem;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import de.kaffeeliebhaber.core.Camera;

public class TilemapHandler {

	private Map<Integer, Tilemap> tilemaps;
	
	public TilemapHandler() {
		tilemaps = new HashMap<Integer, Tilemap>();
	}
	
	public void addTilemap(int id, Tilemap tilemap) {
		if (!tilemaps.containsKey(id)) {
			tilemaps.put(id, tilemap);
		}
	}
	
	public void removeTilemap(int id) {
		if (tilemaps.containsKey(id)) {
			tilemaps.remove(id);
		}
	}
	
	public boolean containsTilemapId(int id) {
		return tilemaps.containsKey(id);
	}
	
	public Tilemap getTilemap(int id) {
		
		Tilemap tilemap = null;
		
		if (containsTilemapId(id)) {
			tilemap = tilemaps.get(id);
		}
		
		return tilemap;
	}
	
	public void update(float timeSinceLastFrame) {
		for (final Tilemap tilemap : tilemaps.values()) {
			tilemap.update(timeSinceLastFrame);
		}
	}
	
	public void render(Graphics g, Camera c) {
		for (final Tilemap tilemap : tilemaps.values()) {
			tilemap.render(g, c);
		}
	}
}
