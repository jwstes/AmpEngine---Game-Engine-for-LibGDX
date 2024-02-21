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
	
	
	private void moveLeft() {
		
        float originalPosX = player.getPosX();
        float newX = Math.max(0, originalPosX - 200 * Gdx.graphics.getDeltaTime());
        player.setPosX(newX);
        player.updateCollider(newX, player.getPosY(), 32, 32);

        sceneManager.outputManager.playSound("walking");
        
        Entity collisionEntity = collisionManager.checkPlayerCollisions();
        if (collisionEntity != null) {
           player.setPosX(originalPosX);
        }
    }

    private void moveRight() {        
	    float originalPosX = player.getPosX();
	    float newX = Math.min(Gdx.graphics.getWidth(), originalPosX + 200 * Gdx.graphics.getDeltaTime()); // Assuming the screen width as the limit
	    player.setPosX(newX);
	    player.updateCollider(newX, player.getPosY(), 32, 32);

	    sceneManager.outputManager.playSound("walking");
	    
	    Entity collisionEntity = collisionManager.checkPlayerCollisions();
	    if (collisionEntity != null) {
	        // Collision detected, revert to the original position
	        player.setPosX(originalPosX);
	    }
    }
    
    private void jump() {
    	float JUMP_VELOCITY = 300;
    	

    	playerControl.updateVerticalVelocity(Gdx.graphics.getDeltaTime());
    			
    	if (playerControl.getIsOnGround() == true) {
    		playerControl.setVerticalVelocity(JUMP_VELOCITY);
    		playerControl.setIsOnGround(false);
        }
    	
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
	                player.setPosY(groundRect.y + groundRect.height); // Adjust position to stand on the ground
	                playerControl.setVerticalVelocity(0); // Reset vertical velocity
	                break; // Exit the loop after finding ground collision
	            }
	        }
	     // Check if the player's top edge is colliding with the bottom edge of an entity (hitting head)
	        if (playerRect.y + playerRect.height >= groundRect.y && playerRect.y + playerRect.height < groundRect.y + groundRect.height) {
	            // Check for horizontal overlap
	            if (playerRect.x + playerRect.width > groundRect.x && playerRect.x < groundRect.x + groundRect.width) {
	                // Player has hit the bottom side of an entity
	                // You might want to handle this differently, e.g., stopping upward movement
	            		
	            	// Ensure vertical velocity is not positive (not moving upwards)
	            	float minVelocity = Math.min(playerControl.getVerticalVelocity(), 0);
	            	playerControl.setVerticalVelocity(minVelocity);
	            	
	                player.setPosY(groundRect.y - playerRect.height); // Adjust player's position to be just below the entity
	                // Note: No need to set isOnGround = true here, as the player is not landing on top of the entity
	            }
	        }
	    }
    	
    	
    }
    
    
    
	@Override
	public void create() {
		Array<String> sceneJSONArr = new Array<String>();
		sceneJSONArr.add("Level1.json");
		//... add more if needed

		/* **************************
		 	Initialize Managers
		************************* */
		sceneManager = new SceneManager(sceneJSONArr); // new SceneManager
		// Things carried out in SceneManager Constructor
		// 1. create dashboard for healthbar & timetracking
		// 2. sets a spriteBatch to render
		// 3. initialize Scene and make it load all entity into respective arrays
		// 4. Initialize lastEntityUpdate to know when was the last update made
		// 5. Initialize an output manager to manage sounds

		// Initialize CollisionManager using function in sceneManager -> create local reference
		collisionManager = sceneManager.initializeCollisionManager();

		//get Local Reference of entityManager made in sceneManager
		entityManager = sceneManager.entityManager;
		sceneManager.populateScene(0); // create scene relevant entities with the properties defined in JSON file.

		// get a Local Reference of player entity from EntityManager class
		player = entityManager.getAllPEntity().get(0);



		/* **************************
		 	Set KeyBindings in playerControl
		   ************************* */

		playerControl = new PlayerControl();
		playerControl.bindKey(Keys.LEFT, () -> moveLeft());
		playerControl.bindKey(Keys.RIGHT, () -> moveRight());
		playerControl.bindKey(Keys.SPACE, () -> jump());
		sceneManager.setPlayerControl(playerControl);



		inputManager = new InputManager(sceneManager);
		
		Map<String, Sound> soundList = new HashMap<>();
		soundList.put("walking", inputManager.loadSound("walking.mp3"));

		sceneManager.outputManager.setSoundList(soundList);
	}
	


	@Override
	public void render() {
		sceneManager.clearScreen();
		sceneManager.loadScene(0);
//        sceneManager.drawCollider();
        sceneManager.updateScene();
        
        inputManager.runnable();
        inputManager.CCRunnable("onGround");
        Boolean anyKeyDown = inputManager.isAnyKeyDown();
        if(anyKeyDown != true) {
        	sceneManager.outputManager.stopAllSound();
        }
        
        
	}
	
	public AmpEngine() {
		
	}
}









