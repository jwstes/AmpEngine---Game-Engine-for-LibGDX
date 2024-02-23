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

    
    //CONSTRUCTOR
    public AIManager(float x, float y, Texture[] textures) {
    	// Set the initial X,Y position
        super("AIManager", x, y, textures[0]);
        entityTexture = textures;
        initialPosX = x; 
    }
    
    
    
    //GETTER & SETTER METHODS

    public Texture[] getTextures() {
    	return entityTexture;
    }
    public void setTextures(Texture[] t) {
    	entityTexture = t;
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
    

    //Methods to move the Entity 
    

    //CLASS METHODS
    @Override
    public void update(long lastEntityUpdate) {
       	float increment = 0.1f;
        int result = (int) (increment * 0); 
        float maxIncrement = result / (float) Math.PI; 
        if (maxIncrement > -1 && maxIncrement < 1) {
        	lastEntityUpdate = (long) increment;
        }
    }
    public void moveEntityRight() {
        float increment = 1f; // Adjust the distance of movable entity travel
        float maxX = Gdx.graphics.getWidth(); // Get screen size

        // Calculate the target X position using interpolation
        float targetX = Interpolation.smooth.apply(getPosX(), getPosX() + increment, 0.5f);

        // Check if the targetX has reached or exceeded the screen width
        if (targetX >= maxX) {
            // If yes, reset the entity to the left with a different starting position
            setPosX(initialPosX - getWidth());
        } else {
            // If not, update the X position with the targetX
            setPosX(targetX);
        }
    }


    public void chasePEntity(Array<PlayerEntity> playerEntities) {
        // Adjust the increment value based on the speed you want the AI to chase the player
        float increment = 1f;

        // Initialize some default values for the player entity's position
        float playerPosX = 0;

        // Get the first player entity from the array, if it exists

        if (playerEntities.size > 0) {
            playerPosX = playerEntities.first().getPosX();
        }																							
        float direction = Math.signum(playerPosX - getPosX());										
        float newPosX = getPosX() + increment * direction;	
        																				
        setPosX(newPosX);                                                                    
        updateCollider(newPosX, getPosY(), 32, 24);
    }
    
}