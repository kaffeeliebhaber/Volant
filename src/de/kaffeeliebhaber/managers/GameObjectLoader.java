package de.kaffeeliebhaber.managers;

import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.newUI.UIInventory;
import de.kaffeeliebhaber.tilesystem.chunk.GameWorld;
import de.kaffeeliebhaber.tilesystem.transition.Transition;
import de.kaffeeliebhaber.ui.UIHud;
import de.kaffeeliebhaber.ui.UIInfoPane;

public abstract class GameObjectLoader {
	
	public abstract Camera getCamera();
	public abstract Player getPlayer();
	public abstract Transition getTransition();
	//public abstract UIInventoryManager getUIInventoryManager();
	public abstract UIInfoPane getUIInfoPane();
	public abstract UIHud getUIHud();
	public abstract GameWorld getGameWorld();
	public abstract UIInventory getUIInventory();
}
