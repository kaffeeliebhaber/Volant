package de.kaffeeliebhaber.inventorySystem.item;

import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.inventorySystem.InventoryItemState;

public class EquipItem extends Item {

	private boolean isEquipped;
	
	public EquipItem(ItemCategory itemCategory, ItemType itemType, String itemName, BufferedImage itemImage) {
		super(itemCategory, itemType, itemName, itemImage);
	}
	
	private void toggleIsEquipped() {
		isEquipped = !isEquipped;
	}
	
	private InventoryItemState getInventoryItemState() {
		return isEquipped ? InventoryItemState.EQUIPPED : InventoryItemState.UNEQUIPPED;
	}

	@Override
	public void use() {
		toggleIsEquipped();
		notifyItemListener(this, getInventoryItemState());
	}

}
