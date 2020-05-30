package de.kaffeeliebhaber.animation;

import java.awt.image.BufferedImage;

public interface IAnimationController {

	void update(final float timeSinceLastFrame);
	
	void addAnimation(final Animation animation);
	
	void removeAnimation(final Animation animation);
	
	void updateState(final float dx, final float dy);
	
	Direction getViewDirection();
	
	BufferedImage getImage();
}
