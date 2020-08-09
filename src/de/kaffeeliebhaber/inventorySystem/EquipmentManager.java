package de.kaffeeliebhaber.inventorySystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.kaffeeliebhaber.inventorySystem.item.Item;
import de.kaffeeliebhaber.inventorySystem.item.ItemListener;
import de.kaffeeliebhaber.inventorySystem.item.ItemType;

public class EquipmentManager implements EquipmentManagerModel, ItemListener {
	
	private final List<InventoryModelListener> inventoryModelListeners;
	private final List<EquipmentManagerListener> equipmentManagerListeners;
	private final Map<ItemType, Item> availableItems;
	
	public EquipmentManager() {
		
		equipmentManagerListeners = new ArrayList<EquipmentManagerListener>();
		inventoryModelListeners = new ArrayList<InventoryModelListener>();
		availableItems = new HashMap<ItemType, Item>();
		
		init();
	}
	
	private void init() {
		availableItems.put(ItemType.CHEST, null);
		availableItems.put(ItemType.FEETS, null);
		availableItems.put(ItemType.HEAD, null);
		availableItems.put(ItemType.LEGS, null);
		availableItems.put(ItemType.WEAPON, null);
		availableItems.put(ItemType.SHIELD, null);
	}
	
	public Set<ItemType> getAvailableItemTypes() {
		return availableItems.keySet();
	}
	
	public Item getByItemType(final ItemType itemType) {
		return availableItems.get(itemType);
	}
	
	public void clear() {
		availableItems.values().clear();
		notifyAllEquipmentManagerModelListener();	
	}
	
	public void add(final Item item) {
		addItem(item);
		item.addItemListner(this);
		notifyAllEquipmentManagerModelListener();
	}
	
	private void addItem(final Item item) {
		final ItemType itemType = item.getItemType();
		final Item oldItem = availableItems.get(itemType);
		availableItems.replace(itemType, item);
		fireEquippedEvent(oldItem, item, itemType);
	}
	
	private void remove(final Item item) {
		availableItems.replace(item.getItemType(), null);
		fireUnequippedEvent(item);
		notifyAllEquipmentManagerModelListener();
	}
	
	//
	// EQUIPMENT_MANAGER_LISTERN
	//
	private void fireEquippedEvent(final Item oldItem, final Item newItem, final ItemType itemType) {
		equipmentManagerListeners.stream().forEach(l -> l.equipped(oldItem, newItem, itemType));
	}
	
	private void fireUnequippedEvent(final Item item) {
		equipmentManagerListeners.stream().forEach(l -> l.unequipped(item));
	}
	
	public void addEquipmentManagerListener(EquipmentManagerListener l) {
		equipmentManagerListeners.add(l);
	}
	
	public void removeEquipmentManagerListener(EquipmentManagerListener l) {
		equipmentManagerListeners.remove(l);
	}
	
	private void notifyAllEquipmentManagerModelListener() {
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

	@Override
	public void itemUpdate(Item item, InventoryItemState itemState) {
		
		if (itemState == InventoryItemState.UNEQUIPPED) {
			item.removeItemListener(this);
			remove(item);
		}
		
	}
	
}
