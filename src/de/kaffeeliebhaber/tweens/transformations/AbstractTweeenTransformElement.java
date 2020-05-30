package de.kaffeeliebhaber.tweens.transformations;

public abstract class AbstractTweeenTransformElement implements TweenTransformElement {

	protected float startValue;
	protected float endValue;
	protected float currentValue;
	
	public AbstractTweeenTransformElement(final float startValue, final float endValue) {
		this.startValue = startValue;
		this.currentValue = startValue;
		this.endValue = endValue;
	}
	
	@Override public void reset() {
		currentValue = startValue;
	}
}
