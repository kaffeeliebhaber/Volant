package de.kaffeeliebhaber.object;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

import de.kaffeeliebhaber.Config;
import de.kaffeeliebhaber.animation.Animation;
import de.kaffeeliebhaber.animation.AnimationConstants;
import de.kaffeeliebhaber.animation.DefaultAnimationController;
import de.kaffeeliebhaber.animation.Direction;
import de.kaffeeliebhaber.animation.IAnimationController;
import de.kaffeeliebhaber.assets.AssetsLoader;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.inventory.EquipmentManager;
import de.kaffeeliebhaber.inventory.Inventory;
import de.kaffeeliebhaber.inventory.ItemManager;
import de.kaffeeliebhaber.inventory.item.EquipItem;
import de.kaffeeliebhaber.inventory.item.Item;
import de.kaffeeliebhaber.inventory.item.ItemCategory;
import de.kaffeeliebhaber.inventory.item.ItemType;
import de.kaffeeliebhaber.inventory.item.UseItem;
import de.kaffeeliebhaber.inventory.item.actions.FillUpHealthPointsAction;
import de.kaffeeliebhaber.inventory.item.actions.IItemAction;
import de.kaffeeliebhaber.inventory.stats.PlayerStats;
import de.kaffeeliebhaber.inventory.stats.Stat;
import de.kaffeeliebhaber.math.Vector2f;
import de.kaffeeliebhaber.object.movingBehavior.IMovingBehavior;
import de.kaffeeliebhaber.object.movingBehavior.LinearInterpolationToTargetPointMovingBehavior;
import de.kaffeeliebhaber.object.movingBehavior.LinearMovingBehavior;
import de.kaffeeliebhaber.object.movingBehavior.NoneMovingBehavior;
import de.kaffeeliebhaber.object.movingBehavior.PlayerMovingBehavior;
import de.kaffeeliebhaber.object.npc.NPC;
import de.kaffeeliebhaber.object.npc.pets.fox.Fox;
import de.kaffeeliebhaber.object.npc.pets.fox.NPCAnimationController;
import de.kaffeeliebhaber.object.npc.volant.VolantFemaleAnne;
import de.kaffeeliebhaber.object.npc.volant.VolantMasterKnight;
import de.kaffeeliebhaber.object.npc.volant.VolantVillageElder;
import de.kaffeeliebhaber.object.npc.volant.VolantVillagePeopleOne;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystemController;
import de.kaffeeliebhaber.tilesystem.chunk.TiledXML;
import de.kaffeeliebhaber.tilesystem.transition.Transition;
import de.kaffeeliebhaber.tilesystem.transition.tile.TransitionDirection;
import de.kaffeeliebhaber.tilesystem.transition.tile.TransitionTile;
import de.kaffeeliebhaber.ui.UIInfoPane;
import de.kaffeeliebhaber.ui.inventory.UIInventoryManager;

public class GameObjectManager {

	private ChunkSystem chunkSystem;
	private Transition transition;
	private Player player;
	private Camera camera;
	private EntityHandler entityHandler;
	private UIInventoryManager inventoryManager;
	private ItemManager itemManager;
	private UIInfoPane infoPane;

	public GameObjectManager() {

		// LOAD AND CREATE CHUNKSYSTEM
		chunkSystem = createChunkSystem(Config.TILE_MAP_PATH);

		addTransitionTilesToChunkSystem(chunkSystem);

		createPlayer();

		// create camera
		camera = new Camera(0, 0, Config.WIDTH, Config.HEIGHT, new Dimension(chunkSystem.getChunkWidthInTile() * chunkSystem.getTileWidth(), chunkSystem.getChunkHeightInTile() * chunkSystem.getTileHeight()));
		camera.focusOn(player);

		player.addEntityUpdateListener(camera);

		// create transition
		transition = new Transition(new Rectangle(0, 0, Config.WIDTH, Config.HEIGHT), 20, 20, 20);
		transition.setColor(Color.BLACK);

		entityHandler = new EntityHandler();
		infoPane = new UIInfoPane();

		createNPCs(chunkSystem);
		createAndSetupInventory(player);

		ChunkSystemController chunkSystemController = new ChunkSystemController(chunkSystem, player, transition, entityHandler, itemManager);
		chunkSystemController.setChunkID(0);
	}

