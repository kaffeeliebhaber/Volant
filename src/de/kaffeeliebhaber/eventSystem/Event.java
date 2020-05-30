package de.kaffeeliebhaber.eventSystem;

import de.kaffeeliebhaber.entitySystem.Entity;

public abstract class Event {

	private final Entity entity;
	
	public Event(final Entity entity) {
		this.entity = entity;
	}
	
	public Entity getSource() {
		return entity;
	}
}
