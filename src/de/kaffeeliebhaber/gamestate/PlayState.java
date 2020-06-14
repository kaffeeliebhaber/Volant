package de.kaffeeliebhaber.gamestate;

import java.awt.Graphics;

import de.kaffeeliebhaber.assets.AssetsLoader;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.gamestate.mode.GameStateMode;
import de.kaffeeliebhaber.managers.GameObjectLoader;
import de.kaffeeliebhaber.managers.GameObjectManager;
import de.kaffeeliebhaber.tilesystem.chunk.GameWorld;
import de.kaffeeliebhaber.tilesystem.transition.Transition;
import de.kaffeeliebhaber.ui.UIHud;
import de.kaffeeliebhaber.ui.UIInfoPane;
import de.kaffeeliebhaber.ui.inventory.UIInventoryManager;

public class PlayState extends GameState {

	private final Transition transition;
	private final Camera camera;
	private GameStateMode gameStateMode;
	private final UIInventoryManager inventoryManager;
	private final UIHud hud;
	private final UIInfoPane infoPane;
	private final GameWorld gameWorld;
	
	public PlayState(GameStateManager gameStateManager) {
		super(gameStateManager);
		
		// LOAD ASSETS
		AssetsLoader.load();
		
		final GameObjectLoader gameObjectLoader = new GameObjectManager();
		
		// GET OBJECTS
		camera 				= gameObjectLoader.getCamera();
		transition 			= gameObjectLoader.getTransition();
		inventoryManager 	= gameObjectLoader.getUIInventoryManager();
		infoPane 			= gameObjectLoader.getUIInfoPane();
		gameWorld 			= gameObjectLoader.getGameWorld();
		hud 				= gameObjectLoader.getUIHud();
	}

	@Override
	public void update(float timeSinceLastFrame) {
		
		// update current game state mode
		updateGameStateMode();
		
		switch (gameStateMode) {
		
			case PLAY:
				updateGameStateModePlay(timeSinceLastFrame);
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
	
	private void updateGameStateModePlay(float timeSinceLastFrame) {
		
		if (infoPane.isActive()) {
			infoPane.update(timeSinceLastFrame);
			gameStateMode = GameStateMode.INTERACTION;
			return;
		}
		
		gameWorld.update(timeSinceLastFrame);
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
		} else if (infoPane.isActive() || inventoryManager.isOpen()) {
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
		inventoryManager.render(g);
		infoPane.render(g);
		hud.render(g);
	}

	@Override
	public void enter() {
		gameStateMode = GameStateMode.PLAY;
	}

	@Override
	public void exit() {}
}
