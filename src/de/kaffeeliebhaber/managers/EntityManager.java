package de.kaffeeliebhaber.managers;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.entitySystem.EntityComparator;

public class EntityManager {

	/**
	 * 
	 * Eine Idee für den EntityManager wäre, dass dieser für den jeweils aktuellen Chunk
	 * alle zusätzlichen Entities abspeichert. Zum Beispiel Pfeile etc. Beim Wechsel eines CHunks wird
	 * der EntityManager dann geleert.
	 * 
	 * DOch die Frage ist: Wie prüfen wir dann die Kollisionen?
	 * Weitere Frage: Wie lösen wir das Problem bzgl. des Renderns?
	 * 
	 */
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
