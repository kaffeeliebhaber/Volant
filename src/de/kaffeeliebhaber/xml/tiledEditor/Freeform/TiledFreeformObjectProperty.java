package de.kaffeeliebhaber.xml.tiledEditor.Freeform;

public class TiledFreeformObjectProperty {

	private String property;
	private String value;
	
	public TiledFreeformObjectProperty(final String property, final String value) {
		this.property = property;
		this.value = value;
	}

	public String getProperty() {
		return property;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "TiledFreeformObjectProperty [property=" + property + ", value=" + value + "]";
	}
	
}
