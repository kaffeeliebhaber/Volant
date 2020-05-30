package de.kaffeeliebhaber.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import de.kaffeeliebhaber.inventory.item.Item;
import de.kaffeeliebhaber.inventory.item.ItemCategory;

/**
 * SINGELTON PATTERN FOR INVENTORY.
 * Get this instance via Inventory.instance.
 * @author User
 *
 */
public class Inventory {

	public static Inventory instance;
	
	static {
		
		if (instance == null) {
			instance = new Inventory();
		}
	}

	private Map<ItemCategory, List<Item>> items;
	
	
	private List<InventoryListener> inventoryListeners;
	
	private Inventory() {
		items = new TreeMap<ItemCategory, List<Item>>();
		inventoryListeners = new ArrayList<InventoryListener>();
	}
	
	public void add(final Item item) {
		
		final ItemCategory itemCategory = item.getItemCategory();
		
		/*
		 Die Category des aktuellen Items befindet sich noch nicht im Inventar. 
		 Wir fügen die neue Category und das Item in unser Inventory ein.
		 */
		if (!items.containsKey(itemCategory)) {
			List<Item> tempList = new ArrayList<Item>();
			tempList.add(item);
			items.put(itemCategory, tempList);
			fireInventoryChangedEvent();
		} else {
			/*
			 Die übergebene Category existiert bereits.
			 */
			List<Item> currentItems = items.get(itemCategory);

			if (currentItems.contains(item)) {

				// Falls das Item stackable ist, wird der Count erhöht. Ansonsten wir das Item erneut hinzugefügt.
				Item currentItem = currentItems.get(currentItems.indexOf(item));
				
				if (currentItem.isStackable()) {
					currentItem.adjustQuantity(item.getQuantity());
				} else {
					items.get(itemCategory).add(item);
					fireInventoryChangedEvent();
				}
			} else {
				items.get(itemCategory).add(item);
				fireInventoryChangedEvent();
			}
		}
		
	}
	
	public void remove(final Item item) {
		
		final ItemCategory itemCategory = item.getItemCategory();
		
		if (items.containsKey(itemCategory)) {
			items.get(itemCategory).remove(item);
			fireInventoryChangedEvent();
		}
	}
	
	public List<Item> getItemList(final ItemCategory itemCategory) {
		List<Item> itemList = null;
		
		if (items.containsKey(itemCategory)) {
			itemList = items.get(itemCategory);
		}
		
		return itemList;
	}
	
	public Item getItem(final ItemCategory itemCategory, final int index) {
		Item item = null;
		
		if (items.containsKey(itemCategory)) {
			item = items.get(itemCategory).get(index);
		}
		
		return item;
	}
	
	public List<Item> getAllItems() {
		List<Item> returnItems = new ArrayList<Item>();
		
		Iterator<List<Item>> listItemIterator = items.values().iterator();
		
		while (listItemIterator.hasNext()) {
			returnItems.addAll(listItemIterator.next());
		}
		
		return returnItems;
	}
	
	public int size(final ItemCategory itemCategory) {
		int size = 0;
		
		if (items.containsKey(itemCategory)) {
			size = items.get(itemCategory).size();
		}
		
		return size;
	}

	private void fireInventoryChangedEvent() {
		for (InventoryListener l : inventoryListeners) {
			l.inventoryChanged();
		}
	}
	
	public void addInventoryListener(final InventoryListener l) {
		inventoryListeners.add(l);
	}
	
	public void removeInventoryListener(final InventoryListener l) {
		inventoryListeners.remove(l);
	}
	
}
