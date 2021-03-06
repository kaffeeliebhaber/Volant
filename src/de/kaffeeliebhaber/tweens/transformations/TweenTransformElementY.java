package de.kaffeeliebhaber.tweens.transformations;

import de.kaffeeliebhaber.ui.inventory.UIElement;

public class TweenTransformElementY extends AbstractTweeenTransformElement {

	public TweenTransformElementY(final float startValue, final float endValue) {
		super(startValue, endValue);
	}
	
	public void transform(final UIElement element, final float tweenFunctionValue, final float timeSinceLastFrame) {
		currentValue += tweenFunctionValue;
		element.setY(currentValue);
	}
	
	public boolean isEndReached() {
		return currentValue >= endValue;
	}
}
