package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class SpikeEntity extends Entity {
	
	private long lastTextureChangeTime;


    private boolean moveRight;
    private float spikeSpeed;

    private Texture[] spikeTextures; // Array to store spike textures
    private int currentSpikeImageIndex; // Index of the current spike image

    public SpikeEntity(float initialPosX, float initialPosY, float spikeSpeed, Texture[] textures, float speed) {
        super(initialPosX, initialPosY, spikeSpeed, textures[0]);
        this.moveRight = true; // Set the initial direction to move right
        this.spikeSpeed = speed; // Set the speed of the spike

        // Initialize spikeTextures array with the provided textures
        spikeTextures = textures;
        currentSpikeImageIndex = 0; // Start with the first spike image
    }

    @Override
    public void update() {
        // Adjust speed based on your requirements
        float deltaTime = Gdx.graphics.getDeltaTime();
        float spikeMovement = spikeSpeed * deltaTime;

        if (moveRight) {
            setPosX(getPosX() + spikeMovement);
        } else {
            setPosX(getPosX() - spikeMovement);
        }

        // Check if spike has reached screen boundaries
        if (getPosX() < 0) {
            moveRight = true;
        } else if (getPosX() > Gdx.graphics.getWidth() - getTexture().getWidth()) {
            moveRight = false;
        }

        // Control the speed of transition between spike textures
        float transitionSpeed = 0.5f; // Adjust this value for the desired transition speed
        long currentTime = TimeUtils.millis();
        float timeElapsed = (currentTime - lastTextureChangeTime) / 1000.0f; // Convert to seconds

        if (timeElapsed > transitionSpeed) {
            // Increment currentSpikeImageIndex to smoothly transition between spike textures
            currentSpikeImageIndex++;
            if (currentSpikeImageIndex >= spikeTextures.length) {
                currentSpikeImageIndex = 0; // Reset to the first texture
            }

            setTexture(spikeTextures[(int) currentSpikeImageIndex]); // Set the current texture
            lastTextureChangeTime = currentTime; // Update the last texture change time
        }
    }




    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getPosX(), getPosY());
    }
}
