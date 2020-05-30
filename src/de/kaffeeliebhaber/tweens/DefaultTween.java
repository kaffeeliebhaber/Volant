package de.kaffeeliebhaber.tweens;

import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.tweens.functions.TweenFunction;
import de.kaffeeliebhaber.tweens.transformations.TweenTransformElement;
import de.kaffeeliebhaber.ui.inventory.UIElement;

public class DefaultTween implements Tween {

	protected final int ID;
	protected UIElement element;
	protected boolean active;
	protected boolean finish;
	protected TweenFunction tweenFunction;
	private List<TweenStateListener> tweenStateListeners;
	private TweenTransformElement transformElement;
	
	public DefaultTween(final int ID, final UIElement element, final TweenFunction tweenFunction, final TweenTransformElement transformElement) {
		this.ID = ID;
		this.element = element;
		this.tweenFunction = tweenFunction;
		
		tweenStateListeners = new ArrayList<TweenStateListener>();
		
		this.transformElement = transformElement;
	}
	
	@Override public void enter() {
		active = true;
	}

	@Override public void exit() {
		finish = true;
		
		transformElement.reset();
		
		fireTweenEndedEvent();
	}

	@Override public void update(float timeSinceLastFrame) {
	
		if (!finish) {
			
			updatePosition(timeSinceLastFrame);
			
			if (isEndReached()) {
				exit();
			}
		}
	}

	protected void updatePosition(final float timeSinceLastFrame) {
		final float tweenFunctionValue = tweenFunction.calc(timeSinceLastFrame);
		transformElement.transform(element, tweenFunctionValue, timeSinceLastFrame);
	}
	
	protected boolean isEndReached() {
		return transformElement.isEndReached();
	}
	
	private void fireTweenEndedEvent() {
		final int size = tweenStateListeners.size();
		
		for (int i = 0; i < size; i++) {
			tweenStateListeners.get(i).ended(new TweenEvent(this));
		}
	}

	@Override public void addTweenStateListener(TweenStateListener l) {
		tweenStateListeners.add(l);
	}

	@Override public void removeTweenStateListener(TweenStateListener l) {
		tweenStateListeners.remove(l);
	}

	@Override public int getID() {
		return ID;
	}

	@Override public void reset() {
		active = false;
		finish = false;
	}
}
