package de.kaffeeliebhaber.input;

import java.awt.Point;

public interface MouseManagerListener {

	void mouseReleased(final int buttonID, final Point p);
	
	void mouseMoved(final Point p);
}
