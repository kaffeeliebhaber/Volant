package de.kaffeeliebhaber.animation;

import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.core.KeyManager;

public interface IAnimationController {

	void update(final float timeSinceLastFrame);
	
	void addAnimation(final Animation animation);
	
	void removeAnimation(final Animation animation);
	
	void updateState(final KeyManager keyManager, final float dx, final float dy);
	
	Direction getViewDirection();
	
	BufferedImage getImage();
}
