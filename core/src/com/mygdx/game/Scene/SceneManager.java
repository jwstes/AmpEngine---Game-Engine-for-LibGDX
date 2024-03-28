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
	private GameOverScene displayFact;
	
    
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private CollisionManager collisionManager;
    private GameOverScene gameOverScene;
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
    private String correctAnswer;
    private boolean showWrongAnswerMessage = false;
    private float wrongAnswerMessageTimer = 0;
    private final float MESSAGE_DISPLAY_TIME = 3;
    private boolean displayingCutscene = false;
    private String cutsceneMessage;
    
    private int globalBossHP = 100;
    private long factDisplayStartTime = 0; // Tracks when the fact started being displayed
    private int randomFactIndex;

    

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
		
	    lastFactDisplayTime = System.currentTimeMillis(); // Initialize lastFactDisplayTime

	    
		gameOver = false;
		entityManager = new EntityManager();
		allScenes = new Array<Scene>();

		// Load dashboard assets
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
		lastEntityUpdate = System.currentTimeMillis();
		animatedTextureID = 0;
		//outputManager = new OutputManager();
		//Instead of creating a new instance, retrieve the Singleton instance
        outputManager = OutputManager.getInstance();
		
		
		drawQuiz = 0;
		drawMenu = 1;
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
	
	
	public void setDisplayingCutscene(boolean b) {
		displayingCutscene = b;
	}
	public boolean getDisplayingCutscene() {
		return displayingCutscene;
	}
	
	public void setCutsceneMessage(String s) {
		cutsceneMessage = s;
	}
	
	public int getGlobalBossHP() {
		return globalBossHP;
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
		System.out.println("In SceneManager a function to reset Dashboard Health has been called");
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


	// updateScene handles animation texture changes, checks for collision, move AI,
	public void updateScene() {
	    long currentTime = System.currentTimeMillis();

		if (System.currentTimeMillis() >= (lastEntityUpdate + 80)) {
			if(animatedTextureID < 4) {
				animatedTextureID++;
			}
			else {
				animatedTextureID = 0;
			}
			//checkCollision(); //instead of calling here, shift to AmpEngine so other details can be modified
	        
			lastEntityUpdate = System.currentTimeMillis();
		}

		// time taken before movable entity moves 
        if (currentTime >= (lastAIUpdate + 800))
        {
            Array<PlayerEntity> playerEntities = entityManager.getAllPEntity();

              for (AIManager ai : entityManager.getAllAIMEntity()) {
            	  if (isEntityName("meteor")) {
            	        if (ai.getIsMovable() && !ai.getIsKillable() && !ai.getIsAlive() && !ai.getIsCollidable()) {
            	            ai.moveEntityDiagonallyRight();
            	            ai.updateCollider(ai.getPosX(), ai.getPosY(), 100, 100);
            	        }
            	    }
                      
//                      if (animatedTextureID == 2) 
//                      {   	
//                      ai.updateCollider(ai.getPosX(), ai.getPosY(), 32, 48);
//                      }
//                      else {
//	                        ai.updateCollider(ai.getPosX(), ai.getPosY(), 32, 12);
//                      }
                   
                  
            	  
                 if (ai.getIsMovable() && ai.getIsKillable() && ai.getIsHostile() && isEntityName("enemy")) {
                	  
                     ai.chasePEntity(playerEntities);
                  }
                  else if (ai.getIsAlive() && ai.getIsMovable() && ai.getIsHostile() == false && isEntityName("facts_npc")) 
                  {
                	  
                      ai.moveEntityRandomly();
                      ai.updateCollider(ai.getPosX(), ai.getPosY(), 32, 32);
                   }
                
            
                  
              }


        }
       
        
        
        
        
	}
	
	
	private boolean isEntityName(String desiredEntityName) {
	    List<String> entityNames = currentScene.GetEntityName(); 
	    for (String name : entityNames) {
	        if (desiredEntityName.equals(name)) {
	            return true; // Found the desired entity name
	        }
	    }
	    return false; // Desired entity name not found
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
	        lastFactDisplayTime = System.currentTimeMillis(); // Update lastFactDisplayTime to the current time
	    }
	}

	// Method to get a random fact from the list of facts
		private String getRandomFact(List<String> facts) {
			Random rand = new Random();
		    int randomIndex = rand.nextInt(facts.size());
		    return facts.get(randomIndex);
		  
		}
		
		private long lastFactTime = 0; // Time when the last fact was shown
		private final long FACT_DELAY = 5000; // Delay in milliseconds (5 seconds)
		
		public void updateOrRender() {
		    long currentTime = System.currentTimeMillis();

		    // Check if it's time to display a new fact
		    if (!textDisplayed && currentTime - lastFactTime >= FACT_DELAY) {
		        List<String> facts = allScenes.get(currentSceneID).GetAllFacts(); // Adjust accordingly to get your facts
		        String newFact = getRandomFact(facts);
		        if (newFact != null) {
		            DisplayText2(newFact); // Your method to display the fact
		            textDisplayed = true; // Set to prevent new facts from being fetched immediately
		            lastFactTime = currentTime; // Reset the timer
		        }
		    }

		    // Your existing logic to hide the fact or update the game state
		    // For example, to auto-hide the fact after some time:
		    if (textDisplayed && currentTime - lastFactTime >= FACT_DELAY) {
		        // Hide the fact
		        textDisplayed = false;
		        // Optionally, reset lastFactTime if you want a delay before the next fact can be shown
		        lastFactTime = currentTime; // Uncomment to add delay before next fact can be shown again
		    }
		}
		
	public void checkCollision(int sceneID) {
	    long currentTime = System.currentTimeMillis();
	    boolean previousTextDisplayed = textDisplayed;
	    collisionManager.checkPlayerCollisions();
	    
	    Entity collidedEntity = collisionManager.checkPlayerCollisions();
	    
	    
	    
	    
	    if (collidedEntity != null && collidedEntity.getEntityType().equals("adversarial") && collidedEntity.getIsHostile()) {
	        // If collision occurs with an adversarial entity, reduce player's health
	        if (currentTime - lastHealthReductionTime > 1000) {
	            dashboard.reduceHealth(1); // Reduce player's health
	            lastHealthReductionTime = currentTime; // Update the last reduction time to now
	        }
	    }
	    if (collidedEntity != null && collidedEntity.getEntityType().equals("npc")) {
	        // If collision occurs with an NPC entity
	        if (!textDisplayed) {
	        	setDrawQuiz(1);
	        }
	    } else {
	        textDisplayed = false; // Reset textDisplayed flag if no collision with NPC
	    }
	    
	    
		    
	    	if (collidedEntity != null && collidedEntity.getEntityType().equals("adversarial") && !collidedEntity.getIsHostile()) 
	    	{
	            if (currentTime - lastFactDisplayTime >= 5000) { // 5 seconds have passed since the last fact was displayed
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
            gameOverScene = new GameOverScene(gameOverTexture);
        }

        clearScreen();
        gameOverScene.render(batch, "");
    }
	
	public void DisplayText2(String fact) {
	    if (displayFact == null) {
	        displayFact = new GameOverScene(fact); // Create a GameOverScene with the provided fact
	    }
	    displayFact.render(batch, fact);
	    textDisplayed = true;
	}
	
	
	public void drawPopQuiz(int currentSceneID, boolean menuMode) {
	    List<Map<String, Object>> questionsList = allScenes.get(currentSceneID).GetAllQuestions();
//	    Map<String, Object> currentQuestion = questionsList.get(0);
	    Map<String, Object> currentQuestion = questionsList.get(randomFactIndex);

	    String questionText = (String) currentQuestion.get("question");
	    List<String> answers = (List<String>) currentQuestion.get("answers");
	    correctAnswer = (String) currentQuestion.get("real");

	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    float mouseX = Gdx.input.getX();
	    float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

	    batch.begin();
	    
	    
	    if (menuMode) {
	        // Draw menu text
	        // Centering and scaling for menu text
	        // Draw background image
	        Texture backgroundImage = new Texture(Gdx.files.internal("menu_BG.png"));
	        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        
	        // Draw header image
	        Texture headerImage = new Texture(Gdx.files.internal("menu_header.png"));
	        batch.draw(headerImage, 150, Gdx.graphics.getHeight() - headerImage.getHeight() - 200);

//	        font.setColor(1, 1, 1, 1);
//	        font.getData().setScale(4.0f); // Adjust the scale as needed
//	        GlyphLayout layout = new GlyphLayout(font, "Menu:");
//	        
//	        // Reset scale for other text
//	        font.getData().setScale(10.0f);
	      
	        float startGameX = 400; // Adjust this value as needed
	        float startGameY = 300; // Adjust this value as needed
	  
	        float endGameX = 400; 
	        float endGameY = 150;
	        
	        // Load the "Start Game" image
	        Texture startGameImage = new Texture(Gdx.files.internal("menu_start.png"));
	    	
	        
	        batch.draw(startGameImage, startGameX, startGameY);
	        

		    menuChoiceA = new Rectangle(startGameX, startGameY, startGameImage.getWidth(), startGameImage.getHeight());
		    
	        // Update menuChoiceA coordinates to match "Start Game" image
	        menuChoiceA.setPosition(startGameX, startGameY);
		     // Assuming startGameImage is not null
		    menuChoiceA.setSize(startGameImage.getWidth(), startGameImage.getHeight());
	        
		    
		    // Draw header image
	        Texture exitGameImage = new Texture(Gdx.files.internal("menu_exit.png"));
	        
	     
	        
	        batch.draw(exitGameImage, endGameX, endGameY);
	        
		    menuChoiceB = new Rectangle(endGameX, endGameY, exitGameImage.getWidth(), exitGameImage.getHeight());

	        menuChoiceB.setPosition(endGameX, endGameY);
		    menuChoiceB.setSize(exitGameImage.getWidth(), exitGameImage.getHeight());
	        
		    
	    }
	    
	    
	    else 
	    {
	    	Texture backgroundImage = new Texture(Gdx.files.internal("questionBG.png"));
	        batch.draw(backgroundImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	        
	        font.setColor(1, 1, 1, 1);
	        
	        // Centering text for question and choices
	        GlyphLayout layout = new GlyphLayout(font, "Question: " + questionText);
	        float xPosition = (Gdx.graphics.getWidth() - layout.width) / 2;
	        font.draw(batch, "Question: " + questionText, xPosition, Gdx.graphics.getHeight() / 2);
	        
	        font.setColor(getColorForChoice(choiceA, mouseX, mouseY));
	        font.draw(batch, answers.get(0), choiceA.x, choiceA.y + choiceA.height);
	        
	        font.setColor(getColorForChoice(choiceB, mouseX, mouseY));
	        font.draw(batch, answers.get(1), choiceB.x, choiceB.y + choiceB.height);
	        
	        font.setColor(getColorForChoice(choiceC, mouseX, mouseY));
	        font.draw(batch, answers.get(2), choiceC.x, choiceC.y + choiceC.height);
	        
	        font.setColor(getColorForChoice(choiceD, mouseX, mouseY));
	        font.draw(batch, answers.get(3), choiceD.x, choiceD.y + choiceD.height);
	        
	    	
	    }

	    if (showWrongAnswerMessage) {
	        wrongAnswerMessageTimer -= Gdx.graphics.getDeltaTime();
	        if (wrongAnswerMessageTimer <= 0) {
	            showWrongAnswerMessage = false;
	        } else {
	            font.setColor(com.badlogic.gdx.graphics.Color.RED);
	            font.draw(batch, "Wrong Answer!", 100, 50);
	        }
	    }

	    batch.end();
	}


	private com.badlogic.gdx.graphics.Color getColorForChoice(Rectangle choice, float mouseX, float mouseY) {
	    return choice.contains(mouseX, mouseY) ? com.badlogic.gdx.graphics.Color.RED : com.badlogic.gdx.graphics.Color.WHITE;
	}


	public int handleInput(boolean menuMode) {
	    if (Gdx.input.justTouched()) {
	        float x = Gdx.input.getX();
	        float y = Gdx.graphics.getHeight() - Gdx.input.getY();
	        
	        // Check if the game is in menu mode
	        if (menuMode) {
	            if (menuChoiceA.contains(x, y)) {
	                return 1; // Start Game
	            } else if (menuChoiceB.contains(x, y)) {
	                return 2; // Exit Game
	            }
	        } else {
	            // Handle input for game mode (questions)
	            if (choiceA.contains(x, y)) {
	                return checkAnswer("A");
	            } else if (choiceB.contains(x, y)) {
	                return checkAnswer("B");
	            } else if (choiceC.contains(x, y)) {
	                return checkAnswer("C");
	            } else if (choiceD.contains(x, y)) {
	                return checkAnswer("D");
	            }
	        }
	    }
	    return -1; // Default return if no action is triggered
	}
	
	private int checkAnswer(String selectedAnswer) {
	    if (selectedAnswer.equals(correctAnswer)) {
	        drawQuiz = 0;
	        showWrongAnswerMessage = false;
	        wrongAnswerMessageTimer = 0;
	        return 1;
	    } else {
	        System.out.println("Wrong Answer!");
	        showWrongAnswerMessage = true;
	        wrongAnswerMessageTimer = MESSAGE_DISPLAY_TIME;
	        return 0;
	    }
	}
	
	
	public void renderDashboard() {
		dashboard.render(batch);
	}
	
	public void displayCutscene() {
	    batch.begin();
	    BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
	    font.getData().setScale(0.7f);
	    
	    String loremIpsum = cutsceneMessage;
	    String pressEnter = "Press SPACE to continue";
	    
	    GlyphLayout loremLayout = new GlyphLayout(font, loremIpsum);
	    GlyphLayout enterLayout = new GlyphLayout(font, pressEnter);
	    
	    float loremX = (Gdx.graphics.getWidth() - loremLayout.width) / 2;
	    float loremY = (Gdx.graphics.getHeight() + loremLayout.height) / 2;
	    float enterX = (Gdx.graphics.getWidth() - enterLayout.width) / 2;
	    float enterY = (Gdx.graphics.getHeight() / 4f) + enterLayout.height / 2;
	    
	    font.draw(batch, loremLayout, loremX, loremY);
	    font.draw(batch, enterLayout, enterX, enterY);
	    batch.end();
	}
	
	
	public void decreaseBossHP(int baseDecrement, int basePenalty, int wrongAttempts) {
		int penalty = basePenalty * wrongAttempts;
		globalBossHP = globalBossHP - (baseDecrement - penalty);
		
	}
	public void increaseBossHP(int baseIncrement) {
		globalBossHP = globalBossHP + (baseIncrement);
	}
	
	public void resetBossHP() {
		globalBossHP = 100; //reset bossHP
	}
	
	
	public void autoSetRandomFactIndex(int currentSceneID) {
		Random r = new Random();
		List<String> facts = allScenes.get(currentSceneID).GetAllFacts();
		int max = facts.size();
		randomFactIndex = (r.nextInt((max-1) - 0 + 1) + 0);
	}
	
	
	
}

