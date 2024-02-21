package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

// Game Logic
public class Dashboard implements DashboardInterface {
    private DashboardManager dashboardManager;
    private SceneManager sceneManager; // New addition

    public Dashboard(int maxHealth, BitmapFont font, Texture healthSprite) {
        this.dashboardManager = new DashboardManager(maxHealth, font, healthSprite);
    }

    public void update() {
        // Update any necessary logic related to the dashboard
    }

    public void render(SpriteBatch batch) {
        dashboardManager.drawOnScene(batch);
        displayHealthSprites(batch);
    }
    
    
    public void reduceHealth(int amount) {
        int currentHealth = dashboardManager.getCurrentHealth();
        currentHealth -= amount;
        dashboardManager.setCurrentHealth(currentHealth);

        // Check if current health is 0
        if (currentHealth <= 0) {
            // If health is 0 or negative, switch to the game over scene
        	sceneManager.loadGameOverScene();
        } else {
            // Resize the health sprites array to match the updated current health
            Texture[] healthSprites = new Texture[currentHealth];
            for (int i = 0; i < currentHealth; i++) {
                healthSprites[i] = dashboardManager.getHealthSprites()[i];
            }
            dashboardManager.setHealthSprites(healthSprites);
        }
    }
    
    public void displayHealthSprites(SpriteBatch batch) {
        float spriteX = 100; // Initial x-coordinate for health sprites
        float spriteY = Gdx.graphics.getHeight() - 20 - dashboardManager.getFont().getLineHeight(); // Align sprites with the bottom of the text

        for (int i = 0; i < dashboardManager.getMaxHealth(); i++) { // Iterate over maxHealth instead of currentHealth
            if (i < dashboardManager.getCurrentHealth()) { // Check if the current index is within the current health range
                Texture health = dashboardManager.getHealthSprites()[i]; // Assuming each health sprite is different
                batch.draw(health, spriteX, spriteY);
                spriteX += health.getWidth() + 10; // Update x-coordinate for next sprite
            }
        }
    }
    
 // Setter method for SceneManager reference
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}