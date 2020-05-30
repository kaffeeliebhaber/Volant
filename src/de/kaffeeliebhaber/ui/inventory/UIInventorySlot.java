package de.kaffeeliebhaber.ui.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import de.kaffeeliebhaber.core.MouseManager;
import de.kaffeeliebhaber.input.MouseManagerListener;
import de.kaffeeliebhaber.inventory.item.Item;

public class UIInventorySlot implements MouseManagerListener {

	// constants
	private final int padding = 2;
	private Item item;
	private Rectangle area;
	private boolean hover;
	
	public UIInventorySlot(final Rectangle area) {
		this.area = area;
		MouseManager.instance.addMouseManagerListener(this);
	}
	
	// setter
	public void setItem(final Item item) {
		this.item = item;
	}
	
	// getter
	public Item getItem() {
		return item;
	}
	
	// core
	public boolean contains(final Point p) {
		return area.contains(p);
	}
	
	public void clicked() {
		if (item != null) {
			item.use();
		}
	}
	
	public void clear() {
		item = null;
	}
	
	public boolean isEmpty() {
		return item == null;
	}
	
	public void update(float timeSinceLastFrame) {}
	
	public void render(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(area.x, area.y, area.width, area.height);
		
		g.setColor(UIInventoryConstants.itemSlotsBorderColor);
		g.drawRect(area.x, area.y, area.width, area.height);
		
		if (!isEmpty()) {
			
			g.drawImage(
					item.getItemImage(), 
					area.x + padding, 
					area.y + padding, 
					area.width - 2 * padding, 
					area.height - 2 * padding, 
					null);
			
			if (item.isStackable()) {
				g.setColor(Color.WHITE);
				g.drawString(
						"" + item.getQuantity(), 
						area.x + area.width - 4 * padding, 
						area.y + area.height - 2 * padding);
			}
		}
		
		if (hover) {
			g.setColor(UIInventoryConstants.itemSlotHoverColor);
			g.fillRect(area.x + 2, area.y + 2, area.width - 3, area.height - 3);
		}
	}

	@Override public void mouseReleased(int buttonID, Point p) {}

	@Override
	public void mouseMoved(Point p) {
		hover = area.contains(p) ? true : false;
	}
}
