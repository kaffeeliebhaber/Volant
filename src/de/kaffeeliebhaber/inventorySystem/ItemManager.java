package de.kaffeeliebhaber.inventorySystem;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.kaffeeliebhaber.controller.BoundingBoxController;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.inventorySystem.item.Item;
import de.kaffeeliebhaber.tweens.InfoPaneEvent;
import de.kaffeeliebhaber.tweens.InfoPaneInformerListener;
import de.kaffeeliebhaber.ui.textbox.TextboxNodeInformation;

public class ItemManager {

	private List<Item> items;
	private InfoPaneInformerListener infoPaneInformerListener;
	private List<ItemManagerListener> itemManagerListeners;
	
	public ItemManager() {
		items = new ArrayList<Item>();
		itemManagerListeners = new ArrayList<ItemManagerListener>();
	}
	
	public void addItem(final Item item) {
		items.add(item);
	}
	
	public void removeItem(final Item item) {
		items.remove(item);
	}
	
	public void update(final Entity entity) {
		if (items.size() > 0) {
			BoundingBoxController playerBoundingBoxController = entity.getBoundingBoxController();
			for (int i = items.size() - 1; i >= 0; i--) {
				Item item = items.get(i);
				if (item.getBoundingBoxController().intersects(playerBoundingBoxController)) {
					items.remove(i);
					fireInformationPaneEvent(item);
					fireItemPickedEvent(item);
					
				}
			}
		}
	}
	
	public void clear() {
		items.clear();
	}
	
	public void render(Graphics g, Camera camera) {
		
		if (items.size() > 0) {
			
			Iterator<Item> itemIterator = items.iterator();
			
			while (itemIterator.hasNext()) {
				itemIterator.next().render(g, camera);
			}
		}
	}
	
	private void fireInformationPaneEvent(Item item) {
		if (infoPaneInformerListener != null) {
			infoPaneInformerListener.performInfoPane(new InfoPaneEvent(null, item.getItemImage(), new TextboxNodeInformation(item.getName() + " was picked.")));
		}
	}

	public void setInfoPaneInformerListener(final InfoPaneInformerListener l) {
		infoPaneInformerListener = l;
	}
	
	public void removeInfoPaneInformerListener(final InfoPaneInformerListener l) {
		infoPaneInformerListener = null;
	}
	
	private void fireItemPickedEvent(final Item pickedItem) {
		itemManagerListeners.stream().forEach(l -> l.itemPicked(pickedItem));
	}
	
	public void addItemManagerListener(final ItemManagerListener itemManagerListener) {
		itemManagerListeners.add(itemManagerListener);
	}
	
	public void removeItemManagerListener(final ItemManagerListener itemManagerListener) {
		itemManagerListeners.remove(itemManagerListener);
	}

}
