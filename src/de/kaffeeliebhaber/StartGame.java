package de.kaffeeliebhaber;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import de.kaffeeliebhaber.core.Game;

public class StartGame {

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					JFrame frame = new JFrame("Voland");
					Game game = new Game(Config.WIDTH, Config.HEIGHT);
					frame.add(game);
					game.setFrame(frame);
					frame.pack();
					frame.setLocationRelativeTo(null);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					game.start();
					frame.setVisible(true);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
