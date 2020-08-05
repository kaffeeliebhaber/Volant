package de.kaffeeliebhaber.newUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import de.kaffeeliebhaber.inventorySystem.item.Item;

public class UIInventorySlot extends UIView {
	
	// SLOT CONSTANTS
	public static final int WIDTH   = 50;
	public static final int HEIGHT  = 50;
	public static final int PADDING =  5;
	
	@SuppressWarnings("unused")
	private final int ID;
	private final int padding = 2;
	private Item item;
	
	public UIInventorySlot(final int ID, float x, float y) {
		this(ID, x, y, WIDTH, HEIGHT);
	}
	
	public UIInventorySlot(final int ID, float x, float y, int width, int height) {
		super(x, y, width, height);
		this.ID = ID;
	}
	
	public void setItem(final Item item) {
		this.item = item;
	}

	@Override
	public void clicked(int buttonID, Point p) {
		if (buttonID == MouseEvent.BUTTON1 && contains(p)) {
			if (item != null) {
				item.use();
			}
		}
	}
	
	public void clear() {
		item = null;
	}
	
	public void render(Graphics g) {
		
		// DRAW: BACKGROUND
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) x, (int) y, width, height);
		
		// DRAW: BORDER
		g.setColor(new Color(138, 138, 138));
		g.drawRect((int) x, (int) y, width, height);
		
		if (item != null) {
			
			// DRAW: ITEM IMAGE
			g.drawImage(
					item.getItemImage(), 
					(int) x + padding, 
					(int) y + padding, 
					width - 2 * padding, 
					height - 2 * padding, 
					null);
			
			// TODO: DUPLICTAE DRAW ACTION
			// DRAW: ITEM QUANTITY
			if (item.isStackable()) {
				g.setColor(Color.WHITE);
				g.drawString(
						"" + item.getQuantity(), 
						(int) x + width - 4 * padding, 
						(int) y + height - 2 * padding); 
			}
		}
		
		// DRAW: HOVER EFFECT
		if (hover) {
			g.setColor(new Color(245, 255, 250, 125));
			g.fillRect((int) x + 2, (int) y + 2, width - 3, height - 3);
		}
	}
}
