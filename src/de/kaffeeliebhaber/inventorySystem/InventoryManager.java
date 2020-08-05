package de.kaffeeliebhaber.inventorySystem;

import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.inventorySystem.item.Item;
import de.kaffeeliebhaber.inventorySystem.item.ItemListener;

public class InventoryManager implements InventoryManagerModel, ItemManagerListener, ItemListener {

	private final EquipmentManager equipmentManager;
	private final List<InventoryModelListener> inventoryModelListeners;
	private final List<Item> items;
	
	public InventoryManager(final EquipmentManager equipmentManager) {
		this.equipmentManager = equipmentManager;
		items = new ArrayList<Item>();
		inventoryModelListeners = new ArrayList<InventoryModelListener>();
	}
	
	public void add(final Item item) {
		
		// IF ITEM IS ALREADY IN THE INVENTORY AND ITS STACKABLE, WE HAVE TO ADJUST THE QUANTITY
		// IN CASE OF A ITEM, WHICH IS NOT IN THE INVENTORY, WE HAVE TO ADD THEM TO IT.
		if (items.contains(item) && items.get(items.indexOf(item)).isStackable()) {
			final Item inventoryItem = items.get(items.indexOf(item));
			inventoryItem.adjustQuantity(item.getQuantity());
		} else {
			try {
				Item itemClone = item.clone();
				items.add(itemClone);
				itemClone.addItemListner(this);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		
		notifyAllInventoryManagerModelListener();
	}
	
	public void remove(final Item item) {
		
		// IF ITEM IS IN THE INVENTORY, IT WILL BE REMOVE. LISTENER WILL BE CALLED AFTER THSI.
		if (items.contains(item)) {
			items.remove(item);
			notifyAllInventoryManagerModelListener();
		}
	}

	@Override
	public int size() {
		return items.size();
	}

	@Override
	public Item getElementAt(int index) {
		return items.get(index);
	}
	
	//
	// INVENTORY_MANAGER_MODEL_LISTENER
	//
	private void notifyAllInventoryManagerModelListener() {
		inventoryModelListeners.stream().forEach(l -> l.inventoryChanged());
	}

	@Override
	public void addInventoryModelListener(final InventoryModelListener l) {
		inventoryModelListeners.add(l);
	}

	@Override
	public void removeInventoryModelListener(final InventoryModelListener l) {
		inventoryModelListeners.remove(l);
	}

	//
	// ITEM_MANAGER_LISTENER
	// NOTE: CALLED, THEN THE PLAYER PICKS A ITEM. THE ITEMMANAGER DELEGATES THE INFORMATION TO THIS INSTANCE.
	@Override
	public void itemPicked(Item pickedItem) {
		add(pickedItem);
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void itemUpdate(Item item, InventoryItemState itemState) {
		
		switch (itemState) {
			case REMOVED:
				item.removeItemListener(this); 
				remove(item);
				break;
			case EQUIPPED: 
				equipmentManager.add(item); 
				remove(item); 
				break;
			case UNEQUIPPED: 
				add(item); 
				break;
		}
	}

}
