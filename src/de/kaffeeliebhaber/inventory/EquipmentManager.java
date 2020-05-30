package de.kaffeeliebhaber.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.kaffeeliebhaber.inventory.item.Item;
import de.kaffeeliebhaber.inventory.item.ItemType;

/**
 * SINGELTON PATTERN FOR EquipmentManager.
 * @author User
 *
 */
public class EquipmentManager {

	public static EquipmentManager instance;
	
	static {
		if (instance == null) {
			instance = new EquipmentManager();
		}
	}
	
	private Map<ItemType, Item> equippedItems;
	private List<EquipmentManagerListener> equipmentManagerListeners;
	
	private EquipmentManager() {
		equippedItems = new HashMap<ItemType, Item>();
		equipmentManagerListeners = new ArrayList<EquipmentManagerListener>();
		
		addItemType(ItemType.HEAD);
		addItemType(ItemType.CHEST);
		addItemType(ItemType.LEGS);
		addItemType(ItemType.FEETS);
		addItemType(ItemType.WEAPON);
		addItemType(ItemType.SHIELD);
	}
	
	/**
	 * Gets the specific item.
	 * @param itemType
	 * @return
	 */
	public Item getItem(final ItemType itemType) {
		return equippedItems.get(itemType);
	}
	
	/**
	 * Adds the current item type as new selection type into the equipment manager.
	 */
	public void addItemType(final ItemType itemType) {
		equippedItems.put(itemType, null);
	}
	
	/**
	 * Removes the given item type value from the equipment manager as selection value.
	 * @param itemType
	 */
	public void removeItemType(final ItemType itemType) {
		equippedItems.remove(itemType);
	}
	
	/**
	 * This function equips the given item. If a item was already equipped for the given item and the same item type, the new item will be equipped. The
	 * old one will be returned for later use.
	 * @param item
	 * @return
	 */
	public Item equip(Item newItem) {
		
		Item oldItem = null;
		
		final ItemType itemType = newItem.getItemType();
		
		final Set<ItemType> availableItemTypes = getAvailableItemTypes();
		
		if (availableItemTypes.contains(itemType)) {
			
			// Equip the new item
			oldItem = equippedItems.put(itemType, newItem);
			
			fireEquippedEvent(oldItem, newItem);
		} 
		
		//printEquipment();
		
		return oldItem;
	}
	
	/**
	 * Notifies all registered listeners for equipped item event.
	 * @param newItem
	 */
	private void fireEquippedEvent(Item oldItem, Item newItem) {
		for (EquipmentManagerListener l : equipmentManagerListeners) {
			l.equipped(oldItem, newItem);
		}
	}
	
	/**
	 * Notifies all registered listeners for equipped item event.
	 * @param newItem
	 */
	private void fireUnequippedEvent(Item oldItem) {
		for (EquipmentManagerListener l : equipmentManagerListeners) {
			l.unequipped(oldItem);
		}
	}
	
	/**
	 * Unequips the current item
	 * @param item
	 * @return
	 */
	public boolean unequip(Item item) {
		boolean unequipped = equippedItems.replace(item.getItemType(), item, null);
		
		if (unequipped) {
			fireUnequippedEvent(item);
		}
		
		return unequipped;
	}
	
	/**
	 * Unequips the current item type slot.
	 * @param type
	 */
	public boolean unequip(ItemType type) {
		return equippedItems.replace(type, null) != null;
	}
	
	/**
	 * Gets the set of available item types in the equipment manager.
	 * @return
	 */
	public Set<ItemType> getAvailableItemTypes() {
		return equippedItems.keySet();
	}
	
	/**
	 * Add a EquipmentManagerListener.
	 * @param l
	 */
	public void addEquipmentManagerListener(EquipmentManagerListener l) {
		equipmentManagerListeners.add(l);
	}
	
	/**
	 * Removes a EquipmentManagerListener.
	 * @param l
	 */
	public void removeEquipmentManagerListener(EquipmentManagerListener l) {
		equipmentManagerListeners.remove(l);
	}
	
	public void printEquipment() {
		System.out.println(equippedItems.toString());
	}
	
	/*
	public Item getEquippedItemHead() {
		return getItem(ItemType.HEAD);
	}
	
	public Item getEquippedItemChest() {
		return getItem(ItemType.CHEST);	
	}
	
	public Item getEquippedItemLegs() {
		return getItem(ItemType.LEGS);	
	}
	
	public Item getEquippedItemFeets() {
		return getItem(ItemType.FEETS);
	}
	
	public Item getEquippedItemWeapon() {
		return getItem(ItemType.WEAPON);
	}
	
	public Item getEquippedItemShield() {
		return getItem(ItemType.SHIELD);
	}
	*/
}
