package de.kaffeeliebhaber.inventory.item.actions;

import de.kaffeeliebhaber.entitySystem.Player;

public class FillUpHealthPointsAction implements IItemAction {

	private Player player;
	private int value;
	
	public FillUpHealthPointsAction(Player player, int value) {
		this.player = player;
		this.value = value;
	}
	
	public void execute() {
		player.getStats().increaseHelth(value);
	}
}
