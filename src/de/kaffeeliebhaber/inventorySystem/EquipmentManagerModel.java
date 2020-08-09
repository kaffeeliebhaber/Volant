package de.kaffeeliebhaber.inventorySystem;

import java.util.Set;

import de.kaffeeliebhaber.inventorySystem.item.Item;
import de.kaffeeliebhaber.inventorySystem.item.ItemType;

public interface EquipmentManagerModel {

	Set<ItemType> getAvailableItemTypes();
	
	Item getByItemType(final ItemType itemType);
	
	void addInventoryModelListener(final InventoryModelListener l);
	
	void removeInventoryModelListener(final InventoryModelListener l);
}
