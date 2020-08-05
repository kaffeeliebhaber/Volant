package de.kaffeeliebhaber.animation;

import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.core.KeyManager;

public abstract class AbstractAnimationHandler implements IAnimationController {

	protected AnimationHandler animationHandler;
	
	public AbstractAnimationHandler() {
		this(new AnimationHandler());
	}
	
	public AbstractAnimationHandler(final AnimationHandler animationHandler) {
		this.animationHandler = animationHandler;
	}
	
	public abstract void update(float timeSinceLastFrame);

	public void addAnimation(Animation animation) {
		animationHandler.addAnimation(animation);
	}

	public void removeAnimation(Animation animation) {
		animationHandler.removeAnimation(animation);
	}

	public abstract void updateState(final KeyManager keyManager, float dx, float dy);

	public abstract Direction getViewDirection();

	public BufferedImage getImage() {
		return animationHandler.getSprite();
	}

}
