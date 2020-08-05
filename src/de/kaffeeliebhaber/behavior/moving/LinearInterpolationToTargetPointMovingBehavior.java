package de.kaffeeliebhaber.behavior.moving;

import java.util.Random;

import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.entitySystem.MovingEntity;
import de.kaffeeliebhaber.math.Vector2f;

public class LinearInterpolationToTargetPointMovingBehavior implements IMovingBehavior {

	private MovingEntity movingEntity;
	private final int distance = 3;
	private final int squareDistance = distance * distance;
	private float speed;
	private float x, y;
	private int width, height;
	private Vector2f currentPosition;
	private Vector2f startPosition;
	private Vector2f targetPosition;
	private Random random;
	private float dx, dy;
	
	public LinearInterpolationToTargetPointMovingBehavior(Vector2f startPosition, float x, float y, int width, int height, float speed) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		
		random = new Random();
		
		this.startPosition = startPosition;
		this.currentPosition = startPosition;
		
		targetPosition = createRandomPosition();
	}

	@Override public Vector2f move(final KeyManager keyManager, float timeSinceLastFrame) {
		
		// AKTUALISIEREN DER AKTUELLEN POSITION
		createTranslationDelta(timeSinceLastFrame);
		
		translateCurrentPositionWithTranslationDelta();
		
		if (isTargetReached()) {
			// WIR HABEN UNSER ZIEL ERREICHT -> NEUE ZIEL-POSITION ERSTELLEN 
			generateNewRandomTargetPoint();
		}
		
		return getCurrentTranslation();
	}
	
	private boolean isTargetReached() {
		return currentPosition.squareLenght(targetPosition) <= squareDistance;
	}
	
	private void createTranslationDelta(float timeSinceLastFrame) {
		dx = (targetPosition.x - startPosition.x) * speed * timeSinceLastFrame;
		dy = (targetPosition.y - startPosition.y) * speed * timeSinceLastFrame;
	}
	
	private void translateCurrentPositionWithTranslationDelta() {
		currentPosition.translate(dx, dy);
	}
	
	private void generateNewRandomTargetPoint() {
		startPosition = targetPosition;
		currentPosition = startPosition;
		targetPosition = createRandomPosition();
	}
	
	private Vector2f createRandomPosition() {
		
		int randomX = (int) (random.nextInt(width) + x);
		int randomY = (int) (random.nextInt(height) + y);
		
		return new Vector2f(randomX, randomY);
	}
	
	private Vector2f getCurrentTranslation() {
		return new Vector2f(dx, dy);
	}

	@Override
	public void contextMovingEntity(MovingEntity movingEntity) {
		this.movingEntity = movingEntity;
	}
	
}
