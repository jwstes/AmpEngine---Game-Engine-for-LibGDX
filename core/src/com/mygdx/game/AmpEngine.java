package com.mygdx.game;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import com.mygdx.game.SceneManager;

public class AmpEngine extends ApplicationAdapter{
	private SceneManager sceneManager;
	
	//Experimental Collider. Can Delete Later
	private long startTime;
	private long lastCheckTime;
	
//	private int selectedMenuIndex;
	
	private Scene scene;
	
	@Override
	public void create() {
		Array<String> sceneJSONArr = new Array<String>();
		sceneJSONArr.add("Level1.json");
		//... add more if needed
		sceneManager = new SceneManager(sceneJSONArr);
		
		
		//Experimental Collider. Can Delete Later
		startTime = System.currentTimeMillis();
		lastCheckTime = System.currentTimeMillis();

		
		
		
		//		selectedMenuIndex = 2;
		
	}
	


	@Override
	public void render() {
		sceneManager.clearScreen();
        sceneManager.loadScene(0);
        
        
        //Experimental Collider. Can Delete Later
        if(System.currentTimeMillis() >= (lastCheckTime + 1000)) {
        	System.out.println("Checking For Collision");
        	sceneManager.checkCollision();
        	lastCheckTime = System.currentTimeMillis();
        }
        
        
        
        
        
        
        
        
        
//        batch.begin();
        
        // Draw all entities
        
//        for (GameEntity entity : scene.getEntities()) {
//            entity.draw(batch);
//        }
//        batch.end();
        
		/*
		boolean splashScreen = sceneManager.displaySplashScreen(true, 2, startTime);
		if(splashScreen) {
			String[] menuItems = {"Exit", "Options", "Start Game"};
			Runnable[] actions = {
	                () -> System.out.println("Exit selected"),
	                () -> System.out.println("Options selected"),
	                () -> System.out.println("Start Game selected")
	        };
			selectedMenuIndex = sceneManager.displayMenu(menuItems, actions, selectedMenuIndex);
		}
		*/
		
	}
	
	public AmpEngine() {
		
	}
}
