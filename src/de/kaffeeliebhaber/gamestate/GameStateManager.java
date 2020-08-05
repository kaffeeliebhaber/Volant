package de.kaffeeliebhaber.gamestate;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

import de.kaffeeliebhaber.core.KeyManager;
import de.kaffeeliebhaber.core.MouseManager;

public class GameStateManager {

    private final Map<String, GameState> gameStates;
    private GameState currentGameState;

    public GameStateManager() {
    	gameStates = new HashMap<>();
        currentGameState = new DefaultEmptyState(this, null, null);
        gameStates.put(null, currentGameState);
    }

    public void add(String name, GameState gameState) {
    	gameStates.put(name, gameState);
    }

    public void change(String name) {
    	currentGameState.exit();
    	currentGameState = gameStates.get(name);
    	currentGameState.enter();
    }

    public void update(final KeyManager keyManager, final MouseManager mouseManager, float timeSinceLastFrame) {
    	currentGameState.update(keyManager, mouseManager, timeSinceLastFrame);
    }

    public void render(Graphics g) {
    	currentGameState.render(g);
    }

    public void enter() {
    	currentGameState.enter();
    }

    public void exit() {
    	currentGameState.exit();
    }
}
