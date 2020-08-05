package de.kaffeeliebhaber.managers;

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
import de.kaffeeliebhaber.behavior.moving.IMovingBehavior;
import de.kaffeeliebhaber.behavior.moving.LinearMovingBehavior;
import de.kaffeeliebhaber.behavior.moving.NoneMovingBehavior;
import de.kaffeeliebhaber.behavior.moving.PlayerMovingBehavior;
import de.kaffeeliebhaber.collision.BoundingBox;
import de.kaffeeliebhaber.core.Camera;
import de.kaffeeliebhaber.entitySystem.EntitySystem;
import de.kaffeeliebhaber.entitySystem.Player;
import de.kaffeeliebhaber.entitySystem.npc.Fox;
import de.kaffeeliebhaber.entitySystem.npc.NPC;
import de.kaffeeliebhaber.entitySystem.npc.NPCAnimationController;
import de.kaffeeliebhaber.entitySystem.npc.VolantFemaleAnne;
import de.kaffeeliebhaber.entitySystem.npc.VolantMasterKnight;
import de.kaffeeliebhaber.entitySystem.npc.VolantVillageElder;
import de.kaffeeliebhaber.entitySystem.npc.VolantVillagePeopleOne;
import de.kaffeeliebhaber.entitySystem.stats.PlayerStats;
import de.kaffeeliebhaber.entitySystem.stats.Stat;
import de.kaffeeliebhaber.inventorySystem.EquipmentManager;
import de.kaffeeliebhaber.inventorySystem.InventoryManager;
import de.kaffeeliebhaber.inventorySystem.ItemManager;
import de.kaffeeliebhaber.inventorySystem.item.EquipItem;
import de.kaffeeliebhaber.inventorySystem.item.Item;
import de.kaffeeliebhaber.inventorySystem.item.ItemCategory;
import de.kaffeeliebhaber.inventorySystem.item.ItemType;
import de.kaffeeliebhaber.inventorySystem.item.UseItem;
import de.kaffeeliebhaber.inventorySystem.item.action.FillUpHealthPointsAction;
import de.kaffeeliebhaber.inventorySystem.item.action.IItemAction;
import de.kaffeeliebhaber.math.Vector2f;
import de.kaffeeliebhaber.newUI.UIInventory;
import de.kaffeeliebhaber.tilesystem.chunk.ChunkSystem;
import de.kaffeeliebhaber.tilesystem.chunk.GameWorld;
import de.kaffeeliebhaber.tilesystem.transition.Transition;
import de.kaffeeliebhaber.ui.UIHud;
import de.kaffeeliebhaber.ui.UIInfoPane;
import de.kaffeeliebhaber.xml.tiledEditor.TiledEditorMapLoader;
import de.kaffeeliebhaber.xml.tiledEditor.ChunkSystem.ChunkSystemCreator;
import de.kaffeeliebhaber.xml.tiledEditor.ChunkSystem.ChunkSystemCreatorModel;

public class GameObjectManager extends GameObjectLoader {

	private ChunkSystem chunkSystem;
	private Transition transition;
	private Player player;
	private Camera camera;
	private UIHud hud;
	private ItemManager itemManager;
	private UIInfoPane infoPane;
	private EntitySystem entitySystem;
	private GameWorld gameWorld;
	private UIInventory uiInventory;
	private InventoryManager inventoryManager;
	private EquipmentManager equipmentManager;

