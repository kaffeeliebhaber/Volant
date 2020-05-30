package de.kaffeeliebhaber.animation;	

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import de.kaffeeliebhaber.core.Keyboard;

public class DefaultAnimationController implements IAnimationController {

	private AnimationHandler animationHandler;
	private Direction viewDirection;
	private List<Integer> movementKeys;
	
	
	public DefaultAnimationController() {
		this(new AnimationHandler());
	}
	
	public DefaultAnimationController(final AnimationHandler animationHandler) {
		this.animationHandler = animationHandler;
		
		// MOVEMENT KEYS
		movementKeys = new LinkedList<Integer>();
		movementKeys.add(KeyEvent.VK_W);
		movementKeys.add(KeyEvent.VK_A);
		movementKeys.add(KeyEvent.VK_S);
		movementKeys.add(KeyEvent.VK_D);
	}
	
	public void update(final float timeSinceLastFrame) {
		animationHandler.update(timeSinceLastFrame);
	}
	
	public void updateState(final float dx, final float dy) {
		if (animationHandler != null) {
			if (isMoving(dx, dy)) {
				updateMoveAnimation();
			} else {
				updateIdleAnimation();
			}
		}
	}
	
	private void updateMoveAnimation() {
		switch (Keyboard.getMaxPressedKey(movementKeys)) {
			case KeyEvent.VK_A:
				animationHandler.setCurrentAnimation(AnimationConstants.LEFT_MOVE);
				animationHandler.startAnimation();
				viewDirection = Direction.LEFT;
				break;
			case KeyEvent.VK_D:
				animationHandler.setCurrentAnimation(AnimationConstants.RIGHT_MOVE);
				animationHandler.startAnimation();
				viewDirection = Direction.RIGHT;
				break;
			case KeyEvent.VK_S:
				animationHandler.setCurrentAnimation(AnimationConstants.DOWN_MOVE);
				animationHandler.startAnimation();
				viewDirection = Direction.DOWN;
				break;
			case KeyEvent.VK_W:
				animationHandler.setCurrentAnimation(AnimationConstants.TOP_MOVE);
				animationHandler.startAnimation();
				viewDirection = Direction.UP;
				break;
		}		
	}

	private boolean isMoving(final float dx, final float dy) {
		return dx != 0 || dy != 0;
	}
	
	private void updateIdleAnimation() {
		// IDLE POSITION IN LETZTE RICHTUNG
		Animation currentAnimation = animationHandler.getCurrentAnimation();
		if (currentAnimation != null) {
			currentAnimation.stopp();

			switch (currentAnimation.getName()) {
				case AnimationConstants.DOWN_MOVE:
				case AnimationConstants.DOWN_IDLE: 
					animationHandler.setCurrentAnimation(AnimationConstants.DOWN_IDLE); 
					viewDirection = Direction.DOWN;
					break;
					
				case AnimationConstants.LEFT_MOVE: 
				case AnimationConstants.LEFT_IDLE:
					animationHandler.setCurrentAnimation(AnimationConstants.LEFT_IDLE); 
					viewDirection = Direction.LEFT;
					break;
					
				case AnimationConstants.RIGHT_MOVE: 
				case AnimationConstants.RIGHT_IDLE: 
					animationHandler.setCurrentAnimation(AnimationConstants.RIGHT_IDLE); 
					viewDirection = Direction.RIGHT;
					break;
					
				case AnimationConstants.TOP_MOVE: 
				case AnimationConstants.TOP_IDLE: 
					animationHandler.setCurrentAnimation(AnimationConstants.TOP_IDLE); 
					viewDirection = Direction.UP;
					break;
			}
			
			currentAnimation.start();
			
		} else {
			animationHandler.setCurrentAnimation(AnimationConstants.DOWN_IDLE);
		}
	}

	public BufferedImage getImage() {
		return animationHandler.getSprite();
	}

	public Direction getViewDirection() {
		return viewDirection;
	}

	public void addAnimation(Animation animation) {
		if (animationHandler != null) {
			animationHandler.addAnimation(animation);
		}
	}

	public void removeAnimation(Animation animation) {
		if (animationHandler != null) {
			animationHandler.removeAnimation(animation);
		}
	}
}
