package de.kaffeeliebhaber.inventorySystem;

import de.kaffeeliebhaber.inventorySystem.item.Item;

public interface EquipmentManagerModel {

	Item getItemHead();
	
	Item getItemChest();
	
	Item getItemFeets();
	
	Item getItemWeapon();
	
	Item getItemShield();
	
	void addInventoryModelListener(final InventoryModelListener l);
	
	void removeInventoryModelListener(final InventoryModelListener l);
}
