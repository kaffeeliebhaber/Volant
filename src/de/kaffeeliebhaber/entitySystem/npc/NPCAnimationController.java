package de.kaffeeliebhaber.entitySystem.npc;

import de.kaffeeliebhaber.animation.AbstractAnimationHandler;
import de.kaffeeliebhaber.animation.AnimationConstants;
import de.kaffeeliebhaber.animation.Direction;
import de.kaffeeliebhaber.core.KeyManager;

public class NPCAnimationController extends AbstractAnimationHandler {

	private Direction viewDirection;
	
	public void update(float timeSinceLastFrame) {
		animationHandler.update(timeSinceLastFrame);
	}

	public void updateState(final KeyManager keyManager, float dx, float dy) {
		
		if (dx > 0) {
			animationHandler.setCurrentAnimation(AnimationConstants.RIGHT_MOVE);
			viewDirection = Direction.DOWN;
		} else if ( dx == 0 && dy == 0) {
			animationHandler.setCurrentAnimation(AnimationConstants.DOWN_IDLE);
			viewDirection = Direction.DOWN;
		} else {
			animationHandler.setCurrentAnimation(AnimationConstants.LEFT_MOVE);
			viewDirection = Direction.DOWN;
		}
		
		animationHandler.startAnimation();
	}

	public Direction getViewDirection() {
		return viewDirection;
	}

}
