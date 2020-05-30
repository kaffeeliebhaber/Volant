package de.kaffeeliebhaber.object.movingBehavior;

import java.util.Random;

import de.kaffeeliebhaber.math.Vector2f;
import de.kaffeeliebhaber.object.MovingEntity;

public class LinearMovingBehavior implements IMovingBehavior {

	private final int distance = 3;
	private final int squareDistance = distance * distance;
	
	// AREA DIMENSION
	private final float areaPosX;
	private final float areaPosY;
	private final int areaWidth;
	private final int areaHeight;
	
//	private MovingEntity movingEntity;
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
	public void contextMovingEntity(MovingEntity movingEntity) {
//		this.movingEntity = movingEntity;
	}

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
		
//		System.out.println("DX: " + dx + ", DY: " + dy);
		calcTranslationDelta(timeSinceLastFrame);
		
		translatePositionCurrent();

//		System.out.println("DEGREES: " + degrees + ", DX: " + dx + ", DY: " + dy + ", START-POSITION: " + positionStart + ", CURRENT-POSITION: " + positionCurrent + ", END-POSITION: " + positionEnd);
		
//		System.out.println("DEGREES: " + degrees + ", START: " + positionStart + ", END: " + positionEnd);
		return createTranslationDeltaVector();
		
//		return new Vector2f(0, 0);
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
	
//	private void createPositionEnd() {}
	
	/*
	private void createPositionEnd() {
		positionEnd = new Vector2f(300, 57);
	}
	
	
	@Override
	public void contextMovingEntity(MovingEntity movingEntity) {
		this.movingEntity = movingEntity;
	}

	@Override
	public Vector2f move(float timeSinceLastFrame) {

		resetTranslateVector();
		
		if (positionEnd == null) {
			createPositionEnd();
			createLineData();
		}
		
		if (isTargetReached()) {
			System.out.println("END REACHED");
		}  else {
			updateTranslatevector(timeSinceLastFrame);
			translateCurrentPosition();
		}
		
		return createTranslateVector();
	}
	
	private Vector2f createTranslateVector() {
		return new Vector2f(dx, dy);
	}
	
	private void resetTranslateVector() {
		dx = 0;
		dy = 0;
	}
	
	private void updateTranslatevector(final float timeSinceLastFrame) {
		dx = speed * timeSinceLastFrame;
		dy = dx * slope + gap;
	}
	
	private void translateCurrentPosition() {
		positionCurrent.translate(dx, dy);
	}
	
	private void createPositionEnd() {
		positionEnd = new Vector2f(100, 100);
	}
	
	private void createLineData() {
		slope = (positionEnd.y - positionStart.y) / (positionEnd.x - positionStart.x);
		gap = positionStart.y - slope * positionStart.x;
	}
	
	private boolean isTargetReached() {
		return positionCurrent.squareLenght(positionEnd) <= squareDistance;
	}
	*/

}
