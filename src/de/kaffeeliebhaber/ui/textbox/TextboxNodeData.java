package de.kaffeeliebhaber.ui.textbox;

public class TextboxNodeData {

	private static final int DEFAULT_INCREASE_VALUE = 1;
	private String text;
	private int charsToShow;
	
	public TextboxNodeData(final String text) {
		setText(text);
	}
	
	public void setText(final String text) {
		this.text = text;
	}
	
	public void setCharsToShow(final int charsToShow) {
		this.charsToShow = charsToShow;
	}
	
	public void increaseCharsToShow(final int increaseValue) {
		setCharsToShow(increaseValue + charsToShow);
	}
	
	public void increaseCharsToShow() {
		increaseCharsToShow(DEFAULT_INCREASE_VALUE);
	}
	
	public String getText() { 
		return text; 
	}
	
	public int getCharsToShow() { 
		return charsToShow; 
	}
	
	public boolean endReached() {
		return charsToShow == (text.length());
	}
	
	public void reset() {
		charsToShow = 0;
	}
	
	public void adjustCharToShowEndReachedCondition() {
		
		if (getCharsToShow() >= text.length()) {
			charsToShow = text.length();
		}
	}
	
	public String toString() {
		return text;
	}
}
