package com.mygdx.game;
import java.util.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.*;
import com.mygdx.AiControlled.AIManager;
import com.mygdx.Animated.AnimatedEntity;
import com.mygdx.game.Entities.CollisionManager;
import com.mygdx.game.Entities.Entity;
import com.mygdx.game.Entities.EntityManager;
import com.mygdx.game.Entities.PlayerEntity;
import com.mygdx.game.IO.InputManager;
import com.mygdx.game.IO.OutputManager;
import com.mygdx.game.IO.PlayerControl;
import com.mygdx.game.Scene.Scene;
import com.mygdx.game.Scene.SceneManager;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import org.w3c.dom.css.Rect;


public class AmpEngine extends ApplicationAdapter{
	private SceneManager sceneManager;
	public EntityManager entityManager;
	private Scene scene;
	private SpriteBatch batch;
	
	private PlayerControl playerControl;
	private PlayerEntity player;
	private CollisionManager collisionManager;
	private InputManager inputManager;
	private SimulationLifeCycle simulationLifeCycle;
	private Dashboard dashboard;
	
	private int currentSceneID;
	
	
	//game specific code for vid demo only
	private float playerStartPositionX;
	private float playerStartPositionY;
    private boolean canJump = true;  // Variable to track whether the player can jump again


  
    //game specific code for vid demo only
    private AnimatedEntity weaponSkill;
    private Texture[] swordTextures;
    
    private boolean gameLost;
    private BitmapFont font;

  
    private Music backgroundMusic;

    private Boss boss;
    
    
    

    
   

  
    //game specific code for vid demo only
	private void moveLeft() {
		
        float originalPosX = player.getPosX();
        float newX = Math.max(0, originalPosX - 200 * Gdx.graphics.getDeltaTime());
        player.setPosX(newX);
        player.updateCollider(newX, player.getPosY(), 32, 32);
        
        if (playerControl.getIsOnGround() == true) {
    		sceneManager.outputManager.playSound("walking");
    		//updateIsOnGround();
        }
	    if (collisionManager.checkPlayerCollisions() == null) {
	    	playerControl.setIsOnGround(false);
	    }
	    float newXs = collisionManager.LeftRightCollision(player);
	    player.setPosX(newXs);
	    
        

    }
	//game specific code for vid demo only
    private void moveRight() {        
	    float originalPosX = player.getPosX();
	    float newX = Math.min(Gdx.graphics.getWidth(), originalPosX + 200 * Gdx.graphics.getDeltaTime()); // Assuming the screen width as the limit
	    player.setPosX(newX);
	    player.updateCollider(newX, player.getPosY(), 32, 32);

	    if (playerControl.getIsOnGround() == true) {
    		sceneManager.outputManager.playSound("walking");
    		//updateIsOnGround();
        }
	    if (collisionManager.checkPlayerCollisions() == null) {
	    	playerControl.setIsOnGround(false);
	    }
	    float newXs = collisionManager.LeftRightCollision(player);
	    player.setPosX(newXs);
    }
    