	private void createNPCs(final ChunkSystem chunkSystem) {
		chunkSystem.addEntity(0, createNPCVolantVillageElder());
		chunkSystem.addEntity(0, createNPCVolantMasterKnight());
		chunkSystem.addEntity(0, createNPCVolantVillagePeopleOne());
		chunkSystem.addEntity(0, createNPCVolandFemaleAnne());

		final int countOfFox = 1;

		final Random r = new Random();

		for (int i = 0; i < countOfFox; i++) {
			float startX = r.nextInt(300);
			float startY = r.nextInt(300);
			
			chunkSystem.addEntity(0, createFOX(startX, startY, 32, new Vector2f(startX, startY), 200, 200, 200, 200, 1f));
//			chunkSystem.addEntity(0, createFOX(233, 316, 32, new Vector2f(233, 316), 200, 200, 200, 200, 0.01f));
		}

//		chunkSystem.addEntity(0, createFOX(150, 300, 32, new Vector2f(166, 316), 150, 280, 300, 300, 0.01f));
//		chunkSystem.addEntity(0, createFOX(350, 177, 32, new Vector2f(366, 193), 350, 150, 200, 400, 0.006f));

	}

	private NPC createNPCVolandFemaleAnne() {

		// Moving - Animation
		IAnimationController animationController = new NPCAnimationController();
		animationController.addAnimation(new Animation(AnimationConstants.DOWN_IDLE, AssetsLoader.femaleIdle, 10));
		animationController.updateState(0, 0);

		NPC anne = new VolantFemaleAnne(400, 150, 32, 32, Direction.DOWN, animationController, new NoneMovingBehavior());

		anne.setPopupImage(AssetsLoader.spritesheetFemale.getImageByIndex(1));
		anne.setActionKeyID(KeyEvent.VK_E);
		anne.setPlayer(player);
		anne.addInfoPaneInformerListener(infoPane);

		return anne;
	}

	private void createPlayer() {

		// CREATE PLAYER STATS
		final PlayerStats playerStats = new PlayerStats();
		playerStats.setArmorStat(new Stat(10));
		playerStats.setDamageStat(new Stat(2));

		EquipmentManager.instance.addEquipmentManagerListener(playerStats);

		IAnimationController animationController = new DefaultAnimationController();

		// Animation direction sequences
		animationController.addAnimation(new Animation(AnimationConstants.DOWN_MOVE, AssetsLoader.playerDownAnimationSequence, 10));
		animationController.addAnimation(new Animation(AnimationConstants.LEFT_MOVE, AssetsLoader.playerLeftAnimationSequence, 10));
		animationController.addAnimation(new Animation(AnimationConstants.RIGHT_MOVE, AssetsLoader.playerRightAnimationSequence, 10));
		animationController.addAnimation(new Animation(AnimationConstants.TOP_MOVE, AssetsLoader.playerTopAnimationSequence, 10));

		// Animation idle sequences
		animationController.addAnimation(new Animation(AnimationConstants.DOWN_IDLE, AssetsLoader.playerIdleDownAnimationSequence, 10));
		animationController.addAnimation(new Animation(AnimationConstants.LEFT_IDLE, AssetsLoader.playerIdleLeftAnimationSequence, 10));
		animationController.addAnimation(new Animation(AnimationConstants.RIGHT_IDLE, AssetsLoader.playerIdleRightAnimationSequence, 10));
		animationController.addAnimation(new Animation(AnimationConstants.TOP_IDLE, AssetsLoader.playerIdleTopAnimationSequence, 10));

		animationController.updateState(0, 0);

		// CREATE PLAYER
		player = new Player(100, 100, Config.PLAYER_SIZE, Config.PLAYER_SIZE, animationController, new PlayerMovingBehavior(2f), playerStats, new BoundingBox(100, 120, 32, 12));
		// player.setPosition(100, 100);
		// player.setBoundingBox(new BoundingBox(100, 120, 32, 12));
		player.setDistrict(new Rectangle(0, 0, chunkSystem.getChunkWidthInTile() * chunkSystem.getTileWidth(), chunkSystem.getChunkHeightInTile() * chunkSystem.getTileHeight()));
	}

