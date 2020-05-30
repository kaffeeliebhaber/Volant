package de.kaffeeliebhaber.tweens;

import java.util.ArrayList;
import java.util.List;

public class TweenManager implements TweenStateListener {

	private List<TweenManagerListener> tweenManagerListeners;
	private List<Tween> tweens;
	private Tween currentTween;
	
	public TweenManager() {
		tweens = new ArrayList<Tween>();
		tweenManagerListeners = new ArrayList<TweenManagerListener>();
	}
	
	public void update(final float timeSinceLastFrame) {
		if (currentTween != null) {
			currentTween.update(timeSinceLastFrame);
		} 
	}
	
	public void setCurrentTween(final int ID) {
		if (ID >= 0 && ID < tweens.size()) {
			setCurrentTween(tweens.get(ID));
		}
	}
	
	public void setCurrentTween(final Tween currentTween) {
		this.currentTween = currentTween;
	}
	
	public Tween getCurrentTween() {
		return currentTween;
	}
	
	public void addTween(final Tween tween) {
		tweens.add(tween);
		tween.addTweenStateListener(this);
	}
	
	public void removeTween(final Tween tween) {
		tweens.remove(tween);
		tween.removeTweenStateListener(this);
	}
	
	@Override
	public void ended(TweenEvent event) {
		changeTween(event.getTween());
	}
	
	private void changeTween(final Tween lastTween) {
		final Tween nextTween = getNextTween(lastTween);
		setCurrentTween(nextTween);
		notifyAllTweenManagerListener(lastTween, nextTween);
	}
	
	private Tween getNextTween(final Tween currentTween) {
		Tween nextTween = null;
		
		final int currentTweenIndex = tweens.indexOf(currentTween);
		
		if (tweens.size() > currentTweenIndex + 1) {
			nextTween = tweens.get(tweens.indexOf(currentTween) + 1);
		}
		
		return nextTween;
	}
	
	public void addTweenManagerListener(final TweenManagerListener l) {
		tweenManagerListeners.add(l);
	}
	
	public void removeTweenManagerListener(final TweenManagerListener l) {
		tweenManagerListeners.remove(l);
	}
	
	private void notifyAllTweenManagerListener(final Tween oldTween, final Tween newTween) {
		final int size = tweenManagerListeners.size();
		
		for (int i = 0; i < size; i++) {
			tweenManagerListeners.get(i).tweenChanged(oldTween, newTween);
		}
	}
	
	public void reset() {
		currentTween = tweens.get(0);
		tweens.forEach(e -> e.reset());
	}
	
}
