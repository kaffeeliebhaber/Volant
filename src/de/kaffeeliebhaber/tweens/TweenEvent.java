package de.kaffeeliebhaber.tweens;

public class TweenEvent {

	private Tween tween;
	
	public TweenEvent(final Tween tween) {
		setTween(tween);
	}
	
	public void setTween(final Tween tween) {
		this.tween = tween;
	}
	
	public Tween getTween() {
		return tween;
	}
}
