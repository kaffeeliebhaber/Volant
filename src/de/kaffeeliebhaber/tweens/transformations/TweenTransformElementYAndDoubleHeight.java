package de.kaffeeliebhaber.tweens.transformations;

import de.kaffeeliebhaber.newUI.UIElement;

public class TweenTransformElementYAndDoubleHeight extends AbstractTweeenTransformElement {
	
	public TweenTransformElementYAndDoubleHeight(final float startValue, final float endValue) {
		super(startValue, endValue);
	}
	
	@Override public void transform(UIElement element, float tweenFunctionValue, float timeSinceLastFrame) {
		currentValue -= tweenFunctionValue;
		
		int height = (int) (2 * Math.abs(startValue - currentValue));
		
		element.setY(currentValue);
		element.setHeight(height); 
	}

	@Override public boolean isEndReached() {
		return currentValue <= endValue;
	}

}
