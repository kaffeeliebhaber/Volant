package de.kaffeeliebhaber.inventory.stats;

import de.kaffeeliebhaber.inventory.EquipmentManagerListener;
import de.kaffeeliebhaber.inventory.item.Item;

public class PlayerStats extends CharacterStats implements EquipmentManagerListener {

	private float food;
	
	public int getFood() {
		return (int) food;
	}
	
	@Override
	public void die() {
		System.out.println("(PlayerStats.die) | PLAYER DIED.");
	}

	@Override
	public void equipped(Item oldItem, Item newItem) {
	
		if (newItem != null) {
			armorStat.addModifier(newItem.getArmorValue());
			damageStat.addModifier(newItem.getDamageValue());
		}
		
		if (oldItem != null) {
			armorStat.removeModifier(oldItem.getArmorValue());
			damageStat.removeModifier(oldItem.getDamageValue());
		}
	}

	@Override
	public void unequipped(Item oldItem) {
		
		if (oldItem != null) {
			armorStat.removeModifier(oldItem.getArmorValue());
			damageStat.removeModifier(oldItem.getDamageValue());
		}
		
	}
}
