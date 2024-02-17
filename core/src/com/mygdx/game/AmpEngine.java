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
//	private long startTime;
	
//	private int selectedMenuIndex;
	
	private Scene scene;
	
	@Override
	public void create() {
		Array<String> sceneJSONArr = new Array<String>();
		sceneJSONArr.add("Level1.json");
		//... add more if needed
		sceneManager = new SceneManager(sceneJSONArr);
		
		
		
//		startTime = TimeUtils.nanoTime();
//		selectedMenuIndex = 2;
		
		
		
//		scene = new Scene();
		
		//========================================================Test getting JSON==========================================
//	    scene.ParseFromJSON("Level1.json");
//	    scene.createEntities();
	    //Order : isAlive , isKillable , isMovable , isBreakable
//	    for (boolean[] properties : scene.GetEntityProperty()) {
//	        System.out.println(Arrays.toString(properties));
//	    }
	    //===================================================================================================================
	}
	


	@Override
	public void render() {
		sceneManager.clearScreen();
        sceneManager.loadScene(0);
        
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
