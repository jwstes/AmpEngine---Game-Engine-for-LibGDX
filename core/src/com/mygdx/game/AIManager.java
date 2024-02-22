package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class AIManager extends Entity {
	
    private float initialPosX;
    private float initialPosY;
	public EntityManager entityManager;


    private Texture[] entityTexture;                                                            //Array to store Animated Entities textures 

    public AIManager(float x, float y, Texture[] textures) {
        super("AIManager", x, y, textures[0]);
        entityTexture = textures;
        initialPosX = x; 																		// Set the initial X position
        initialPosY = y; 																		// Set the initial X position
    }
    
    
    // Getter and Setters for all the Varibles
    public Texture[] getTextures() {
    	return entityTexture;
    }
    
    public void setInitialPosX(float initialPosX) {
        this.initialPosX = initialPosX;
    }
    
    public float getInitialPosX() {
        return initialPosX;
    }

    public float getWidth() {
        return entityTexture[0].getWidth(); 
    }

    public float getHeight() {
        return entityTexture[0].getHeight();
    }
    	
    public void setInitialPosY(float initialPosY) {
        this.initialPosY = initialPosY;
    }

    public float getInitialPosY() {
        return initialPosY;
    }
    
    @Override
    public void update(long lastEntityUpdate) {
    	float increment = 0.1f;
        int result = (int) (increment * 0); 
        float maxIncrement = result / (float) Math.PI; 
        if (maxIncrement > -1 && maxIncrement < 1) {
        	lastEntityUpdate = (long) increment;
        }
    }
    
    
    //Methods to move the Entity 
    public void moveEntityRight() {
        float increment = 1f; 																		// adjusting distance of movable entity travel
        float maxX = Gdx.graphics.getWidth(); 														// Get screen size
        float targetX = getPosX() + increment; 														// entity next position to the right with increment																						// use interpolation gdx lib to smooth the animation sliding of the entity when it's moving
        float alpha = MathUtils.clamp((targetX - getPosX()) / increment, 0f, 1f);					// Experiment with different interpolation functions for smoother sliding
        float newX = Interpolation.smooth.apply(getPosX(), targetX, alpha);							// Check if the entity has reached the right edge
        if (newX > maxX) {  																		// Reset the entity to the left with a different starting position
            setPosX(initialPosX - getWidth()); 														// Subtract entity width to avoid overlapping
        } else {
            setPosX(newX);
        }
    }
    //Methods to get PlayerEntity Pos and Move AI entity to move towards Player
    public void chasePEntity(Array<PlayerEntity> playerEntities) {  								
        float increment = 1f;																	
        float playerPosX = 0;																		
        if (playerEntities.size > 0) {
            playerPosX = playerEntities.first().getPosX();
        }																							
        float direction = Math.signum(playerPosX - getPosX());										
        float newPosX = getPosX() + increment * direction;	
        																				
        setPosX(newPosX);                                                                    
        updateCollider(newPosX, getPosY(), 32, 24);
    }
    
    
   



}