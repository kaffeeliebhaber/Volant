package de.kaffeeliebhaber.entitySystem;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import de.kaffeeliebhaber.animation.Direction;
import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.behavior.moving.IMovingBehavior;
import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
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

	public void update(float timeSinceLastFrame, final List<Entity> entities) {
			
		translationVector = movingBehavior.move(timeSinceLastFrame);
	
		// UPDATE ANIMATION
		animationController.updateState(translationVector.x, translationVector.y);
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
	
 	public boolean intersects(final Entity entity) {
 		return intersects(entity.getBoundingBox());
	}
	
	public boolean intersects(final BoundingBox entityBoundingBox) {
		return boundingBox != null ? boundingBox.intersects(entityBoundingBox) : false;
	}
	
	private boolean collides(final BoundingBox translatedBoundingBox, final List<Entity> entities) {
		boolean collides = false;
		
		if (translatedBoundingBox != null) {
			for (int i = 0; i < entities.size() && !collides; i++) {
	
				Entity currentEntity = entities.get(i);
				
				if (currentEntity != this && currentEntity.intersects(translatedBoundingBox)) {
					collides = true;
				}
			}
		}
		return collides;
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
		return collides(BoundingBox.createTranslatedBoundingBox(boundingBox, translationVector.x, 0), entities);
	}
	
	private boolean isCollisionYDir(final List<Entity> entities) {
		return collides(BoundingBox.createTranslatedBoundingBox(boundingBox, 0, translationVector.y), entities);
	}
	
	public void render(Graphics g, Camera camera) {
		g.drawImage(animationController.getImage(), (int) (x - camera.getX()), (int) (y - camera.getY()), width, height, null);
	}
	
	protected void adjustDistricBorder() {
		if (district != null) {
			
			if (boundingBox.getX() < district.x) {
				translate(district.x - boundingBox.getX(), 0);
			} else if (boundingBox.getX() > district.width - boundingBox.getWidth()) {
				translate(district.width - boundingBox.getWidth()  - boundingBox.getX(), 0);
			}
			
			if (boundingBox.getY() < district.y) { 
				translate(0, district.y - boundingBox.getY());
			} else if (boundingBox.getY() > district.height - boundingBox.getHeight()) { 
				translate(0, district.height - boundingBox.getHeight() - boundingBox.getY());
			}
		}
	}
	
	public Direction getViewDirection() {
		return animationController.getViewDirection();
	}
	
}
