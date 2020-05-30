package de.kaffeeliebhaber.managers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.entitySystem.EntityComparator;

public class EntityManager {

	private final EntityComparator comperator; 
	private final List<Entity> entities;
	
	public EntityManager() {
		entities = new ArrayList<Entity>();
		comperator = new EntityComparator(); 
	}
	
	public void clear() {
		entities.clear();
	}
	
	public void add(final Entity entity) {
		entities.add(entity);
	}
	
	public void addAll(final List<Entity> entities) {
		this.entities.addAll(entities);
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public void ySort() {
		entities.sort(comperator);
	}
	
	public void render(final Graphics g, final Camera camera) {
		entities.forEach(e -> e.render(g, camera));
	}
}
