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

public class VolantFemaleAnne extends NPC implements TextboxNodeDecisionListener {

	private TextboxNodeInformation infoIntroduction;
	private TextboxNodeInformation infoIntroduction2;
	private TextboxNodeDecision searchForFoxDecistion;
	private TextboxNodeInformation optionYes;
	private TextboxNodeInformation optionNo;
	private TextboxNodeInformation thanks;
	private TextboxNodeInformation foxFounded;
	
	public VolantFemaleAnne(float x, float y, int width, int height, Direction interactionDirection, IAnimationController animationController, IMovingBehavior movingBehavior) {
		super(x, y, width, height, interactionDirection, animationController, movingBehavior);
	}

	@Override
	public boolean isOnInteractionDirection(Player player) {
		return interactionDirection == Direction.DOWN  &&  player.getViewDirection() == Direction.UP;
	}

	@Override
	public TextboxNode getTextboxNode() {
		
		TextboxNode node = null;
		
		if (SwitchSystem.instance.isActivated(102)) {
			node = foxFounded;
		} else if (SwitchSystem.instance.isActivated(101)) {
			node = thanks;
		} else {
			node = infoIntroduction;
		}
		
		return node;
	}

	@Override
	protected void initTextboxSetup() {
		infoIntroduction = new TextboxNodeInformation("Hallo...\n...ich suche...\n...meinen Fuchs...");
		
		infoIntroduction2 = new TextboxNodeInformation("Hast du Ihn gesehen?\nNein? Kann ich dich etwas fragen?\nJa? Super");
		
		infoIntroduction.setNextNode(infoIntroduction2);
		
		searchForFoxDecistion = new TextboxNodeDecision(101, "Suchst du meinen Fuchs für mich?");
		infoIntroduction2.setNextNode(searchForFoxDecistion);
		
		searchForFoxDecistion.addTextboxNodeDecisionListener(this);
		
		optionYes = new TextboxNodeInformation("Ich danke dir.");
		optionNo = new TextboxNodeInformation("Oh! Schade.\nSehr schade.\nBis bald!");
		
		searchForFoxDecistion.setLeftNode(optionYes);
		searchForFoxDecistion.setRightNode(optionNo);
		
		thanks = new TextboxNodeInformation("Danke das du meinen Fuchs suchst!");
		
		foxFounded = new TextboxNodeInformation("Vielen Dank das du mir meinen\nFuchs zurückgebracht hast.");
	}

	@Override
	public void textboxDecisionNodeSelected(int currentTextboxNodeID, boolean isLeftTextboxNodeSelected) {

		// 101 = Die Aufgabe wurde angenommen
		// 102 = Die Aufgabe wurde abgeschlossen
		if (currentTextboxNodeID == 101 && isLeftTextboxNodeSelected) {
			SwitchSystem.instance.activate(currentTextboxNodeID);
		}
	}

}
