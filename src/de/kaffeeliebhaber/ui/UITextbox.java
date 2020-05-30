package de.kaffeeliebhaber.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.animation.Animation;
import de.kaffeeliebhaber.animation.AnimationHandler;
import de.kaffeeliebhaber.assets.AssetsLoader;
import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.input.KeyManagerListener;
import de.kaffeeliebhaber.ui.textbox.TextboxListener;
import de.kaffeeliebhaber.ui.textbox.TextboxNode;

public class UITextbox implements KeyManagerListener {

	private static final String ANIMATION_NAME = "ARROW";
	private static final int ANIMATION_DELAY_TIME = 4;
	private boolean active;
	private TextboxNode node;
	private AnimationHandler animationHandler;

	private int textPosX;
	private int textPosY;

	private int arrowPosX;
	private int arrowPosY;

	private List<TextboxListener> textboxListeners;

	public UITextbox() {

		textboxListeners = new ArrayList<TextboxListener>();

		KeyManager.instance.addKeyManagerListener(this);

		// CREATE AND INIT ANIMATIONHANDLER
		animationHandler = new AnimationHandler();
		animationHandler.addAnimation(new Animation(ANIMATION_NAME, AssetsLoader.selectionArrowVisualizationPane, ANIMATION_DELAY_TIME));
		animationHandler.setCurrentAnimation(ANIMATION_NAME);
		animationHandler.startAnimation();
	}

	public void setTextPostion(int textPosX, int textPosY) {
		this.textPosX = textPosX;
		this.textPosY = textPosY;
	}

	private void setActive(boolean active) {
		this.active = active;
		fireTextboxStateChanged();
	}

	public void activate() {

		if (node != null) {
			setActive(true);
			node.activate();
		} else {
			deactivate();
		}
	}

	public void deactivate() {
		setActive(false);
		node = null;
	}

	public void update(float timeSinceLastFrame) {

		if (active) {
			updateNode(timeSinceLastFrame);
			updateAnimation(timeSinceLastFrame);
		}
	}
	
	private void updateNode(float timeSinceLastFrame) {
		// TODO: Muss diese Überprüfung in diese Klasse? Was genau bedeutet es, dass (node == null)? 
		if (node != null) {
			node.update(timeSinceLastFrame);
		}
	}

	private void updateAnimation(final float timeSinceLastFrame) {
		if (node.hasNextNode()) {
			animationHandler.update(timeSinceLastFrame);
		}
	}

	public void render(Graphics g) {
		if (active) {
			renderNode(g);
			renderAnimation(g);
		}
	}

	public void setNode(TextboxNode node) {
		this.node = node;
	}

	private void renderNode(final Graphics g) {
		g.setFont(AssetsLoader.visializationPaneFont);
		node.render(g, textPosX, textPosY);
	}

	public void setArrowPosition(final int arrowPosX, final int arrowPosY) {
		this.arrowPosX = arrowPosX;
		this.arrowPosY = arrowPosY;
	}

	private void renderAnimation(final Graphics g) {
		if (node.hasNextNode()) {
			g.drawImage(animationHandler.getCurrentAnimation().getSprite(), arrowPosX, arrowPosY, null);
		}
	}

	public void keyPressed(int keyID) {}

	public void keyReleased(int keyID) {

		if (active) {

			if (node.isActive()) {

				node.input(keyID);

				if (!node.isActive() && node.hasNextNode()) {
					node = node.getNextNode();
					node.activate();
				} else if (!node.isActive() && !node.hasNextNode()) {
					deactivate();
				}
			} else {
				deactivate();
			}
		}
	}

	private void fireTextboxStateChanged() {
		textboxListeners.forEach(l -> l.stateChanged(active));
	}

	public void addTextboxListener(final TextboxListener l) {
		textboxListeners.add(l);
	}

	public void removeTextboxListener(final TextboxListener l) {
		textboxListeners.remove(l);
	}
}
