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

import com.mygdx.game.SceneManager;

public class AmpEngine extends ApplicationAdapter{
	private SceneManager sceneManager;
	private long startTime;
	
	private int selectedMenuIndex;
	
	private Scene scene;
	private SpriteBatch batch;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		sceneManager = new SceneManager();
		//sceneManager.setDeveloperLogo("badlogic.jpg");
		
		startTime = TimeUtils.nanoTime();
		selectedMenuIndex = 2;
		
		scene = new Scene();

		//========================================================Test getting JSON==========================================
	    scene.ParseFromJSON("Level1.json");
	    scene.createEntities();
	    //Order : isAlive , isKillable , isMovable , isBreakable
	    for (boolean[] properties : scene.GetEntityProperty()) {
	        System.out.println(Arrays.toString(properties));
	    }
	    //===================================================================================================================
	}
	


	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Draw all entities
        for (GameEntity entity : scene.getEntities()) {
            entity.draw(batch);
        }
        batch.end();
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
