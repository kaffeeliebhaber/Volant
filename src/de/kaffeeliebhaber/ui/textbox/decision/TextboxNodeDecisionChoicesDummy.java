package de.kaffeeliebhaber.ui.textbox.decision;

public class TextboxNodeDecisionChoicesDummy implements ITextboxNodeDecisionChoices {

	@Override
	public String getLeftChoice() {
		return "Links";
	}

	@Override
	public String getRightChoice() {
		return "Rechts";
	}

}
