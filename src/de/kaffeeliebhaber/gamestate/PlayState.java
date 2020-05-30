package de.kaffeeliebhaber.gamestate;

import java.awt.Graphics;

import de.kaffeeliebhaber.assets.AssetsLoader;
import de.kaffeeliebhaber.collision.CollisionController;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.gamestate.mode.GameStateMode;
import de.kaffeeliebhaber.inventory.ItemManager;
import de.kaffeeliebhaber.managers.EntityManager;
import de.kaffeeliebhaber.managers.GameObjectManager;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.tilesystem.transition.Transition;
import de.kaffeeliebhaber.ui.UIHud;
import de.kaffeeliebhaber.ui.UIInfoPane;
import de.kaffeeliebhaber.ui.inventory.UIInventoryManager;

public class PlayState extends GameState {

	private ChunkSystem chunkSystem;
	private Transition transition;
	private Player player;
	private Camera camera;
	private GameStateMode gameStateMode;
	private EntityManager entityHandler;
	private ItemManager itemManager;
	
	// USER INTERFACE
	private UIInventoryManager inventoryManager;
	private UIHud hud;
	private UIInfoPane infoPane;
	
	public PlayState(GameStateManager gameStateManager) {
		super(gameStateManager);
		
		// LOAD ASSETS
		AssetsLoader.load();
		
		final GameObjectManager gameObjectManager = new GameObjectManager();
		
		// GET OBJECTS
		camera 				= gameObjectManager.getCamera();
		chunkSystem 		= gameObjectManager.getChunkSystem();
		player 				= gameObjectManager.getPlayer();
		transition 			= gameObjectManager.getTransition();
		entityHandler 		= gameObjectManager.getEntityHandler();
		itemManager			= gameObjectManager.getItemManager();
		inventoryManager 	= gameObjectManager.getInventoryManager();
		infoPane 			= gameObjectManager.getUIInfoPane();
		
		hud = new UIHud(player);
		itemManager.addInfoPaneInformerListener(infoPane);
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
		
		// Hier werden auch die Entit�ten im EntityManager aktualisiert (update)
		chunkSystem.update(timeSinceLastFrame);
		
		player.update(timeSinceLastFrame);
		
		CollisionController.collision(chunkSystem, entityHandler.getEntities());
		
		
		// update item manager
		itemManager.update();
		
		// y-sort entity list 
		entityHandler.ySort();
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
	
	// TODO: Macht es vielleicht Sinn, dass man den PlayState als Variable an die entsprechenden Stellen mitgibt, soda� diese den
	// GameStateMode selbst setzen k�nnen?
	public void setGameStateMode(final GameStateMode newGameStateMode) {
		gameStateMode = newGameStateMode;
	}
	
	public GameStateMode getGameStateMode() {
		return gameStateMode;
	}

	@Override
	public void render(Graphics g) {
		chunkSystem.render(g, camera);
		itemManager.render(g, camera);
		entityHandler.render(g, camera);
		transition.render(g);
		inventoryManager.render(g);
		infoPane.render(g);
		hud.render(g);
	}

	@Override
	public void enter() {
		// initial game state mode
		gameStateMode = GameStateMode.PLAY;
	}

	@Override
	public void exit() {}
}
