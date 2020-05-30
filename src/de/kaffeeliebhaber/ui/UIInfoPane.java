package de.kaffeeliebhaber.ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.input.KeyManagerListener;
import de.kaffeeliebhaber.math.Sign;
import de.kaffeeliebhaber.tweens.DefaultTween;
import de.kaffeeliebhaber.tweens.InfoPaneEvent;
import de.kaffeeliebhaber.tweens.InfoPaneInformerListener;
import de.kaffeeliebhaber.tweens.InfoPaneListener;
import de.kaffeeliebhaber.tweens.Tween;
import de.kaffeeliebhaber.tweens.TweenManager;
import de.kaffeeliebhaber.tweens.TweenManagerListener;
import de.kaffeeliebhaber.tweens.functions.TweenFunctionExp;
import de.kaffeeliebhaber.tweens.transformations.TweenTransformElementX;
import de.kaffeeliebhaber.tweens.transformations.TweenTransformElementXNeg;
import de.kaffeeliebhaber.tweens.transformations.TweenTransformElementYAndDoubleHeight;
import de.kaffeeliebhaber.tweens.transformations.TweenTransformElementYAndDoubleHeightEnd;
import de.kaffeeliebhaber.ui.textbox.TextboxListener;

public class UIInfoPane 
implements InfoPaneInformerListener, TextboxListener, TweenManagerListener, KeyManagerListener {

	private UITextbox textbox;
	private UIImageContainer imageContainer;
	private UIStripContainer stripContainer;
	private TweenManager tweenManager;
	private InfoPaneListener currentInfoPaneListener;
	private boolean active;
	private boolean textboxMode;
	
	private final int width, height;
	
	public UIInfoPane(final int width, final int height) {
		
		this.width = width;
		this.height = height;
		
		setUITextbox(createUITextbox());
		setUIImageContainer(createUIImageContainer());
		setUIStripContainer(createUIStripContainer());
		setTweenManager(createTweenManager());
		setUpTweenManager();
		registerListener();
	}
	
	private TweenManager createTweenManager() {
		tweenManager = new TweenManager();
		return tweenManager;
	}
	
	private void setUpTweenManager() {
		
		tweenManager.addTween(new DefaultTween(0, stripContainer, new TweenFunctionExp(Sign.POSITIVE,  1.0f, 1.0f), new TweenTransformElementYAndDoubleHeight(240, 200)));
		tweenManager.addTween(new DefaultTween(1, imageContainer, new TweenFunctionExp(Sign.POSITIVE,  9.0f, 1.0f), new TweenTransformElementX(-100, 100)));
		tweenManager.addTween(new DefaultTween(2, imageContainer, new TweenFunctionExp(Sign.POSITIVE,  9.0f, 1.0f), new TweenTransformElementXNeg(100, 80)));
		tweenManager.addTween(new DefaultTween(6, imageContainer, new TweenFunctionExp(Sign.POSITIVE,  9.0f, 1.0f), new TweenTransformElementXNeg(80, 60)));
		tweenManager.addTween(new DefaultTween(7, imageContainer, new TweenFunctionExp(Sign.POSITIVE, 11.0f, 1.2f), new TweenTransformElementX(60, width + 10)));
		tweenManager.addTween(new DefaultTween(8, stripContainer, new TweenFunctionExp(Sign.POSITIVE,  1.0f, 1.0f), new TweenTransformElementYAndDoubleHeightEnd(200, 240)));

		tweenManager.setCurrentTween(0);
	}
	
	private UIImageContainer createUIImageContainer() {
		imageContainer = new UIImageContainer(-100, 190, 100, 100);
		imageContainer.setImage(null);
		imageContainer.setIndent(10, 10);
		return imageContainer;
	}
	
	private UITextbox createUITextbox() {
		final UITextbox textbox = new UITextbox();
		textbox.setTextPostion(200, 220);
		textbox.setArrowPosition(530, 260);
		textbox.addTextboxListener(this);
		return textbox;
	}
	
	private UIStripContainer createUIStripContainer() {
		final UIStripContainer stripContainer = new UIStripContainer(0, 240, width, 0);
		return stripContainer;
	}
	
	private void registerListener() {
		tweenManager.addTweenManagerListener(this);
		KeyManager.instance.addKeyManagerListener(this);
	}
	
	private void setUITextbox(final UITextbox textbox) {
		this.textbox = textbox;
	}
	
	private void setUIImageContainer(final UIImageContainer imageContainer) {
		this.imageContainer = imageContainer;
	}
	
	public void update(float timeSinceLastFrame) {
		if (active) {
			if (textboxMode) {
				textbox.update(timeSinceLastFrame);
			} else {
				tweenManager.update(timeSinceLastFrame);
			}
		}
	}
	
	public void render(Graphics g) {
		if (active) {
			stripContainer.render(g);
			imageContainer.render(g);
			textbox.render(g);
		}
	}
	
	public void reset() {
		deactivate();
		notifyInfoPaneListener();
		tweenManager.reset();
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void activate() {
		active = true;
	}
	
	public void deactivate() {
		active = false;
	}
	
	private void setTweenManager(final TweenManager tweenManager) {
		this.tweenManager = tweenManager;
	}
	
	private void setInfoPaneListener(final InfoPaneListener listener) {
		currentInfoPaneListener = listener;
	}
	
	private void setUIStripContainer(final UIStripContainer stripContainer) {
		this.stripContainer = stripContainer;
	}
	
	private void notifyInfoPaneListener() {
		if (currentInfoPaneListener != null) {
			currentInfoPaneListener.closed();
			currentInfoPaneListener = null;
		}
	}

	@Override public void performInfoPane(InfoPaneEvent info) {
		
		imageContainer.setImage(info.getImage());
		imageContainer.setImageDimension(32, 32);
		textbox.setNode(info.getTextboxNode());
		
		setInfoPaneListener(info.getInfoPaneListener());
		activate();
	}
	
	// WIRD IMMER DANN AUFGERUFEN, WENN SICH DER STATUS DER UI-TEXTBOX ÄNDERT.
	@Override public void stateChanged(boolean active) {
		if (!active) {
			textboxMode = false;
		}
	}

	@Override
	public void tweenChanged(final Tween lastTween, final Tween nextTween) {
		
		// TODO: Dieser Code muss definitiv geändert werden. 
		final int textboxID = 2;
		
		if (lastTween.getID() == textboxID) {
			this.textboxMode = true;
		}
		
		if (lastTween.getID() == (textboxID + 1)) {
			this.textboxMode = false;
		}
		
		if (nextTween == null) {
			reset();
		} else if (nextTween.getID() == textboxID) {
			textbox.activate();
		}
	}

	@Override public void keyPressed(int keyID) {}

	@Override
	public void keyReleased(int keyID) {
		
		if (keyID == KeyEvent.VK_O) {
			activate();
		}
	}
}
