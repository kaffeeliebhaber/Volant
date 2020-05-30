package de.kaffeeliebhaber.inventory.stats;

public abstract class CharacterStats {
	
	// DEFAULT VALUE
	private int maxHealth = 100;
	private int currentHelth;
	
	protected Stat armorStat;
	protected Stat damageStat;
	
	public CharacterStats() {
		currentHelth = maxHealth;
	}
	
	// setter
	public void setArmorStat(Stat armorStat) {
		this.armorStat = armorStat;
	}
	
	public void setDamageStat(Stat damageStat) {
		this.damageStat = damageStat;
	}
	
	// getter
	public int getHP() {
		return currentHelth;
	}
	
	public int getArmorValue() {
		return armorStat.getValue();
	}
	
	public int getDamageValue() {
		return damageStat.getValue();
	}
	
	// core
	public void takeDamage(int damage) {
		
		damage -= armorStat.getValue();

		if (damage > 0) {
			
			currentHelth -= damage;
			
			if (currentHelth <= 0) {
				die();
			}
		}
	}
	
	public void increaseHelth(int increaseHPValue) {
		currentHelth += increaseHPValue;
		
		if (currentHelth > maxHealth) {
			currentHelth = maxHealth;
		}
	}

	public void printStats() {
		System.out.println("(CharacterStats.printStats) | ARMOR " + armorStat.getValue() + ", DAMAGE: " + damageStat.getValue());
	}
	
	public abstract void die();
}
