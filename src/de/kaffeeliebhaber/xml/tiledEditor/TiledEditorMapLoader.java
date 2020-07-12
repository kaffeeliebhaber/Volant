package de.kaffeeliebhaber.xml.tiledEditor;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TiledEditorMapLoader {

	private final String documentPath;
	private Document document;
	
	private static final boolean DEBUG = true;
	
	public TiledEditorMapLoader(final String documentPath) {
		this.documentPath = documentPath;
		init();

	}
	
	private void init() {
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
		
		String mapWidth = elementMap.getAttribute("width");
		String mapHeight = elementMap.getAttribute("height");
		String mapTilewidth = elementMap.getAttribute("tilewidth");
		String mapTileheight = elementMap.getAttribute("tileheight");
		
		// TODO: create object
		if (DEBUG)
			System.out.println("width: " + mapWidth + ", height: " + mapHeight + ", tilewidth: " + mapTilewidth + ", tileheight: " + mapTileheight);
		
	}
	
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
						createBoundingBox(tileID, objectgroupChild);
					}
				}
			}
		}
	}
	
	private void createBoundingBox(final int tileID, Node nodeTileObjectgroup) {
		Element elementObject = (Element) nodeTileObjectgroup;
		
		float boundingBoxX = Float.parseFloat(elementObject.getAttribute("x"));
		float boundingBoxY = Float.parseFloat(elementObject.getAttribute("y"));
		int boundingBoxWidth = (int) Float.parseFloat(elementObject.getAttribute("width"));
		int boundingBoxHeight = (int) Float.parseFloat(elementObject.getAttribute("height"));

		// TODO: create object
		if (DEBUG)
			System.out.println("(BB TILED-ID: " + tileID + ") x: " + boundingBoxX + ", y: " + boundingBoxY + ", width: " + boundingBoxWidth + ", height: " + boundingBoxHeight);
	}
	
	private void readTagEditorsettings(Node nodeEditorsettings) {
		
		NodeList nodeListEditorsettingsChilds = nodeEditorsettings.getChildNodes();
		
		final int nodeListEditorsettingsChildsCnt = nodeListEditorsettingsChilds.getLength();
		
		for (int i = 0; i < nodeListEditorsettingsChildsCnt; i++) {
			Node currentNode = nodeListEditorsettingsChilds.item(i);
			
			if (currentNode.getNodeName().equals(TiledEditorTags.chunksize)) {
				Element elementChunksize = (Element) currentNode;
				
				/*
				 * READ 'PROPERTIES'
				 */
				String chunkwidth = elementChunksize.getAttribute("width");
				String chunkheight = elementChunksize.getAttribute("height");
				
				// TODO: create object
				if (DEBUG)
					System.out.println("chunkWidth: " + chunkwidth + ", chunkHeight: " + chunkheight);
			}
		}
	}
	
	private void readTagLayer(Node nodeLayer) {
		
		Element elementLayer = (Element) nodeLayer;
		String layerID = elementLayer.getAttribute("id");
		
		if (DEBUG)
			System.out.println("LayerID | " + layerID);
		
		NodeList nodeListLayerChilds = nodeLayer.getChildNodes();
		
		final int nodeListLayerChildsCnt = nodeListLayerChilds.getLength();
		
		for (int i = 0; i < nodeListLayerChildsCnt; i++) {
			Node dataNode = nodeListLayerChilds.item(i);
			
			// TODO: create object
			final String layerData = dataNode.getTextContent();
		}
		
	}
	
	private void readTagObjectgroup(Node nodeObjectgroup) {
	
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
							
							// TODO: create object
							if (DEBUG)
								System.out.println("single | " + propertySingleValue);
						}
					}
				}
				
			} else if (currentNode.getNodeName().equals(TiledEditorTags.object)) {
				Element elementObject = (Element) currentNode;
				
				// TODO:
				// BEI DER 'GID' MUSS EVENTUELL EINE ANPASSUNG DER ID PASSIEREN (GID - 1), DA SICH <TILEID> UND <GID> UM DEN WERT <1> UNTERSCHEIDEN!!!
				String objectGID = elementObject.getAttribute("gid");
				String objectX = elementObject.getAttribute("x");
				String objectY = elementObject.getAttribute("y");
				String objectwidth = elementObject.getAttribute("width");
				String objectHeight = elementObject.getAttribute("height");
				
				// TODO: create object
				if (DEBUG)
					System.out.println("(Object) gid: " + objectGID + ", x: " + objectX + ", y: " + objectY + ", width: " + objectwidth + ", height: " + objectHeight);
			}
		}
		
	}
	
	private void readTagProperties(Node nodeProperties) {
		
		NodeList nodeListPropertiesChilds = nodeProperties.getChildNodes();
		
		final int nodeListPropertiesChildsCnt = nodeListPropertiesChilds.getLength();
		
		for (int i = 0; i < nodeListPropertiesChildsCnt; i++) {
			Node currentNode = nodeListPropertiesChilds.item(i);
			
			if (currentNode.getNodeName().equals(TiledEditorTags.property)) {
				Element elementProperty = (Element) currentNode;
				
				/*
				 * READ 'PROPERTY' #ObjectLayerID
				 */
				if (elementProperty.hasAttribute("name") && elementProperty.getAttribute("name").equals("ObjectLayerID")) {
					String objectLayerID = elementProperty.getAttribute("value");
					
					// TODO: create object
					if (DEBUG)
						System.out.println("objectLayerID: " + objectLayerID);
				}
			}
		}
	}
}
