package de.kaffeeliebhaber.math;

public enum Sign {

	POSITIVE(1), NEGATIVE(-1);

	private int value;
	
	private Sign(final int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
