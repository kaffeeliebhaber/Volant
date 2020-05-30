package de.kaffeeliebhaber.tweens.functions;

import de.kaffeeliebhaber.math.Sign;

public class TweenFunctionExp implements TweenFunction {

	private Sign sign;
	private float alpha;
	private float speed;
	
	public TweenFunctionExp(final Sign sign, final float alpha, final float speed) {
		this.sign = sign;
		this.alpha = alpha;
		this.speed = speed;
	}
	
	@Override public float calc(final float value) {
		return sign.getValue() * alpha* (float) Math.exp(speed * value);
	}

}
