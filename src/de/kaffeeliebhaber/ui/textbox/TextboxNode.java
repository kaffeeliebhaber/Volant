package de.kaffeeliebhaber.ui.textbox;

import java.awt.Graphics;

public abstract class TextboxNode {

	private static final int DELAY_TIME_CHARS = 4;
	protected boolean active;
	protected boolean ended;
	protected float pastTime;
	protected int ID;
	
	public TextboxNode(final String text) {
		this(0, text);
	}
	
	public TextboxNode(final int ID, final String text) {
		deactivate();
		setID(ID);
		processText(text);
	}
	
	private void setID(final int ID) {
		this.ID = ID;
	}
	
	public final void activate() {
		setActive(true);
	}
	
	public final void deactivate() {
		setActive(false);
	}
	
	public void update(float timeSinceLastFrame) {
		if (shouldUpdateCalled()) {
			
			final TextboxNodeData nodeData = getCurrentNodeData();
			
				
			if (!nodeData.endReached()) {
				updatePastTime(timeSinceLastFrame);
				
				if (shouldIncreaseCharsToShow()) {
					nodeData.increaseCharsToShow();
					resetPastTime();
				}
				
				nodeData.adjustCharToShowEndReachedCondition();
				
			} else {
				checkAndUpdateForEndOrNextLine();
			}
		}
	}
	
	protected boolean shouldUpdateCalled() {
		return active && !ended;
	}
	
	protected void updatePastTime(float timeSinceLastFrame) {
		pastTime += timeSinceLastFrame;
	}
	
	protected void resetPastTime() {
		pastTime = 0;
	}
	
	protected boolean shouldIncreaseCharsToShow() {
		return pastTime > DELAY_TIME_CHARS;
	}
	
	private void setActive(boolean active) {
		this.active = active;
	}
	
	public final boolean isActive() {
		return active;
	}
	
	protected void checkAndUpdateForEndOrNextLine() {}

	protected abstract TextboxNodeData getCurrentNodeData();

	protected abstract void processText(final String text);
	
	public abstract void render(Graphics g, int x, int y);
	
	public abstract void reset();

	public abstract boolean isEnded();
	
	public abstract TextboxNode getNextNode();
	
	public abstract boolean hasNextNode();
	
	public void input(int keyID) {}
}
