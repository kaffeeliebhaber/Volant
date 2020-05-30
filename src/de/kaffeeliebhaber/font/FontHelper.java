package de.kaffeeliebhaber.font;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class FontHelper {

	public static int getWidht(Font font, String text) {
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext fontRenderContext = new FontRenderContext(affinetransform, true, true);
		return (int) (font.getStringBounds(text, fontRenderContext).getWidth());
	}
}
