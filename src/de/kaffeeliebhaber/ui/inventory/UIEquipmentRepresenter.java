package de.kaffeeliebhaber.ui.inventory;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.kaffeeliebhaber.core.MouseManager;
import de.kaffeeliebhaber.input.MouseManagerListener;
import de.kaffeeliebhaber.inventory.EquipmentManager;
import de.kaffeeliebhaber.inventory.EquipmentManagerListener;
import de.kaffeeliebhaber.inventory.Inventory;
import de.kaffeeliebhaber.inventory.item.Item;
import de.kaffeeliebhaber.inventory.item.ItemType;

/**
 * Stellt die graphische Darstellung der ausgerüsteten Items dar.
 * @author User
 *
 */
public class UIEquipmentRepresenter extends UIElement implements MouseManagerListener, EquipmentManagerListener {

	private Map<ItemType, UIInventorySlot> slots;
	private EquipmentManager equipmentManager;
	
	public UIEquipmentRepresenter(EquipmentManager equipmentManager, int x, int y) {
		
		super(x, y);
		
		this.equipmentManager = equipmentManager;
		
		init();
		
		equipmentManager.addEquipmentManagerListener(this);
		MouseManager.instance.addMouseManagerListener(this);
	}
	
	protected void calcDimension() {
		width = 3 * UIInventoryConstants.cellWidth + 4 * UIInventoryConstants.padding;
		height = 4 * UIInventoryConstants.cellHeight + 5 * UIInventoryConstants.padding;
	}
	
	private void init() {

		slots = new HashMap<ItemType, UIInventorySlot>();		
	
		slots.put(ItemType.HEAD, createInventorySlot(1, 0)); // HEAD
		slots.put(ItemType.WEAPON, createInventorySlot(0, 1)); // WEAPON
		slots.put(ItemType.CHEST, createInventorySlot(1, 1)); // CHEST
		slots.put(ItemType.SHIELD, createInventorySlot(2, 1)); // SHIELD
		slots.put(ItemType.LEGS, createInventorySlot(1, 2)); // LEGS
		slots.put(ItemType.FEETS, createInventorySlot(1, 3)); // FEETS
		
		initSlots();
	}
	
	private UIInventorySlot createInventorySlot(int cellX, int cellY) {

		int x = (int) this.x + (cellX + 1) * UIInventoryConstants.padding + cellX * UIInventoryConstants.cellWidth;
		int y = (int) this.y + (cellY + 1) * UIInventoryConstants.padding + cellY * UIInventoryConstants.cellHeight;
		
		Rectangle area = new Rectangle(x, y, UIInventoryConstants.cellWidth, UIInventoryConstants.cellHeight);

		return new UIInventorySlot(area);
	}
	
	public void render(Graphics g) {
		g.setColor(UIInventoryConstants.inventoryBackgroundColor);
		g.fillRect((int) x, (int) y, width, height);
		
		Collection<UIInventorySlot> newSlots = slots.values();
		
		for (UIInventorySlot slot : newSlots) {
			slot.render(g);
		}
	}

	@Override
	public void mouseReleased(int buttonID, Point p) {
		
		if (visible && buttonID == MouseEvent.BUTTON1) {
			Collection<UIInventorySlot> newSlots = slots.values();
			
			for (UIInventorySlot slot : newSlots) {
				if (slot.contains(p) && !slot.isEmpty()) {
					Item item = slot.getItem();
					
					// TODO: (Inventory)
					Inventory.instance.add(item);
					equipmentManager.unequip(item);
					
					slot.clear();
					break;
				}
			}
		}
		
	}
	
	private void initSlots() {

		Set<ItemType> types = slots.keySet();
		
		for (ItemType type : types) {
			slots.get(type).setItem(equipmentManager.getItem(type));
		}
	}
	
	private void clearSlots() {
		Collection<UIInventorySlot> newSlots = slots.values();
		
		for (UIInventorySlot slot : newSlots) {
			slot.clear();
		}
	}

	@Override
	public void equipped(Item oldItem, Item newItem) {
		clearSlots();
		initSlots();
	}

	@Override
	public void unequipped(Item oldItem) {
		clearSlots();
		initSlots();
	}
	
	@Override public void mouseMoved(Point p) {}
}
