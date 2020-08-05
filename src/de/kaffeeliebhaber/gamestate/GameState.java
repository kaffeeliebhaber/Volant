package de.kaffeeliebhaber.gamestate;

import java.awt.Graphics;

import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.core.MouseManager;

public abstract class GameState {

	protected GameStateManager gameStateManager;
	
	public GameState(final GameStateManager gameStateManager, final KeyManager keyManager, final MouseManager mouseManager) {
		this.gameStateManager = gameStateManager;
	}

    public abstract void update(final KeyManager keyManager, final MouseManager mouseManager, float timeSinceLastFrame);
    
    public abstract void render(Graphics g);
    
    public abstract void enter();
    
    public abstract void exit();
}
