package de.kaffeeliebhaber.newUI;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import de.kaffeeliebhaber.inventorySystem.EquipmentManagerModel;
import de.kaffeeliebhaber.inventorySystem.InventoryModelListener;
import de.kaffeeliebhaber.inventorySystem.item.ItemType;

public class UIViewEquipment extends UIView implements InventoryModelListener {

	private Map<ItemType, UIInventorySlot> slots;
	private EquipmentManagerModel model;
	private static final int COLS = 3;
	private static final int ROWS = 4;
	
	public UIViewEquipment(float x, float y, final EquipmentManagerModel model) {
		super(x, y);
		this.model = model;
		this.model.addInventoryModelListener(this);
		
		slots = new HashMap<ItemType, UIInventorySlot>();
		initSlots();
		initFromModel();
	}
	
	private void initSlots() {
		addSlot(ItemType.HEAD	, 0,  1);
		addSlot(ItemType.WEAPON	, 1,  3);
		addSlot(ItemType.CHEST	, 2,  4);
		addSlot(ItemType.SHIELD	, 3,  5);
		addSlot(ItemType.LEGS	, 4,  7);
		addSlot(ItemType.FEETS	, 5, 10);
	}
	
	private Point calcSlotDrawPoint(final int cellID) {
		
		int posX = (int) x + (cellID % COLS) * (UIInventorySlot.PADDING + UIInventorySlot.WIDTH) + UIInventorySlot.PADDING;
		int posY = (int) y + (cellID / COLS) * (UIInventorySlot.PADDING + UIInventorySlot.HEIGHT) + UIInventorySlot.PADDING;
		return new Point(posX, posY);
	}
	
	private void addSlot(final ItemType itemType, final int ID, final int cellID) {
		final Point drawPoint = this.calcSlotDrawPoint(cellID);
		slots.put(itemType, new UIInventorySlot(ID, drawPoint.x, drawPoint.y));
	}
	
	private void clear() {
		slots.values().stream().forEach(s -> s.clear());
	}
	
	public void initFromModel() {
		model.getAvailableItemTypes().stream().forEach(t -> slots.get(t).setItem(model.getByItemType(t)));
	}
	
	public void render(final Graphics g) {
		if (visible) {
			slots.values().stream().forEach(s -> s.render(g));
		}
	}
	
	public void clicked(int buttonID, Point p) {
		slots.values().stream().forEach(s -> s.clicked(buttonID, p));
	}
	
	public void mouseMoved(Point p) {
		slots.values().stream().forEach(s -> s.mouseMoved(p));
	}

	@Override
	public void inventoryChanged() {
		clear();
		initFromModel();
	}
}
