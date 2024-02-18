package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class AIManager extends Entity {

    private List<MovementPosition> movementPositions;
    private int movementIndex;

    private boolean loopBehaviour;
    private boolean fullyErected; // Flag to track if the spike is fully erected

    private Texture[] spikeTextures; // Array to store spike textures
    private static int textureID;
    
    private Rectangle rec = new Rectangle();
    
    public Texture[] getTextures() {
    	return spikeTextures;
    }

    public AIManager(float initialPosX, float initialPosY, Texture[] textures) {
        super("n",initialPosX, initialPosY, textures); // Call the parameterized constructor of the Entity class with appropriate values
        movementPositions = new ArrayList<>();
        movementIndex = 0;
        loopBehaviour = false;
        spikeTextures = textures;
        //rec = new Rectangle(x, y, tex.getWidth(), tex.getHeight());
    }

    public void makeLoop(boolean loop) {
        loopBehaviour = loop;
    }

    public void setPositions(List<MovementPosition> positions) {
        movementPositions = positions;
    }

    public void addPositions(float x, float y) {
        movementPositions.add(new MovementPosition(x, y));
    }

    public List<MovementPosition> getPositions() {
        return movementPositions;
    }

    @Override
    public long update(long lastEntityUpdate) {
//        if (!movementPositions.isEmpty()) {
//        	
//        	System.out.println("---");
//        	System.out.println(System.currentTimeMillis());
//        	System.out.println((lastEntityUpdate + 300));
//        	
//            if (System.currentTimeMillis() >= (lastEntityUpdate + 300)) {
//            	if(textureID < 4) {
//            		textureID++;
//            	}
//            	else {
//            		textureID = 0;
//            	}
//            	
//            	
//            	this.setTexture(spikeTextures[textureID]);
//                return System.currentTimeMillis();
//            }
//
//        }
        return lastEntityUpdate;
    }

//    @Override
//    public void draw(SpriteBatch batch) {
//        // Render the enemy AI with spikes based on the current movement position
//        if (!movementPositions.isEmpty()) {
//            MovementPosition currentPosition = movementPositions.get(movementIndex);
//            Texture spikeTexture = spikeTextures[currentSpikeImageIndex];
//
//            // Adjust the position as needed
//            float spikeX = currentPosition.x - spikeTexture.getWidth() / 2; // Center the spike on X-axis
//            float spikeY = currentPosition.y - spikeTexture.getHeight() / 2; // Center the spike on Y-axis
//
//            batch.draw(spikeTexture, spikeX, spikeY);
//        }
//    }

    // Inner static class for MovementPosition
    public class MovementPosition {
        public float x;
        public float y;

        public MovementPosition(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}