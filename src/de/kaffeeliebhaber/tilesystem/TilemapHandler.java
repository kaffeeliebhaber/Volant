package de.kaffeeliebhaber.tilesystem;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.core.Camera;

public class TilemapHandler {

	private List<Tilemap> tilemaps;
	
	public TilemapHandler() {
		tilemaps = new ArrayList<Tilemap>();
	}
	
	public void addTilemap(final Tilemap tilemap) {
		tilemaps.add(tilemap);
	}
	
	public Tilemap getTilemap(final int tilemapID) {
		return tilemaps
				.stream()
				.filter(t -> t.getID() == tilemapID)
				.findFirst()
				.get();
	}
	
	public void update(float timeSinceLastFrame) {
		tilemaps.stream().forEach(t -> t.update(timeSinceLastFrame));
	}
	
	public void render(Graphics g, Camera c) {
		tilemaps.stream().forEach(t -> t.render(g, c));
	}
	/*
	private Map<Integer, List<Tilemap>> tilemaps;
	
	public TilemapHandler() {
		tilemaps = new HashMap<Integer, List<Tilemap>>();
	}
	
	public void addTilemap(int ID, Tilemap tilemap) {
		if (!tilemaps.containsKey(ID)) {
			tilemaps.put(ID, tilemap);
		}
		
		System.out.println("(TilemapHandler.addTilemap) | Tilemap added | ID: " + ID + "size: " + tilemaps.size());
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
	*/
}
