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
import com.badlogic.gdx.audio.*;

import com.mygdx.game.SceneManager;
import java.util.HashMap;
import java.util.Map;

public class AmpEngine extends ApplicationAdapter{
	private SceneManager sceneManager;
	public EntityManager entityManager;
	private Scene scene;
	private SpriteBatch batch;

	private boolean damageTaken;
	
	private PlayerControl playerControl;
	private PlayerEntity player;
	private CollisionManager collisionManager;
	private InputManager inputManager;
	

	@Override
	public void create() {
		Array<String> sceneJSONArr = new Array<String>();
		sceneJSONArr.add("Level1.json");
		//... add more if needed

		sceneManager = new SceneManager(sceneJSONArr);
		sceneManager.populateScene(0);
		sceneManager.initializeCollisionManager();
		collisionManager = sceneManager.getCollisionManager();

		player = sceneManager.entityManager.getAllPEntity().get(0);


		/* **************************
		 	Set KeyBindings in playerControl
		   ************************* */

		playerControl = new PlayerControl(player, sceneManager, collisionManager);
		playerControl.bindKey(Keys.LEFT, () -> playerControl.moveLeft());
		playerControl.bindKey(Keys.RIGHT, () -> playerControl.moveRight());
		playerControl.bindKey(Keys.SPACE, () -> playerControl.jump());
		sceneManager.setPlayerControl(playerControl);



		inputManager = new InputManager(sceneManager);
		
		Map<String, Sound> soundList = new HashMap<>();
		soundList.put("walking", inputManager.loadSound("walking.mp3"));

		sceneManager.outputManager.setSoundList(soundList);
	}
	


	@Override
	public void render() {
		sceneManager.clearScreen();
		sceneManager.loadScene(0);
//        sceneManager.drawCollider();
        sceneManager.updateScene();


        inputManager.runnable();
        inputManager.CCRunnable("onGround");
        Boolean anyKeyDown = inputManager.isAnyKeyDown();
        if(anyKeyDown != true) {
        	sceneManager.outputManager.stopAllSound();
        }
        
        
	}
	
	public AmpEngine() {
		
	}
}









