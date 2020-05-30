package de.kaffeeliebhaber.entitySystem;

import de.kaffeeliebhaber.math.Vector2f;

public abstract class GameObject {

	protected float x, y;
	protected int width, height;
	
	public GameObject(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
	}
	
	public void setPosition(Vector2f position) {
		setPosition(position.x, position.y);
	}
	
	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Vector2f getCenterPosition() {
		return new Vector2f(x + width * 0.5f, y + height * 0.5f);
	}

	@Override
	public String toString() {
		return "GameObject [x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]";
	}
}
