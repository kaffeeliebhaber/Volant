package de.kaffeeliebhaber.gamestate;

import java.awt.Graphics;

import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.core.MouseManager;

public class DefaultEmptyState extends GameState {

	public DefaultEmptyState(GameStateManager gameStateManager, final KeyManager keyManager, final MouseManager mouseManager) {
		super(gameStateManager, keyManager, mouseManager);
	}

	@Override
	public void update(final KeyManager keyManager, final MouseManager mouseManager, float timeSinceLastFrame) {
		
	}

	@Override
	public void render(Graphics g) {
		
	}

	@Override
	public void enter() {
		
	}

	@Override
	public void exit() {
		
	}

}
