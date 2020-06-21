package de.kaffeeliebhaber.inventory.item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.inventory.item.actions.IItemAction;

public abstract class Item {

	private final ItemCategory itemCategory;
	private final ItemType itemType;
	private final String itemName;
	private String itemDescription;
	protected int quantity;
	private boolean stackable;
	private BoundingBox boundingBox;
	private BufferedImage itemImage;
	
	// action
	protected IItemAction itemAction;
	
	// item values
	private int damageValue;
	private int armorValue;
	
	public Item(final ItemCategory itemCategory, final ItemType itemType, String itemName, BufferedImage itemImage) {
		this.itemCategory = itemCategory;
		this.itemType = itemType;
		this.itemName = itemName;
		this.itemImage = itemImage;
		
		// DEFAULT QUANTITY
		this.quantity = 1;
	}
	
	// getter
	public ItemCategory getItemCategory() {
		return itemCategory;
	}
	
	public BoundingBox getBoundingBox() {
		return boundingBox;
	}
	
	public String getName() {
		return itemName;
	}
	
	public boolean isStackable() {
		return stackable;
	}
	
	public String getItemDescription() {
		return itemDescription;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public ItemType getItemType() {
		return itemType;
	}
	
	public int getDamageValue() {
		return damageValue;
	}
	
	public int getArmorValue() {
		return armorValue;
	}
	
	public BufferedImage getItemImage() {
		return itemImage;
	}
	
	public void setBoundingBox(final BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}
	
	public void setStackable(boolean stackable) {
		this.stackable = stackable;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void adjustQuantity(int count) {
		this.quantity += count;
	}
	
	public void adjustQuantity() {
		adjustQuantity(-1);
	}
	
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	
	public void setItemAction(IItemAction itemAction) {
		this.itemAction = itemAction;
	}
	
	public void setDamageValue(int damageValue) {
		this.damageValue = damageValue;
	}
	
	public void setArmorValue(int armorValue) {
		this.armorValue = armorValue;
	}
	
	public abstract void use();
	
	public boolean isAvailable() {
		return quantity > 0;
	}
	
	public void render(Graphics g, Camera camera) {
		g.drawImage(itemImage, (int) (boundingBox.getX() - camera.getX()), (int) (boundingBox.getY() - camera.getY()), boundingBox.getWidth(), boundingBox.getHeight(), null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boundingBox == null) ? 0 : boundingBox.hashCode());
		result = prime * result + ((itemCategory == null) ? 0 : itemCategory.hashCode());
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((itemType == null) ? 0 : itemType.hashCode());
		result = prime * result + (stackable ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		
		if (itemCategory != other.itemCategory)
			return false;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (itemType != other.itemType)
			return false;
		if (stackable != other.stackable)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [itemCategory=" + itemCategory + ", itemType=" + itemType + ", itemName=" + itemName
				+ ", damageValue=" + damageValue + ", armorValue=" + armorValue + "]";
	}
	
}
