package de.kaffeeliebhaber.inventorySystem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.kaffeeliebhaber.inventorySystem.item.Item;
import de.kaffeeliebhaber.inventorySystem.item.ItemListener;
import de.kaffeeliebhaber.inventorySystem.item.ItemType;

public class EquipmentManager implements EquipmentManagerModel, ItemListener {

	private List<InventoryModelListener> inventoryModelListeners;
	private List<EquipmentManagerListener> equipmentManagerListeners;
	
	// TODO:
	// --------------------------------------------------------
	private Item itemHead, itemChest, itemFeets, itemWeapon, itemShield;
	
	// private Map<ItemType, Item> availableItems;
	// --------------------------------------------------------
	
	public EquipmentManager() {
		equipmentManagerListeners = new ArrayList<EquipmentManagerListener>();
		inventoryModelListeners = new ArrayList<InventoryModelListener>();
	}
	
	// --------------------------------------------------------
	// TODO:
	public Set<ItemType> getAvailableItemTypes() {
		final Set<ItemType> availableItemTypes = new HashSet<ItemType>();
		
		availableItemTypes.add(ItemType.CHEST);
		
		return availableItemTypes;
	}
	
	// TODO:
	public Item getByItemType(final ItemType itemType) {
		Item item = null;
		
		
		
		return item;
	}
	
	// --------------------------------------------------------
	public void clear() {
		
		itemHead = null;
		itemChest = null;
		itemFeets = null;
		itemWeapon = null;
		itemShield = null;
		
		notifyAllEquipmentManagerModelListener();	
	}
	
	@SuppressWarnings("incomplete-switch")
	public void add(final Item item) {
		
		switch (item.getItemType()) {
			case HEAD: setItemHead(item); break;
			case CHEST: setItemChest(item);  break;
			case FEETS: setItemFeets(item); break;
			case WEAPON: setItemWeapon(item); break;
			case SHIELD: setItemShield(item); break;
		}
		
		item.addItemListner(this);
		notifyAllEquipmentManagerModelListener();
	}
	
	@SuppressWarnings("incomplete-switch")
	private void remove(final Item item) {
		switch (item.getItemType()) {
			case HEAD: 
				itemHead = null; 
				break;
			case CHEST: 
				itemChest = null; 
				break;
			case FEETS: 
				itemFeets = null;
				break;
			case WEAPON: 
				itemWeapon = null; 
				break;
			case SHIELD: 
				itemShield = null; 
				break;
		}
		
		fireUnequippedEvent(item);
		notifyAllEquipmentManagerModelListener();
	}
	
	private void setItemHead(final Item head) {
		Item oldItem = itemHead;
		itemHead = head;
		fireEquippedEvent(oldItem, itemHead, head.getItemType());
	}
	
	private void setItemChest(final Item chest) {
		Item oldItem = itemChest;
		itemChest = chest;
		fireEquippedEvent(oldItem, itemChest, chest.getItemType());
	}
	
	private void setItemFeets(final Item feets) {
		Item oldItem = itemFeets;
		itemFeets = feets;
		fireEquippedEvent(oldItem, itemFeets, feets.getItemType());
	}
	
	private void setItemWeapon(final Item weapon) {
		Item oldItem = itemWeapon;
		itemWeapon = weapon;
		fireEquippedEvent(oldItem, itemWeapon, weapon.getItemType());
	}
	
	private void setItemShield(final Item shield) {
		Item oldItem = itemShield;
		itemShield = shield;
		fireEquippedEvent(oldItem, itemShield, shield.getItemType());
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

	//
	// EQUIPMENT_MANAGER_MODEL
	//
	
	@Override
	public Item getItemHead() {
		return itemHead;
	}

	@Override
	public Item getItemChest() {
		return itemChest;
	}

	@Override
	public Item getItemFeets() {
		return itemFeets;
	}

	@Override
	public Item getItemWeapon() {
		return itemWeapon;
	}

	@Override
	public Item getItemShield() {
		return itemShield;
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
