package com.mygdx.game.Scene;

import java.util.Arrays;

import com.badlogic.gdx.Gdx;
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

    public void resetStartTime() {
        startTime = TimeUtils.nanoTime();
    }
    
    public void displayHealthSprites(SpriteBatch batch) {
        float spriteX = 230; // Initial x-coordinate for health sprites
        float spriteY = Gdx.graphics.getHeight() - getFont().getLineHeight(); // Align sprites with the bottom of the text

        for (int i = 0; i < getMaxHealth(); i++) { // Iterate over maxHealth instead of currentHealth
            if (i < getCurrentHealth()) { // Check if the current index is within the current health range
                Texture health = getHealthSprites()[i]; // Assuming each health sprite is different
                batch.draw(health, spriteX, spriteY);
                spriteX += health.getWidth() + 10; // Update x-coordinate for next sprite
            }
        }
    }

    public void drawOnScene(SpriteBatch batch) {
        displayHealthSprites(batch);
    }
    
    

}