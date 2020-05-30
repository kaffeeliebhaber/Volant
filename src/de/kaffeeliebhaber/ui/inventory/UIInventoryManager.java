package de.kaffeeliebhaber.ui.inventory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

import de.kaffeeliebhaber.assets.AssetsLoader;
import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.input.KeyManagerListener;
import de.kaffeeliebhaber.inventory.EquipmentManager;
import de.kaffeeliebhaber.inventory.Inventory;

/**
 * Dient zur Darstellung unseres Inventars vom Spieler.
 * @author User
 *
 */
public class UIInventoryManager extends UIElement implements KeyManagerListener {

	private UIInventoryRepresenter inventoryRepresenter;
	private UIEquipmentRepresenter equipmentRepresenter;
	
	private final Inventory inventory;
	private final EquipmentManager equipmentManager;
	private final String inventoryName = "Inventory";
	private int inventoryNamePositionX;
	
	public UIInventoryManager(Inventory inventory, EquipmentManager equipmentManager, int x, int y) {
		
		super(x, y);
		
		this.inventory = inventory;
		this.equipmentManager = equipmentManager;

		// register listener
		KeyManager.instance.addKeyManagerListener(this);
		
		init();
	}

	private void init() {
		
		inventoryRepresenter = new UIInventoryRepresenter(inventory, (int) x, (int) y);
		equipmentRepresenter = new UIEquipmentRepresenter(equipmentManager,(int)  x + inventoryRepresenter.getWidth() + UIInventoryConstants.margin, 100);
		
		// text dimension
		FontRenderContext frc = new FontRenderContext(new AffineTransform(), true, true);     
		int textwidth = (int)(AssetsLoader.inventoryFont.getStringBounds(inventoryName, frc).getWidth());
		
		// text center position
		inventoryNamePositionX = (int) x + (inventoryRepresenter.getWidth() - textwidth) / 2;
	}
	
	public void toggleVisibility() {
		super.toggleVisibility();
		
		inventoryRepresenter.toggleVisibility();
		equipmentRepresenter.toggleVisibility();
	}
	
	public void update(float timeSinceLastFrame) {}
	
	public boolean isOpen() {
		return visible;
	}
	
	public void render(Graphics g) {
		
		if (visible) {
			inventoryRepresenter.render(g);
			equipmentRepresenter.render(g);
			
			// draw inventory name
			g.setFont(AssetsLoader.inventoryFont);
			g.setColor(Color.WHITE);		
			g.drawString(inventoryName, inventoryNamePositionX, (int) (y - 5));
		}
	}

	@Override
	public void keyReleased(int keyID) {
		
		if (keyID == KeyEvent.VK_I) {
			toggleVisibility();
		}
	}

	@Override public void keyPressed(int keyID) {}
	@Override protected void calcDimension() {}

}
