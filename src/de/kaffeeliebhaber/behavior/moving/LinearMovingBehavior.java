package de.kaffeeliebhaber.behavior.moving;

import java.util.Random;

import de.kaffeeliebhaber.entitySystem.MovingEntity;
import de.kaffeeliebhaber.math.Vector2f;

public class LinearMovingBehavior implements IMovingBehavior {

	private final int distance = 3;
	private final int squareDistance = distance * distance;
	
	// AREA DIMENSION
	private final float areaPosX;
	private final float areaPosY;
	private final int areaWidth;
	private final int areaHeight;
	
	private Vector2f positionStart;
	private Vector2f positionCurrent;
	private Vector2f positionEnd;
	
	private final float speed;
	
	private float axisX;
	private float axisY;
	
	private float dx;
	private float dy;
	
	// TODO: REMOVE
	private double degrees;
	
	public LinearMovingBehavior(final float areaPosX, final float areaPosY, final int areaWidth, final int areaHeight, final float speed, Vector2f positionStart) {
		this.areaPosX = areaPosX;
		this.areaPosY = areaPosY;
		this.areaWidth = areaWidth;
		this.areaHeight = areaHeight;
		this.speed = speed;
		this.positionStart = positionStart;
		this.positionCurrent = positionStart.clone();
	}

	private void createPositionEnd() {
		
		final Random random = new Random();
		
		int randomX = (int) (random.nextInt(areaWidth) + areaPosX);
		int randomY = (int) (random.nextInt(areaHeight) + areaPosY);
		
		positionEnd = new Vector2f(randomX, randomY);
		
	}

	@Override
	public void contextMovingEntity(MovingEntity movingEntity) {}

	@Override
	public Vector2f move(float timeSinceLastFrame) {

		if (positionEnd == null) {
			createPositionEnd();
			calcAxesSpeed();
			return createTranslationDeltaVector();
		}
		
		if (isPostionEndReached()) {
			
			storePositions();
			createPositionEnd();
			calcAxesSpeed();
			
		}
		
		calcTranslationDelta(timeSinceLastFrame);
		translatePositionCurrent();
		return createTranslationDeltaVector();
	}

	private void storePositions() {
		positionStart = positionEnd;
		positionCurrent = positionStart;
	}

	private Vector2f createTranslationDeltaVector() {
		return new Vector2f(dx, dy);
	}

	private void translatePositionCurrent() {
		positionCurrent.translate(dx, dy);
	}

	private void calcTranslationDelta(float timeSinceLastFrame) {
		dx =  axisX * timeSinceLastFrame * speed;
		dy =  axisY * timeSinceLastFrame * speed;
	}

	private void calcAxesSpeed() {
		final float dx = positionEnd.x - positionStart.x;
		final float dy = positionEnd.y - positionStart.y;
		
		final double angle = Math.atan2(dy, dx);
		degrees = Math.toDegrees(angle);
		
		axisX = (float) Math.cos(degrees);
		axisY = (float) Math.sin(degrees);
	}

	private boolean isPostionEndReached() {
		return positionCurrent.squareLenght(positionEnd) <= squareDistance;
	}
	
}