	public GameObjectManager() {

		// USER INTERFACE
		equipmentManager = new EquipmentManager();
		inventoryManager = new InventoryManager(equipmentManager);
		uiInventory = new UIInventory(0, 0, 100, 100, inventoryManager, equipmentManager);
		
		itemManager = new ItemManager();
		infoPane = new UIInfoPane(Config.WIDTH, Config.HEIGHT);
		
		// INVENTORY REGISTRATION
		itemManager.addItemManagerListener(inventoryManager);
		itemManager.setInfoPaneInformerListener(infoPane);
		

		
		// LOAD CHUNKSYSTEM FROM TILED-EDITOR
		final ChunkSystemCreatorModel model = new TiledEditorMapLoader(Config.MAP_PATH);
		final ChunkSystemCreator creator = new ChunkSystemCreator(model, AssetsLoader.spritesheet);
		
		// CREATE CHUNKSYSTEM
		chunkSystem = creator.createChunkSystem();
		
		// CREATE FREEFORM OBJECTS FROM TILED-EDITOR
//		creator.createFreeformObjects();
		
		// CREATE PLAYER
		createPlayer();
		
		// CREATE DUMMY ITEMS
		createAndSetupInventory();
		
		// CREATE ENTITYSYSTEM
		entitySystem = creator.createEntitySystem(player);
		
		// CREATE CAMERA
		camera = new Camera(0, 0, Config.WIDTH, Config.HEIGHT, new Dimension(chunkSystem.getChunkWidthInTile() * chunkSystem.getTileWidth(), chunkSystem.getChunkHeightInTile() * chunkSystem.getTileHeight()));
		camera.focusOn(player);
		player.addEntityUpdateListener(camera);
		
		// CREATE CHUNK-TRANSITION
		transition = new Transition(new Rectangle(0, 0, Config.WIDTH, Config.HEIGHT), 20, 20, 20);
		transition.setColor(Color.BLACK);
		
		// GLOBAL SETUP
		entitySystem.add(0, player);
		gameWorld = new GameWorld(player, chunkSystem, itemManager, entitySystem, transition);
		
		hud = new UIHud(player);
		hud.activate();
		
		// LOAD TEST-GAME OBJECTS
//		createEntityFox(1);
//		createEntityNPCs();
	}

	private void createEntityFox(final int countOfFox) {
		
		final Random r = new Random();

		for (int i = 0; i < countOfFox; i++) {
			float startX = r.nextInt(800);
			float startY = r.nextInt(800);
			
			entitySystem.add(0, createFOX(startX, startY, 32, new Vector2f(startX, startY), 200, 200, 200, 200, 1f));
		}
	}
	
	private void createEntityNPCs() {
		entitySystem.add(0, createNPCVolandFemaleAnne());
		entitySystem.add(0, createNPCVolantVillageElder());
		entitySystem.add(0, createNPCVolantMasterKnight());
		entitySystem.add(0, createNPCVolantVillagePeopleOne());
	}
	
	private NPC createNPCVolandFemaleAnne() {

		// Moving - Animation
		IAnimationController animationController = new NPCAnimationController();
		animationController.addAnimation(new Animation(AnimationConstants.DOWN_IDLE, AssetsLoader.femaleIdle, 10));
		animationController.updateState(null, 0, 0);

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

		equipmentManager.addEquipmentManagerListener(playerStats);

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

		animationController.updateState(null, 0, 0);

		// CREATE PLAYER
		final int playerBoundingBoxHeight = 7;
		player = new Player(50, 50, Config.PLAYER_SIZE, Config.PLAYER_SIZE, animationController, new PlayerMovingBehavior(2f), playerStats);
		player.addBoundingBox(new BoundingBox(50, 50 + Config.PLAYER_SIZE - playerBoundingBoxHeight, Config.PLAYER_SIZE, playerBoundingBoxHeight));
		player.setDistrict(new Rectangle(0, 0, chunkSystem.getChunkWidthInTile() * chunkSystem.getTileWidth(), chunkSystem.getChunkHeightInTile() * chunkSystem.getTileHeight()));
		
	}

	private NPC createNPCVolantVillageElder() {

		// Moving - Animation
		IAnimationController animationController = new NPCAnimationController();
		animationController.addAnimation(new Animation(AnimationConstants.DOWN_IDLE, AssetsLoader.volantVillageElderAnimationSequenceIdle, 10));
		animationController.updateState(null, 0, 0);

		// CREATE AND CONFIGURE NPC - GORDOM
		final NPC gordom = new VolantVillageElder(500, 400, 32, 32, Direction.DOWN, animationController, new NoneMovingBehavior());
		
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
		animationController.updateState(null, 0, 0);

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
		animationController.updateState(null, 0, 0);

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

		animationController.updateState(null, 0, 0);

		final IMovingBehavior linearMoving = new LinearMovingBehavior(areaX, areaY, areaWidth, areaHeight, movingSpeed, new Vector2f(startX, startY));
		final NPC fox = new Fox(startX, startY, size, size, Direction.DOWN, animationController, linearMoving);
		fox.setPopupImage(AssetsLoader.spritesheetNPCFox.getImageByIndex(0));
		fox.setActionKeyID(KeyEvent.VK_E);
		fox.setPlayer(player);
		fox.addInfoPaneInformerListener(infoPane);

		return fox;
	}

