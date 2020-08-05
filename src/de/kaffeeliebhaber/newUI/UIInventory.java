package de.kaffeeliebhaber.newUI;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.core.KeyManagerListener;
import de.kaffeeliebhaber.core.MouseManagerListener;
import de.kaffeeliebhaber.inventorySystem.EquipmentManagerModel;
import de.kaffeeliebhaber.inventorySystem.InventoryManagerModel;

public class UIInventory extends UIView implements KeyManagerListener, MouseManagerListener {
	
	private List<UIView> uiViews;
	private int openInventoryKeyID;
	
	public UIInventory(float x, float y, int width, int height, final InventoryManagerModel inventoryManagerModel, final EquipmentManagerModel equipmentManagerModel) {
		super(x, y, width, height);
		
		uiViews = new ArrayList<UIView>();
		
		uiViews.add(new UIViewInventory(100, 100, inventoryManagerModel));
		uiViews.add(new UIViewEquipment(500, 110, 210, 100, equipmentManagerModel));
	}
	
	public void setKeyIDOpenInventory(final int keyID) {
		this.openInventoryKeyID = keyID;
	}

	public void render(Graphics g) {
		if (visible) {
			uiViews.stream().forEach(view -> view.render(g));
		}
	}
	
	@Override
	public void mouseReleased(int buttonID, Point p) {
		if (visible) {
			uiViews.stream().forEach(view -> view.clicked(buttonID, p));
		}
	}

	@Override public void mouseMoved(Point p) {
		if (visible) {
			super.mouseMoved(p);
			uiViews.stream().forEach(view -> view.mouseMoved(p));
		}
	}

	@Override public void keyPressed(int keyID) {}

	@Override
	public void keyReleased(int keyID) {
		if (keyID == openInventoryKeyID) {
			toggleVisibility();
			uiViews.stream().forEach(view -> view.toggleVisibility());
		}
	}
	
}
