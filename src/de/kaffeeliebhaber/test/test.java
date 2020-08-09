package de.kaffeeliebhaber.test;

import java.awt.Point;

public class test {

	private static final int COLS = 3;
	
	private static final int WIDTH = 40;
	private static final int HEIGHT = 40;
	
	
	public static void main(String[] args) {
		//new TiledEditorMapLoader("src/de/kaffeeliebhaber/assets/test/test.xml");
		test.calcSlotDrawPoint(3); // col: 3, row: 1;
	}
	
	
	private static Point calcSlotDrawPoint(final int cell) {
		return new Point(WIDTH * (cell % COLS), HEIGHT * (cell / COLS));
	}
}
