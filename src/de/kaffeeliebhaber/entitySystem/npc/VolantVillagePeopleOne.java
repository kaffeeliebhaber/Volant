package de.kaffeeliebhaber.entitySystem.npc;

import de.kaffeeliebhaber.animation.Direction;
import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.behavior.moving.IMovingBehavior;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.switchsystem.SwitchSystem;
import de.kaffeeliebhaber.ui.textbox.TextboxNode;
import de.kaffeeliebhaber.ui.textbox.TextboxNodeDecision;
import de.kaffeeliebhaber.ui.textbox.TextboxNodeInformation;

public class VolantVillagePeopleOne extends NPC {

	public VolantVillagePeopleOne(float x, float y, int width, int height, Direction interactionDirection,
			IAnimationController animationController, IMovingBehavior movingBehavior) {
		super(x, y, width, height, interactionDirection, animationController, movingBehavior);
	}

	private TextboxNodeInformation infoIntroduction;
	private TextboxNodeInformation infoMasterKnightBrotherVisited;
	private TextboxNodeDecision decisionBroughtCaffe;
	private TextboxNodeInformation optionYesBroughtCaffe;
	private TextboxNodeInformation optioneNoBroughtCaffe;

	public TextboxNode getTextboxNode() {
		
		TextboxNode node = null;
		
		if (SwitchSystem.instance.isActivated(VolantMasterKnight.EVENT_BROUGHT_CAFFE)) {
			node = infoMasterKnightBrotherVisited;
		} else {
			node = infoIntroduction;
		}
		
		return node;
	}

	protected void initTextboxSetup() {
		
		infoIntroduction = new TextboxNodeInformation("Ich brauche dringend einen Kaffee.");
		infoMasterKnightBrotherVisited = new TextboxNodeInformation("Ich sehe du hast Heem einen Kaffee gebracht.");
		
		decisionBroughtCaffe = new TextboxNodeDecision("Hast du auch einen Kaffee für mich?");
		optionYesBroughtCaffe = new TextboxNodeInformation("Der schmeckt wirklich super.");
		optioneNoBroughtCaffe = new TextboxNodeInformation("Schade. Dann vielleicht ein anderes Mal.");
		
		infoMasterKnightBrotherVisited.setNextNode(decisionBroughtCaffe);
		decisionBroughtCaffe.setLeftNode(optionYesBroughtCaffe);
		decisionBroughtCaffe.setRightNode(optioneNoBroughtCaffe);
	}
	
	@Override public boolean isOnInteractionDirection(Player player) {
		return this.interactionDirection == Direction.DOWN  &&  player.getViewDirection() == Direction.UP;
	}

}
