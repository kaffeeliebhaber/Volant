package de.kaffeeliebhaber.inventorySystem;

import de.kaffeeliebhaber.inventorySystem.item.Item;
import de.kaffeeliebhaber.inventorySystem.item.ItemType;

public interface EquipmentManagerListener {

	void equipped(Item oldItem, Item newItem, ItemType itemType);
	
	void unequipped(Item item);
	
	// TODO: (EquipmentManagerListener) equipmentChanged(final Item oldItem, final Item newItem, final ItemType itemType);
	// DÜRFTE EIGENTLICH AUCH MIT DIESER EINEN METHODE FUNKTIONIEREN.

}
