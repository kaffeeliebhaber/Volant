package de.kaffeeliebhaber.newUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;

import de.kaffeeliebhaber.inventorySystem.item.Item;

public class UIInventorySlot extends UIView {
	
	// COLOR
	private static final Color COLOR_BORDER = Color.DARK_GRAY;
	private static final Color COLOR_BACKGROUND = new Color(138, 138, 138);
	
	// SLOT CONSTANTS
	public static final int WIDTH   = 40;
	public static final int HEIGHT  = 40;
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
		renderBackground(g);
		renderBorder(g);
		renderImage(g);
		renderQuantity(g);
		renderHoverEffect(g);
//		renderLookup(g);
	}
	
	private void renderBackground(Graphics g) {
		g.setColor(UIInventorySlot.COLOR_BORDER);
		g.fillRect((int) x, (int) y, width, height);
	}
	
	private void renderBorder(Graphics g) {
		g.setColor(UIInventorySlot.COLOR_BACKGROUND);
		g.drawRect((int) x, (int) y, width, height);
	}
	
	private void renderImage(Graphics g) {
		if (item != null) {
			g.drawImage(
				item.getItemImage(), 
				(int) x + padding, 
				(int) y + padding, 
				width - 2 * padding, 
				height - 2 * padding, 
				null);
		}
	}
	
	private void renderQuantity(Graphics g) {
		if (item != null && item.isStackable()) {
			g.setColor(Color.WHITE);
			g.drawString("" + item.getQuantity(), (int) x + width - 4 * padding, (int) y + height - 2 * padding); 
		}
	}
	
	private void renderHoverEffect(Graphics g) {
		if (hover) {
			g.setColor(new Color(245, 255, 250, 125));
			g.fillRect((int) x + 2, (int) y + 2, width - 3, height - 3);
		}
	}
	
	private void renderLookup(Graphics g) {
		if (item != null && hover) {
			UIInventorySlotLookup.getInstance().render(g, currentMousePosition, item);
		}
	}
}
