package de.kaffeeliebhaber.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class Keyboard implements KeyListener {
	
	public static final int POSSIBLE_KEY_CODES = 1024;
	public static boolean[] keys = new boolean[POSSIBLE_KEY_CODES];
	public static boolean[] keyStatesBefore = new boolean[POSSIBLE_KEY_CODES];
	public static long[] keyPressedLenght = new long[POSSIBLE_KEY_CODES];

	public static boolean isKeyPressed(int keyCode) {
		return keys[keyCode];
	}
	
	public static boolean isKeyReleased(int keyCode) {
		return !keys[keyCode];
	}
	
	public static boolean getKeyStateBefore(int keyCode) {
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
	public static int getMaxPressedKey(List<Integer> keyList) {
		
		int keyCode = 0;

		long maxPressedSinceTime = Long.MAX_VALUE;

		for (int currentKeyCode : keyList) {
			
			long currentKeyCodePressedTime = Keyboard.getPressedTime(currentKeyCode);
			
			if (currentKeyCodePressedTime > 0 && currentKeyCodePressedTime < maxPressedSinceTime) {
				maxPressedSinceTime = currentKeyCodePressedTime;
				keyCode = currentKeyCode;
			}
		}
		
		return keyCode;
	}
	
	// Liefert den Zeitstempel, wann die Tase gedrückt wurde.
	public static long getPressedTime(int keyCode) {
		return Keyboard.keyPressedLenght[keyCode];
	}
	
}
