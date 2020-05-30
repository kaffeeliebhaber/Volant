package de.kaffeeliebhaber.core;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.input.MouseManagerListener;

public class MouseManager implements MouseListener, MouseMotionListener {

	public static MouseManager instance;
	
	static {
		if (instance == null) {
			instance = new MouseManager();
		}
	}
	
	private List<MouseManagerListener> mouseManagerListeners;
	
	public MouseManager() {
		mouseManagerListeners = new ArrayList<MouseManagerListener>();
	}
	
	public void addMouseManagerListener(final MouseManagerListener l) {
		mouseManagerListeners.add(l);
	}
	
	public void removeMouseManagerListener(final MouseManagerListener l) {
		mouseManagerListeners.remove(l);
	}
	
	private void fireMouseEvent(MouseEvent e) {
		for (MouseManagerListener l : mouseManagerListeners) {
			l.mouseReleased(e.getButton(), e.getPoint());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		fireMouseEvent(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		final int size = mouseManagerListeners.size();
		for (int i = size - 1; i >= 0; i--) {
			mouseManagerListeners.get(i).mouseMoved(e.getPoint());
		}
	}
	
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	@Override public void mouseDragged(MouseEvent e) {}
}
