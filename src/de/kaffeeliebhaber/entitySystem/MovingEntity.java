package de.kaffeeliebhaber.entitySystem;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import de.kaffeeliebhaber.animation.Direction;
import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.behavior.moving.IMovingBehavior;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.math.Vector2f;

public abstract class MovingEntity extends Entity {

	protected final IAnimationController animationController;
	protected final IMovingBehavior movingBehavior;
	protected Rectangle district;
	protected Vector2f translationVector;
	
	public MovingEntity(float x, float y, int width, int height, final IAnimationController animationController, final IMovingBehavior movingBehavior) {
		super(x, y, width, height);
		this.animationController = animationController;
		this.movingBehavior = movingBehavior;
		this.movingBehavior.contextMovingEntity(this);
	}

	public void render(Graphics g, Camera camera) {
		g.drawImage(animationController.getImage(), (int) (x - camera.getX()), (int) (y - camera.getY()), width, height, null);
		renderBoundingBox(g, camera);
	}
	
	public void update(final KeyManager keyManager, float timeSinceLastFrame, final List<Entity> entities) {
			
		translationVector = movingBehavior.move(keyManager, timeSinceLastFrame);
		
		// UPDATE ANIMATION
		animationController.updateState(keyManager, translationVector.x, translationVector.y);
		animationController.update(timeSinceLastFrame);
		
		move(entities);
	}
	
	public void moveX() {
		translate(translationVector.x, 0);
		adjustDistricBorder();
	}
	
	public void moveY() {
		translate(0, translationVector.y);
		adjustDistricBorder();
	}
	
	public void setDistrict(final Rectangle district) {
		this.district = district;
	}

	public Rectangle getDestrict() {
		return district;
	}
	
	public IMovingBehavior getMovingBahavior() {
		return movingBehavior;
	}
	
	protected void move(final List<Entity> entities) {
		if (!isCollisionXDir(entities)) {
			moveX();
		}
		
		if (!isCollisionYDir(entities)) {
			moveY();
		}
	}
	
	private boolean isCollisionXDir(final List<Entity> entities) {
		return boundingBoxController.intersects(this, entities, translationVector.x, 0);
	}
	
	private boolean isCollisionYDir(final List<Entity> entities) {
		return boundingBoxController.intersects(this, entities, 0, translationVector.y);
	}
	
	public void updatePosition(float newPosX, float newPosY) {
		final int dx = (int) (newPosX - x);
		final int dy = (int) (newPosY - y);
		
		translate(dx, dy);
		adjustDistricBorder();
	}
	
	protected void adjustDistricBorder() {
		if (district != null) {

			if (boundingBoxController.getX() < district.x) {
				translate(district.x - boundingBoxController.getX(), 0);
			} else if (boundingBoxController.getX() > district.width - boundingBoxController.getWidth()) {
				translate(district.width - boundingBoxController.getWidth()  - boundingBoxController.getX(), 0);
			}
			
			if (boundingBoxController.getY() < district.y) { 
				translate(0, district.y - boundingBoxController.getY());
			} else if (boundingBoxController.getY() > district.height - boundingBoxController.getHeight()) { 
				translate(0, district.height - boundingBoxController.getHeight() - boundingBoxController.getY());
			}
		}
	}
	
	public Direction getViewDirection() {
		return animationController.getViewDirection();
	}
	
}
