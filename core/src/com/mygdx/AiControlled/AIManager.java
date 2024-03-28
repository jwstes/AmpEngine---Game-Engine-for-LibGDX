package com.mygdx.AiControlled;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Entities.Entity;
import com.mygdx.game.Entities.EntityManager;
import com.mygdx.game.Entities.PlayerEntity;



public class AIManager extends Entity{
	private float initialPosX;
	private float initialPosY;
	public EntityManager entityManager;
	private Texture[] entityTexture;            
	private boolean isMovingRight = false; // Track the direction of movement
	private float moveTimer = 0; // Timer to track the duration of movement in one direction
	private float maxMoveTime = 3; // Maximum time to move in one direction (in seconds)
	private float stopTimer = 0; // Timer to track the duration of stopping before switching direction
	private float maxStopTime = 2; // Maximum time to stop (in seconds)

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
	public void drawBounds(ShapeRenderer shapeRenderer) {
		Rectangle bounds = getRec();
		shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		
	}
	@Override
	public void draw(SpriteBatch b) {
		if (getTexture() != null) {
            b.draw(getTexture(), getPosX(), getPosY());
        }
	}
	
	
	public void moveEntityRight() {
		// Calculate the target X position using interpolation
		float increment = 1f; 
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

	public void moveEntityLeft() {
		// Calculate the target X position using interpolation
		float increment = -1f; // Adjust the increment to move to the left
		float minX = 0; // Set the minimum X position

		// Calculate the target X position using interpolation
		float targetX = Interpolation.smooth.apply(getPosX(), getPosX() + increment, 0.5f);

		// Check if the targetX has reached or exceeded the minimum X position
		if (targetX <= minX) {
			// If yes, reset the entity to the right with a different starting position
			setPosX(initialPosX + getWidth());
		} else {
			// If not, update the X position with the targetX
			setPosX(targetX);
		}
	}

	public void moveEntityDiagonallyRight() {
	    // Calculate the target X and Y positions using interpolation
	    float incrementX = 1f; // Adjust the increment to move to the right
	    float incrementY = -1f; // Adjust the increment to move downwards
	    float maxX = Gdx.graphics.getWidth(); // Get screen width
	    float maxY = Gdx.graphics.getHeight(); // Get screen height

	    // Calculate the target X and Y positions using interpolation
	    float targetX = Interpolation.smooth.apply(getPosX(), getPosX() + incrementX, 0.5f);
	    float targetY = Interpolation.smooth.apply(getPosY(), getPosY() + incrementY, 0.5f);

	    // Check if the entity has completely left the screen on the right side
	    if (getPosX() + getWidth() < 0) {
	        // Reset the entity to the right side of the screen
	        setPosX(maxX);
	    } else if (getPosX() > maxX) {
	        // Reset the entity to the left side of the screen
	        setPosX(-getWidth());
	    } else {
	        // If not, update the X position with the targetX
	        setPosX(targetX);
	    }

	    // Check if the entity has completely left the screen on the top or bottom side
	    if (getPosY() + getHeight() < 0) {
	        // Reset the entity to the bottom side of the screen
	        setPosY(maxY);
	    } else if (getPosY() > maxY) {
	        // Reset the entity to the top side of the screen
	        setPosY(-getHeight());
	    } else {
	        // If not, update the Y position with the targetY
	        setPosY(targetY);
	    }
	}

	public void moveEntityRandomly() {
	    if (stopTimer > 0) {
	        // If the entity is currently stopped, decrement the stop timer
	        stopTimer -= Gdx.graphics.getDeltaTime();
	        return; // Exit the method without moving
	    } else if (moveTimer <= 0) {
	        // If the move timer has elapsed, change direction and start stopping
	        isMovingRight = !isMovingRight; // Switch direction
	        moveTimer = MathUtils.random(1, maxMoveTime); // Reset the move timer with random duration
	        stopTimer = MathUtils.random(1, maxStopTime); // Start stopping
	        return; // Exit the method without moving
	    }

	    // Update the move timer
	    moveTimer -= Gdx.graphics.getDeltaTime();

	    // Move in the chosen direction
	    if (isMovingRight) {
	        moveEntityRight();
	    } else {
	        moveEntityLeft();
	    }
	}

	public void chasePEntity(Array<PlayerEntity> playerEntities) {
		// Adjust the increment value based on the speed you want the AI to chase the player
		float increment = 0.6f;

		// Initialize some default values for the player entity's position
		float playerPosX = 0;

		// Get the first player entity from the array, if it exists

		if (playerEntities.size > 0) {
			playerPosX = playerEntities.first().getPosX();
		}					

		//determine which direction  the entity should move to the player, 

		float direction = Math.signum(playerPosX - getPosX());	

		//moving distance of the entity based on chase increment and the direction calculated 

		float newPosX = getPosX() + increment * direction;	

		setPosX(newPosX);                                                                    
		updateCollider(newPosX, getPosY(), 32, 24);
	}

}