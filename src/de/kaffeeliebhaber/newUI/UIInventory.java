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
	
	private static final int GAB_BETWEEN_INVENTORY_AND_EQUIPMENT = 300;
	private static final int TRANSLATE_X = 30;
	private static final int TRANSLATE_Y = 30;
	private final List<UIView> uiViews;
	private int openInventoryKeyID;
	
	public UIInventory(float x, float y, int width, int height, final InventoryManagerModel inventoryManagerModel, final EquipmentManagerModel equipmentManagerModel) {
		super(x, y, width, height);
		
		uiViews = new ArrayList<UIView>();
		
		uiViews.add(new UIViewPanel(x, y, width, height));
		uiViews.add(new UIViewLabel("INVENTORY", x + 200, y + 20, new java.awt.Color(255, 255, 255)));
		uiViews.add(new UIViewInventory(x + TRANSLATE_X, y + TRANSLATE_Y, inventoryManagerModel));
		uiViews.add(new UIViewEquipment(x + TRANSLATE_X + GAB_BETWEEN_INVENTORY_AND_EQUIPMENT, y + TRANSLATE_Y, equipmentManagerModel));
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
