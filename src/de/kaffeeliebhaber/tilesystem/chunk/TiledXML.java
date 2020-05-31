package de.kaffeeliebhaber.tilesystem.chunk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.kaffeeliebhaber.assets.AssetsLoader;
import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.tilesystem.Tile;
import de.kaffeeliebhaber.tilesystem.Tilemap;
import de.kaffeeliebhaber.tilesystem.TilemapHandler;

public class TiledXML {

	private Map<Integer, TilemapHandler> tilemapHandlers;
	private Map<Integer, int[][]> layerData;
	private int cols;
	private int rows;
	private int tileWidth;
	private int tileHeight;
	private int chunkWidth;	
	private int chunkHeight;
	private int chunks;
	private String path;
	private Map<Integer, List<BoundingBox>> boundingBoxesMap;
	
	public TiledXML(String path) {
		this.path = path;
		
		layerData = new HashMap<Integer, int[][]>();
		tilemapHandlers = new HashMap<Integer, TilemapHandler>();
	}
	
	private void loadSetupFromDocument(Document document) {
		
		Element root = document.getDocumentElement();

		cols = Integer.parseInt(root.getAttribute("width"));
		rows = Integer.parseInt(root.getAttribute("height"));
		tileWidth = Integer.parseInt(root.getAttribute("tilewidth"));
		tileHeight = Integer.parseInt(root.getAttribute("tileheight"));
		
		// load chunk dimension
		NodeList chunksizeNodeList = document.getElementsByTagName("chunksize");
		
		if (chunksizeNodeList != null && chunksizeNodeList.getLength() > 0) {
			Element chunksiteElement = (Element) chunksizeNodeList.item(0);
			chunkWidth = Integer.parseInt(chunksiteElement.getAttribute("width"));
			chunkHeight = Integer.parseInt(chunksiteElement.getAttribute("height"));
		} else {
			// If no chunks are definied, we set cols and rows as default values.
			chunkWidth  = cols;
			chunkHeight = rows;
		}
		
		System.out.print("(TiledXML) - loadSetupFromDocument(): ");
		System.out.println("ChunkWidth: " + chunkWidth + ", ChunkHeight: " + chunkHeight);
		
		chunks = (cols / chunkWidth) * (rows / chunkHeight);
		
		System.out.println("chunks: " + chunks);
		
		initTilemapHandlerData();
	}
	
	public void clear() {
		tilemapHandlers = null;
		layerData = null;
		boundingBoxesMap = null;
	}
	
	private void initLayerData() {
		for (int chunk = 0; chunk < chunks; chunk++) {
			layerData.put(chunk, new int[chunkHeight][chunkWidth]);
		}
	}
	
	private void initTilemapHandlerData() {
		for (int chunk = 0; chunk < chunks; chunk++) {
			tilemapHandlers.put(chunk, new TilemapHandler());
		}
	}
	
	private void setBoundingBoxesMap(Map<Integer, List<BoundingBox>> boundingBoxesMap) {
		this.boundingBoxesMap = boundingBoxesMap;
	}
	
