package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class PlayerControl {
	private PlayerEntity player;
	private CollisionManager colm;
	private Array<PlayerEntity> pList;
	Array<StaticEntity> sList;
	Array<AIManager> aiList;
	Array<AdversarialEntity> aList;
	Rectangle worldBounds = new Rectangle(1, 1, 1279, 718);
	
	float verticalVelocity = 0;
    final float GRAVITY = -200; // Gravity pulling the player down each frame
    final float JUMP_VELOCITY = 300; // The initial velocity impulse when jumping
    boolean isOnGround = true; // Initially, assume the player is on the ground
	
	
	public PlayerControl(Array<PlayerEntity> pList, Array<StaticEntity> sList, Array<AdversarialEntity> aList, Array<AIManager>aiList) {
		this.pList = pList;
		this.sList = sList;
		this.aiList = aiList;
		this.aList = aList;
		colm = new CollisionManager(worldBounds,0, pList, sList, aList, aiList);
		
	}
	
	public void Movement() {
		Entity player = pList.get(0);
		if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            // Store the player's original position
            float originalPosX = player.getPosX();

            // Calculate the new position
            float newX = Math.max(0, originalPosX - 200 * Gdx.graphics.getDeltaTime());

            // Update the player's position temporarily to check for collisions
            player.setPosX(newX);
            player.updateCollider(newX, player.getPosY(), 32, 32);
            
            // Check for collisions
            Entity collisionEntity = colm.checkPlayerCollisions();
            if (collisionEntity != null) {
                // Collision detected, revert to the original position
               player.setPosX(originalPosX);
            }
        }
		
		
		if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
		    // Store the player's original position
		    float originalPosX = player.getPosX();

		    // Calculate the new position
		    float newX = Math.min(Gdx.graphics.getWidth(), originalPosX + 200 * Gdx.graphics.getDeltaTime()); // Assuming the screen width as the limit

		    // Update the player's position temporarily to check for collisions
		    player.setPosX(newX);
		    player.updateCollider(newX, player.getPosY(), 32, 32);

		    // Check for collisions
		    
		    Entity collisionEntity = colm.checkPlayerCollisions();
		    if (collisionEntity != null) {
		        // Collision detected, revert to the original position
		        player.setPosX(originalPosX);
		    }
		}
				
	}
	

	
	
}