	private NPC createNPCVolantVillageElder() {

		// Moving - Animation
		IAnimationController animationController = new NPCAnimationController();
		animationController.addAnimation(new Animation(AnimationConstants.DOWN_IDLE, AssetsLoader.volantVillageElderAnimationSequenceIdle, 10));
		animationController.updateState(0, 0);

		// CREATE AND CONFIGURE NPC - GORDOM
		final NPC gordom = new VolantVillageElder(500, 400, 32, 32, Direction.DOWN, animationController,new NoneMovingBehavior());

		gordom.setPopupImage(AssetsLoader.spritesheetNPC.getImageByIndex(10));
		gordom.setActionKeyID(KeyEvent.VK_E);
		gordom.setPlayer(player);
		gordom.addInfoPaneInformerListener(infoPane);

		return gordom;
	}

	private NPC createNPCVolantMasterKnight() {
		// Moving - Animation
		IAnimationController animationController = new NPCAnimationController();
		animationController.addAnimation(new Animation(AnimationConstants.DOWN_IDLE, AssetsLoader.volantMasterKnightAnimationSequenceIdle, 10));
		animationController.updateState(0, 0);

		// CREATE AND CONFIGURE NPC - GORDOM
		final NPC knight = new VolantMasterKnight(300, 550, 32, 32, Direction.DOWN, animationController,new NoneMovingBehavior());

		knight.setPopupImage(AssetsLoader.spritesheetNPC.getImageByIndex(55));
		knight.setActionKeyID(KeyEvent.VK_E);
		knight.setPlayer(player);
		knight.addInfoPaneInformerListener(infoPane);

		return knight;
	}

	private NPC createNPCVolantVillagePeopleOne() {

		// Moving - Animation
		IAnimationController animationController = new NPCAnimationController();
		animationController.addAnimation(new Animation(AnimationConstants.DOWN_IDLE,AssetsLoader.volantVillagePeopleOneAnimationSequenceIdle, 10));
		animationController.updateState(0, 0);

		// CREATE AND CONFIGURE NPC - GORDOM
		final NPC npc = new VolantVillagePeopleOne(150, 600, 32, 32, Direction.DOWN, animationController, new NoneMovingBehavior());

		npc.setPopupImage(AssetsLoader.spritesheetNPC.getImageByIndex(58));
		npc.setActionKeyID(KeyEvent.VK_E);
		npc.setPlayer(player);
		npc.addInfoPaneInformerListener(infoPane);

		return npc;
	}

	private NPC createFOX(float startX, float startY, int size, Vector2f startPoint, float areaX, float areaY, int areaWidth, int areaHeight, float movingSpeed) {

		// Moving - Animation
		IAnimationController animationController = new NPCAnimationController();

		// Animation direction sequences
		animationController.addAnimation(new Animation(AnimationConstants.DOWN_MOVE, AssetsLoader.foxDownAnimationSequence, 10));
		animationController.addAnimation(new Animation(AnimationConstants.LEFT_MOVE, AssetsLoader.foxLeftAnimationSequence, 10));
		animationController.addAnimation(new Animation(AnimationConstants.RIGHT_MOVE, AssetsLoader.foxRightAnimationSequence, 10));
		animationController.addAnimation(new Animation(AnimationConstants.TOP_MOVE, AssetsLoader.foxTopAnimationSequence, 10));
		animationController.addAnimation(new Animation(AnimationConstants.DOWN_IDLE, AssetsLoader.foxIdle, 10));

		animationController.updateState(0, 0);

		final IMovingBehavior movingBehavior = new LinearInterpolationToTargetPointMovingBehavior(startPoint, areaX, areaY, areaWidth, areaHeight, movingSpeed);
		final IMovingBehavior linearMoving = new LinearMovingBehavior(areaX, areaY, areaWidth, areaHeight, movingSpeed, new Vector2f(startX, startY));
		
		
		final NPC fox = new Fox(startX, startY, size, size, Direction.DOWN, animationController, linearMoving);
		fox.setPopupImage(AssetsLoader.spritesheetNPCFox.getImageByIndex(0));
		fox.setActionKeyID(KeyEvent.VK_E);
		fox.setPlayer(player);
		fox.addInfoPaneInformerListener(infoPane);

		return fox;
	}

