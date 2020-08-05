package de.kaffeeliebhaber.entitySystem;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.controller.BoundingBoxController;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.debug.Debug;
import de.kaffeeliebhaber.math.Vector2f;

public abstract class Entity extends GameObject {

	protected Collection<IEntityListener> entityListeners;
	protected BoundingBoxController boundingBoxController;

	public Entity(float x, float y, int width, int height) {
		super(x, y, width, height);
		entityListeners = new ArrayList<IEntityListener>();
		boundingBoxController = new BoundingBoxController();
	}

	public void addBoundingBox(final BoundingBox boundingBox) {
		boundingBoxController.addBoundingBox(boundingBox);
	}
	
	public void addBoundingBoxes(final List<BoundingBox> boundingBoxes) {
		boundingBoxController.addBoundingBoxes(boundingBoxes);
	}

	public BoundingBoxController getBoundingBoxController() {
		return boundingBoxController;
	}

	public boolean intersects(final Entity entity) {
		return boundingBoxController.intersects(entity.getBoundingBoxController());
	}

	protected void translate(final float dx, final float dy) {
		setX(x + dx);
		setY(y + dy);
		translateBoundingBoxes(dx, dy);
	}

	public void translateBoundingBoxes(final float dx, final float dy) {
		boundingBoxController.translate(dx, dy);
	}

	public void setX(final float newX) {
		super.setX(newX);
		notifyEntityListenerEntityUpdated();
	}

	public void setY(final float newY) {
		super.setY(newY);
		notifyEntityListenerEntityUpdated();
	}

	public void setPosition(Vector2f position) {
		super.setPosition(position);
		notifyEntityListenerEntityUpdated();
	}
	
	public abstract void update(final KeyManager keyManager, float timeSinceLastFrame, final List<Entity> entities);

	public abstract void render(Graphics g, Camera c);
	
	protected void renderBoundingBox(Graphics g, Camera camera) {
		if (Debug.ENTITY_RENDERBOUNDINGBOXES_SHOW_BOUNDINGBOX) {
			boundingBoxController.render(g, camera);
		}
	}

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
