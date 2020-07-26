package de.kaffeeliebhaber.test;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.controller.BoundingBoxController;

public class BoundingBoxControllerTest {

	public static void main(String[] args) {
		
		BoundingBoxController controller = new BoundingBoxController();
		
		controller.addBoundingBox(new BoundingBox(10, 10, 50, 50));
		controller.addBoundingBox(new BoundingBox(-5, 2, 42, 67));
		controller.translate(10, 10);
		controller.getBoundingBoxes().stream().forEach(b -> System.out.println(b));
	}
}
