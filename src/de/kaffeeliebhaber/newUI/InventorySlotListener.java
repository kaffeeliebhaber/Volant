package de.kaffeeliebhaber.newUI;

import de.kaffeeliebhaber.inventorySystem.item.Item;

public interface InventorySlotListener {

	//void clicked(final UIInventorySlot slot, final Item item, final InventoryItemState itemState);
	void clicked(final int ID, final Item item);
}
