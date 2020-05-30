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

public class VolantVillageElder extends NPC implements TextboxNodeDecisionListener {

	// TEXTBOX-INFORMATION
	public VolantVillageElder(float x, float y, int width, int height, Direction interactionDirection, IAnimationController animationController, IMovingBehavior movingBehavior) {
		super(x, y, width, height, interactionDirection, animationController, movingBehavior);
	}

	// 1. contact
	private TextboxNodeInformation infoIntroduction;
	private TextboxNodeDecision decisionNeedTea;
	private TextboxNodeInformation optionYesNeedTea;
	private TextboxNodeInformation optionNoNeedTea;
	
	// 2. contact
	private TextboxNodeInformation alreadyVisited;
	
	public static final int EVENT_NEED_TEA_DECISION_ID = 1;
	private TextboxNode currentNode;
	

	protected void initTextboxSetup() {
		
		// 1. contact
		infoIntroduction = new TextboxNodeInformation("Hallo Held!\nMein Name ist Gordom.\nIch bin der Hüter dieses Dorfes.");
		decisionNeedTea = new TextboxNodeDecision(EVENT_NEED_TEA_DECISION_ID, "Brauchst du einen Kaffee?");
		
		decisionNeedTea.addTextboxNodeDecisionListener(this);
		
		optionYesNeedTea = new TextboxNodeInformation("Dann würde ich vorschlagen, dass\ndu dir einen Kaffee zubereitest.");
		optionNoNeedTea = new TextboxNodeInformation("Hast du es mal mit Tee\nversucht? Solltest du probieren.");
		
		// 2. contact
		alreadyVisited = new TextboxNodeInformation("Ich gehe gleich erst einmal einen\nKaffee trinken.");
		
		// create connection between each textbox node.
		infoIntroduction.setNextNode(decisionNeedTea);
		
		decisionNeedTea.setLeftNode(optionYesNeedTea);
		decisionNeedTea.setRightNode(optionNoNeedTea);
		
		// init textbox node
		currentNode = infoIntroduction;
	}

	public TextboxNode getTextboxNode() {
		return currentNode;
	}

	public void textboxDecisionNodeSelected(final int currentTextboxNodeID, final boolean isLeftTextboxNodeSelected) {
		if (currentTextboxNodeID == EVENT_NEED_TEA_DECISION_ID && isLeftTextboxNodeSelected) {
			SwitchSystem.instance.activate(EVENT_NEED_TEA_DECISION_ID);
			currentNode = alreadyVisited;
		}
	}
	
	@Override public boolean isOnInteractionDirection(Player player) {
		return this.interactionDirection == Direction.DOWN  &&  player.getViewDirection() == Direction.UP;
	}
}