  //game specific code for vid demo only
    private void jump() {
    	float JUMP_VELOCITY = 350;
    	
    	
    	if (playerControl.getIsOnGround() && canJump) {
    		playerControl.setIsOnGround(false);
    		//updateIsOnGround();

    		playerControl.setVerticalVelocity(JUMP_VELOCITY);
            canJump = false;  // Set to false to prevent jumping until the player is on the ground again

    		
    		
        }
    	playerControl.updateVerticalVelocity(Gdx.graphics.getDeltaTime());
    			
    
    	
    	float newY = player.getPosY() + playerControl.getVerticalVelocity() * Gdx.graphics.getDeltaTime();
    	player.setPosY(newY);
        player.updateCollider(player.getPosX(), newY, 32, 32);
    	

	    playerControl.setIsOnGround(false);
	    Rectangle playerRect = player.getRec();
	    for (Entity groundEntity : sceneManager.entityManager.getAllSEntity()) { // Loop through ground entities
	        Rectangle groundRect = groundEntity.getRec();

	        // Check if the player's bottom edge is within the top edge of a ground entity
	        if (playerRect.y <= groundRect.y + groundRect.height && playerRect.y > groundRect.y) {
	            // Check for horizontal overlap
	            if (playerRect.x + playerRect.width > groundRect.x && playerRect.x < groundRect.x + groundRect.width) {
	            	playerControl.setIsOnGround(true); // Player is on the ground
	        		//updateIsOnGround();
                    canJump = true;  // Set to true when the player is on the ground

	                player.setPosY(groundRect.y + groundRect.height); 
	                playerControl.setVerticalVelocity(0); 
	                break; 
	            }
	        }
	        if (playerRect.y + playerRect.height >= groundRect.y && playerRect.y + playerRect.height < groundRect.y + groundRect.height) {
	            if (playerRect.x + playerRect.width > groundRect.x && playerRect.x < groundRect.x + groundRect.width) {
	            	float minVelocity = Math.min(playerControl.getVerticalVelocity(), 0);
	            	playerControl.setVerticalVelocity(minVelocity);
	            	
	                player.setPosY(groundRect.y - playerRect.height); 
	   
	            }
	        }

	    }
	    
	    
	    
    }
  //game specific code for vid demo only
    private void applyGravity() {
        if (!playerControl.getIsOnGround()) {
            // Apply gravity to vertical velocity
            playerControl.setVerticalVelocity(playerControl.getVerticalVelocity() + -600.8f * Gdx.graphics.getDeltaTime());

            // Update player's Y position based on the new vertical velocity
            float newY = player.getPosY() + playerControl.getVerticalVelocity() * Gdx.graphics.getDeltaTime();
            player.setPosY(newY);
            player.updateCollider(player.getPosX(), newY, 32, 32);

            playerControl.setIsOnGround(false);
    	    Rectangle playerRect = player.getRec();
    	    for (Entity groundEntity : sceneManager.entityManager.getAllSEntity()) { // Loop through ground entities
    	        Rectangle groundRect = groundEntity.getRec();

    	        // Check if the player's bottom edge is within the top edge of a ground entity
    	        if (playerRect.y <= groundRect.y + groundRect.height && playerRect.y > groundRect.y) {
    	            // Check for horizontal overlap
    	            if (playerRect.x + playerRect.width > groundRect.x && playerRect.x < groundRect.x + groundRect.width) {
    	            	playerControl.setIsOnGround(true); // Player is on the ground
    	        		//updateIsOnGround();
    	                player.setPosY(groundRect.y + groundRect.height); // Adjust position to stand on the ground
    	                playerControl.setVerticalVelocity(0); // Reset vertical velocity
    	                break; // Exit the loop after finding ground collision
    	            }
    	        }
    	        if (playerRect.x <= groundRect.x + groundRect.width && playerRect.x > groundRect.x) {
    	            // Check for vertical overlap
    	            if (playerRect.y < groundRect.y + groundRect.height && playerRect.y + playerRect.height > groundRect.y) {
    	                // Collision on the player's left side
    	                player.setPosX(groundRect.x + groundRect.width); // Adjust player's position to the right of the entity
    	                
    	                break;
    	            }
    	        }

    	    }}
    }
    
  //game specific code for vid demo only
    private void attack() {
		int spawnX = (int)player.getPosX();
		int spawnY = (int)player.getPosY();
		weaponSkill = new AnimatedEntity("", spawnX, spawnY, swordTextures);
		
		
    	simulationLifeCycle.simulationCycle(weaponSkill, (entity, animationTime, time) -> {    		
            Texture[] entityTextures = entity.getAnimatedTexture();
            long rt = animationTime;
            
            if(time >= (animationTime + 35)) {
                
            	int animationFrame = entity.getTextureID();
            	
                if(animationFrame < entityTextures.length - 1) {
                    animationFrame++;
                    entity.setTexture(entityTextures[animationFrame]);
                }
                else {
                    animationFrame = 0;
                }
                entity.setTextureID(animationFrame);
                
                rt = time;
            }

            return rt;
        }, 1500);
    }
    
    
	private void restart() {
		if (sceneManager.getGameOverStatus()){
			sceneManager.setGameOverStatus(false);
			sceneManager.clearScreen();
			sceneManager.resetDashboard();
			player.setPosX(playerStartPositionX);
			player.setPosY(playerStartPositionY);
		}
	}
    
