package de.kaffeeliebhaber.core;

import java.awt.Dimension;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.entitySystem.GameObject;
import de.kaffeeliebhaber.entitySystem.IEntityListener;
import de.kaffeeliebhaber.math.Vector2f;

public class Camera extends GameObject implements IEntityListener {

	// Game dimension
	private final Dimension gameDimension;
	
	// Current game object
	private Entity currentEntity;

	public Camera(float startX, float startY, int cameraWidth, int cameaHeight, Dimension gameDimension) {
		super(startX, startY, cameraWidth, cameaHeight);
		this.gameDimension = gameDimension;
	}
	
	@Override
	public void entityUpdated(Entity e) {
		centerOnGameObject(e);
	}
	
	// Ausrichtung der x-Ausprägung nach links und rechts. 
	// Befindet sich die Kamera von der Ausprägung am linken oder rechten Rand des Spiels, werden die X-Ausprägungen auf 0 oder (Spielfeldbreit - Sichtbreit) gesetzt.
	private float alignX(float x) {
		
		if (x < 0) {
			x = 0;
		} else if (x > gameDimension.width - width) {
			x = gameDimension.width - width;
		}
		
		return x;
	}
	
	// Ausrichtung der y-Ausprägung nach oben und unten. 
	// Befindet sich die Kamera von der Ausprägung am oberen oder unteren Rand des Spiels, werden die y-Ausprägungen auf 0 oder (Spielfeldhöhe - Sichthöhe) gesetzt.
	private float alignY(float y) {

		if (y < 0) {
			y = 0;
		} else if (y > gameDimension.height - height) {
			y = gameDimension.height - height;
		}
		
		return y;
	}
	
	/*
	 * Das übergebene Spielobjekt wird als Ankerpunkt gesetzt. 
	 * Die Kamera entfernt sich als Listener dem noch hinterlegten Spielobjekt.
	 * Anschließend wird das neue Spielobjekt gesetzt.
	 * Die Kamera registriert sich als Listener dem neuen Spielobjekt.
	 */
	public void focusOn(final Entity entity) {
		
		if (entity != null && this.currentEntity != entity) {
			
			removeCurrentGameObjectListener();
			setGameObject(entity);
			centerOnGameObject(this.currentEntity);
		}
	}
	
	// Entfernt die Kamera als GameObjektListener aus dem aktuellen Spielobjekt (Ankerobjekt).
	private void removeCurrentGameObjectListener() {
		if (currentEntity != null) {
			currentEntity.removeEntityUpdateListener(this);
		}
	}
	
	// Setzt das übergebene Spielobjekt als aktuelles Ankerobjekt.
	private void setGameObject(final Entity entity) {
		this.currentEntity = entity;
		this.currentEntity.addEntityUpdateListener(this);
	}
	
	// Die Kamera wird auf das übergebene Spielobjekt gesetzt.
	private void centerOnGameObject(final Entity entity) {
		
		// Mittelpunkt des <GameObjects>. 
		final Vector2f entityCenter = entity.getCenterPosition();
		
		// Camera Position (TOP-LEFT)
		float cX = entityCenter.x - width * 0.5f;
		float cY = entityCenter.y - height * 0.5f;
		
		// Ausrichten der x-Position | Prüfen, ob die Grenzen erreicht wurden.
		setX(alignX(cX));
		
		// Ausrichten der y-Position | Prüfen, ob die Grenzen erreicht wurden.
		setY(alignY(cY));
	}
	
	public boolean isVisible(final GameObject gameObject) {
		final BoundingBox boundingBoxCamera = new BoundingBox((int) x, (int) y, width, height);
		final BoundingBox boundingBoxGameObject = new BoundingBox((int) gameObject.getX(), (int) gameObject.getY(), gameObject.getWidth(), gameObject.getHeight());
		return boundingBoxCamera.intersects(boundingBoxGameObject);
	}
	
	@Override
	public String toString() {
		return "(Camera) " + super.toString();
	}
	
}