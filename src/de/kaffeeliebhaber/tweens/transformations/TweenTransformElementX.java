package de.kaffeeliebhaber.tweens.transformations;

import de.kaffeeliebhaber.ui.inventory.UIElement;

public class TweenTransformElementX extends AbstractTweeenTransformElement {
	
	public TweenTransformElementX(final float startValue, final float endValue) {
		super(startValue, endValue);
	}
	
	public void transform(final UIElement element, final float tweenFunctionValue, final float timeSinceLastFrame) {
		currentValue += tweenFunctionValue;
		element.setX(currentValue);
	}
	
	public boolean isEndReached() {
		return currentValue >= endValue;
	}
}
