package de.kaffeeliebhaber.controller;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Entity;

public class BoundingBoxController {

	private List<BoundingBox> boundingBoxes;
	private float xMin, yMin;
	private int width, height;
	
	public BoundingBoxController() {
		boundingBoxes = new ArrayList<BoundingBox>();
		resetMinMaxValues();
	}
	
	public List<BoundingBox> getBoundingBoxes() {
		return new ArrayList<BoundingBox>(boundingBoxes);
	}
	
	public void addBoundingBox(final BoundingBox boundingBox) {
		boundingBoxes.add(boundingBox);
	}
	
	public void addBoundingBoxes(final List<BoundingBox> boundingBoxes) {
		this.boundingBoxes.addAll(new ArrayList<BoundingBox>(boundingBoxes));
	}
	
	public boolean intersects(final Entity entity) {
		return intersects(entity.getBoundingBoxController());
	}
	
	public boolean intersects(final BoundingBoxController controller) {
		return intersects(controller.getBoundingBoxes());
	}
	
	public boolean intersects(final Entity callerEntity, final List<Entity> entities, final float dx, final float dy) {
		boolean intersects = false;

		final int size = boundingBoxes.size();
		
		for (int i = 0; i < size && !intersects; i++) {
			final BoundingBox currentTranslatedBoundingBox = boundingBoxes.get(i).createTranslatedBoundingBox(dx, dy);
			
			final int entityListSize = entities.size();
			
			for (int j = 0; j < entityListSize && !intersects; j++) {
				
				Entity currentEntity = entities.get(j);
				if (currentEntity != callerEntity && currentTranslatedBoundingBox.intersects(currentEntity.getBoundingBoxController().getBoundingBoxes()))
				{
					intersects = true;
				}
			}
		}
		
		return intersects;
	}
	
	private boolean intersects(final List<BoundingBox> boundingBoxes) {
		boolean intersects = false;

		final int size = this.boundingBoxes.size();

		for (int i = 0; i < size && !intersects; i++) {
			intersects = boundingBoxes.get(i).intersects(this.boundingBoxes);
		}

		return intersects;
	}
	
	public void translate(final float dx, final float dy) {
		boundingBoxes.stream().forEach(b -> b.translate(dx, dy));
		resetMinMaxValues();
		calcBoundingBoxesDimensions();
	}
	
	private void resetMinMaxValues() {
		xMin = Integer.MAX_VALUE;
		yMin = Integer.MAX_VALUE;
	}
	
	public void calcBoundingBoxesDimensions() {
		final int size = boundingBoxes.size();
		
		float xMax = Integer.MIN_VALUE;
		float yMax = Integer.MIN_VALUE;

		
		for (int i = 0; i < size; i++) {
			final BoundingBox currentBoundingBox = boundingBoxes.get(i);
			
			float currentX = currentBoundingBox.getX();
			float currentY = currentBoundingBox.getY();
			int currentWidth = currentBoundingBox.getWidth();
			int currentHeight = currentBoundingBox.getHeight();
			
			// x
			if (currentX < xMin) {
				xMin = currentX;
			}
			
			if ((currentX + currentWidth) > xMax) {
				xMax = currentX + currentWidth;
			}

			// y
			if (currentY < yMin) {
				yMin = currentY;
			}
			
			if ((currentY + currentHeight) > yMax) {
				yMax = currentY + currentHeight;
			}
		}
		
		width = (int) (xMax - xMin);
		height = (int) (yMax - yMin);
	}
	
	public boolean areBoundingBoxesAvailable() {
		return boundingBoxes.size() > 0;
	}
	
	public float getX() {
		return xMin;
	}
	
	public float getY() {
		return yMin;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void render(final Graphics g, final Camera c) {
		boundingBoxes.stream().forEach(b -> b.render(g, c));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boundingBoxes == null) ? 0 : boundingBoxes.hashCode());
		result = prime * result + height;
		result = prime * result + width;
		result = prime * result + Float.floatToIntBits(xMin);
		result = prime * result + Float.floatToIntBits(yMin);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BoundingBoxController other = (BoundingBoxController) obj;
		if (boundingBoxes == null) {
			if (other.boundingBoxes != null)
				return false;
		} else if (!boundingBoxes.equals(other.boundingBoxes))
			return false;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		if (Float.floatToIntBits(xMin) != Float.floatToIntBits(other.xMin))
			return false;
		if (Float.floatToIntBits(yMin) != Float.floatToIntBits(other.yMin))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "x: " + this.xMin + ", y: " + this.yMin + ", width: " + this.width + ", height: " + this.height;
	}
	
}
