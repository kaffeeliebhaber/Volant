package de.kaffeeliebhaber.xml.tiledEditor;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.kaffeeliebhaber.xml.tiledEditor.BoundingBox.TiledBoundingBoxManager;
import de.kaffeeliebhaber.xml.tiledEditor.ChunkSystem.ChunkSystemCreatorModel;
import de.kaffeeliebhaber.xml.tiledEditor.ChunkSystem.LayerDataContainer;
import de.kaffeeliebhaber.xml.tiledEditor.Freeform.TiledFreeformGroupManager;
import de.kaffeeliebhaber.xml.tiledEditor.ObjectSystem.TiledObjectGroupManager;

public class TiledEditorMapLoader implements ChunkSystemCreatorModel {

	// global data
	private final String documentPath;
	private Document document;
	
	// model data
	private TiledBoundingBoxManager boundingBoxManager;
	private LayerDataContainer layerDataContainer;
	private TiledObjectGroupManager objectGroupManager;
	private TiledFreeformGroupManager freeformGroupManager;
	private int tileWidth;
	private int tileHeight;
	private int tilesX;
	private int tilesY;
	private int chunkwidth;
	private int chunkheight;
	private int objectLayerID;
	private final int TILED_ID_DELTA = 1;
	
	// debug mode
	private static final boolean DEBUG = !true;
	
	public TiledEditorMapLoader(final String documentPath) {
		this.documentPath = documentPath;
		init();
	}
	
	private void init() {
		
		boundingBoxManager 		= new TiledBoundingBoxManager();
		layerDataContainer 		= new LayerDataContainer();
		objectGroupManager 		= new TiledObjectGroupManager();
		freeformGroupManager 	= new TiledFreeformGroupManager();
		
		openDocument();
		readDocument();
	}
	