	public void createScene(int sceneID) {
		currentSceneID = sceneID;
		sceneManager.populateScene(currentSceneID);
		sceneManager.initializeCollisionManager();
		collisionManager = sceneManager.getCollisionManager();

		player = sceneManager.entityManager.getAllPEntity().get(0);
		
		playerStartPositionX = player.getPosX();
		playerStartPositionY = player.getPosY();
		
		autoSetRandomFactIndex(sceneID);
	}
	
	//Don't use in any Key Press functions, 
	//It will cause nextSceneID to go crazy and throw an error. 
	public void nextScene() {

//		sceneManager.getAllScenes().get(currentSceneID).EmptyAllFacts();
		sceneManager.unloadScene();
		int nextSceneID = currentSceneID + 1;
		
		if((nextSceneID + 1) > sceneManager.getAllScenes().size) {
			if(boss.getGlobalBossHP() < 0){
				gameLost = true;
				sceneManager.setCutsceneMessage("You Won OMG!!!!! So Cool wtf?");
			}
			else {
				gameLost = true;
				sceneManager.setCutsceneMessage("upper?");
			}
			
		}
		else {
			createScene(nextSceneID);
		}
	    //sceneManager.getAllScenes().get(currentSceneID).EmptyAllFacts();
	    sceneManager.unloadScene();

	    if (nextSceneID >= sceneManager.getAllScenes().size) {
	        if (boss.getGlobalBossHP() < 0) {
	            gameLost = false;
	            sceneManager.setCutsceneMessage("You Won OMG!!!!! So Cool wtf?");
	        } else {
	            gameLost = true;
	            sceneManager.setCutsceneMessage("OMG YOU WON WOOOOO!!!");
	            
	        }
	    } else {
	        createScene(nextSceneID);
	    }
	}
	public void restartToScene1() {
        gameLost = false; // Reset game lost status 
        
        createScene(0); // Restart scene to level 1
        
    }
    
	@Override
	public void create() {
		  // Load the background music
		Array<String> sceneJSONArr = new Array<String>();
		sceneJSONArr.add("Level1.json");
		sceneJSONArr.add("Level2.json");
		sceneJSONArr.add("Level3.json");
		//... add more if needed
		
		currentSceneID = 0;
		
		boss = new Boss();
		
		sceneManager = new SceneManager(sceneJSONArr,"player.png");
		createScene(currentSceneID);
		
		font = new BitmapFont();
		font.getData().setScale(1); // Scale up the font size

		/* **************************
		 	Set KeyBindings in playerControl
		   ************************* */

		playerControl = new PlayerControl();
		playerControl.bindKey(Keys.LEFT, () -> moveLeft());
		playerControl.bindKey(Keys.RIGHT, () -> moveRight());
		playerControl.bindKey(Keys.SPACE, () -> jump());
		playerControl.bindKey(Keys.ENTER, () -> restart());
		playerControl.bindKey(Keys.Z, () -> attack());
		playerControl.bindKey(Keys.ESCAPE,() -> Gdx.app.exit());
		
		sceneManager.setPlayerControl(playerControl);

		inputManager = new InputManager(sceneManager);
		
		Map<String, Sound> soundList = new HashMap<>();
		soundList.put("walking", inputManager.loadSound("walking.mp3"));

		sceneManager.outputManager.setSoundsList(soundList);
		
		
		swordTextures = new Texture[4];
		swordTextures[0] = new Texture(Gdx.files.internal("sword1.png"));
		swordTextures[1] = new Texture(Gdx.files.internal("sword2.png"));
		swordTextures[2] = new Texture(Gdx.files.internal("sword3.png"));
		swordTextures[3] = new Texture(Gdx.files.internal("sword4.png"));
		
		
		simulationLifeCycle = new SimulationLifeCycle(System.currentTimeMillis(), sceneManager);
		sceneManager.setDisplayingCutscene(true);
		sceneManager.setCutsceneMessage("The goal of the game is to overcome a black hole's gravitational pull by\nsuccessfully completing quizzes on three different planets it's affecting.");
	
		dashboard = sceneManager.getDashboard();
		resetBossHP();
	}

	
	

