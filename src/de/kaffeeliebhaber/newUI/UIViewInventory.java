package de.kaffeeliebhaber.newUI;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.inventorySystem.InventoryManagerModel;
import de.kaffeeliebhaber.inventorySystem.InventoryModelListener;

public class UIViewInventory extends UIView implements InventoryModelListener {

	private final int MAX_SLOTS = 24;
	private final int COLS 		=  6;
	private final InventoryManagerModel model;
	private final List<UIInventorySlot> slots;

	public UIViewInventory(float x, float y, InventoryManagerModel model) {
		super(x, y);
		this.model = model;
		this.model.addInventoryModelListener(this);
		
		slots = new ArrayList<UIInventorySlot>();
		initDimension();
		initInventorySlots();
		initFromModel();
	}
	
	private void initDimension() {
		width = COLS * (UIInventorySlot.PADDING + UIInventorySlot.WIDTH) + UIInventorySlot.PADDING;
		height = (MAX_SLOTS / COLS)  * (UIInventorySlot.PADDING + UIInventorySlot.HEIGHT) + UIInventorySlot.PADDING;
	}
	
	private void initInventorySlots() {
		for (int i = 0; i < MAX_SLOTS; i++) {
			int posX = (int) x + (i % COLS) * (UIInventorySlot.PADDING + UIInventorySlot.WIDTH) + UIInventorySlot.PADDING;
			int posY = (int) y + (i / COLS) * (UIInventorySlot.PADDING + UIInventorySlot.HEIGHT) + UIInventorySlot.PADDING;
			
			slots.add(new UIInventorySlot(i, posX, posY));
		}
	}
	
	private void initFromModel() {
		final int size = model.size();
		
		for (int i = 0; i < size; i++) {
			slots.get(i).setItem(model.getElementAt(i));
		}
	}
	
	private void clear() {
		slots.stream().forEach(slot -> slot.clear());
	}
	
	public void clicked(int buttonID, Point p) {
		slots.stream().forEach(slot -> slot.clicked(buttonID, p));
	}
	
	public void render(final Graphics g) {
		if (isVisible()) {
			slots.stream().forEach(slot -> slot.render(g));
		}
	}
	
	public void mouseMoved(Point p) {
		slots.stream().forEach(slot -> slot.mouseMoved(p));
	}

	@Override
	public void inventoryChanged() {
		clear();
		initFromModel();
	}
}
