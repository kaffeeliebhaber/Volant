package de.kaffeeliebhaber.inventory.item;

import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.inventory.Inventory;

public class UseItem extends Item {

	public UseItem(ItemCategory itemCategory, ItemType itemType, String itemName, BufferedImage itemImage) {
		super(itemCategory, itemType, itemName, itemImage);
	}

	@Override
	public void use() {
		if (itemAction != null) {
			itemAction.execute();
		}
		
		quantity--;
		
		if (!isAvailable()) {
			
			//System.out.println("(UseItem.use) | REMOVE ITEM");
			Inventory.instance.remove(this);
		}
	}

}
