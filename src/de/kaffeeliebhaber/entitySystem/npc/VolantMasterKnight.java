package de.kaffeeliebhaber.entitySystem.npc;

import de.kaffeeliebhaber.animation.Direction;
import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.behavior.moving.IMovingBehavior;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.switchsystem.SwitchSystem;
import de.kaffeeliebhaber.ui.textbox.TextboxNode;
import de.kaffeeliebhaber.ui.textbox.TextboxNodeDecision;
import de.kaffeeliebhaber.ui.textbox.TextboxNodeDecisionListener;
import de.kaffeeliebhaber.ui.textbox.TextboxNodeInformation;

public class VolantMasterKnight extends NPC implements TextboxNodeDecisionListener {
	
	public VolantMasterKnight(float x, float y, int width, int height, Direction interactionDirection,
			IAnimationController animationController, IMovingBehavior movingBehavior) {
		super(x, y, width, height, interactionDirection, animationController, movingBehavior);
	}

	private TextboxNodeInformation infoIntroduction;
	private TextboxNodeDecision decisionBroughtCaffe;
	private TextboxNodeInformation optionYesBroughtCaffe;
	private TextboxNodeInformation optionNoBroughtCaffe;
	
	private TextboxNodeInformation infoBrotherAlreadyVisited;
	
	public static final int EVENT_BROUGHT_CAFFE = 2;

	protected void initTextboxSetup() {
		infoBrotherAlreadyVisited = new TextboxNodeInformation("Ich sehe du warst schon beim\nGordom. Wie geht es ihm?\n Habe ihn schon lange nicht mehr gesehen.");
		decisionBroughtCaffe = new TextboxNodeDecision(EVENT_BROUGHT_CAFFE, "Hast du mir einen Kaffee mitgebracht?");
		decisionBroughtCaffe.addTextboxNodeDecisionListener(this);
		optionYesBroughtCaffe = new TextboxNodeInformation("Ist das der Kaffee der Kaffeerösterei\nBurg aus Hamburg? Schmeckt man.");
		optionNoBroughtCaffe = new TextboxNodeInformation("Finde ich schade.\nVielleicht ein anderes Mal.");
		infoIntroduction = new TextboxNodeInformation("Heute nicht.\nSprich erst mit Gordom, meinem Bruder.");
		
		infoBrotherAlreadyVisited.setNextNode(decisionBroughtCaffe);
		decisionBroughtCaffe.setLeftNode(optionYesBroughtCaffe);
		decisionBroughtCaffe.setRightNode(optionNoBroughtCaffe);
	}

	public TextboxNode getTextboxNode() {
		
		TextboxNode node = null;
		
		if (SwitchSystem.instance.isActivated(VolantVillageElder.EVENT_NEED_TEA_DECISION_ID)) {
			node = infoBrotherAlreadyVisited;
		} else {
			node = infoIntroduction;
		}
		
		return node;
	}

	public void textboxDecisionNodeSelected(final int currentTextboxNodeID, final boolean isLeftTextboxNodeSelected) {
		if (currentTextboxNodeID == EVENT_BROUGHT_CAFFE && isLeftTextboxNodeSelected) {
			SwitchSystem.instance.activate(EVENT_BROUGHT_CAFFE);
		}
	}

	@Override public boolean isOnInteractionDirection(Player player) {
		return this.interactionDirection == Direction.DOWN  &&  player.getViewDirection() == Direction.UP;
	}
}
