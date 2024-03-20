package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/***********************************************************************
 * The Dashboard class is used for the Display of Health Points        *
 * it also manages methods like resetting of Dashboards and rendering  *
 * of Sprites                                                          *
 * ******************************************************************* */
public class Dashboard implements DashboardInterface {
    private DashboardManager dashboardManager;
    private SceneManager sceneManager; // New addition
    
    
    //CONSTRUCTOR
    public Dashboard(int maxHealth, BitmapFont font, Texture healthSprite) {
        this.dashboardManager = new DashboardManager(maxHealth, font, healthSprite);
    }
    
    
    //GETTER & SETTER METHODS
    public DashboardManager getDashboardManager(){
        return dashboardManager;
    }
    public void setDashboardManager(DashboardManager d) {
    	dashboardManager = d;
    }
    public SceneManager getSceneManager() {
    	return sceneManager;
    }
    
    
    public void displayHealthText(SpriteBatch batch) {
        // Draw "Health:" text
        dashboardManager.getFont().setColor(Color.WHITE);
        dashboardManager.getFont().getData().setScale(1.5f);
        dashboardManager.getFont().draw(batch, "Health:", 20, Gdx.graphics.getHeight() - 20);
    }

    public void displayTimer(SpriteBatch batch) {
        float spriteX = 100; // Assuming this is the initial x-coordinate for health sprites
        float posX = spriteX + 150; // Align with the end of health sprites
        float posY = Gdx.graphics.getHeight() - 20; // Align vertically with the "Health:" text

        // Draw time passed text beside the health status
        long elapsedTimeSeconds = dashboardManager.getTimePassedSeconds();
        String timePassedText = "Time Passed: " + elapsedTimeSeconds + "s";
        dashboardManager.getFont().draw(batch, timePassedText, posX, posY);
    }

    public void render(SpriteBatch batch) {
    	displayTimer(batch);
    	displayHealthText(batch);
    	dashboardManager.drawOnScene(batch);
    	
    	
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
    
    public void test() {
    	sceneManager.DisplayText();
    }
    


	@Override
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


	@Override
    //Setter method for SceneManager reference
    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}