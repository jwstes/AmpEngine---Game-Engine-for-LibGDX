package com.mygdx.game;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    
	private int drawQuiz;
    private BitmapFont font;
    private Rectangle choiceA, choiceB, choiceC, choiceD;
    private String correctAnswer = "A";
    private boolean showWrongAnswerMessage = false;
    private float wrongAnswerMessageTimer = 0;
    private final float MESSAGE_DISPLAY_TIME = 3;

	
    
	
    

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
		gameOver = false;
		entityManager = new EntityManager();
		allScenes = new Array<Scene>();

		// Load dashboard assets
		Texture healthSprite = new Texture(healthbarTex);
		BitmapFont font = new BitmapFont();
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
		outputManager = new OutputManager();
		
		
		drawQuiz = 0;
		this.font = new BitmapFont();
		batch = new SpriteBatch();
		choiceA = new Rectangle(100, 230, 200, 30);
		choiceB = new Rectangle(100, 180, 200, 30);
		choiceC = new Rectangle(100, 130, 200, 30);
		choiceD = new Rectangle(100, 80, 200, 30);
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
			checkCollision(); //instead of calling here, shift to AmpEngine so other details can be modified
	        
			lastEntityUpdate = System.currentTimeMillis();
		}

		// time taken before movable entity moves 
        if (currentTime >= (lastAIUpdate + 800))
        {
            Array<PlayerEntity> playerEntities = entityManager.getAllPEntity();

              for (AIManager ai : entityManager.getAllAIMEntity()) {
                  if (ai.getIsMovable() && !ai.getIsKillable()) {
                  	
                  	// movable entity moves to the right
                      ai.moveEntityRight();
                      
                      if (animatedTextureID == 2) 
                      {   	
                      ai.updateCollider(ai.getPosX(), ai.getPosY(), 32, 48);
                      }
                      else {
	                        ai.updateCollider(ai.getPosX(), ai.getPosY(), 32, 12);
                      }
                   
                  }
                  else if (ai.getIsMovable() && ai.getIsKillable()) {
                     ai.chasePEntity(playerEntities);;
                  }
              }
        }
        if (textDisplayed) {
            DisplayText();
        }
        
        
        
        
	}
	public void populateScene(int sceneID) {
		Scene selectedScene = allScenes.get(sceneID);
		entityManager.createEntities(selectedScene);
	}
	public void unloadScene() {
		entityManager.deleteEntities();
		clearScreen();
	}
	public void loadScene(int sceneID) {
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
		
		// Dashboard Render
		dashboard.render(batch);
		
		
		currentScene = selectedScene;
		currentSceneID = sceneID;
		endBatch();
	}
	public void loadBackground(Texture backgroundTexture) {
		batch.begin();
		batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
	}
    public void checkCollision() {
    	long currentTime = System.currentTimeMillis();
    	boolean previousTextDisplayed = textDisplayed;
        collisionManager.checkPlayerCollisions();
        Entity collidedEntity = collisionManager.checkPlayerCollisions();
        if (collidedEntity != null && collidedEntity.getEntityType().equals("adversarial")) {
			// If collision occurs with an adversarial entity, reduce player's health
        	
        	if (currentTime - lastHealthReductionTime > 1000) {
                dashboard.reduceHealth(1); // Reduce player's health
                
                lastHealthReductionTime = currentTime; // Update the last reduction time to now
            }
		}
        if (collidedEntity != null && collidedEntity.getEntityType().equals("npc")) {
            // If collision occurs with an NPC entity
        	if (!textDisplayed) {
                DisplayText();
            }
        }
        else {
        	textDisplayed = false;
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
	    BitmapFont font = new BitmapFont();
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
        gameOverScene.render(batch);
    }
	
	public void DisplayText() {
		if (displayFact == null) {
		ranFac = currentScene.GetAllFacts();
		String[] factsArray = ranFac.toArray(new String[0]);
		Random rand = new Random();
		int i = rand.nextInt(factsArray.length);
		displayFact = new GameOverScene(factsArray[i]);
		}
		displayFact.render(batch);
		textDisplayed = true;
		
		//Test for questions
		//List<Map<String, Object>> que = currentScene.GetAllQuestions();
		//System.out.print(que.get(0).get("real"));
	}
	
	
	
	public void drawPopQuiz(int currentSceneID) {		
		List<Map<String, Object>> questionsList = allScenes.get(currentSceneID).GetAllQuestions();
	    Map<String, Object> currentQuestion = questionsList.get(0);

	    String questionText = (String) currentQuestion.get("question");
	    List<String> answers = (List<String>) currentQuestion.get("answers");
	    correctAnswer = (String) currentQuestion.get("real");
		
	    Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    float mouseX = Gdx.input.getX();
	    float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

	    batch.begin();
	    font.setColor(1, 1, 1, 1);
	    font.draw(batch, "Question: " + questionText, 100, 300);
	    
	    font.setColor(getColorForChoice(choiceA, mouseX, mouseY));
	    font.draw(batch, answers.get(0), choiceA.x, choiceA.y + choiceA.height);
	    
	    font.setColor(getColorForChoice(choiceB, mouseX, mouseY));
	    font.draw(batch, answers.get(1), choiceB.x, choiceB.y + choiceB.height);
	    
	    font.setColor(getColorForChoice(choiceC, mouseX, mouseY));
	    font.draw(batch, answers.get(2), choiceC.x, choiceC.y + choiceC.height);
	    
	    font.setColor(getColorForChoice(choiceD, mouseX, mouseY));
	    font.draw(batch, answers.get(3), choiceD.x, choiceD.y + choiceD.height);
	    
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


	public int handleInput() {
	    if (Gdx.input.justTouched()) {
	        float x = Gdx.input.getX();
	        float y = Gdx.graphics.getHeight() - Gdx.input.getY();

	        if (choiceA.contains(x, y)) {
	            return checkAnswer("A");
	        } else if (choiceB.contains(x, y)) {
	            return checkAnswer("B");
	        } else if (choiceC.contains(x, y)) {
	        	return checkAnswer("C");
	        } else if (choiceD.contains(x, y)) {
	        	return checkAnswer("D");
	        }
	        else {
	        	return -1;
	        }
	    }
	    return -1;
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
}

