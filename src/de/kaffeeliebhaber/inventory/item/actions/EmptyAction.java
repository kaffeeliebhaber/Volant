package de.kaffeeliebhaber.inventory.item.actions;

public class EmptyAction implements IItemAction {

	@Override
	public void execute() {
		
		System.out.println("(EmptyAction) No action to execute available.");
		
	}

}