package de.kaffeeliebhaber.tweens;

import java.awt.image.BufferedImage;

import de.kaffeeliebhaber.ui.textbox.TextboxNode;

public class InfoPaneEvent {

	private InfoPaneListener listener;
	private BufferedImage image;
	private TextboxNode textboxNode;
	
	public InfoPaneEvent(final InfoPaneListener listener, final BufferedImage image, final TextboxNode textboxNode) {
		this.listener = listener;
		this.image = image;
		this.textboxNode = textboxNode;
	}
	
	public InfoPaneListener getInfoPaneListener() {
		return listener;
	}
	
	public BufferedImage getImage() {
		return image;
	}
	
	public TextboxNode getTextboxNode() {
		return textboxNode;
	}
}
