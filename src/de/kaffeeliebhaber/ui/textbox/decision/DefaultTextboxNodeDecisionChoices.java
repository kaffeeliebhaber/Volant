package de.kaffeeliebhaber.ui.textbox.decision;

public class DefaultTextboxNodeDecisionChoices implements ITextboxNodeDecisionChoices {

	@Override
	public String getLeftChoice() {
		return "Ja";
	}

	@Override
	public String getRightChoice() {
		return "Nein";
	}

}
