package de.kaffeeliebhaber.entitySystem.worldObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import de.kaffeeliebhaber.core.Camera;

public class StaticEntityImageController {

	private List<StaticEntityImage> staticEntityImages;
	
	public StaticEntityImageController() {
		staticEntityImages = new ArrayList<StaticEntityImage>();
	}

	public void add(final BufferedImage image, final float x, final float y) {
		staticEntityImages.add(new StaticEntityImage(image, x, y));
	}
	
	public void render(final Graphics g, final Camera camera) {
		staticEntityImages.stream().forEach(s -> s.render(g, camera));
	}
}
