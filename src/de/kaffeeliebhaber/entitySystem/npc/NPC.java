package de.kaffeeliebhaber.entitySystem.npc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.animation.Direction;
import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.behavior.moving.IMovingBehavior;
import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.debug.Debug;
import de.kaffeeliebhaber.entitySystem.Entity;
import de.kaffeeliebhaber.entitySystem.MovingEntity;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.input.KeyManagerListener;
import de.kaffeeliebhaber.tweens.InfoPaneEvent;
import de.kaffeeliebhaber.tweens.InfoPaneInformerListener;
import de.kaffeeliebhaber.tweens.InfoPaneListener;
import de.kaffeeliebhaber.ui.textbox.TextboxNode;

public abstract class NPC extends MovingEntity implements KeyManagerListener, InfoPaneListener {

	// CONSTANTS
	private final int BOUNDINGBOX_HEIGHT = 10;
	private final int INTERACTIONBOX_HEIGHT = 20;

	protected BufferedImage popupImage;
	protected int actionKeyID;
	protected Player player;
	protected boolean active;
	protected List<InfoPaneInformerListener> infoPaneInformerListeners;
	protected Direction interactionDirection;

	public NPC(float x, float y, int width, int height, Direction interactionDirection, final IAnimationController animationController, final IMovingBehavior movingBehavior) {
		super(x, y, width, height, animationController, movingBehavior);

		setInteractionDirection(interactionDirection);
		addBoundingBox(new BoundingBox((int) x, (int) (y + height - BOUNDINGBOX_HEIGHT), width, BOUNDINGBOX_HEIGHT));

		infoPaneInformerListeners = new ArrayList<InfoPaneInformerListener>();

		// REGISTER KEYMANAGER-LISTENER
		KeyManager.instance.addKeyManagerListener(this);

		initTextboxSetup();
	}

	private void setInteractionDirection(Direction interactionDirection) {
		this.interactionDirection = interactionDirection;
	}

	public void render(Graphics g, Camera camera) {

		g.drawImage(animationController.getImage(), (int) (x - camera.getX()), (int) (y - camera.getY()), width, height, null);

		if (Debug.NPC_RENDER_SHOW_BOUNDINGBOX) {
			boundingBoxController.render(g, camera);
		}

		if (Debug.NPC_RENDER_SHOW_INTERACTIONOX) {
			g.setColor(Color.red);
			BoundingBox interactionBoundingBox = getInteractionBox();
			g.drawRect((int) (interactionBoundingBox.getX() - camera.getX()), (int) (interactionBoundingBox.getY() - camera.getY()), interactionBoundingBox.getWidth(), interactionBoundingBox.getHeight());
		}
	}

	public boolean canInteract(Player player) {
		boolean canInteract = false;

		if (player != null) {
			canInteract = isOnInteractionLayer(player) && isOnInteractionDirection(player);
		}

		return canInteract;
	}

	public abstract boolean isOnInteractionDirection(Player player);

	public void setPopupImage(final BufferedImage popupImage) {
		this.popupImage = popupImage;
	}

	public void setActionKeyID(final int keyID) {
		actionKeyID = keyID;
	}

	public abstract TextboxNode getTextboxNode();

	public void setPlayer(Player player) {
		this.player = player;
	}

	private BoundingBox getInteractionBox() {
		return new BoundingBox((int) x, (int) (y + height), width, INTERACTIONBOX_HEIGHT);
	}

	public void update(float timeSinceLastFrame, final List<Entity> entities) {
		super.update(timeSinceLastFrame, entities);
	}

	protected void interact(Player player) {
		fireInformationPaneEvent();
	}

	private boolean isOnInteractionLayer(Player player) {
		return getInteractionBox().intersects(player.getBoundingBoxController().getBoundingBoxes());
	}

	private void fireInformationPaneEvent() {
		infoPaneInformerListeners.forEach(e -> e.performInfoPane(new InfoPaneEvent(this, popupImage, getTextboxNode())));
	}

	public void addInfoPaneInformerListener(final InfoPaneInformerListener l) {
		infoPaneInformerListeners.add(l);
	}

	public void removeInfoPaneInformerListener(final InfoPaneInformerListener l) {
		infoPaneInformerListeners.remove(l);
	}

	private void activate() {
		active = true;
	}

	private void deactivate() {
		active = false;
	}

	public boolean isActive() {
		return active;
	}

	public void keyPressed(int keyID) {
	}

	public void keyReleased(int keyID) {
		if (canInteract(player) && isInteractionKeyPressed(keyID) && !isActive()) {
			activate();
			interact(player);
		}
	}

	private boolean isInteractionKeyPressed(int keyID) {
		return keyID == actionKeyID;
	}

	public void closed() {
		deactivate();
	}

	protected abstract void initTextboxSetup();

}
