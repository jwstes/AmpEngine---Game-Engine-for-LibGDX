package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/***********************************************************************
 * The Dashboard class is used for the Display of Health Points        *
 * it also manages methods like resetting of Dashbaords and rendering  *
 * of Sprites                                                          *
 * ******************************************************************* */
public class Dashboard implements DashboardInterface {
    private DashboardManager dashboardManager;
    private SceneManager sceneManager; 
 
    public Dashboard(int maxHealth, BitmapFont font, Texture healthSprite) {
        this.dashboardManager = new DashboardManager(maxHealth, font, healthSprite);
    }
    public void update() {}
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

    public void resetDashboard() {
        int maxHealth = dashboardManager.getMaxHealth();;
        dashboardManager.setCurrentHealth(maxHealth);

        Texture[] healthSprites = new Texture[maxHealth];
        for (int i = 0; i < dashboardManager.getCurrentHealth(); i++) {
            healthSprites[i] = dashboardManager.getHealthSprites()[0];
        }

        dashboardManager.setHealthSprites(healthSprites);
        dashboardManager.resetStartTime();
        System.out.println("Current Health set to max Health again : " + maxHealth);
    }




    public DashboardManager getDashboardManager(){
        return dashboardManager;
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
    
    //Setter method for SceneManager reference
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }


}

