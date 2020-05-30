package de.kaffeeliebhaber.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.input.KeyManagerListener;

public class KeyManager implements KeyListener {

	public static KeyManager instance;
	
	static {
		
		if (instance == null) {
			instance = new KeyManager();
		}
		
	}
	
	private List<KeyManagerListener> keyManagerListeners;
	
	public KeyManager() {
		keyManagerListeners = new ArrayList<KeyManagerListener>();
	}
	
	public void addKeyManagerListener(final KeyManagerListener l) {
		keyManagerListeners.add(l);
	}
	
	public void removeKeyManagerListener(final KeyManagerListener l) {
		keyManagerListeners.remove(l);
	}
	
	@Override public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		
		final int keyID = e.getKeyCode();
		
		for (KeyManagerListener l : keyManagerListeners) {
			l.keyPressed(keyID);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		final int keyID = e.getKeyCode();
		
		for (KeyManagerListener l : keyManagerListeners) {
			l.keyReleased(keyID);
		}
		
	}

	
}
