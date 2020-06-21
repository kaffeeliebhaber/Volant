package de.kaffeeliebhaber.inventory.item;

import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.inventory.Inventory;
import de.kaffeeliebhaber.inventory.item.actions.IItemAction;

public class UseItem extends Item {

	public UseItem(ItemCategory itemCategory, ItemType itemType, String itemName, BufferedImage itemImage, IItemAction itemAction) {
		super(itemCategory, itemType, itemName, itemImage);
		this.setItemAction(itemAction);
	}

	@Override
	public void use() {
		/*
		if (itemAction != null) {
			itemAction.execute();
		}
		*/
		
		itemAction.execute();
		quantity--;
		
		if (!isAvailable()) {
			
			//System.out.println("(UseItem.use) | REMOVE ITEM");
			Inventory.instance.remove(this);
		}
	}

}
