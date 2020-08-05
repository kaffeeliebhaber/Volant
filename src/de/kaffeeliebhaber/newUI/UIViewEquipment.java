package de.kaffeeliebhaber.newUI;

import java.awt.Graphics;
import java.awt.Point;

import de.kaffeeliebhaber.inventorySystem.EquipmentManagerModel;
import de.kaffeeliebhaber.inventorySystem.InventoryModelListener;

public class UIViewEquipment extends UIView implements InventoryModelListener {

	private UIInventorySlot itemSlotWeapon;
	private UIInventorySlot itemSlotFeet;
	
	/*
	 * Map<ItemType, Item>
	 * 
	 */
	// TODO: Es muss für jeden ItemType ein Slot bereitgestellt werden
	// In der clear MEthdoe müssen alle Slots von ihrem Item befreit werden -> slot.clear()
	// Anschließend müssen alle SLots wieder durch das Model vorbeleget werden.
	private EquipmentManagerModel model;
	
	public UIViewEquipment(float x, float y, int width, int height, final EquipmentManagerModel model) {
		super(x, y, width, height);
		this.model = model;
		this.model.addInventoryModelListener(this);
		
//		slots = new ArrayList<UIInventorySlot>();
		initSlots();
		init();
	}
	
	private void initSlots() {
		itemSlotWeapon = new UIInventorySlot(0, 500, 100, 40, 40);
		itemSlotFeet = new UIInventorySlot(1, 550, 100, 40, 40);
	}
	
	private void clear() {
		//slots.stream().forEach(s -> s.clear());
		itemSlotWeapon.clear();
		itemSlotFeet.clear();
	}
	
	public void init() {
		itemSlotWeapon.setItem(model.getItemWeapon());
		itemSlotFeet.setItem(model.getItemFeets());
	}
	
	public void render(final Graphics g) {
		if (visible) {
			itemSlotWeapon.render(g);
			itemSlotFeet.render(g);
		}
	}
	
	public void clicked(int buttonID, Point p) {
		itemSlotWeapon.clicked(buttonID, p);
		itemSlotFeet.clicked(buttonID, p);
	}
	
	public void mouseMoved(Point p) {
		itemSlotWeapon.mouseMoved(p);
		itemSlotFeet.mouseMoved(p);
	}

	@Override
	public void inventoryChanged() {
		clear();
		init();
	}
}
