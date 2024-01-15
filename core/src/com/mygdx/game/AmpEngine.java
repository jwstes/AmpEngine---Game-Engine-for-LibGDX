package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
	
	@Override
	public void create() {
		sceneManager = new SceneManager();
		sceneManager.setDeveloperLogo("badlogic.jpg");
		
		startTime = TimeUtils.nanoTime();
		selectedMenuIndex = 2;
	}
	
	
	@Override
	public void render() {
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
		
	}
	
	public AmpEngine() {
		
	}
}
