package de.kaffeeliebhaber.xml.tiledEditor.Freeform;

public class TiledFreeformObject {

	private int ID;
	private String type;
	private float x;
	private float y;
	private int width;
	private int height;
	
	public TiledFreeformObject(int ID, String type, float x, float y, int width, int height) {
		this.ID = ID;
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public int getID() {
		return ID;
	}

	public String getType() {
		return type;
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

	@Override
	public String toString() {
		return "TiledFreeformObject [ID= " + ID + ", type=" + type + ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height
				+ "]";
	}
}
