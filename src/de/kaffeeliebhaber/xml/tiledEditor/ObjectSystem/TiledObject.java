package de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem;

public class TiledObject {

	private final int GID;
	private float x, y;
	private int width, height;
	
	public TiledObject(final int GID, float x, float y, int width, int height) {
		this.GID = GID;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getGID() {
		return GID;
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
		return "TiledObject [ID=" + GID + ", x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + "]\n";
	}
	
}
