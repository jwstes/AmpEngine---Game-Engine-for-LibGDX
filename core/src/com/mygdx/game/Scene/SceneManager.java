package com.mygdx.game.Scene;

import com.mygdx.AiControlled.AIManager;
import com.mygdx.AiControlled.NPCEntity;
import com.mygdx.game.*;
import com.mygdx.game.Entities.*;
import com.mygdx.game.IO.OutputManager;
import com.mygdx.game.IO.PlayerControl;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


import com.badlogic.gdx.utils.Array;


public class SceneManager{
	// INSTANCES AND VARIABLES
	Rectangle worldBounds = new Rectangle(1, 1, 1279, 718);
	private Array<Scene> allScenes;
	private Texture developerLogo;
    private boolean gameOver;
    private long lastAIUpdate;

	private long lastEntityUpdate;
	private int animatedTextureID;
	private int currentSceneID;
	
	private List<String> ranFac;
	long lastHealthReductionTime = 0;
	private boolean textDisplayed = false;
	private boolean factsDisplayed = false;
	private SceneOverlay displayFact;
	
    
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private CollisionManager collisionManager;
    private SceneOverlay gameOverScene;
    private PlayerEntity player;
    public PlayerControl playerControl;
    public OutputManager outputManager;
    private SpriteBatch batch;
    private Scene currentScene;
    public EntityManager entityManager;
	private Dashboard dashboard;
    
	
	private int drawMenu;
	private int drawQuiz;
    private BitmapFont font;
    private Rectangle choiceA, choiceB, choiceC, choiceD;
    private Rectangle menuChoiceA, menuChoiceB, menuChoiceC, menuChoiceD;
    private Rectangle settingCloseChoice, musicChoice;
    private String correctAnswer;
    private boolean showWrongAnswerMessage = false;
    private float wrongAnswerMessageTimer = 0;
    private final float MESSAGE_DISPLAY_TIME = 3;
    private boolean displayingCutscene = false;
    private String cutsceneMessage;
    
    
    private long factDisplayStartTime = 0; // Tracks when the fact started being displayed
    private int randomFactIndex;
    
	private int drawSettings;
    private boolean showingSettings = false;
    private boolean isMuted = false; // Add this flag

    

	// Things carried out in SceneManager Constructor
	// New Entity Manager is made
	// Dashboard Healthbar is made from function getDashboard>dashboard class>dashboardManagerClass
	// health sprite is the player sprite, font from Libgdx, maxHealth define in variable in constructor
	// initialize a spriteBatch
	// Initialize a new Scene Object. Use ParseFromJSON function to store all entities into the arrayLists in Scene Class.
	// set LastEntityUpdate property to current time.
	// initialize an output manager (controls sound)
	// Constructor
	public SceneManager(Array<String> sceneJSONArr, String healthbarTex) {


	    lastFactDisplayTime = getCurrentTime(); // Initialize lastFactDisplayTime
		lastEntityUpdate = getCurrentTime();
		gameOver = false;
		entityManager = new EntityManager();
		allScenes = new Array<Scene>();

		// Dash Board Feature
		Texture healthSprite = new Texture(healthbarTex);
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
		int maxHealth = 3;
		// Create or get the dashboard
		dashboard = getDashboard(maxHealth, font, healthSprite);

		batch = new SpriteBatch();

		for(String sceneJSON : sceneJSONArr) {
			Scene s = new Scene();
			s.ParseFromJSON(sceneJSON);
			allScenes.add(s);
		}

		animatedTextureID = 0;
		//outputManager = new OutputManager();
		//Instead of creating a new instance, retrieve the Singleton instance
        outputManager = OutputManager.getInstance();

		
		
		drawQuiz = 0;
		drawMenu = 1;
		drawSettings = 2;
		
		this.font = new BitmapFont(Gdx.files.internal("font.fnt"));
		batch = new SpriteBatch();
		choiceA = new Rectangle(100, 230, 200, 30);
		choiceB = new Rectangle(100, 180, 200, 30);
		choiceC = new Rectangle(100, 130, 200, 30);
		choiceD = new Rectangle(100, 80, 200, 30);
		
		menuChoiceA = new Rectangle(100, 180, 200, 30);
		menuChoiceB = new Rectangle(100, 180, 200, 30);
		menuChoiceC = new Rectangle(100, 130, 200, 30);
		menuChoiceD = new Rectangle(100, 80, 200, 30);
		
		choiceB = new Rectangle(100, 180, 200, 30);
		
		musicChoice = new Rectangle(100, 180, 200, 30);
	}

