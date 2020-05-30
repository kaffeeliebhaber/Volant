package de.kaffeeliebhaber.ui.textbox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.ui.textbox.decision.DefaultTextboxNodeDecisionChoices;
import de.kaffeeliebhaber.ui.textbox.decision.ITextboxNodeDecisionChoices;

public class TextboxNodeDecision extends TextboxNode {

	private static final int TEXT_HEIGHT = 20; 
	private TextboxNodeData nodeData;
	private TextboxNode leftNode;
	private TextboxNode rightNode;
	private TextboxNode decisionNode;
	private List<TextboxNodeDecisionListener> decisionListeners;
	private ITextboxNodeDecisionChoices choices;
	private String currentChoice;
	
	public TextboxNodeDecision(final String text) {
		this(0, text); 
	}
	
	public TextboxNodeDecision(final int ID, final String text) {
		this(ID, text, new DefaultTextboxNodeDecisionChoices()); 
	}
	
	public TextboxNodeDecision(final int ID, final String text, final ITextboxNodeDecisionChoices choices) {
		super(ID, text);
		
		decisionListeners = new ArrayList<TextboxNodeDecisionListener>();
		
		this.choices = choices;
		
		currentChoice = choices.getLeftChoice();
	}
	
	public void render(Graphics g, int x, int y) {
		
		if (active) {
			
			g.setColor(Color.WHITE);
			g.drawString(nodeData.getText().substring(0, nodeData.getCharsToShow()), x , y);

			if (nodeData.endReached()) {
				
				String leftSelection = currentChoice.equals(choices.getLeftChoice()) ? "> " + choices.getLeftChoice() + " <" : choices.getLeftChoice();
				String rightSelection = currentChoice.equals(choices.getRightChoice()) ? "> " + choices.getRightChoice() + " <" : choices.getRightChoice();
				
				g.drawString(leftSelection, x, y + 1 * TEXT_HEIGHT);
				g.drawString(rightSelection, x, y + 2 * TEXT_HEIGHT);
			}
		}
	}

	public boolean isEnded() {
		return ended;
	}
	
	public void input(int keyID) {
		updateSelection(keyID);
		checkToChangePage(keyID);
	}
	
	private void updateSelection(final int keyID) {
		
		switch (keyID) {
			case KeyEvent.VK_UP:
				currentChoice = choices.getLeftChoice();
				break;
			case KeyEvent.VK_DOWN:
				currentChoice = choices.getRightChoice();
				break;
		}
	}
	
	private void checkToChangePage(final int keyID) {
		// Set up selected decision
		if (keyID == KeyEvent.VK_ENTER && nodeData.endReached()) {
			ended = true;
			decisionNode = getNextSelectedNode();
			fireSelectedEvent();
			deactivate();
			reset();
		}
	}
	
	private TextboxNode getNextSelectedNode() {
		
		TextboxNode node = null;
		
		if (currentChoice.contentEquals(choices.getLeftChoice())) {
			node = leftNode;
		} else if (currentChoice.contentEquals(choices.getRightChoice())) {
			node = rightNode;
		}
		
		return node;
	}
	
	private boolean isCurrentLeftChoiceSelected() {
		return currentChoice.contentEquals(choices.getLeftChoice());
	}

	public void reset() {
		ended = false;
		currentChoice = choices.getLeftChoice();
		nodeData.reset();
	}
	
	public void processText(final String text) {
		nodeData = new TextboxNodeData(text);
	}

	public void setLeftNode(final TextboxNode leftNode) {
		this.leftNode = leftNode;
	}
	
	public void setRightNode(final TextboxNode rightNode) {
		this.rightNode = rightNode;
	}
	
	public TextboxNode getNextNode() {
		return decisionNode;
	}
	
	public boolean hasNextNode() {
		return decisionNode != null;
	}

	protected TextboxNodeData getCurrentNodeData() {
		return nodeData;
	}
	
	private void fireSelectedEvent() {
		for (int i = 0; i < decisionListeners.size(); i++) {
			decisionListeners.get(i).textboxDecisionNodeSelected(this.ID, isCurrentLeftChoiceSelected());
		}
	}
	
	public void addTextboxNodeDecisionListener(final TextboxNodeDecisionListener l) {
		decisionListeners.add(l);
	}
	
	public void removeTextboxNodeDecisionListener(final TextboxNodeDecisionListener l) {
		decisionListeners.remove(l);
	}

}
