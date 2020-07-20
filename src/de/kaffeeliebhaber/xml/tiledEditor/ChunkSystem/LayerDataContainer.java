package de.kaffeeliebhaber.xml.tiledEditor.ChunkSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LayerDataContainer {

	private Map<Integer, int[][]> layerData;
	
	public LayerDataContainer() {
		layerData = new HashMap<Integer, int[][]>();
	}
	
	public void put(final Integer layerID, final int[][] data) {
		layerData.put(layerID, data);
	}
	
	public int[][] get(final Integer layerID) {
		return layerData.get(layerID);
	}
	
	public Set<Integer> getLayerIDs() {
		return layerData.keySet();
	}
	
}
