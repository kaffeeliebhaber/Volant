package de.kaffeeliebhaber.newUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import de.kaffeeliebhaber.inventorySystem.item.Item;

public class UIInventorySlotLookup {

	// COLOR
	private static final Color COLOR_BORDER = Color.DARK_GRAY;
	private static final Color COLOR_BACKGROUND = new Color(138, 138, 138);
	
	// DIMENSION
	private static final int WIDTH = 300;
	private static final int HEIGHT = 100;
	
	// SINGELTON
	private static UIInventorySlotLookup instance;
	
	public static UIInventorySlotLookup getInstance() {
		if (instance == null) {
			instance = new UIInventorySlotLookup();
		}
		
		return instance;
	}
	
	public void render(Graphics g, final Point currentMousePosition, final Item item) {
		
		// BACKGROUND
		g.setColor(UIInventorySlotLookup.COLOR_BORDER);
		g.fillRect(currentMousePosition.x, currentMousePosition.y, WIDTH, HEIGHT);
		
		// BORDER
		g.setColor(UIInventorySlotLookup.COLOR_BACKGROUND);
		g.drawRect(currentMousePosition.x, currentMousePosition.y, WIDTH, HEIGHT);
		
		g.setColor(Color.WHITE);
		
		g.drawString(item.getName() + "\n" + item.getItemDescription(), currentMousePosition.x + 10, currentMousePosition.y + 10);
		
	}
}