	private void createAndSetupInventory() {

		// create items
		//inventoryManager = new UIInventoryManager(Inventory.instance, EquipmentManager.instance, 100, 100);

		
		IItemAction increaseHP10 = new FillUpHealthPointsAction(player, 10);

		Item sword = new EquipItem(ItemCategory.EQUIPMENT, ItemType.WEAPON, "Einfaches Schwert", AssetsLoader.spritesheetInventory.getImageByIndex(37));
		sword.addBoundingBox(new BoundingBox(300, 300, 16, 16));
		sword.setStackable(false);
		sword.setArmorValue(1);
		sword.setDamageValue(4);

		Item chest = new EquipItem(ItemCategory.EQUIPMENT, ItemType.CHEST, "Einfacher Brustpanzer", AssetsLoader.spritesheetInventory.getImageByIndex(5));
		chest.addBoundingBox(new BoundingBox(120, 210, 16, 16));
		chest.setStackable(false);
		chest.setArmorValue(5);
		chest.setDamageValue(2);

		Item legs = new EquipItem(ItemCategory.EQUIPMENT, ItemType.LEGS, "Einfache Hose", AssetsLoader.spritesheetInventory.getImageByIndex(12));
		legs.addBoundingBox(new BoundingBox(400, 320, 16, 16));
		legs.setStackable(false);
		legs.setArmorValue(3);
		legs.setDamageValue(1);

		Item feets = new EquipItem(ItemCategory.EQUIPMENT, ItemType.FEETS, "Einfache Stiefel", AssetsLoader.spritesheetInventory.getImageByIndex(13));
		feets.addBoundingBox(new BoundingBox(170, 520, 16, 16));
		feets.setStackable(false);
		feets.setArmorValue(5);
		feets.setDamageValue(2);

		Item head = new EquipItem(ItemCategory.EQUIPMENT, ItemType.HEAD, "Einfache Helm", AssetsLoader.spritesheetInventory.getImageByIndex(4));
		head.addBoundingBox(new BoundingBox(70, 320, 16, 16));
		head.setStackable(false);
		head.setArmorValue(5);
		head.setDamageValue(3);

		Item shield = new EquipItem(ItemCategory.EQUIPMENT, ItemType.SHIELD, "Einfache Stiefel", AssetsLoader.spritesheetInventory.getImageByIndex(20));
		shield.addBoundingBox(new BoundingBox(20, 70, 16, 16));
		shield.setStackable(false);
		shield.setArmorValue(5);
		shield.setDamageValue(2);

		Item potion = new UseItem(ItemCategory.OBJECT, ItemType.POTION, "Heiltrank (1)", AssetsLoader.spritesheetInventory.getImageByIndex(0), increaseHP10);
		potion.addBoundingBox(new BoundingBox(190, 60, 16, 16));
		potion.setStackable(true);

		Item potion2 = new UseItem(ItemCategory.OBJECT, ItemType.POTION, "Heiltrank (1)", AssetsLoader.spritesheetInventory.getImageByIndex(0), increaseHP10);
		potion2.addBoundingBox(new BoundingBox(290, 160, 16, 16));
		potion2.setStackable(true);
		potion2.setQuantity(10);

		Item poisson = new UseItem(ItemCategory.OBJECT, ItemType.POTION, "GIFT (1)", AssetsLoader.spritesheetInventory.getImageByIndex(1), new FillUpHealthPointsAction(player, -30));
		poisson.addBoundingBox(new BoundingBox(340, 260, 16, 16));
		poisson.setStackable(true);

		// create item manager
		
		itemManager.addItem(sword);
		itemManager.addItem(chest);
		itemManager.addItem(potion);
		itemManager.addItem(potion2);
		itemManager.addItem(poisson);
		itemManager.addItem(legs);
		itemManager.addItem(feets);
		itemManager.addItem(head);
		itemManager.addItem(shield);
		
		// ---- INVENTORY
		inventoryManager.add(sword);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion);
		inventoryManager.add(potion2);
		inventoryManager.add(potion2);
//		inventoryManager.add(potion);
		
		inventoryManager.add(feets);
		
		inventoryManager.add(poisson);
		inventoryManager.add(poisson);
		
	}

	/*
	 * GETTER FOR MODEL OBJECTS
	 */

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

	public ItemManager getItemManager() {
		return itemManager;
	}

	public UIInfoPane getUIInfoPane() {
		return infoPane;
	}
	
	public EntitySystem getEntitySystem() {
		return entitySystem;
	}
	
	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public UIHud getUIHud() {
		return hud;
	}

	@Override
	public UIInventory getUIInventory() {
		return uiInventory;
	}

}
