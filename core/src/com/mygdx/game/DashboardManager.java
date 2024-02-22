package com.mygdx.game;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

// Game Engine
public class DashboardManager  {
    private int maxHealth; // Maximum health level
    private int currentHealth; // Current health level
    private long startTime; // Calculate time passed
    private BitmapFont font; // Font for displaying timer
    private Texture[] healthSprites; // Array of health sprites
    
    //CONSTRUCTOR
    public DashboardManager(int maxHealth, BitmapFont font, Texture healthSprite) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth; // Start with full health
        this.font = font; // Assign the font for displaying timer
        resetStartTime(); // Reset the start time
        this.healthSprites = new Texture[maxHealth]; // Create array to hold player textures
        Arrays.fill(healthSprites, healthSprite); // Initialize all elements with the same health sprite
    }
    
    
    //GETTER & SETTER
    public long getTimePassedSeconds() {
        return (TimeUtils.nanoTime() - startTime) / 1000000000;
    }
    
    
    public Texture[] getHealthSprites() {
        return healthSprites;
    }
    public void setHealthSprites(Texture[] healthSprites) {
        this.healthSprites = healthSprites;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(int x) {
    	maxHealth = x;
    }
    public BitmapFont getFont() {
        return font;
    }
    public void setFont(BitmapFont b) {
    	font = b;
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
    
    
    //CLASS METHODS
    public void displayHealthText(SpriteBatch batch) {
        // Draw "Health:" text
        font.setColor(Color.WHITE);
        font.getData().setScale(1.5f);
        font.draw(batch, "Health:", 20, Gdx.graphics.getHeight() - 20);
    }

    public void displayTimer(SpriteBatch batch) {
        float spriteX = 100; // Assuming this is the initial x-coordinate for health sprites
        float posX = spriteX + 150; // Align with the end of health sprites
        float posY = Gdx.graphics.getHeight() - 20; // Align vertically with the "Health:" text

        // Draw time passed text beside the health status
        long elapsedTimeSeconds = getTimePassedSeconds();
        String timePassedText = "Time Passed: " + elapsedTimeSeconds + "s";
        font.draw(batch, timePassedText, posX, posY);
    }

    public void drawOnScene(SpriteBatch batch) {
        displayHealthText(batch);
        displayTimer(batch);
    }

    public void resetStartTime() {
        startTime = TimeUtils.nanoTime();
    }

    

}
