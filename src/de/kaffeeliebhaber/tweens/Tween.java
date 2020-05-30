package de.kaffeeliebhaber.tweens;

public interface Tween {
	
	int getID();
	
	void enter();
	
	void exit();
	
	void reset();
	
	void update(final float timeSinceLastFrame);
	
	void addTweenStateListener(final TweenStateListener l);
	
	void removeTweenStateListener(final TweenStateListener l);
}