	public void load() {
		
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		Document document = null;
		
		long loadStartTime = System.currentTimeMillis();
		
		try {
			
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
			document = documentBuilder.parse(path);
			document.getDocumentElement().normalize();
			
			// setup basic data
			this.loadSetupFromDocument(document);
			
			// load tilemap data
			this.loadTilemapFromDocument(document);
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Tiled Editor tilemaps loading complete after " + (System.currentTimeMillis() - loadStartTime) + " ms.");
	}
		
	private void loadTilemapFromDocument(Document document) {
		
		NodeList nodeList = document.getElementsByTagName("layer");
		
		int layers = nodeList.getLength();
		
		Element layerNode;
		
		this.setBoundingBoxesMap(loadBoundingBoxes(document));

		for (int layer = 0; layer < layers; layer++) {
			
			layerNode = (Element) nodeList.item(layer);
						
			final String currentLayerData = layerNode.getElementsByTagName("data").item(0).getTextContent();
			
			//System.out.print("curren data: \n " + currentLayerData);
			
			final int layerId = Integer.parseInt(layerNode.getAttribute("id"));
			
			this.loadTilemaps(currentLayerData, layerId);
			this.createTilemapHandlersFromTilemaps(layerId);
		}
	}
	
	private Map<Integer, List<BoundingBox>> loadBoundingBoxes(Document document) {
		
		NodeList nodeList = document.getElementsByTagName("tile");
		
		final int tiles = nodeList.getLength();
		
		Map<Integer, List<BoundingBox>> boundingBoxes = new HashMap<Integer, List<BoundingBox>>();
		
		for (int i = 0; i < tiles; i++) {
			Element node = (Element) nodeList.item(i);
		
			Element boundingBox = (Element) node.getElementsByTagName("object").item(0);
			
			final int tileID = Integer.parseInt(node.getAttribute("id"));
			
			final BoundingBox b = new BoundingBox(
					(int) Float.parseFloat(boundingBox.getAttribute("x")), 
					(int) Float.parseFloat(boundingBox.getAttribute("y")), 
					(int) Float.parseFloat(boundingBox.getAttribute("width")), 
					(int) Float.parseFloat(boundingBox.getAttribute("height")));

			addBoundingBox(tileID, boundingBoxes, b);
		}
		
		return boundingBoxes;
	}
	
	private void addBoundingBox(final int tileID, final Map<Integer, List<BoundingBox>> boundingBoxes, final BoundingBox boundingBox) {
		
		if (!boundingBoxes.containsKey(tileID)) {
			boundingBoxes.put(tileID, new ArrayList<BoundingBox>());
		}
		
		boundingBoxes.get(tileID).add(boundingBox);
	}
	
	public ChunkSystem createChunkSystem() {
		ChunkSystem chunkSystem = new ChunkSystem(chunkWidth, chunkHeight);
		chunkSystem.setTileWidth(tileWidth);
		chunkSystem.setTileHeight(tileHeight);
		
		for (int chunkID : tilemapHandlers.keySet()) {
			chunkSystem.addChunk(chunkID, tilemapHandlers.get(chunkID));
		}

		return chunkSystem;
	}
	
	private void loadTilemaps(String data, int layerId) {
		String[] splittedData = data.split(",");
		
		if (splittedData.length != (cols * rows)) {
			throw new IllegalArgumentException("Readed data dimension is wrong.");
		}
		
		initLayerData();
		
		int col = 0;
		int row = 0;
		
		int chunkX = 0;
		int chunkY = 0;
		
		int chunkID = 0;
		
		for (int i = 0; i < (cols * rows); i++) {
			
			col = i % cols;
			row = i / cols;

			// In welchem Chunk liegt die Spalte 'col' und Zeile 'row'?
			chunkX = col / chunkWidth;
			chunkY = row / chunkHeight;
			
			chunkID = chunkY * (cols / chunkWidth) + (chunkX);
			
			// set data
			layerData.get(chunkID)[col % chunkWidth][row % chunkHeight] = Integer.parseInt(splittedData[i].replaceAll("\\s+", "")) - 1;
		}
	}
	
	private void createTilemapHandlersFromTilemaps(final int layerId) {
		
		for (int chunk = 0; chunk < chunks; chunk++) {
			int[][] map = layerData.get(chunk);
			
			Tile[][] tiles = new Tile[chunkHeight][chunkWidth];
			
			//System.out.println(" ---------------------------- " + chunk + " ----------------------");
			for (int x = 0; x < map.length; x++) {
				for (int y = 0; y < map[x].length; y++) {
				
					final int tileID = map[x][y];
					
					
					//System.out.print(tileID + ", ");
					if (tileID != -1) {
						
						//System.out.print(tileID + ", ");
						Tile tile = new Tile(tileID, x * tileHeight, y * tileWidth, tileWidth, tileHeight, AssetsLoader.spritesheet.getImageByIndex(tileID));
						
						// Abspeichern der BoundingBoxes 
						if (boundingBoxesMap.containsKey(tileID)) {
							tile.setBoundingBoxes(boundingBoxesMap.get(tileID));
							tile.adjustBoundingBoxes();
						}
						
						tiles[x][y] = tile;
					}
				}
				
				//System.out.println("");
			}
			
			//System.out.println("----------------");
			
			Tilemap tilemap = new Tilemap(chunkHeight, chunkWidth, tileHeight, tileWidth);
			tilemap.setTiles(tiles);
			
			tilemapHandlers.get(chunk).addTilemap(layerId, tilemap);
			layerData.remove(chunk);
		}
	}

	public int getTileWidth() {
		return tileWidth;
	}

	public int getTileHeight() {
		return tileHeight;
	}
}
