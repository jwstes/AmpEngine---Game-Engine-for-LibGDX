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
    private Texture[] entityTexture;
    
    
    
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
    
    

    //CLASS METHODS
    @Override
    public void update(long lastEntityUpdate) {
    	
    }
    public void moveEntityRight() {
        float increment = 1f; // adjusting distance of movable entity travel
        float maxX = Gdx.graphics.getWidth(); // Get screen size

        float targetX = getPosX() + increment; // entity next position to the right with increment

        // use interpolation gdx lib to smooth the animation sliding of the entity when it's moving
        float alpha = MathUtils.clamp((targetX - getPosX()) / increment, 0f, 1f);

        // Experiment with different interpolation functions for smoother sliding
        float newX = Interpolation.smooth.apply(getPosX(), targetX, alpha);

        // Check if the entity has reached the right edge
        if (newX > maxX) {
            // Reset the entity to the left with a different starting position
            setPosX(initialPosX - getWidth()); // Subtract entity width to avoid overlapping
        } else {
            setPosX(newX);
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

        // AI move towards the player (positive if AI is to the left, negative if AI is to the right)
        float direction = Math.signum(playerPosX - getPosX());

        // get the new position of the entity based on the increment and direction
        float newPosX = getPosX() + increment * direction;

        // Set the new position of the AI entity
        setPosX(newPosX);
        updateCollider(newPosX, getPosY(), 32, 24);
    }
    
}