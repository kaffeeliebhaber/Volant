package de.kaffeeliebhaber.inventorySystem.item;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import de.kaffeeliebhaber.inventorySystem.InventoryItemState;
import de.kaffeeliebhaber.inventorySystem.item.action.IItemAction;

public class UseItem extends Item {

	public UseItem(ItemCategory itemCategory, ItemType itemType, String itemName, BufferedImage itemImage, IItemAction itemAction) {
		super(itemCategory, itemType, itemName, itemImage);
		this.setItemAction(itemAction);
	}

	@Override
	public void use() {
		itemAction.execute();
		quantity--;
		
		if (!isAvailable()) {
			notifyItemListener(this, InventoryItemState.REMOVED);
		}
	}
	
	@Override
	public Item clone() throws CloneNotSupportedException {
		Item itemClone = new UseItem(this.itemCategory, this.itemType, this.itemName, this.itemImage, this.itemAction);
		itemClone.itemDescription = this.itemDescription;
		itemClone.quantity = this.quantity;
		itemClone.stackable = this.stackable;
		itemClone.boundingBoxController = this.boundingBoxController.clone();
		itemClone.itemListeners = new ArrayList<>(this.itemListeners);
		return itemClone;
	}

}
