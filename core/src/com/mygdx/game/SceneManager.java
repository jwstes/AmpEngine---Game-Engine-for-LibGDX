package com.mygdx.game;
import com.mygdx.game.AmpEngine;
import com.mygdx.game.EntityManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.TimeUtils;



public class SceneManager{
	// INSTANCES AND VARIABLES
	private EntityManager entityManager;
	private Texture developerLogo;
	
	// METHODS
	public void clearScreen() {
		ScreenUtils.clear(100, 100, 100, 1);
	}
	
	public float[] getWindowSize() {
		float[] size = {
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
		};
	    return size;
	}
	
	public void setDeveloperLogo(String assetName) {
		developerLogo = entityManager.loadTexture(assetName);
	}
	
	public void displaySplashScreen(boolean timerMode, int seconds, long startTime) {
		clearScreen();
		
		SpriteBatch spriteBatch = new SpriteBatch();
		spriteBatch.begin();
		
		float[] windowSize = getWindowSize();
		float x = (windowSize[0] - developerLogo.getWidth()) / 2f;
	    float y = (windowSize[1] - developerLogo.getHeight()) / 2f;
	    
		spriteBatch.draw(developerLogo, x, y);
		spriteBatch.end();
		
		if(timerMode == true) {
			float elapsedTime = (TimeUtils.nanoTime() - startTime) / 1000000000.0f;
			if(elapsedTime > seconds) {
				clearScreen();
			}
		}
		
		
	}
	
	public SceneManager() {
		entityManager = new EntityManager();
	}
	
}