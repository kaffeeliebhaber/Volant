package de.kaffeeliebhaber.ui.textbox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TextboxNodeInformation extends TextboxNode {
	
	private static final String NEWLINE_CHAR = "\n";
	private static final int DEFAULT_CURRENTID = 0;
	private static final int TEXT_HEIGHT = 20; 
	private TextboxNode nextNode;
	private int size;
	private int currentID;
	private Map<Integer, TextboxNodeData> lines;
	private Set<Integer> linesKeySet;
	
	public TextboxNodeInformation(final String text) {
		this(0, text);
	}
	
	public TextboxNodeInformation(final int ID, final String text) {
		super(ID, text);
		setUpLinesKeySet(lines);
	}
	
	public void processText(final String text) {
		final String[] textContainer = createSplittedText(text);
		
		size = textContainer.length;
		
		lines = createAndInitNodeDataMap(textContainer);
	}
	
	private void setUpLinesKeySet(final Map<Integer, TextboxNodeData> lines) {
		linesKeySet = lines.keySet();
	}
	
	private String[] createSplittedText(String text) {
		return text.contains(NEWLINE_CHAR) ? text.split(NEWLINE_CHAR) : new String[] {text};
	}
	
	private Map<Integer, TextboxNodeData> createAndInitNodeDataMap(final String[] textContainer) {
		
		Map<Integer, TextboxNodeData> lines = new TreeMap<Integer, TextboxNodeData>();
		
		for (int i = 0; i < textContainer.length; i++) {
			
			TextboxNodeData nodeData = new TextboxNodeData(textContainer[i]);
			
			lines.put(i, nodeData);
		}
		
		return lines;
	}

	protected void checkAndUpdateForEndOrNextLine() {
		
		checkForEnd();
		
		if (!isEnded()) {
			nextLine();
		}
	}
	
	private void checkForEnd() {
		if (currentID == size - 1) {
			setEnded(true);
		}
	}
	
	public void render(Graphics g, int x, int y) {
		if (active) {
			
			g.setColor(Color.WHITE);
			
			for (Integer key : linesKeySet) {
				TextboxNodeData dataNode = lines.get(key);
				g.drawString(dataNode.getText().substring(0, dataNode.getCharsToShow()), x, key * TEXT_HEIGHT + y);
			}
		}
	}

	public void reset() {
		setCurrentID(DEFAULT_CURRENTID);
		setEnded(false);
		resetDataNodes();
	}
	
	private void resetDataNodes() {
		for (Integer key : linesKeySet) {
			lines.get(key).reset();
		}
	}
	
	public void input(final int keyID) {
		if (keyID == KeyEvent.VK_ENTER) {
			deactivate();
			reset();
		}
	}
	
	protected TextboxNodeData getCurrentNodeData() {
		return lines.get(currentID);
	}
	
	private void nextLine() {
		currentID++;	
	}
	
	private void setEnded(final boolean ended) {
		this.ended = ended;
	}

	public boolean isEnded() {
		return ended;
	}

	public boolean hasNextNode() {
		return nextNode != null;
	}
	
	public void setNextNode(final TextboxNode nextNode) {
		this.nextNode = nextNode;
	}
	
	public TextboxNode getNextNode() {
		return nextNode;
	}
	
	private void setCurrentID(final int currentID) {
		this.currentID = currentID;
	}

}