	// Property Getter Setters

	public void setPlayerEntity(){
		player = entityManager.getAllPEntity().get(0);
	}

	public Array<Scene> getAllScenes(){
		return allScenes;
	}
	public CollisionManager getCollisionManager() {
		return collisionManager;
	}
	public void setPlayerControl(PlayerControl p) {
		playerControl = p;
	}
	public boolean getGameOverStatus(){
		return this.gameOver;
	}

	public void setGameOverStatus(boolean status){
		this.gameOver = status;
	}

	public long getLastEntityUpdate(){return lastEntityUpdate;}
	public void setLastEntityUpdate(long newtime){lastEntityUpdate = newtime;}
	
	public int getDrawQuiz() {
		return drawQuiz;
	}
	public void setDrawQuiz(int v) {
		drawQuiz = v;
	}
	public int getDrawMenu() {
		return drawMenu;
	}
	public void setDrawMenu(int v) {
		drawMenu = v;
	}
	
	public int getDrawSettings() {
		return drawSettings;
	}
	public void setDrawSettings(int s) {
	    // Reset other states as necessary
	    drawQuiz = 0;
	    drawMenu = 0;
	    drawSettings = s;
	}
	
	  // Getter for showingSettings
    public boolean isShowingSettings() {
        return showingSettings;
    }
    
    // Setter for showingSettings
    public void setShowingSettings(boolean showingSettings) {
        this.showingSettings = showingSettings;
    }

	public long getCurrentTime(){
		return System.currentTimeMillis();
	}


	
	
	public void setDisplayingCutscene(boolean b) {
		displayingCutscene = b;
	}
	public boolean getDisplayingCutscene() {
		return displayingCutscene;
	}
	
	public void setCutsceneMessage(String s) {
		cutsceneMessage = s;
	}
	
	
	
	
	
	// METHODS
	public void clearScreen() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public float[] getWindowSize() {
		float[] size = {
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
		};
	    return size;
	}
	
	
	

	public Dashboard getDashboard(int maxHealth, BitmapFont font, Texture healthSprite) {
		if (dashboard == null) {
			dashboard = new Dashboard(maxHealth, font, healthSprite);
			dashboard.setSceneManager(this);
		}
		return dashboard;
	}

	public Dashboard getDashboard(){
		return dashboard;
	}
	

	public void resetDashboard(){
		dashboard.resetDashboard(); // resets the currentHealth, sprite display and Time in dashboard class using Dashboard Manager
		//System.out.println("In SceneManager a function to reset Dashboard Health has been called");
	}



	public void beginBatch() {
		batch.begin();
	}
	public void endBatch() {
		batch.end();
	}
	public SpriteBatch getBatch() {
		return this.batch;
	}

	
	
