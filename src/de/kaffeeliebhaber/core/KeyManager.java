package de.kaffeeliebhaber.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyManager implements KeyListener {

	private List<KeyManagerListener> keyManagerListeners;
	
	public final int POSSIBLE_KEY_CODES = 1024;
	public boolean[] keys = new boolean[POSSIBLE_KEY_CODES];
	public boolean[] keyStatesBefore = new boolean[POSSIBLE_KEY_CODES];
	public long[] keyPressedLenght = new long[POSSIBLE_KEY_CODES];
	
	public KeyManager() {
		keyManagerListeners = new ArrayList<KeyManagerListener>();
	}

	public boolean isKeyPressed(int keyCode) {
		return keys[keyCode];
	}
	
	public boolean isKeyReleased(int keyCode) {
		return !keys[keyCode];
	}
	
	public boolean getKeyStateBefore(int keyCode) {
		return keyStatesBefore[keyCode];
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if (validKeyCode(e)) {
			final int keyCode = e.getKeyCode();
			
			if (!keys[keyCode]) {
				keys[keyCode] = true;
				keyPressedLenght[e.getKeyCode()] = System.currentTimeMillis();
				//stateBefore[keyCode] = !stateBefore[keyCode];
				fireKeyPressedEvent(keyCode);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (validKeyCode(e)) {
			final int keyCode = e.getKeyCode();
			
			keys[keyCode] = false;
			keyPressedLenght[keyCode] = 0;
			keyStatesBefore[keyCode] = !keyStatesBefore[keyCode];
			fireKeyReleasedEvent(keyCode);
		}
	}
	
	
	// Prüft, ob es sich um einen gültigen KeyCode handelt.
	private boolean validKeyCode(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (keyCode < 0 || keyCode > keys.length) {
			return false;
		}
		
		return true;
	}
	
	// Diese Methode liefert unter allen registerierten Buttons den KeyCode des Buttons zurück, der von allen als erster gedrückt wurde.
	public int getMaxPressedKey(List<Integer> keyList) {
		
		int keyCode = 0;

		long maxPressedSinceTime = Long.MAX_VALUE;

		for (int currentKeyCode : keyList) {
			
			long currentKeyCodePressedTime = this.getPressedTime(currentKeyCode);
			
			if (currentKeyCodePressedTime > 0 && currentKeyCodePressedTime < maxPressedSinceTime) {
				maxPressedSinceTime = currentKeyCodePressedTime;
				keyCode = currentKeyCode;
			}
		}
		
		return keyCode;
	}
	
	// Liefert den Zeitstempel, wann die Tase gedrückt wurde.
	public long getPressedTime(int keyCode) {
		return this.keyPressedLenght[keyCode];
	}
	
	private void fireKeyPressedEvent(final int keyID) {
		keyManagerListeners.stream().forEach(l -> l.keyPressed(keyID));
	}
	
	private void fireKeyReleasedEvent(final int keyID) {
		keyManagerListeners.stream().forEach(l -> l.keyReleased(keyID));
	}
	
	public void addKeyManagerListener(final KeyManagerListener KeyManagerListener) {
		keyManagerListeners.add(KeyManagerListener);
	}
	
	public void removeKeyManagerListener(final KeyManagerListener KeyManagerListener) {
		keyManagerListeners.remove(KeyManagerListener);
	}
	
}
