package de.kaffeeliebhaber.entitySystem.npc;

import de.kaffeeliebhaber.animation.Direction;
import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.behavior.moving.IMovingBehavior;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.switchsystem.SwitchSystem;
import de.kaffeeliebhaber.ui.textbox.TextboxNode;
import de.kaffeeliebhaber.ui.textbox.TextboxNodeInformation;

public class Fox extends NPC {

	private TextboxNode infoNode;
	private TextboxNode questCompletedNode;
	
	public Fox(float x, float y, int width, int height, Direction interactionDirection, IAnimationController animationController, IMovingBehavior movingBehavior) {
		super(x, y, width, height, interactionDirection, animationController, movingBehavior);
	}
	
	@Override
	public void interact(Player player) {
		super.interact(player);
		
		if (SwitchSystem.instance.isActivated(101) && !SwitchSystem.instance.isActivated(102)) {
			SwitchSystem.instance.activate(102);
		}
	}
	
	@Override public TextboxNode getTextboxNode() {
		// TODO: Wenn 'null' zurückgegeben wird, wird zwar aktuell die UIInfoPane angezeigt, richtig auch, dass 
		// kein Text angezeigt wird. Jedoch lässt sich die UIInfoPane nicht schließen.
		
		TextboxNode returnNode = null;
		
		if (SwitchSystem.instance.isActivated(101)) {
			returnNode = questCompletedNode;
		} else {
			returnNode = infoNode;
		}
		
		return returnNode;
	}

	@Override protected void initTextboxSetup() {
		infoNode = new TextboxNodeInformation("Verschwinde!!!!\nIch suche mir mein Essen....");
		questCompletedNode = new TextboxNodeInformation("Du kennst Anne?\nBringst du mich zu ihr?");
	}
	
	@Override public boolean isOnInteractionDirection(Player player) {
		return this.interactionDirection == Direction.DOWN  &&  player.getViewDirection() == Direction.UP;
	}
	
	@Override
	public boolean isCollidable() {
		return false;
	}

}
