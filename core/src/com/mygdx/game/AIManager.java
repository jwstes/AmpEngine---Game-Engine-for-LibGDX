package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class AIManager extends Entity {

    private List<MovementPosition> movementPositions;
    private int movementIndex;
    private long lastMoveTime;
    private int movementWaitInterval; // in milliseconds
    private boolean loopBehaviour;
    private boolean fullyErected; // Flag to track if the spike is fully erected

    private Texture[] spikeTextures; // Array to store spike textures
    private int currentSpikeImageIndex; // Index of the current spike image

    public AIManager(float initialPosX, float initialPosY) {
        super(initialPosX, initialPosY, 0); // Call the parameterized constructor of the Entity class with appropriate values
        movementPositions = new ArrayList<>();
        movementIndex = 0;
        lastMoveTime = TimeUtils.millis();
        movementWaitInterval = 300; // default interval is 1 second
        loopBehaviour = false; // default behavior is no loop

        // Initialize spikeTextures array with 3 spike images, adjust accordingly if need longer spike
        spikeTextures = new Texture[3];
        for (int i = 0; i < 3; i++) {
            spikeTextures[i] = new Texture("spike" + (i + 1) + ".png");
        }

        currentSpikeImageIndex = 0; // Start with the first spike image
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

    public void setWaitInterval(int interval) {
        movementWaitInterval = interval;
    }

    public List<MovementPosition> getPositions() {
        return movementPositions;
    }

    @Override
    public void update() {
        // Check if there are any movement positions
        if (!movementPositions.isEmpty()) {
            // Check if enough time has passed to move to the next position
            if (TimeUtils.timeSinceMillis(lastMoveTime) > movementWaitInterval) {
                fullyErected = (currentSpikeImageIndex == 2);
                if (fullyErected) {
                    MovementPosition currentPosition = movementPositions.get(movementIndex);
                    setPosX(currentPosition.x);
                    setPosY(currentPosition.y);
                    movementIndex++;

                    // Reset movementIndex to 0 if it reaches the end of the list
                    if (movementIndex >= movementPositions.size()) {
                        movementIndex = 0;
                    }
                } else if (loopBehaviour) {
                    movementIndex = 0; // reset to the first position if looping
                }

                // Increment currentSpikeImageIndex to switch to the next spike image
                currentSpikeImageIndex = (currentSpikeImageIndex + 1) % 3;

                // Check if the spike is fully erected (reached the last frame)
                fullyErected = (currentSpikeImageIndex == 2);

                lastMoveTime = TimeUtils.millis();
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Render the enemy AI with spikes based on the current movement position
        if (!movementPositions.isEmpty()) {
            MovementPosition currentPosition = movementPositions.get(movementIndex);
            Texture spikeTexture = spikeTextures[currentSpikeImageIndex];

            // Adjust the position as needed
            float spikeX = currentPosition.x - spikeTexture.getWidth() / 2; // Center the spike on X-axis
            float spikeY = currentPosition.y - spikeTexture.getHeight() / 2; // Center the spike on Y-axis

            batch.draw(spikeTexture, spikeX, spikeY);
        }
        //System.out.println("AIManager draw method is called");
    }

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