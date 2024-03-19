package com.mygdx.game;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.*;

import com.mygdx.game.SceneManager;
import java.util.HashMap;
import java.util.Map;

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
	
	private int currentSceneID;
	
	
	//game specific code for vid demo only
	private float playerStartPositionX;
	private float playerStartPositionY;
    private boolean canJump = true;  // Variable to track whether the player can jump again


  
    //game specific code for vid demo only
    private AnimatedEntity weaponSkill;
    private Texture[] swordTextures;

  
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
	}
	
	//Don't use in any Key Press functions, 
	//It will cause nextSceneID to go crazy and throw an error. 
	public void nextScene() {
		sceneManager.unloadScene();
		int nextSceneID = currentSceneID + 1;
		createScene(nextSceneID);
	}
    
    
	@Override
	public void create() {
		Array<String> sceneJSONArr = new Array<String>();
		sceneJSONArr.add("Level1.json");
		sceneJSONArr.add("Level2.json");
		//... add more if needed
		
		currentSceneID = 0;
		
		sceneManager = new SceneManager(sceneJSONArr,"player.png");
		sceneManager.populateScene(currentSceneID);
		
		sceneManager.initializeCollisionManager();
		collisionManager = sceneManager.getCollisionManager();

		player = sceneManager.entityManager.getAllPEntity().get(0);
		
		playerStartPositionX = player.getPosX();
		playerStartPositionY = player.getPosY();


		/* **************************
		 	Set KeyBindings in playerControl
		   ************************* */

		playerControl = new PlayerControl();
		playerControl.bindKey(Keys.LEFT, () -> moveLeft());
		playerControl.bindKey(Keys.RIGHT, () -> moveRight());
		playerControl.bindKey(Keys.SPACE, () -> jump());
		playerControl.bindKey(Keys.ENTER, () -> restart());
		playerControl.bindKey(Keys.Z, () -> attack());
		
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
	}
	


	@Override
	public void render() {
	    sceneManager.clearScreen();
	    sceneManager.loadScene(currentSceneID);
	    //sceneManager.drawCollider();
	    sceneManager.updateScene();
	    
	    simulationLifeCycle.simulationUpdate();
	    player.updateCollider(player.getPosX(), player.getPosY(), 32, 32);
	    

	    inputManager.runnable();
	    inputManager.CCRunnable("onGround");
	    boolean anyKeyDown = inputManager.isAnyKeyDown();

	    if (!anyKeyDown) {
	        sceneManager.outputManager.stopAllSound();
	    }
	    
	    //Game Specific function for Demo Purpose 
	    applyGravity();
	}

	
	public AmpEngine() {
		
	}
}