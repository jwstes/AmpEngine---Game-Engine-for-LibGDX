package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class PlayerControl {
	private Map<Integer, Runnable> keyBindings = new HashMap<>();

    private PlayerEntity player;

    private CollisionManager collisionManager;

    private SceneManager sceneManager;
	float verticalVelocity = 0;
    final float GRAVITY = -500;
    boolean isOnGround = true;


    // General Functions
    public void bindKey(int key, Runnable action) {
        keyBindings.put(key, action);
    }
    public Map<Integer, Runnable> getKeyBindings(){
        return keyBindings;
    }
    
    //Specific Functions to player
    public void setVerticalVelocity(float x) {
    	this.verticalVelocity = x;
    }
    public void updateVerticalVelocity(float x) {
    	this.verticalVelocity += this.GRAVITY * x;
    }
    public float getVerticalVelocity() {
    	return this.verticalVelocity;
    }
    
    public boolean getIsOnGround() {
    	return this.isOnGround;
    }
    public void setIsOnGround(boolean b) {
    	this.isOnGround = b;
    }
    



    // Base Constructor. Require you to pass PlayerEntity, SceneManager and CollisionManager
    public PlayerControl(PlayerEntity entity, SceneManager sceneManager, CollisionManager collisionManager){
        this.player = entity;
        this.sceneManager = sceneManager;
        this.collisionManager = collisionManager;
    }
    public void moveLeft() {

        float originalPosX = player.getPosX();
        float newX = Math.max(0, originalPosX - 200 * Gdx.graphics.getDeltaTime());//Calculate new position of x when moving left
        player.setPosX(newX);
        player.updateCollider(newX, player.getPosY(), 32, 32);

        sceneManager.outputManager.playSound("walking");

        Entity collisionEntity = collisionManager.checkPlayerCollisions();
        if (collisionEntity != null) {
            player.setPosX(originalPosX);   //If collision is detected, it reverts back to the original position

        }
    }

    public void moveRight() {
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

    public void jump() {
        float JUMP_VELOCITY = 300;


        this.updateVerticalVelocity(Gdx.graphics.getDeltaTime());

        if (this.getIsOnGround()) {
            this.setVerticalVelocity(JUMP_VELOCITY);
            this.setIsOnGround(false);
        }

        float newY = player.getPosY() + this.getVerticalVelocity() * Gdx.graphics.getDeltaTime();
        player.setPosY(newY);
        player.updateCollider(player.getPosX(), newY, 32, 32);


        this.setIsOnGround(false);
        Rectangle playerRect = player.getRec();
        for (Entity groundEntity : sceneManager.entityManager.getAllSEntity()) { // Loop through ground entities
            Rectangle groundRect = groundEntity.getRec();

            // Check if the player's bottom edge is within the top edge of a ground entity
            if (playerRect.y <= groundRect.y + groundRect.height && playerRect.y > groundRect.y) {
                // Check for horizontal overlap
                if (playerRect.x + playerRect.width > groundRect.x && playerRect.x < groundRect.x + groundRect.width) {
                    this.setIsOnGround(true); // Player is on the ground
                    player.setPosY(groundRect.y + groundRect.height); // Adjust position to stand on the ground
                    this.setVerticalVelocity(0); // Reset vertical velocity
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
                    float minVelocity = Math.min(this.getVerticalVelocity(), 0);
                    this.setVerticalVelocity(minVelocity);

                    player.setPosY(groundRect.y - playerRect.height); // Adjust player's position to be just below the entity
                    // Note: No need to set isOnGround = true here, as the player is not landing on top of the entity
                }
            }
        }
    }

}

