	private void createAndSetupInventory(Player player) {

		// create items
		inventoryManager = new UIInventoryManager(Inventory.instance, EquipmentManager.instance, 100, 100);

		IItemAction increaseHP10 = new FillUpHealthPointsAction(player, 10);

		Item sword = new EquipItem(ItemCategory.EQUIPMENT, ItemType.WEAPON, "Einfaches Schwert", AssetsLoader.spritesheetInventory.getImageByIndex(37));
		sword.setBoundingBox(new BoundingBox(300, 300, 16, 16));
		sword.setStackable(false);
		sword.setArmorValue(1);
		sword.setDamageValue(4);

		Item chest = new EquipItem(ItemCategory.EQUIPMENT, ItemType.CHEST, "Einfacher Brustpanzer", AssetsLoader.spritesheetInventory.getImageByIndex(5));
		chest.setBoundingBox(new BoundingBox(120, 210, 16, 16));
		chest.setStackable(false);
		chest.setArmorValue(5);
		chest.setDamageValue(2);

		Item legs = new EquipItem(ItemCategory.EQUIPMENT, ItemType.LEGS, "Einfache Hose", AssetsLoader.spritesheetInventory.getImageByIndex(12));
		legs.setBoundingBox(new BoundingBox(400, 320, 16, 16));
		legs.setStackable(false);
		legs.setArmorValue(3);
		legs.setDamageValue(1);

		Item feets = new EquipItem(ItemCategory.EQUIPMENT, ItemType.FEETS, "Einfache Stiefel", AssetsLoader.spritesheetInventory.getImageByIndex(13));
		feets.setBoundingBox(new BoundingBox(170, 520, 16, 16));
		feets.setStackable(false);
		feets.setArmorValue(5);
		feets.setDamageValue(2);

		Item head = new EquipItem(ItemCategory.EQUIPMENT, ItemType.HEAD, "Einfache Helm", AssetsLoader.spritesheetInventory.getImageByIndex(4));
		head.setBoundingBox(new BoundingBox(70, 320, 16, 16));
		head.setStackable(false);
		head.setArmorValue(5);
		head.setDamageValue(3);

		Item shield = new EquipItem(ItemCategory.EQUIPMENT, ItemType.SHIELD, "Einfache Stiefel", AssetsLoader.spritesheetInventory.getImageByIndex(20));
		shield.setBoundingBox(new BoundingBox(20, 70, 16, 16));
		shield.setStackable(false);
		shield.setArmorValue(5);
		shield.setDamageValue(2);

		Item potion = new UseItem(ItemCategory.OBJECT, ItemType.POTION, "Heiltrank (1)", AssetsLoader.spritesheetInventory.getImageByIndex(0));
		potion.setBoundingBox(new BoundingBox(190, 60, 16, 16));
		potion.setStackable(true);
		potion.setItemAction(increaseHP10);

		Item potion2 = new UseItem(ItemCategory.OBJECT, ItemType.POTION, "Heiltrank (1)", AssetsLoader.spritesheetInventory.getImageByIndex(0));
		potion2.setBoundingBox(new BoundingBox(290, 160, 16, 16));
		potion2.setStackable(true);
		potion2.setItemAction(increaseHP10);

		Item poisson = new UseItem(ItemCategory.OBJECT, ItemType.POTION, "GIFT (1)", AssetsLoader.spritesheetInventory.getImageByIndex(1));
		poisson.setBoundingBox(new BoundingBox(340, 260, 16, 16));
		poisson.setStackable(true);
		poisson.setItemAction(new FillUpHealthPointsAction(player, -30));

		// create item manager
		itemManager = new ItemManager(player);
		itemManager.addItem(sword);
		itemManager.addItem(chest);
		itemManager.addItem(potion);
		itemManager.addItem(potion2);
		itemManager.addItem(poisson);
		itemManager.addItem(legs);
		itemManager.addItem(feets);
		itemManager.addItem(head);
		itemManager.addItem(shield);
	}

