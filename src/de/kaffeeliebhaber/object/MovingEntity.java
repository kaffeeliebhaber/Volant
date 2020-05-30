package de.kaffeeliebhaber.object;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import de.kaffeeliebhaber.animation.Direction;
import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.math.Vector2f;
import de.kaffeeliebhaber.object.movingBehavior.IMovingBehavior;

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

	public void update(float timeSinceLastFrame) {
			
		translationVector = movingBehavior.move(timeSinceLastFrame);
	
		// UPDATE ANIMATION
		animationController.updateState(translationVector.x, translationVector.y);
		animationController.update(timeSinceLastFrame);
		
		adjustDistricBorder();
	}
	
	public boolean isCollidable() {
		return true;
	}
	
	public float getDx() {
		return translationVector.x;
	}

	public float getDy() {
		return translationVector.y;
	}
	
	public void moveX() {
		translateX(translationVector.x);
		boundingBox.translateX(translationVector.x);
	}
	
	public void moveY() {
		translateY(translationVector.y);
		boundingBox.translateY(translationVector.y);
	}
	
	public void move() {
		moveX();
		moveY();
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
	
	public boolean collides(final Entity entity) {
		return boundingBox.intersects(entity.getBoundingBox());
	}
	
	public boolean collides(final List<Entity> entities) {
		boolean collides = false;
		
		final int size = entities.size();
		
		for (int i = 0; i < size || !collides; i++) {
			collides = boundingBox.intersects(entities.get(i).getBoundingBox());
		}
		
		return collides;
	}
	
	public void render(Graphics g, Camera camera) {
		g.drawImage(animationController.getImage(), (int) (x - camera.getX()), (int) (y - camera.getY()), width, height, null);
	}
	
	protected void adjustDistricBorder() {
		if (district != null) {
			
			if (x < district.x) {
				setX(district.x);
			} else if (x > district.width - width) {
				setX(district.width - width);
			}
			
			if (y < district.y) { 
				setY(district.y);
			} else if (y > district.height - height) { 
				setY(district.height - height);
			}
		}
	}
	
	public Direction getViewDirection() {
		return animationController.getViewDirection();
	}
	
}
