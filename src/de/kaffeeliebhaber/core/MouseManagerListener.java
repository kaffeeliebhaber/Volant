package de.kaffeeliebhaber.core;

import java.awt.Point;

public interface MouseManagerListener {

	void mouseReleased(final int buttonID, final Point p);
	
	void mouseMoved(final Point p);
}