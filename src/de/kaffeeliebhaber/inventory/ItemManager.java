package de.kaffeeliebhaber.inventory;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.kaffeeliebhaber.controller.BoundingBoxController;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.inventory.item.Item;
import de.kaffeeliebhaber.tweens.InfoPaneEvent;
import de.kaffeeliebhaber.tweens.InfoPaneInformerListener;
import de.kaffeeliebhaber.ui.textbox.TextboxNodeInformation;

/**
 
 * Holds all available items in the world.
 * @author User
 
 */
public class ItemManager {

	/*
	 * 
	 * TODO: Hier muss auf jeden Fall auch noch eine Änderung passieren. Das perspektivische Rendern der Items passiert 
	 * noch nicht.
	 * 
	 */
	private final Player player;
	private List<Item> items;
//	private List<ItemManagerListener> itemManagerListeners;
	private List<InfoPaneInformerListener> infoPaneInformerListeners;
	
	public ItemManager(final Player player) {
		this.player = player;
		items = new ArrayList<Item>();
//		itemManagerListeners = new ArrayList<ItemManagerListener>();
		infoPaneInformerListeners = new ArrayList<InfoPaneInformerListener>();
	}
	
	public void addItem(final Item item) {
		items.add(item);
	}
	
	public void removeItem(final Item item) {
		items.remove(item);
	}
	
	public void update() {
		if (items.size() > 0) {
//			BoundingBox playerBoundingBox = player.getBoundingBox();
			BoundingBoxController playerBoundingBoxController = player.getBoundingBoxController();
			for (int i = items.size() - 1; i >= 0; i--) {
				Item item = items.get(i);
				if (item.getBoundingBoxController().intersects(playerBoundingBoxController)) {
					
					Inventory.instance.add(item);
					items.remove(i);
//					nofitifyItemManagerListeners(item);
					fireInformationPaneEvent(item);
				}
			}
		}
	}
	
	public void clear() {
		items.clear();
	}
	
	public void render(Graphics g, Camera camera) {
		
//		System.out.println("(ItemManager.render) | render");/
		if (items.size() > 0) {
			
			Iterator<Item> itemIterator = items.iterator();
			
			while (itemIterator.hasNext()) {
				itemIterator.next().render(g, camera);
			}
		}
	}
	
	private void fireInformationPaneEvent(Item item) {
		for (final InfoPaneInformerListener l : infoPaneInformerListeners) {
			l.performInfoPane(new InfoPaneEvent(null, item.getItemImage(), new TextboxNodeInformation(item.getName() + " was picked.")));
		}
	}

//	private void nofitifyItemManagerListeners(final Item pickedItem) {
//		for (final ItemManagerListener l : itemManagerListeners) {
//			l.itemPicked(pickedItem);
//		}
//	}
	
//	public void addItemManagerListener(final ItemManagerListener l) {
//		itemManagerListeners.add(l);
//	}
//	
//	public void removeItemManagerListene(final ItemManagerListener l) {
//		itemManagerListeners.remove(l);
//	}
	

	public void addInfoPaneInformerListener(final InfoPaneInformerListener l) {
		infoPaneInformerListeners.add(l);
	}
	
	public void removeInfoPaneInformerListener(final InfoPaneInformerListener l) {
		infoPaneInformerListeners.remove(l);
	}

}
