package de.kaffeeliebhaber.inventory;

import de.kaffeeliebhaber.inventory.item.Item;

public interface EquipmentManagerListener {

	void equipped(Item oldItem, Item newItem);
	
	void unequipped(Item oldItem);
}
