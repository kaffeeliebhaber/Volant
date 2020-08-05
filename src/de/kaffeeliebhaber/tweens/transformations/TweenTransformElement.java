package de.kaffeeliebhaber.tweens.transformations;

import de.kaffeeliebhaber.newUI.UIElement;

public interface TweenTransformElement {

	void transform(final UIElement element, final float tweenFunctionValue, final float timeSinceLastFrame);
	
	boolean isEndReached();
	
	void reset();
}