	private ChunkSystem createChunkSystem(final String mapPath) {
		TiledXML tiled = new TiledXML(Config.TILE_MAP_PATH);
		tiled.load();

		ChunkSystem chunkSystem = tiled.createChunkSystem();
		chunkSystem.setObjectLayerID(Config.TILE_MAP_OBJECT_LAYER_ID); // TODO: Später aus dem Tiled-XML ziehen -> Muss
																		// dort noch als eigener Parameter definiert
																		// werden.
		tiled.clear();

		return chunkSystem;
	}

	public void addTransitionTilesToChunkSystem(final ChunkSystem chunkSystem) {

		// CHUNK 0
		TransitionTile tile = new TransitionTile(798, 300, 2, 100, 1, TransitionDirection.RIGHT);
		tile.setBoundingBox(new BoundingBox(798, 300, 2, 100));
		chunkSystem.addTransitionTile(0, tile);

		tile = new TransitionTile(480, 798, 180, 2, 2, TransitionDirection.DOWN);
		tile.setBoundingBox(new BoundingBox(480, 798, 180, 2));
		chunkSystem.addTransitionTile(0, tile);

		// CHUNK 1
		tile = new TransitionTile(0, 300, 2, 100, 0, TransitionDirection.LEFT);
		tile.setBoundingBox(new BoundingBox(0, 300, 2, 100));
		chunkSystem.addTransitionTile(1, tile);

		tile = new TransitionTile(150, 798, 100, 2, 3, TransitionDirection.DOWN);
		tile.setBoundingBox(new BoundingBox(150, 798, 100, 2));
		chunkSystem.addTransitionTile(1, tile);

		// CHUNK 2
		tile = new TransitionTile(480, 0, 180, 2, 0, TransitionDirection.UP);
		tile.setBoundingBox(new BoundingBox(480, 0, 180, 2));
		chunkSystem.addTransitionTile(2, tile);

		tile = new TransitionTile(798, 150, 2, 100, 3, TransitionDirection.RIGHT);
		tile.setBoundingBox(new BoundingBox(798, 150, 2, 100));
		chunkSystem.addTransitionTile(2, tile);

		// CHUNK 3
		tile = new TransitionTile(150, 0, 100, 2, 1, TransitionDirection.UP);
		tile.setBoundingBox(new BoundingBox(150, 0, 100, 2));
		chunkSystem.addTransitionTile(3, tile);

		tile = new TransitionTile(0, 150, 2, 100, 2, TransitionDirection.LEFT);
		tile.setBoundingBox(new BoundingBox(0, 150, 2, 100));
		chunkSystem.addTransitionTile(3, tile);
	}

	public ChunkSystem getChunkSystem() {
		return chunkSystem;
	}

	public Transition getTransition() {
		return transition;
	}

	public Player getPlayer() {
		return player;
	}

	public Camera getCamera() {
		return camera;
	}

	public EntityHandler getEntityHandler() {
		return entityHandler;
	}

	public UIInventoryManager getInventoryManager() {
		return inventoryManager;
	}

	public ItemManager getItemManager() {
		return itemManager;
	}

	public UIInfoPane getUIInfoPane() {
		return infoPane;
	}

}
