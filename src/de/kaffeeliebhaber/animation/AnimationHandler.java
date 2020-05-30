package de.kaffeeliebhaber.animation;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class AnimationHandler {

	private Map<String, Animation> animations;
	private Animation currentAnimation;
	
	public AnimationHandler() {
		animations = new HashMap<String, Animation>();
	}
	
	public void addAnimation(final Animation animation) {
		if (animation != null) {
			animations.put(animation.getName(), animation);
		}
	}
	
	public void removeAnimation(final Animation animation) {
		if (animation != null && animations.containsKey(animation.getName())) {
			animations.remove(animation.getName());
		}
	}
	
	public boolean containsAnimation(final String key) {
		return animations.containsKey(key);
	}
	
	public Animation getAnimation(final String key) {
		Animation animation = null;
		
		if (this.containsAnimation(key)) {
			animation = animations.get(key);
		}
		
		return animation;
	}
	
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}
	
	public void setCurrentAnimation(final String key) {
		
		if (currentAnimation == null || !currentAnimation.getName().equalsIgnoreCase(key)) {
			if (currentAnimation != null) {
				currentAnimation.stopp();
			}
			currentAnimation = getAnimation(key);
		}
	}
	
	public void startAnimation() {
		if (currentAnimation != null) {
			currentAnimation.start();
		}
	}
	
	public void update(float timeSinceLastFrame) {
		currentAnimation.update(timeSinceLastFrame);
	}
	
	public BufferedImage getSprite() {
		return currentAnimation.getSprite();
	}
	
    @Override
    public String toString() {
    	return currentAnimation.getName();
    }
}