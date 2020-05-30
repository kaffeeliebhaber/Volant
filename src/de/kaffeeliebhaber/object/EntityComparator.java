package de.kaffeeliebhaber.object;

import java.util.Comparator;

public class EntityComparator implements Comparator<Entity> {

	@Override
	public int compare(Entity a, Entity b) {
		
		int compValue = 0;
		
		if ((a.getY() + a.getHeight()) < (b.getY() + b.getHeight())) {
			compValue = -1;
		} else {
			compValue = 1;
		}
		return compValue;
	}

}
