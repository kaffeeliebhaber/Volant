package de.kaffeeliebhaber.inventorySystem.item.action;

public class EmptyAction implements IItemAction {

	@Override
	public void execute() {
		
		System.out.println("(EmptyAction) No action to execute available.");
		
	}

}