	private void openDocument() {
		
		try {
			document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(documentPath);
			document.getDocumentElement().normalize();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readDocument() {
		Element elementMap = document.getDocumentElement();
		
		/*
		 * READ TAG | MAP
		 */
		readTagMap((Node) elementMap);
		
		NodeList nodeListMap = elementMap.getChildNodes();
		
		for (int tag = 0; tag < nodeListMap.getLength(); tag++) {
			process(nodeListMap.item(tag));
		}
	}
	
	private void process(Node currentNode) {
		
		switch (currentNode.getNodeName()) {
			case TiledEditorTags.editorsettings : readTagEditorsettings(currentNode); break;
			case TiledEditorTags.tileset: readTagTileset(currentNode); break;
			case TiledEditorTags.layer: readTagLayer(currentNode); break;
			case TiledEditorTags.objectgroup: readTagObjectgroup(currentNode); break;
			case TiledEditorTags.properties: readTagProperties(currentNode); break;
		}
	}
	
	private void readTagMap(Node nodeMap) {
		
		Element elementMap = (Element) nodeMap;
		
		tilesX = Integer.parseInt(elementMap.getAttribute("width"));
		tilesY = Integer.parseInt(elementMap.getAttribute("height"));
		tileWidth = Integer.parseInt(elementMap.getAttribute("tilewidth"));
		tileHeight = Integer.parseInt(elementMap.getAttribute("tileheight"));
	}
	
	/**
	 * Loading bounding boxes for each tile.
	 * @param nodeTileset
	 */
	private void readTagTileset(Node nodeTileset) {
		
		NodeList nodeTilesetChilds = nodeTileset.getChildNodes();
		final int nodes = nodeTilesetChilds.getLength();
		
		for (int i = 0; i < nodes; i++) {
			
			Node currentChildNode = nodeTilesetChilds.item(i);
			
			if (currentChildNode.getNodeName().equals(TiledEditorTags.tile)) {
				loadTileBoundingBoxes(currentChildNode);
			}
		}
	}
	
	private void loadTileBoundingBoxes(Node nodeTile) {
		Element elementTile = (Element) nodeTile;
		
		final int tileID = Integer.parseInt(elementTile.getAttribute("id"));
		
		NodeList nodeListTileChilds = nodeTile.getChildNodes();
		
		final int nodes = nodeListTileChilds.getLength();
		
		for (int i = 0; i < nodes; i++) {
			
			Node tileChild = nodeListTileChilds.item(i);
			
			if (tileChild.getNodeName().equals(TiledEditorTags.objectgroup)) {
				
				NodeList objectgroupChilds = tileChild.getChildNodes();
				
				final int objectgroupChildsCnt = objectgroupChilds.getLength();
				
				for (int child = 0; child < objectgroupChildsCnt; child++) {
					Node objectgroupChild = objectgroupChilds.item(child);
					
					if (objectgroupChild.getNodeName().equals(TiledEditorTags.object)) {
						createAndAddBoundingBox(tileID, objectgroupChild);
					}
				}
			}
		}
	}
	
	private void createAndAddBoundingBox(final int tileID, Node nodeTileObjectgroup) {
		Element elementObject = (Element) nodeTileObjectgroup;
		
		float boundingBoxX = Float.parseFloat(elementObject.getAttribute("x"));
		float boundingBoxY = Float.parseFloat(elementObject.getAttribute("y"));
		int boundingBoxWidth = (int) Float.parseFloat(elementObject.getAttribute("width"));
		int boundingBoxHeight = (int) Float.parseFloat(elementObject.getAttribute("height"));
		
		boundingBoxManager.addBoundingBox(tileID, boundingBoxX, boundingBoxY, boundingBoxWidth, boundingBoxHeight);
//		boundingBoxContainer.put(tileID, new BoundingBox(boundingBoxX, boundingBoxY, boundingBoxWidth, boundingBoxHeight));

		// TODO: create object
		if (DEBUG)
			System.out.println("(TiledEditorMapLoader.readTagLayer) | (BB TILED-ID: " + tileID + ") x: " + boundingBoxX + ", y: " + boundingBoxY + ", width: " + boundingBoxWidth + ", height: " + boundingBoxHeight);
	}
	
	private void readTagEditorsettings(Node nodeEditorsettings) {
		
		NodeList nodeListEditorsettingsChilds = nodeEditorsettings.getChildNodes();
		
		final int nodeListEditorsettingsChildsCnt = nodeListEditorsettingsChilds.getLength();
		
		for (int i = 0; i < nodeListEditorsettingsChildsCnt; i++) {
			Node currentNode = nodeListEditorsettingsChilds.item(i);
			
			if (currentNode.getNodeName().equals(TiledEditorTags.chunksize)) {
				Element elementChunksize = (Element) currentNode;
				chunkwidth = Integer.parseInt(elementChunksize.getAttribute("width"));
				chunkheight = Integer.parseInt(elementChunksize.getAttribute("height"));
			}
		}
	}
	
	private void readTagLayer(Node nodeLayer) {
		
		Element elementLayer = (Element) nodeLayer;
		final int layerID = Integer.parseInt(elementLayer.getAttribute("id"));
		
		//System.out.println("(TiledEditorMapLoader.redTagLayer) | LayerID: " + layerID);
		
		if (DEBUG)
			System.out.println("(TiledEditorMapLoader.readTagLayer) | LayerID: " + layerID);
		
		NodeList nodeListLayerChilds = nodeLayer.getChildNodes();
		
		final int nodeListLayerChildsCnt = nodeListLayerChilds.getLength();
		
		for (int i = 0; i < nodeListLayerChildsCnt; i++) {
			Node dataNode = nodeListLayerChilds.item(i);
			
			if (dataNode.getNodeName().equals("data")) {
				final String layerData = dataNode.getTextContent();
				
				String[] splittedLayerData = layerData.split(",");
				int[][] convertedLayerData = convertLayerData(splittedLayerData, tilesX, tilesY);
				layerDataContainer.put(layerID, convertedLayerData);
			}
		}
		
	}
	
	private int[][] convertLayerData(final String[] currentLayerData, final int width, final int height) {
		int[][] data = new int[width][height];
		
		for (int col = 0; col < width; col++) {
			for (int row = 0; row < height; row++) {
				
				final int converted1DIndex = convert2DDimInto2DIndex(col, row, width);
				final String rawCellData = replaceEmptySpace(currentLayerData[converted1DIndex]);
				final int parseIntRawCellData = Integer.parseInt(rawCellData) - TILED_ID_DELTA; // TODO: WRITE OWN TRANSFORMER CLASS FOR THIS!!!
				setData(data, col, row, parseIntRawCellData);
			}
		}
		
		return data;
	}
	
	private int convert2DDimInto2DIndex(final int col, final int row, final int width) {
		return col + row * width;
	}
	
	private String replaceEmptySpace(String currentLayerData) {
		return currentLayerData.replaceAll("\\s+", "");
	}
	
	private void setData(int[][] data, int col, int row, int cellValue) {
		data[col][row] = cellValue;
	}
	
	private void readTagObjectgroup(Node nodeObjectgroup) {
	
		Element elementObjectgroup = (Element) nodeObjectgroup;
		final int objectGroupID = Integer.parseInt(elementObjectgroup.getAttribute("id"));
		
		boolean singleObject = false;
		NodeList nodeListObjectgroupChilds = nodeObjectgroup.getChildNodes();
		
		final int nodeListObjectgroupChildsCnt = nodeListObjectgroupChilds.getLength();
		
		for (int i = 0; i < nodeListObjectgroupChildsCnt; i++) {
			Node currentNode = nodeListObjectgroupChilds.item(i);
			
			if (currentNode.getNodeName().equals(TiledEditorTags.properties)) {
				
				NodeList nodeListProperty = currentNode.getChildNodes();
				
				for (int j = 0; j < nodeListProperty.getLength(); j++) {
					Node nodeProperty = nodeListProperty.item(j);
					
					if (nodeProperty.getNodeName().equals(TiledEditorTags.property)) {
						Element elementProperty = (Element) nodeProperty;	
						if (elementProperty.getAttribute("name").equals("Single")) {
							String propertySingleValue = elementProperty.getAttribute("value");
							
							singleObject = Boolean.parseBoolean(propertySingleValue);
							
							objectGroupManager.addObjectGroup(objectGroupID, singleObject);
						}
					}
				}
			} else if (currentNode.getNodeName().equals(TiledEditorTags.object)) {
				Element elementObject = (Element) currentNode;
				
				// ------- START
				
				// TODO: NullPointerException in case of a freeform object -> gid is not available.
				//final int objectID = Integer.parseInt(elementObject.getAttribute("gid"));
				
				String attributeGID = elementObject.getAttribute("gid");
				
				if (!attributeGID.isEmpty()) {
					final int objectID = Integer.parseInt(attributeGID);
					// WIR SIND IM FALL, DASS ES SICH UM EIN TILE HANDELT.
					objectGroupManager.addObject(
							objectGroupID, 
							objectID, 
							Float.parseFloat(elementObject.getAttribute("x")), 
							Float.parseFloat(elementObject.getAttribute("y")), 
							Integer.parseInt(elementObject.getAttribute("width")), 
							Integer.parseInt(elementObject.getAttribute("height")));
				} else {
					
					// WIR SIND IM FALL, DASS ES SICH UM EINE FREIFORM HANDELT.
					
					// ID, Type, x, y, width, height
					
					final int ID = Integer.parseInt(elementObject.getAttribute("id"));
					final String type = elementObject.getAttribute("type");
					final float x = Float.parseFloat(elementObject.getAttribute("x")); 
					final float y = Float.parseFloat(elementObject.getAttribute("y")); 
					final int width = Integer.parseInt(elementObject.getAttribute("width"));
					final int height = Integer.parseInt(elementObject.getAttribute("height"));
					
					freeformGroupManager.addFreeformObject(ID, type, x, y, width, height);
					
					NodeList nodeListProperty = currentNode.getChildNodes();
					
					final int nodeListPropertyCnt = nodeListProperty.getLength();
					
					for (int p = 0; p < nodeListPropertyCnt; p++) {
						Node nodeFreeformProperty = nodeListProperty.item(p);
						
						NodeList nodeLisProperties = nodeFreeformProperty.getChildNodes();
						
						final int nodeLisPropertiesCnt = nodeLisProperties.getLength();
						
						for (int t = 0; t < nodeLisPropertiesCnt; t++) {
							Node nodeFreeformProperties = nodeLisProperties.item(t);
							
							if (nodeFreeformProperties.getNodeName().equals(TiledEditorTags.property)) {
								Element elementFreeformProperties = (Element) nodeFreeformProperties;
								
								String propertyName = elementFreeformProperties.getAttribute("name");
								String propertyValue = elementFreeformProperties.getAttribute("value");
								
								freeformGroupManager.addFreeformObjectProperties(ID, propertyName, propertyValue);
							}
						}
						
						
						
						

						
					}
				}
				
				// -------- ENDE
				
			}
		}
	}
	
	private void readTagProperties(Node nodeProperties) {
		
		NodeList nodeListPropertiesChilds = nodeProperties.getChildNodes();
		
		final int nodeListPropertiesChildsCnt = nodeListPropertiesChilds.getLength();
		
		boolean objectLayerIDFound = false;
		
		for (int i = 0; i < nodeListPropertiesChildsCnt && !objectLayerIDFound; i++) {
			Node currentNode = nodeListPropertiesChilds.item(i);
			
			if (currentNode.getNodeName().equals(TiledEditorTags.property)) {
				Element elementProperty = (Element) currentNode;
				if (elementProperty.hasAttribute("name") && elementProperty.getAttribute("name").equals("ObjectLayerID")) {
					objectLayerID = Integer.parseInt(elementProperty.getAttribute("value"));
					objectLayerIDFound = true;
				}
			}
		}
	}

	@Override
	public LayerDataContainer getLayerDataContainer() {
		return layerDataContainer;
	}

	@Override
	public TiledBoundingBoxManager getTiledBoundingBoxManager() {
		return boundingBoxManager;
	}

	@Override
	public int getTileWidth() {
		return tileWidth;
	}

	@Override
	public int getTileHeight() {
		return tileHeight;
	}

	@Override
	public int getTilesX() {
		return tilesX;
	}

	@Override
	public int getTilesY() {
		return tilesY;
	}

	@Override
	public int getChunkWidth() {
		return chunkwidth;
	}

	@Override
	public int getChunkHeight() {
		return chunkheight;
	}

	@Override
	public int getObjectLayerID() {
		return objectLayerID;
	}

	@Override
	public TiledObjectGroupManager getTiledObjectGroupManager() {
		return objectGroupManager;
	}

	@Override
	public TiledFreeformGroupManager getTiledFreeformGroupManager() {
		return freeformGroupManager;
	}
	
	
}
