package de.kaffeeliebhaber.gamestate;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

import de.kaffeeliebhaber.assets.AssetsLoader;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.core.MouseManager;
import de.kaffeeliebhaber.gamestate.mode.GameStateMode;
import de.kaffeeliebhaber.managers.GameObjectLoader;
import de.kaffeeliebhaber.managers.GameObjectManager;
import de.kaffeeliebhaber.newUI.UIInventory;
import de.kaffeeliebhaber.tilesystem.chunk.GameWorld;
import de.kaffeeliebhaber.tilesystem.transition.Transition;
import de.kaffeeliebhaber.ui.UIHud;
import de.kaffeeliebhaber.ui.UIInfoPane;

public class PlayState extends GameState {

	private final Transition transition;
	private final Camera camera;
	private GameStateMode gameStateMode;
	private final UIHud hud;
	private final UIInfoPane infoPane;
	private final GameWorld gameWorld;
	private final UIInventory uiInventory;
	
	public PlayState(final GameStateManager gameStateManager, final KeyManager keyManager, final MouseManager mouseManager) {
		super(gameStateManager, keyManager, mouseManager);
		
		// LOAD ASSETS
		AssetsLoader.load();
		
		final GameObjectLoader gameObjectLoader = new GameObjectManager();
		
		// GET OBJECTS
		camera 				= gameObjectLoader.getCamera();
		transition 			= gameObjectLoader.getTransition();
		infoPane 			= gameObjectLoader.getUIInfoPane();
		gameWorld 			= gameObjectLoader.getGameWorld();
		hud 				= gameObjectLoader.getUIHud();
		uiInventory			= gameObjectLoader.getUIInventory();
		
		// USER INTERFACE
		keyManager.addKeyManagerListener(uiInventory);
		mouseManager.addMouseManagerListener(uiInventory);
		uiInventory.setKeyIDOpenInventory(KeyEvent.VK_I);
		
		// REGISTER LISTENER
		infoPane.registerKeyManagerListener(keyManager);
	}

	@Override
	public void update(final KeyManager keyManager, final MouseManager mouseManager, float timeSinceLastFrame) {
		
		// update current game state mode
		updateGameStateMode();
		
		switch (gameStateMode) {
		
			case PLAY:
				updateGameStateModePlay(keyManager, mouseManager, timeSinceLastFrame);
				break;
				
			case INTERACTION:
				updateGameStateModeInteraction(timeSinceLastFrame);
				break;
				
			case TRANSITION:
				updateGameStateModeTransition(timeSinceLastFrame);
				break;
				
			default: break;
		}
	}
	
	private void updateGameStateModePlay(final KeyManager keyManager, final MouseManager mouseManager, float timeSinceLastFrame) {
		
		if (infoPane.isActive()) {
			infoPane.update(timeSinceLastFrame);
			gameStateMode = GameStateMode.INTERACTION;
			return;
		}
		
		gameWorld.update(keyManager, mouseManager, timeSinceLastFrame);
	}

	private void updateGameStateModeInteraction(float timeSinceLastFrame) {
		infoPane.update(timeSinceLastFrame);
	}
	
	private void updateGameStateModeTransition(float timeSinceLastFrame) {
		transition.update(timeSinceLastFrame);
	}
	
	private void updateGameStateMode() {
		if (transition.isActive()) {
			gameStateMode = GameStateMode.TRANSITION;
		} else if (infoPane.isActive() || uiInventory.isVisible()) {
			gameStateMode = GameStateMode.INTERACTION;
		} else {
			gameStateMode = GameStateMode.PLAY;
		}
	}
	
	public void setGameStateMode(final GameStateMode newGameStateMode) {
		gameStateMode = newGameStateMode;
	}
	
	public GameStateMode getGameStateMode() {
		return gameStateMode;
	}

	@Override
	public void render(Graphics g) {
		gameWorld.render(g, camera);
		uiInventory.render(g);
		infoPane.render(g);
		hud.render(g);
		transition.render(g);
		
		// TODO: (NEW-INVENTORY)
		uiInventory.render(g);
	}

	@Override
	public void enter() {
		gameStateMode = GameStateMode.PLAY;
	}

	@Override
	public void exit() {}
}
