package de.kaffeeliebhaber.assets;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import de.kaffeeliebhaber.Config;
import de.kaffeeliebhaber.gfx.ImageLoader;
import de.kaffeeliebhaber.gfx.Spritesheet;

public class AssetsLoader {

	// world
	
	public static Spritesheet spritesheet;
	
	// player
	
	// animation sequences
	public static BufferedImage[] playerDownAnimationSequence;
	public static BufferedImage[] playerLeftAnimationSequence;
	public static BufferedImage[] playerRightAnimationSequence;
	public static BufferedImage[] playerTopAnimationSequence;
	
	// Player idle sequences
	public static BufferedImage[] playerIdleDownAnimationSequence;
	public static BufferedImage[] playerIdleLeftAnimationSequence;
	public static BufferedImage[] playerIdleRightAnimationSequence;
	public static BufferedImage[] playerIdleTopAnimationSequence;
	
	// crate
	
	public static BufferedImage[] defaultCrateSequence;
	
	// inventory
	public static Spritesheet spritesheetInventory;
	
	// font
	public static Font hudFont;
	public static Font inventoryFont;
	public static Font visializationPaneFont;
	
	// ui
	public static BufferedImage imageUI;
	public static BufferedImage[] selectionArrowVisualizationPane;
	
	// NPC - IMAGES
	public static Spritesheet spritesheetNPC;
	public static Spritesheet spritesheetNPCFox;
	//public static BufferedImage[] npcIdle;
	
	public static BufferedImage[] volantVillageElderAnimationSequenceIdle;
	public static BufferedImage[] volantMasterKnightAnimationSequenceIdle;
	public static BufferedImage[] volantVillagePeopleOneAnimationSequenceIdle;
	
	// fox
	public static BufferedImage[] foxDownAnimationSequence;
	public static BufferedImage[] foxLeftAnimationSequence;
	public static BufferedImage[] foxRightAnimationSequence;
	public static BufferedImage[] foxTopAnimationSequence;
	public static BufferedImage[] foxIdle;
	
	// female
	public static BufferedImage[] femaleIdle;
	public static Spritesheet spritesheetFemale;
	
	public static void load() {
		
		// load world sprite sheet
		spritesheet = new Spritesheet("src/de/kaffeeliebhaber/assets/BaseChip_pipo.png", 32, 32);
		spritesheet.load();
		
		// load player
		AssetsLoader.loadPlayerAssets();
		
		// load crate
		AssetsLoader.loadCrates();
		
		// load item spritesheet 
		spritesheetInventory = new Spritesheet(Config.ITEM_SPRITESHEET, Config.ITEM_SIZE, Config.ITEM_SIZE);
		spritesheetInventory.load();
		
		// load font 
		loadFonts();
		
		// ui
		imageUI = ImageLoader.loadImage("src/de/kaffeeliebhaber/assets/ui/ui.png");
		
		AssetsLoader.loadVisualizationPaneAssets();
		
		// npc
		spritesheetNPC = new Spritesheet("src/de/kaffeeliebhaber/assets/AH_SpriteSheet_People1.png", 16, 16);
		spritesheetNPC.load();
		
		// fox
		spritesheetNPCFox = new Spritesheet("src/de/kaffeeliebhaber/assets/imgs/72c68863962b7a6b5a9f5f5bcc5afb16.png", 32, 32);
		spritesheetNPCFox.load();

		// *** LOAD NPCs
		AssetsLoader.loadFoxAssets();
		
		AssetsLoader.loadNPCs();
		
		spritesheetFemale = new Spritesheet("src/de/kaffeeliebhaber/assets/imgs/Female 01-1.png", 32, 32);
		spritesheetFemale.load();
	}
	
	private static void loadVisualizationPaneAssets() {
		final BufferedImage image = ImageLoader.loadImage("src/de/kaffeeliebhaber/assets/ui/selectionArrow2.png");
		
		selectionArrowVisualizationPane = new BufferedImage[6];
		selectionArrowVisualizationPane[0] = AssetsLoader.getSubImage(image, 16, 0, 0);
		selectionArrowVisualizationPane[1] = AssetsLoader.getSubImage(image, 16, 0, 1);
		selectionArrowVisualizationPane[2] = AssetsLoader.getSubImage(image, 16, 0, 2);
		selectionArrowVisualizationPane[3] = AssetsLoader.getSubImage(image, 16, 0, 3);
		selectionArrowVisualizationPane[4] = AssetsLoader.getSubImage(image, 16, 0, 4);
		selectionArrowVisualizationPane[5] = AssetsLoader.getSubImage(image, 16, 0, 5);
	}
	
	private static void loadNPCs() {
		
		String path = "src/de/kaffeeliebhaber/assets/AH_SpriteSheet_People1.png";
		BufferedImage image = ImageLoader.loadImage(path);

		volantVillageElderAnimationSequenceIdle = AssetsLoader.getAnimationSequence(image, 16, new int[][] {{0, 10}});
		volantMasterKnightAnimationSequenceIdle = AssetsLoader.getAnimationSequence(image, 16, new int[][] {{4, 7}});
		volantVillagePeopleOneAnimationSequenceIdle = AssetsLoader.getAnimationSequence(image, 16, new int[][] {{4, 10}});
		
		image = ImageLoader.loadImage("src/de/kaffeeliebhaber/assets/imgs/Female 01-1.png");
		
		femaleIdle = AssetsLoader.getAnimationSequence(image, 32, new int[][] {{0, 1}});
	}
	
