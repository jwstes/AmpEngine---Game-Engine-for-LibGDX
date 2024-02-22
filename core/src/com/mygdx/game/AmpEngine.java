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
	private float playerStartPosition;
	
    private boolean isOnGround = false;

	
	private void moveLeft() {
		
        float originalPosX = player.getPosX();
        float newX = Math.max(0, originalPosX - 200 * Gdx.graphics.getDeltaTime());
        player.setPosX(newX);
        player.updateCollider(newX, player.getPosY(), 32, 32);
        
        if (playerControl.getIsOnGround() == true) {
    		sceneManager.outputManager.playSound("walking");
    		updateIsOnGround();
        }
        
        if (!isOnGround) {
        	updateIsOnGround();
   

        }

        
      
    }

    private void moveRight() {        
	    float originalPosX = player.getPosX();
	    float newX = Math.min(Gdx.graphics.getWidth(), originalPosX + 200 * Gdx.graphics.getDeltaTime()); // Assuming the screen width as the limit
	    player.setPosX(newX);
	    player.updateCollider(newX, player.getPosY(), 32, 32);

	    if (playerControl.getIsOnGround() == true) {
    		sceneManager.outputManager.playSound("walking");
    		updateIsOnGround();
        }
	    
	 // Check if the player is on the ground
	    
	    if (!isOnGround) {
	    	updateIsOnGround();
        

        }
	    
	  
    }
    
    private void updateIsOnGround() {
        Rectangle playerRect = player.getRec();
        float playerBottom = playerRect.y - playerRect.height;

        boolean foundGround = false;
        float newY = player.getPosY();

        for (Entity groundEntity : sceneManager.entityManager.getAllSEntity()) {
            Rectangle groundRect = groundEntity.getRec();

            if (playerBottom <= groundRect.y + groundRect.height && playerRect.y > groundRect.y) {
                if (playerRect.x + playerRect.width > groundRect.x && playerRect.x < groundRect.x + groundRect.width) {
                    if (groundEntity.getEntityType().equals("static")) {
                        foundGround = true;

                        // Gradually decrease player's height until it touches the ground
                        float groundTop = groundRect.y + groundRect.height;
                        float deltaHeight = player.getPosY() - groundTop;

                        if (deltaHeight > 0.1f) { // Adjust the threshold as needed
                            newY -= Math.min(deltaHeight, 200 * Gdx.graphics.getDeltaTime());
                        } else {
                            newY = groundTop;
                        }

                        break;
                    }
                }
            }
        }

        if (!foundGround) {
            // Player is not on the ground, set properties accordingly
            isOnGround = false;
            playerControl.setIsOnGround(isOnGround);
            playerControl.setVerticalVelocity(0);
        } else {
            // Player is on the ground
            isOnGround = true;
            playerControl.setIsOnGround(isOnGround);
            playerControl.setVerticalVelocity(0);
        }

        player.setPosY(newY);
        player.updateCollider(player.getPosX(), newY, 32, 32);
        
        // Print relevant information for debugging
        System.out.println("Player Y: " + playerRect.y);
        System.out.println("Player Bottom: " + playerBottom);
        System.out.println("Is On Ground: " + isOnGround);
    }



    
    private void jump() {
    	float JUMP_VELOCITY = 300;
    	

    	if (playerControl.getIsOnGround() == true) {
    		playerControl.setIsOnGround(false);
    		updateIsOnGround();

    		playerControl.setVerticalVelocity(JUMP_VELOCITY);
    		
    		
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
	        		updateIsOnGround();
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

	private void restart() {
		if (sceneManager.getGameOverStatus()){
			sceneManager.setGameOverStatus(false);
			sceneManager.clearScreen();
			sceneManager.resetDashboard();
			player.setPosX(playerStartPosition);
		}
		// Doesn't work well if you die near spawn point because Goomba position is not resset, so it'll dmg you immediately.
	}
    
    
    
	@Override
	public void create() {
		Array<String> sceneJSONArr = new Array<String>();
		sceneJSONArr.add("Level1.json");
		//... add more if needed

		sceneManager = new SceneManager(sceneJSONArr);
		sceneManager.populateScene(0);
		sceneManager.initializeCollisionManager();
		collisionManager = sceneManager.getCollisionManager();

		player = sceneManager.entityManager.getAllPEntity().get(0);
		playerStartPosition = player.getPosX();





		/* **************************
		 	Set KeyBindings in playerControl
		   ************************* */

		playerControl = new PlayerControl();
		playerControl.bindKey(Keys.LEFT, () -> moveLeft());
		playerControl.bindKey(Keys.RIGHT, () -> moveRight());
		playerControl.bindKey(Keys.SPACE, () -> jump());
		playerControl.bindKey(Keys.ENTER, () -> restart());
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
	    // sceneManager.drawCollider();
	    sceneManager.updateScene();

	    inputManager.runnable();
	    inputManager.CCRunnable("onGround");
	    boolean anyKeyDown = inputManager.isAnyKeyDown();

	    if (!anyKeyDown) {
	        sceneManager.outputManager.stopAllSound();
	    }

	    // Gradual descent if not on the ground
	    if (!isOnGround) {
	        float descentRate = 100 * Gdx.graphics.getDeltaTime(); // Adjust the descent rate as needed
	        float newY = Math.max(0, player.getPosY() - descentRate);
	        player.setPosY(newY);
	        player.updateCollider(player.getPosX(), newY, 32, 32);

	        // Check for ground collision after descent
	        updateIsOnGround();
	    }
	}

	
	public AmpEngine() {
		
	}
}