	private void handleNoMoreQuestions() {
	    // Handle the scenario when there are no more questions
	    System.out.println("No more questions in this scene.");
	    nextScene(); // Go to the next scene
	    // You can add any additional logic needed when no more questions are available
	}


	
	@Override
	public void render() {
	    sceneManager.clearScreen();
	    if(sceneManager.getDrawMenu() == 0) {
	    	if(gameLost == false) {
	    		if(sceneManager.getDrawQuiz() == 0) {
		    		
		    		
		    		if(sceneManager.getDisplayingCutscene()) {
		    		    displayCutscene();
		    		    
		    		    if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
		    		        sceneManager.setDisplayingCutscene(false);
		    		        //Display how to play space bar enter
		    		        
		    		    }
		    		} 
		    		else {
		    			sceneManager.loadScene(currentSceneID, false);
		    			
					    //sceneManager.drawCollider();
					    updateScene();
					    sceneManager.checkCollision(currentSceneID);
					    
					    simulationLifeCycle.simulationUpdate();
					    player.updateCollider(player.getPosX(), player.getPosY(), 32, 32);
					    

					    inputManager.runnable();
					    inputManager.CCRunnable("onGround");
					    boolean anyKeyDown = inputManager.isAnyKeyDown();

					    if (!anyKeyDown) {
					        sceneManager.outputManager.stopAllSound();
					    }
					    
					    applyGravity();
		    			
		    		}
		    		
			    	
			    }
			    else {
			    	boolean noMoreQuestions = false;
			 
			    	String correctAnswer = drawPopQuiz(currentSceneID, false);
			    	sceneManager.loadScene(currentSceneID, true);
			    	
			    	// Existing code in render method for handling quiz questions
			    	int correctAnswerSelected = handleInput(false, correctAnswer);
			    	if(correctAnswerSelected == 1) {
			    	    handleNoMoreQuestions();
			    	    decreaseBossHP(40, 5, 0);
			    	    noMoreQuestions=false;
			    	    System.out.println("here1");
			    	} else if (correctAnswerSelected == 0) {
			    	    increaseBossHP(15);
			    	    System.out.println("here2!");
			    	} 
			    }
	    	}
	    	else {
	    		displayCutscene();
	    		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
    		        sceneManager.setCutsceneMessage("There's no restarting for you bro.");
    				restartToScene1();
    				resetBossHP();
    		        
    		        
    		    }
	    	}
	    }
	    else {
	    	drawPopQuiz(currentSceneID, true);
	    	int startGameSelected = handleInput(true, "");
	    	if(startGameSelected == 1) {
	    		sceneManager.setDrawMenu(0);
	    	}
	    	else if (startGameSelected == -1) {
	            // Then draw the settings page if needed, as an overlay
	            drawSettingsPage();
			}
	    	
			else if (startGameSelected == 3) {
				Gdx.app.exit();
			}
	    }
	}

	public long getCurrentTime(){
		return System.currentTimeMillis();
	}
	private boolean isEntityName(String desiredEntityName) {
		System.out.println("runned at isEntity AmpEngine");
		Scene currentScene = sceneManager.exposeScene();
		List<String> entityNames = currentScene.GetEntityName();
		for (String name : entityNames) {
			if (desiredEntityName.equals(name)) {
				return true; // Found the desired entity name
			}
		}
		return false; // Desired entity name not found
	}


	// updateScene handles animation texture changes, checks for collision, move AI,
	public void updateScene() {

		// Loop Animations
		int animatedTextureID = sceneManager.getAnimatedTextureID();
		if (getCurrentTime() >= (sceneManager.getLastEntityUpdate() + 80)) {
			if(animatedTextureID < 4) {
				sceneManager.setAnimatedTextureID(animatedTextureID + 1);
			}
			else {
				sceneManager.setAnimatedTextureID(0);
			}
			//checkCollision(); //instead of calling here, shift to AmpEngine so other details can be modified
			sceneManager.setLastEntityUpdate(System.currentTimeMillis());
		}

		// Time taken before movable entity moves
		if (getCurrentTime() >= (sceneManager.getLastAIUpdate() + 800))
		{
			Array<PlayerEntity> playerEntities = sceneManager.entityManager.getAllPEntity();

			for (AIManager ai : sceneManager.entityManager.getAllAIMEntity()) {
				if (isEntityName("meteor")) {
					if (ai.getIsMovable() && !ai.getIsKillable() && !ai.getIsAlive() && !ai.getIsCollidable()) {
						ai.moveEntityDiagonallyRight();
						ai.updateCollider(ai.getPosX(), ai.getPosY(), 100, 100);
					}
				}


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

	public String drawPopQuiz(int currentSceneID, boolean menuMode) {
		batch = sceneManager.getBatch();
		Array<Scene> allScenes = sceneManager.getAllScenes();
		List<Map<String, Object>> questionsList = allScenes.get(currentSceneID).GetAllQuestions();
//	    Map<String, Object> currentQuestion = questionsList.get(0);
		Map<String, Object> currentQuestion = questionsList.get(sceneManager.getRandomFactIndex());

		String questionText = (String) currentQuestion.get("question");
		List<String> answers = (List<String>) currentQuestion.get("answers");
		String correctAnswer = sceneManager.getCorrectAnswer();
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

			float settingGameX = 1050;
			float settingGameY = 0;

			float endGameX = 400;
			float endGameY = 150;

			// Load the "Start Game" image
			Texture startGameImage = new Texture(Gdx.files.internal("menu_start.png"));
			batch.draw(startGameImage, startGameX, startGameY);

			Rectangle menuChoiceA = new Rectangle(startGameX, startGameY, startGameImage.getWidth(), startGameImage.getHeight());
			// Update menuChoiceA coordinates to match "Start Game" image
			menuChoiceA.setPosition(startGameX, startGameY);
			// Assuming startGameImage is not null
			menuChoiceA.setSize(startGameImage.getWidth(), startGameImage.getHeight());
			sceneManager.setMenuChoiceA(menuChoiceA);



			// Draw setting image
			Texture settingImage = new Texture(Gdx.files.internal("menu_setting.png"));
			batch.draw(settingImage, settingGameX, settingGameY);
			Rectangle menuChoiceB = new Rectangle(settingGameX, settingGameY, settingImage.getWidth(), settingImage.getHeight());
			menuChoiceB.setPosition(settingGameX, settingGameY);
			menuChoiceB.setSize(settingImage.getWidth(), settingImage.getHeight());
			sceneManager.setMenuChoiceB(menuChoiceB);


			// Draw header image
			Texture exitGameImage = new Texture(Gdx.files.internal("menu_exit.png"));
			batch.draw(exitGameImage, endGameX, endGameY);
			Rectangle menuChoiceC = new Rectangle(endGameX, endGameY, exitGameImage.getWidth(), exitGameImage.getHeight());
			menuChoiceC.setPosition(endGameX, endGameY);
			menuChoiceC.setSize(exitGameImage.getWidth(), exitGameImage.getHeight());
			sceneManager.setMenuChoiceC(menuChoiceC);

		}

		else if (sceneManager.isShowingSettings()) {
			drawSettingsPage();
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

			Rectangle choiceA = sceneManager.getChoiceA();
			font.setColor(sceneManager.getColorForChoice(choiceA, mouseX, mouseY));
			font.draw(batch, answers.get(0), choiceA.x, choiceA.y + choiceA.height);

			Rectangle choiceB = sceneManager.getChoiceB();
			font.setColor(sceneManager.getColorForChoice(choiceB, mouseX, mouseY));
			font.draw(batch, answers.get(1), choiceB.x, choiceB.y + choiceB.height);

			Rectangle choiceC = sceneManager.getChoiceC();
			font.setColor(sceneManager.getColorForChoice(choiceC, mouseX, mouseY));
			font.draw(batch, answers.get(2), choiceC.x, choiceC.y + choiceC.height);

			Rectangle choiceD = sceneManager.getChoiceD();
			font.setColor(sceneManager.getColorForChoice(choiceD, mouseX, mouseY));
			font.draw(batch, answers.get(3), choiceD.x, choiceD.y + choiceD.height);

		}

		if (sceneManager.getShowWrongAnswerMessage()) {
			float wrongAnswerMessageTimer = sceneManager.getWrongAnswerMessageTimer();
			wrongAnswerMessageTimer -= Gdx.graphics.getDeltaTime();
			if (wrongAnswerMessageTimer <= 0) {
				sceneManager.setShowWrongAnswerMessage(false);
			} else {
				font.setColor(com.badlogic.gdx.graphics.Color.RED);
				font.draw(batch, "Wrong Answer!", 100, 50);
			}
		}

		batch.end();
		
		return correctAnswer;
	}

	public void drawSettingsPage() {
		if (sceneManager.getShowingSettings()) {
			batch = sceneManager.getBatch(); // added reference
			// Draw semi-transparent overlay using ShapeRenderer
			ShapeRenderer shapeRenderer = new ShapeRenderer();
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA); // Ensure blending is set up for transparency
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(new Color(0, 0, 0, 0.5f)); // Semi-transparent black
			shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			shapeRenderer.end(); // End ShapeRenderer session
			Gdx.gl.glDisable(GL20.GL_BLEND);

			// Now start SpriteBatch session
			batch.begin();

			// Load the settings background image
			Texture settingsBackgroundImage = new Texture(Gdx.files.internal("setting_BG.png"));

			// Specify the scale dimensions
			float scaleWidth = Gdx.graphics.getWidth(); // Scale to full screen width
			float scaleHeight = Gdx.graphics.getHeight(); // Scale to full screen height

			// Calculate the position to center the image
			float x = (Gdx.graphics.getWidth() - scaleWidth) / 2;
			float y = (Gdx.graphics.getHeight() - scaleHeight) / 2;

			// Draw the scaled settings background image
			batch.draw(settingsBackgroundImage, x, y, scaleWidth, scaleHeight);


			// Load the settings background image
			Texture settingHeader = new Texture(Gdx.files.internal("setting_header.png"));

			// Specify the scale dimensions
			float settingHeaderX = 400; // Scale to full screen width
			float settingHeaderY = 600; // Scale to full screen height
			// Draw the scaled settings background image
			batch.draw(settingHeader, settingHeaderX, settingHeaderY);

			float settingCloseX = 500;
			float settingCloseY = 50;

			// Draw setting image
			Texture settingClose = new Texture(Gdx.files.internal("setting_close.png"));
			batch.draw(settingClose, settingCloseX, settingCloseY);
			Rectangle settingCloseChoice = new Rectangle(settingCloseX, settingCloseY, settingClose.getWidth(), settingClose.getHeight());
			settingCloseChoice.setPosition(settingCloseX, settingCloseY);
			settingCloseChoice.setSize(settingClose.getWidth(), settingClose.getHeight());
			sceneManager.setSettingCloseChoice(settingCloseChoice);
			batch.end();

			batch.begin();

			float musicChoiceX = 500;
			float musicChoiceY = 300;



			// Load the "Mute Music" image

			Texture musicImage = new Texture(Gdx.files.internal("Music_BTN.png"));

			// Check if the music is muted and set the opacity accordingly
			OutputManager outputManager = sceneManager.getOutputManager();
			if (outputManager.isMuted()) {
				// If muted, set the image to be more transparent
				Texture musicOff = new Texture(Gdx.files.internal("MusicOff_BTN.png"));
				batch.draw(musicOff, musicChoiceX, musicChoiceY);


			} else {
				Texture musicOn = new Texture(Gdx.files.internal("Music_BTN.png"));
				batch.draw(musicOn, musicChoiceX, musicChoiceY);
			}
			Rectangle musicChoice = new Rectangle(musicChoiceX, musicChoiceY, musicImage.getWidth(), musicImage.getHeight());
			// Update menuChoiceA coordinates to match "Start Game" image
			musicChoice.setPosition(musicChoiceX, musicChoiceY);
			// Assuming startGameImage is not null
			musicChoice.setSize(musicImage.getWidth(), musicImage.getHeight());
			sceneManager.setMusicChoice(musicChoice);
			batch.end();

			// Update menuChoiceA coordinates to match "Start Game" image
//
//
//
//		    // Draw setting image
//	        Texture settingImage = new Texture(Gdx.files.internal("menu_setting.png"));
//	        batch.draw(settingImage, settingGameX, settingGameY);
//		    menuChoiceB = new Rectangle(settingGameX, settingGameY, settingImage.getWidth(), settingImage.getHeight());
//		    menuChoiceB.setPosition(settingGameX, settingGameY);
//		    menuChoiceB.setSize(settingImage.getWidth(), settingImage.getHeight());
		}
	}
	public void toggleSettingsPage() {
		boolean showingSettings = sceneManager.isShowingSettings();
		showingSettings = !showingSettings; // This will toggle the state
		sceneManager.setShowingSettings(showingSettings);
	}

	public int handleInput(boolean menuMode, String correctAnswer) {
		boolean showingSettings = sceneManager.getShowingSettings();
		if (Gdx.input.justTouched()) {
			float x = Gdx.input.getX();
			float y = Gdx.graphics.getHeight() - Gdx.input.getY();
			Rectangle menuChoiceA = sceneManager.getMenuChoiceA();
			Rectangle menuChoiceB = sceneManager.getMenuChoiceB();
			Rectangle menuChoiceC = sceneManager.getMenuChoiceC();
			Rectangle settingCloseChoice = sceneManager.getSettingCloseChoice();
			Rectangle musicChoice = sceneManager.getMusicChoice();
			Rectangle choiceA = sceneManager.getChoiceA();
			Rectangle choiceB = sceneManager.getChoiceB();
			Rectangle choiceC = sceneManager.getChoiceC();
			Rectangle choiceD = sceneManager.getChoiceD();



			// Check if the game is in menu mode
			if (menuMode && !showingSettings) {
				if (menuChoiceA.contains(x, y)) {
					return 1; // Start Game
				} else if (menuChoiceB.contains(x, y)) {
					toggleSettingsPage(); // Toggle the settings page visibility
					return -1; // Do not change the menu state as we are displaying an overlay
				} else if (menuChoiceC.contains(x, y)) {
					return 3; // Exit Game
				}

			}
			else if (showingSettings) {
				if (settingCloseChoice.contains(x, y)) {
					
					sceneManager.setShowingSettings(false);
					return 2; // Start Game
				} else if (musicChoice.contains(x, y)) {
					OutputManager.getInstance().toggleMute();
					return -1; // Do not change the menu state as we are displaying an overlay
				}
			}
			else {
				// Handle input for game mode (questions)
				if (choiceA.contains(x, y)) {
					return checkAnswer("A", correctAnswer);
				} else if (choiceB.contains(x, y)) {
					return checkAnswer("B", correctAnswer);
				} else if (choiceC.contains(x, y)) {
					return checkAnswer("C", correctAnswer);
				} else if (choiceD.contains(x, y)) {
					return checkAnswer("D", correctAnswer);
				}
			}
		}
		return -1; // Default return if no action is triggered
	}

	public int checkAnswer(String selectedAnswer, String correctAnswer) {
//		String correctAnswer = sceneManager.getCorrectAnswer();
		
		System.out.println(correctAnswer);
		System.out.println(selectedAnswer);
		
		if (selectedAnswer.equals(correctAnswer)) {
			sceneManager.setDrawQuiz(0);
			sceneManager.setShowWrongAnswerMessage(false);
			sceneManager.setWrongAnswerMessageTimer(0);
			return 1;
		} else {
			System.out.println("Wrong Answer!");
			sceneManager.setShowWrongAnswerMessage(true);
			sceneManager.setWrongAnswerMessageTimer(sceneManager.getMESSAGE_DISPLAY_TIME()); //3
			return 0;
		}
	}

	public void displayCutscene() {
		batch = sceneManager.getBatch();
		batch.begin();
		BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
		font.getData().setScale(0.7f);

		String loremIpsum = sceneManager.getCutsceneMessage();
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
		int globalBossHP = boss.getGlobalBossHP() - (baseDecrement - penalty);
		boss.setGlobalBossHP(globalBossHP);
		sceneManager.getDashboard().setBossHP(globalBossHP);
	}
	public void increaseBossHP(int baseIncrement) {
		int globalBossHP = boss.getGlobalBossHP() + (baseIncrement);
		boss.setGlobalBossHP(globalBossHP);
		sceneManager.getDashboard().setBossHP(globalBossHP);
	}

	public void resetBossHP() {
		boss.setGlobalBossHP(100);
		sceneManager.getDashboard().setBossHP(100);
	}

	public void autoSetRandomFactIndex(int currentSceneID) {
		Random r = new Random();
		Array<Scene> allScenes = sceneManager.getAllScenes();

		List<String> facts = allScenes.get(currentSceneID).GetAllFacts();
		if (facts.isEmpty()) {

			// Print log statement if the facts list is empty
			Gdx.app.log("SceneManager", "The facts list is empty for scene ID: " + currentSceneID);
		} else {
			int max = facts.size();
			int randomFactIndex = (r.nextInt((max-1) - 0 + 1) + 0);
			sceneManager.setRandomFactIndex(randomFactIndex);
		}
	}



	
	public AmpEngine() {
		
	}
}