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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import com.mygdx.game.SceneManager;

public class AmpEngine extends ApplicationAdapter{
	private SceneManager sceneManager;
	private Scene scene;
	private SpriteBatch batch;
	
	
	
	@Override
	public void create() {
		Array<String> sceneJSONArr = new Array<String>();
		sceneJSONArr.add("Level1.json");
		//... add more if needed
		sceneManager = new SceneManager(sceneJSONArr);

		
	}
	


	@Override
	public void render() {
		sceneManager.clearScreen();
        sceneManager.loadScene(0);
        sceneManager.updateScene();
        



		
	}
	
	public AmpEngine() {
		
	}
}
