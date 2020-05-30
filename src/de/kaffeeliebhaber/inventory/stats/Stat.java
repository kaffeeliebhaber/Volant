package de.kaffeeliebhaber.inventory.stats;

import java.util.ArrayList;
import java.util.List;

public class Stat {

	private int value;
	private List<Integer> modifiers;
	
	
	public Stat(int value) {
		this.value = value;
		modifiers = new ArrayList<Integer>();
	}
	
	public void addModifier(int modifier) {
		if (modifier != 0) {
			modifiers.add(modifier);
		}
	}
	
	public void removeModifier(int modifier) {
		if (modifier != 0) {
			modifiers.remove(new Integer(modifier));
		}
	}
	
	public int getValue() {
		
		int baseValue = value;
		
		for (int modifier : modifiers) {
			baseValue += modifier;
		}
		
		return baseValue;
	}
	
}
