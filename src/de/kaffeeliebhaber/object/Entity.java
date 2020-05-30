package de.kaffeeliebhaber.object;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.math.Vector2f;

public abstract class Entity extends GameObject {

	private Collection<IEntityListener> entityListeners;
	protected BoundingBox boundingBox;
	
	public Entity(float x, float y, int width, int height) {
		super(x, y, width, height);
		entityListeners = new ArrayList<IEntityListener>();
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	
	public void setBoundingBox(final BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	protected void translateX(final float dx) {
		setX(this.x + dx);
	}
	
	protected void translateY(final float dy) {
		setY(this.y + dy);
	}
	
	@Override public void setX(final float newX) {
		super.setX(newX);
		notifyEntityListenerEntityUpdated();
	}
	
	@Override public void setY(final float newY) {
		super.setY(newY);
		notifyEntityListenerEntityUpdated();
	}
	
	public void setPosition(Vector2f position) {
		super.setPosition(position);
		notifyEntityListenerEntityUpdated();
	}
	
	public abstract void update(float timeSinceLastFrame);
	
	public abstract void render(Graphics g, Camera c);
	
	protected void notifyEntityListenerEntityUpdated() {
		entityListeners.forEach(e -> e.entityUpdated(this));
	}
	
	public void addEntityUpdateListener(IEntityListener l) {
		entityListeners.add(l);
	}
	
	public void removeEntityUpdateListener(IEntityListener l) {
		entityListeners.remove(l);
	}
	
	public void addEntityUpdateListeners(final List<? extends IEntityListener> listeners) {
		entityListeners.addAll(listeners);
	}
	
	public void removeEntityUpdateListeners(final List<? extends IEntityListener> listeners) {
		entityListeners.removeAll(listeners);
	}
}
