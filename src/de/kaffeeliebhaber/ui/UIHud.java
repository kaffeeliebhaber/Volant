package de.kaffeeliebhaber.ui;

import java.awt.Color;
import java.awt.Graphics;

import de.kaffeeliebhaber.assets.AssetsLoader;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.inventory.stats.PlayerStats;


public class UIHud {

	private Player player;
	
	public UIHud(final Player player) {
		this.player = player;
	}
	
	public void update(float timeSinceLastFrame) {}
	
	public void render(Graphics g) {
		
		// layer
		g.setColor(new Color(0, 0, 0, 125));
		g.fillRect(0, 0, 100, 100);
		
		// stats
		final PlayerStats stats = (PlayerStats) player.getStats();
		
		g.setColor(Color.WHITE);
		g.setFont(AssetsLoader.hudFont);
		
		g.drawString("hp " + stats.getHP(), 20, 20);
		g.drawString("Food " + stats.getFood(), 20, 40);
		g.drawString("armor " + stats.getArmorValue(), 20, 60);
		g.drawString("damage " + stats.getDamageValue(), 20, 80);
	
	}
}