	private static void loadFonts() {
		
		final String inventoryFontPath = "src/de/kaffeeliebhaber/assets/font/pixelart.ttf";
		final String visializationPaneFontPath = "src/de/kaffeeliebhaber/assets/font/prstart.ttf";
		
	    try {
	    	
	    	FileInputStream fileInptStream = new FileInputStream(new File(inventoryFontPath));
	    	Font createdBaseFont = Font.createFont(Font.TRUETYPE_FONT, fileInptStream);
	    	
	    	hudFont = createdBaseFont.deriveFont(Font.PLAIN, 8);
			inventoryFont = createdBaseFont.deriveFont(Font.PLAIN, 12);
			
			fileInptStream = new FileInputStream(new File(visializationPaneFontPath));
			createdBaseFont = Font.createFont(Font.TRUETYPE_FONT, fileInptStream);
			
			visializationPaneFont = createdBaseFont.deriveFont(Font.PLAIN, 8);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void loadFoxAssets() {
		
		final BufferedImage image = ImageLoader.loadImage("src/de/kaffeeliebhaber/assets/imgs/72c68863962b7a6b5a9f5f5bcc5afb16.png");
		
		// Animation sequences
		final int[][] downIndices 	= {{0,0}, {0,1}, {0,2}, {0, 3}};
		final int[][] rightIndices 	= {{1,0}, {1,1}, {1,2}, {1, 3}};
		final int[][] topIndices 	= {{2,0}, {2,1}, {2,2}, {2, 3}};
		final int[][] leftIndices 	= {{3,0}, {3,1}, {3,2}, {3, 3}};
		final int[][] idle 			= {{0, 0}};
		
		foxDownAnimationSequence = AssetsLoader.getAnimationSequence(image, 32, downIndices);
		foxLeftAnimationSequence = AssetsLoader.getAnimationSequence(image, 32, leftIndices);
		foxRightAnimationSequence = AssetsLoader.getAnimationSequence(image, 32, rightIndices);
		foxTopAnimationSequence = AssetsLoader.getAnimationSequence(image, 32, topIndices);
		foxIdle = AssetsLoader.getAnimationSequence(image, 32, idle);
	}
	
	private static void loadPlayerAssets() {
		
		final BufferedImage playerAnimationSpritesheet = ImageLoader.loadImage(Config.PLAYER_SPRITESHEET);
		
		// Animation sequences
		final int[][] downIndices 	= {{0,0}, {0,1}, {0,2}};
		final int[][] leftIndices 	= {{1,0}, {1,1}, {1,2}};
		final int[][] topIndices 	= {{2,0}, {2,1}, {2,2}};
		final int[][] rightIndices 	= {{3,0}, {3,1}, {3,2}};
		
		playerDownAnimationSequence = AssetsLoader.getAnimationSequence(playerAnimationSpritesheet, Config.PLAYER_SPRITESHEET_SIZE, downIndices);
		playerLeftAnimationSequence = AssetsLoader.getAnimationSequence(playerAnimationSpritesheet, Config.PLAYER_SPRITESHEET_SIZE, leftIndices);
		playerRightAnimationSequence = AssetsLoader.getAnimationSequence(playerAnimationSpritesheet, Config.PLAYER_SPRITESHEET_SIZE, topIndices);
		playerTopAnimationSequence = AssetsLoader.getAnimationSequence(playerAnimationSpritesheet, Config.PLAYER_SPRITESHEET_SIZE, rightIndices);
		
		// Idle sequences
		final int[][] downIdleIndices 	= {{0,1}};
		final int[][] leftIdleIndices 	= {{1,1}};
		final int[][] topIdleIndices 	= {{2,1}};
		final int[][] rightIdleIndices 	= {{3,1}};
		
		playerIdleDownAnimationSequence = AssetsLoader.getAnimationSequence(playerAnimationSpritesheet, Config.PLAYER_SPRITESHEET_SIZE, downIdleIndices);
		playerIdleLeftAnimationSequence = AssetsLoader.getAnimationSequence(playerAnimationSpritesheet, Config.PLAYER_SPRITESHEET_SIZE, leftIdleIndices);
		playerIdleRightAnimationSequence = AssetsLoader.getAnimationSequence(playerAnimationSpritesheet, Config.PLAYER_SPRITESHEET_SIZE, topIdleIndices);
		playerIdleTopAnimationSequence = AssetsLoader.getAnimationSequence(playerAnimationSpritesheet, Config.PLAYER_SPRITESHEET_SIZE, rightIdleIndices);
	}
	
	public static BufferedImage[] getAnimationSequence(final BufferedImage sheet, final int tileSize, final int[][] indices) {
		
		final int countImages = indices.length;
		
		BufferedImage[] animationSequence = new BufferedImage[countImages];
		
		for (int y = 0; y < countImages; y++) {
			animationSequence[y] = AssetsLoader.getSubImage(sheet, tileSize, indices[y][0], indices[y][1]);
		}
		
		return animationSequence;
	}
	
	public static BufferedImage getSubImage(final BufferedImage spritesheet, final int tileSize, final int tileY, final int tileX) {
		return spritesheet.getSubimage(tileX * tileSize, tileY * tileSize, tileSize, tileSize);
	}
	
	private static void loadCrates() {
		
		final BufferedImage crateSpritesheet = ImageLoader.loadImage(Config.CRATE_SPRITESHEET);
		int[][] indices = {{0,0}, {1,0}, {2,0}, {3,0}};
		
		defaultCrateSequence = AssetsLoader.getAnimationSequence(crateSpritesheet, Config.CRATE_SPRITESHEET_SIZE, indices);
	}
}
