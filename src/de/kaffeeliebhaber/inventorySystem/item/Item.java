package de.kaffeeliebhaber.inventorySystem.item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.controller.BoundingBoxController;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.inventorySystem.InventoryItemState;
import de.kaffeeliebhaber.inventorySystem.item.action.IItemAction;

public abstract class Item implements Cloneable {

	protected List<ItemListener> itemListeners;
	protected final ItemCategory itemCategory;
	protected final ItemType itemType;
	protected final String itemName;
	protected String itemDescription;
	protected int quantity;
	protected boolean stackable;
	protected BufferedImage itemImage;
	protected BoundingBoxController boundingBoxController;
	
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
		
		boundingBoxController = new BoundingBoxController();
		
		// DEFAULT QUANTITY
		this.quantity = 1;
		
		itemListeners = new ArrayList<ItemListener>();
	}
	
	// getter
	public ItemCategory getItemCategory() {
		return itemCategory;
	}
	
	public BoundingBoxController getBoundingBoxController() {
		return boundingBoxController;
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
	
	public void addBoundingBox(final BoundingBox boundingBox) {
		boundingBoxController.addBoundingBox(boundingBox);
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
		boundingBoxController.calcBoundingBoxesDimensions();
		g.drawImage(itemImage, (int) (boundingBoxController.getX() - camera.getX()), (int) (boundingBoxController.getY() - camera.getY()), boundingBoxController.getWidth(), boundingBoxController.getHeight(), null);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((boundingBoxController == null) ? 0 : boundingBoxController.hashCode());
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
	
	protected void notifyItemListener(final Item item, final InventoryItemState itemState) {
		
		for (int i = itemListeners.size() - 1; i >= 0; i--) {
			itemListeners.get(i).itemUpdate(item, itemState);
		}
	}
	
	public void addItemListner(final ItemListener l) {
		if (!itemListeners.contains(l)) {
			itemListeners.add(l);
		}
	}
	
	public void removeItemListener(final ItemListener l) {
		itemListeners.remove(l);
	}
	
	@Override
	public Item clone() throws CloneNotSupportedException {
		return (Item) super.clone();
	}
	
}