	public void populateScene(int sceneID) {
		Scene selectedScene = allScenes.get(sceneID);
		entityManager.createEntities(selectedScene);
	}
	public void unloadScene() {
		entityManager.deleteEntities();
		clearScreen();
	}
	public void loadScene(int sceneID, boolean dashboardOnly) {
		if(dashboardOnly == true) {
			beginBatch();
			renderDashboard();
			endBatch();
			return;
		}

		if (gameOver) {
	        loadGameOverScene();
	        return; // Exit the method to ensure only the GameOver scene is rendered
	    }
		Scene selectedScene = allScenes.get(sceneID);
		//entityManager.createEntities(selectedScene);
		
		Texture backgroundTexture = selectedScene.GetBackgroundTexture();
		loadBackground(backgroundTexture);
		
		
		Array<AdversarialEntity> allAdversarialEntity = entityManager.getAllAdEntity();
		Array<StaticEntity> allStaticEntity  = entityManager.getAllSEntity();
		Array<PlayerEntity> allPlayerEntity = entityManager.getAllPEntity();
		Array<AIManager> allAIMEntity = entityManager.getAllAIMEntity();

		//
		Array<NPCEntity> allNPCEntity = entityManager.getAllNPCEntity();
		
		
		beginBatch();
		for(AdversarialEntity e : allAdversarialEntity) {
			e.draw(batch);
		}
		for(StaticEntity e : allStaticEntity) {
			e.draw(batch);
			//System.out.print(e.getTexture());
		}
		for(PlayerEntity e : allPlayerEntity) {
			e.draw(batch);
		}
		for(AIManager e : allAIMEntity) {
			e.setTexture(e.getTextures()[animatedTextureID]);
			e.draw(batch);
		}

		for(NPCEntity e : allNPCEntity) {
			e.draw(batch);
		}
		
		
		renderDashboard();
		
		currentScene = selectedScene;
		currentSceneID = sceneID;
		endBatch();
	}
	public void loadBackground(Texture backgroundTexture) {
		batch.begin();
		batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
	}
	
	
	// Add instance variables to keep track of the current fact index and the time when the last fact was displayed
	private int currentFactIndex = 0;
	private long lastFactDisplayTime = 0;
	
	public void displayNextFact(int sceneID) {
	    List<String> facts = allScenes.get(sceneID).GetAllFacts(); // Retrieve all facts
	    if (!facts.isEmpty()) {
	        String fact = facts.get(currentFactIndex); // Get the current fact based on currentFactIndex
	        DisplayText2(fact); // Display the current fact
	        currentFactIndex = (currentFactIndex + 1) % facts.size(); // Update currentFactIndex to the next fact
	        lastFactDisplayTime = getCurrentTime(); // Update lastFactDisplayTime to the current time
	    }
	}

	// Method to get a random fact from the list of facts (Remove Maybe, not needed in update & render)
	private String getRandomFact(List<String> facts) {
		Random rand = new Random();
		int randomIndex = rand.nextInt(facts.size());
		return facts.get(randomIndex);

	}
		
	private long lastFactTime = 0; // Time when the last fact was shown
	private final long FACT_DELAY = 3000; // Delay in milliseconds (5 seconds)
		
	public void checkCollision(int sceneID) {
	    boolean previousTextDisplayed = textDisplayed;
	    collisionManager.checkPlayerCollisions();
	    
	    Entity collidedEntity = collisionManager.checkPlayerCollisions();
	    //System.out.print(collidedEntity);
	    
	    
	    
	    
	    if (collidedEntity != null && collidedEntity.getEntityType().equals("adversarial") && collidedEntity.getIsHostile()) {
	        // If collision occurs with an adversarial entity, reduce player's health
	        if (getCurrentTime() - lastHealthReductionTime > 1000) {
	            dashboard.reduceHealth(1); // Reduce player's health
	            lastHealthReductionTime = getCurrentTime(); // Update the last reduction time to now
	        }
	    }
	    if (collidedEntity != null && collidedEntity.getEntityType().equals("npc")) {
	    	//System.out.print("TOUCHED");
	        // If collision occurs with an NPC entity
	        if (!textDisplayed) {
	        	setDrawQuiz(1);
	        	
	        }
	    } else {
	        textDisplayed = false; // Reset textDisplayed flag if no collision with NPC
	    }
	    
	    
		    
	    	if (collidedEntity != null && collidedEntity.getEntityType().equals("adversarial") && !collidedEntity.getIsHostile()) 
	    	{
	            if (getCurrentTime() - lastFactDisplayTime >= 5000) { // 5 seconds have passed since the last fact was displayed
		        	List<String> facts = allScenes.get(sceneID).GetAllFacts(); // Retrieve all facts	

//			        String fact = getRandomFact(facts);
		        	String fact = facts.get(randomFactIndex);

	    	        DisplayText2(fact); // Display the current fact
	            }
	            
	            
	            
	    	
	    	}
	  
	}
    
    
    public void drawCollider() {
    	if (!gameOver) {
    		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        	shapeRenderer.setColor(Color.RED);

        	for (PlayerEntity player : entityManager.getAllPEntity()) {
            	shapeRenderer.rect(player.getRec().x, player.getRec().y, player.getRec().width, player.getRec().height);
        	}
        	for (StaticEntity staticEntity : entityManager.getAllSEntity()) {
            	shapeRenderer.rect(staticEntity.getRec().x, staticEntity.getRec().y, staticEntity.getRec().width, staticEntity.getRec().height);
        	}
        	for (AdversarialEntity adversary : entityManager.getAllAdEntity()) {
            	shapeRenderer.rect(adversary.getRec().x, adversary.getRec().y, adversary.getRec().width, adversary.getRec().height);
        	}
        	for (AIManager ai : entityManager.getAllAIMEntity()) {
        	 	shapeRenderer.rect(ai.getRec().x, ai.getRec().y, ai.getRec().width, ai.getRec().height);
        	}

			for (NPCEntity npc : entityManager.getAllNPCEntity()) {
				shapeRenderer.rect(npc.getRec().x, npc.getRec().y, npc.getRec().width, npc.getRec().height);
			}
        	//quadTree.draw(shapeRenderer); // Draw the entire QuadTree
			
			
			shapeRenderer.end(); // End shape drawing

    	}
    }
	


