package de.kaffeeliebhaber.inventory.item;

import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.inventory.EquipmentManager;
import de.kaffeeliebhaber.inventory.Inventory;

public class EquipItem extends Item {

	public EquipItem(ItemCategory itemCategory, ItemType itemType, String itemName, BufferedImage itemImage) {
		super(itemCategory, itemType, itemName, itemImage);
	}

	@Override
	public void use() {
		EquipmentManager.instance.equip(this);
		Inventory.instance.remove(this);
	}

}
