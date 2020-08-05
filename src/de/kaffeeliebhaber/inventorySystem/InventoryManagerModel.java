package de.kaffeeliebhaber.inventorySystem;

import de.kaffeeliebhaber.inventorySystem.item.Item;

public interface InventoryManagerModel {

	int size();
	
	Item getElementAt(int index);
	
	void addInventoryModelListener(final InventoryModelListener l);
	
	void removeInventoryModelListener(final InventoryModelListener l);
}