	public int displayMenu(String[] menuItemText, Runnable[] actions, int selectedMenuItemIndex) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    SpriteBatch spriteBatch = new SpriteBatch();
	    ShapeRenderer shapeRenderer = new ShapeRenderer();
	    BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
	    OrthographicCamera camera = new OrthographicCamera();
	    camera.setToOrtho(false);

	    shapeRenderer.setProjectionMatrix(camera.combined);
	    spriteBatch.setProjectionMatrix(camera.combined);

	    float menuItemWidth = 300;
	    float menuItemHeight = 100;
	    float menuItemSpacing = 20;

	    float totalHeight = menuItemText.length * menuItemHeight + (menuItemText.length - 1) * menuItemSpacing;

	    float startY = (Gdx.graphics.getHeight() - totalHeight) / 2;
	    
	    
	    if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedMenuItemIndex = Math.max(0, selectedMenuItemIndex - 1);
        }
	    else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedMenuItemIndex = Math.min(menuItemText.length - 1, selectedMenuItemIndex + 1);
        }
	    else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedMenuItemIndex >= 0 && selectedMenuItemIndex < actions.length) {
                actions[selectedMenuItemIndex].run();
            }
        }
	    
	    
	    for (int i = 0; i < menuItemText.length; i++) {
	        float rectangleX = (Gdx.graphics.getWidth() - menuItemWidth) / 2;
	        float rectangleY = startY + i * (menuItemHeight + menuItemSpacing);

	        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	        
	        
	        if (i == selectedMenuItemIndex) {
                shapeRenderer.setColor(Color.RED);
            } else {
                shapeRenderer.setColor(1, 1, 1, 1);
            }
	        
	        shapeRenderer.rect(rectangleX, rectangleY, menuItemWidth, menuItemHeight);
	        shapeRenderer.end();

	        spriteBatch.begin();

	        font.setColor(0, 0, 0, 1);

	        String currentMenuItemText = menuItemText[i];

	        GlyphLayout glyphLayout = new GlyphLayout();
	        glyphLayout.setText(font, currentMenuItemText);

	        float textX = rectangleX + (menuItemWidth - glyphLayout.width) / 2;
	        float textY = rectangleY + (menuItemHeight + glyphLayout.height) / 2;

	        font.draw(spriteBatch, currentMenuItemText, textX, textY);

	        spriteBatch.end();
	    }
	    return selectedMenuItemIndex;
	}

	public void initializeCollisionManager() {
		collisionManager = new CollisionManager(entityManager.getAllPEntity(), entityManager.getAllSEntity(), entityManager.getAllAdEntity(),entityManager.getAllAIMEntity(),entityManager.getAllNPCEntity());
    }

	public void loadGameOverScene() {
        if (!gameOver) {
            gameOver = true;
        }

        // Load the game over scene
        if (gameOverScene == null) {
            Texture gameOverTexture = new Texture("gameover.png");
            gameOverScene = new SceneOverlay(gameOverTexture);
        }

        clearScreen();
        gameOverScene.render(batch, "");
    }
	
	public void DisplayText2(String fact) {
	    if (displayFact == null) {
	        displayFact = new SceneOverlay(fact); // Create a GameOverScene with the provided fact
	    }
	    displayFact.render(batch, fact);
	    textDisplayed = true;
	}
	
	


	public com.badlogic.gdx.graphics.Color getColorForChoice(Rectangle choice, float mouseX, float mouseY) {
	    return choice.contains(mouseX, mouseY) ? com.badlogic.gdx.graphics.Color.RED : com.badlogic.gdx.graphics.Color.WHITE;
	}


	

	
	
	public void renderDashboard() {
		dashboard.render(batch);
	}
	

	
	// New Get Sets
	public int getAnimatedTextureID(){
		return animatedTextureID;
	}
	public void setAnimatedTextureID(int id){
		animatedTextureID = id;
	}
	public long getLastAIUpdate(){return lastAIUpdate;}
	public void setLastAIUpdate(long newTime){lastAIUpdate = newTime;}
	public Scene exposeScene(){
		return currentScene;
	}

	public int getRandomFactIndex(){
		return randomFactIndex;
	}
	public void setRandomFactIndex(int index){
		randomFactIndex = index;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String answer){
		correctAnswer = answer;
	}

	public Rectangle getMenuChoiceA(){
		return menuChoiceA;
	}
	public void setMenuChoiceA(Rectangle choice){
		menuChoiceA = choice;
	}
	public Rectangle getMenuChoiceB(){
		return menuChoiceB;
	}
	public void setMenuChoiceB(Rectangle choice){
		menuChoiceB = choice;
	}
	public Rectangle getMenuChoiceC(){
		return menuChoiceC;
	}
	public void setMenuChoiceC(Rectangle choice){
		menuChoiceC = choice;
	}
	public Rectangle getMenuChoiceD(){
		return menuChoiceD;
	}
	public void setMenuChoiceD(Rectangle choice){
		menuChoiceD = choice;
	}

	public Rectangle getChoiceA(){
		return choiceA;
	}
	public void setChoiceA(Rectangle choice){
		choiceA = choice;
	}

	public Rectangle getChoiceB(){
		return choiceB;
	}
	public void setChoiceB(Rectangle choice){
		choiceB = choice;
	}
	public Rectangle getChoiceC(){
		return choiceC;
	}
	public void setChoiceC(Rectangle choice){
		choiceC = choice;
	}
	public Rectangle getChoiceD(){
		return choiceD;
	}
	public void setChoiceD(Rectangle choice){
		choiceD = choice;
	}
	public boolean getShowWrongAnswerMessage(){
		return showWrongAnswerMessage;
	}

	public void setShowWrongAnswerMessage(boolean value){
		showWrongAnswerMessage = value;
	}
	public float getWrongAnswerMessageTimer(){
		return wrongAnswerMessageTimer;
	}

	public void setWrongAnswerMessageTimer(float timerValue){
		wrongAnswerMessageTimer =  timerValue;
	}
	public boolean getShowingSettings(){
		return showingSettings;
	}


	public Rectangle getSettingCloseChoice(){
		return settingCloseChoice;
	}
	public void setSettingCloseChoice(Rectangle choice){
		settingCloseChoice = choice;
	}

	public OutputManager getOutputManager(){
		return outputManager;
	}

	public void setMusicChoice(Rectangle choice){
		musicChoice = choice;
	}
	public Rectangle getMusicChoice(){
		return musicChoice;
	}

	public float getMESSAGE_DISPLAY_TIME(){
		return MESSAGE_DISPLAY_TIME;
	}

	public String getCutsceneMessage() {
		return cutsceneMessage;
	}




}

