package de.kaffeeliebhaber.inventorySystem.item;

import de.kaffeeliebhaber.inventorySystem.InventoryItemState;

public interface ItemListener {

	void itemUpdate(Item item, InventoryItemState itemState);
}
