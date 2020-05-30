package de.kaffeeliebhaber.ui.inventory;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.core.MouseManager;
import de.kaffeeliebhaber.input.MouseManagerListener;
import de.kaffeeliebhaber.inventory.Inventory;
import de.kaffeeliebhaber.inventory.InventoryListener;
import de.kaffeeliebhaber.inventory.item.Item;

/**
 * Stellt die graphische Darstellung des Inventar dar.
 * @author User
 *
 */
public class UIInventoryRepresenter extends UIElement implements InventoryListener, MouseManagerListener {
		
	private final int MAX_SLOTS = 24;
	private final int COLS = 6;

	private List<UIInventorySlot> slots;
	
	private Inventory inventory;
	
	public UIInventoryRepresenter(Inventory inventory, int x, int y) {
		
		super(x, y);
		
		this.inventory = inventory;
		
		// register listener
		inventory.addInventoryListener(this);
		MouseManager.instance.addMouseManagerListener(this);
		
		init();
		initSlots();
	}
	
	private void initSlots() {

		List<Item> items = inventory.getAllItems();
		
		for (int i = 0; i < slots.size() && i < items.size(); i++) {

			UIInventorySlot uiSlot = slots.get(i);
			
			uiSlot.setItem(items.get(i));
		}
	}
	
	private void clearSlots() {
		for (int i = 0; i < slots.size(); i++) {
			slots.get(i).clear();
		}
	}
	
	public void calcDimension() {
		width = COLS * (UIInventoryConstants.padding + UIInventoryConstants.cellWidth) + UIInventoryConstants.padding;
		height = (MAX_SLOTS / COLS)  * (UIInventoryConstants.padding + UIInventoryConstants.cellHeight) + UIInventoryConstants.padding;
	}
	
	private void init() {
		slots = new ArrayList<UIInventorySlot>();
		
		// init inventory slots
		for (int i = 0; i < MAX_SLOTS; i++) {
			
			Rectangle area = new Rectangle(
					(int) x + (i % COLS) * (UIInventoryConstants.padding + UIInventoryConstants.cellWidth) + UIInventoryConstants.padding,
					(int) y + (i / COLS) * (UIInventoryConstants.padding + UIInventoryConstants.cellHeight) + UIInventoryConstants.padding,
					UIInventoryConstants.cellWidth,
					UIInventoryConstants.cellHeight);
			
			slots.add(new UIInventorySlot(area));
		}
	}
	
	public void render(Graphics g) {
		
		// draw inventory item slots.
		g.setColor(UIInventoryConstants.inventoryBackgroundColor);
		g.fillRect((int) x, (int) y, width, height);
		
		for (UIInventorySlot currentSlot : slots) {
			currentSlot.render(g);
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	@Override
	public void inventoryChanged() {
		clearSlots();
		initSlots();
	}
	
	@Override
	public void mouseReleased(int buttonID, Point p) {
		
		if (visible && buttonID == MouseEvent.BUTTON1) {
			for (UIInventorySlot slot : slots) {
				if (slot.contains(p)) {
					slot.clicked();
					break;
				}
			}
		}
	}

	@Override public void mouseMoved(Point p) {}

}
