package com.mygdx.game;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controller;
import com.badlogic.gdx.controllers;
import com.badlogic.gdx.ControllerAdapter;


public abstract class IOManager extends SceneManager implements InputProcessor{
	private static final int GET_WEAPON_KEY = Input.Keys.G;
	private static final int DROP_WEAPON_KEY = Input.Keys.D;
	private static final int SEE_INVENTORY = Input.Keys.I;
	//MyInputProcessor inputProcessor = new MyInputProcessor();
	//Gdx.input.setInputProcessor(inputProcessor); // to be in GameMaster class
	//map the integer key to objects, key-value pairs
	private HashMap<Integer, Runnable> mappingsToKey = new HashMap<>(); 
	private HashMap<Integer, Runnable> mappingsToJoystick = new HashMap<>();
	private Controller controller;
	
	
	public IOManager() {
		 //mapping of keys to function
		 mappingsToKey.put(Input.Keys.LEFT, () -> setPosX(getPosX() - PLAYER_SPEED * Gdx.graphics.getDeltaTime()));
		 mappingsToKey.put(Input.Keys.A, () -> setPosX(getPosX() - PLAYER_SPEED * Gdx.graphics.getDeltaTime()));
		 
		 mappingsToKey.put(Input.Keys.RIGHT, () -> setPosX(getPosX() + PLAYER_SPEED * Gdx.graphics.getDeltaTime()));	 
		 mappingsToKey.put(Input.Keys.D, () -> setPosX(getPosX() + PLAYER_SPEED * Gdx.graphics.getDeltaTime()));
		 
		 mappingsToKey.put(Input.Keys.UP, () -> setPosY(getPosY() + PLAYER_SPEED * Gdx.graphics.getDeltaTime()));
		 mappingsToKey.put(Input.Keys.W, () -> setPosY(getPosY() + PLAYER_SPEED * Gdx.graphics.getDeltaTime()));
		 
		 mappingsToKey.put(Input.Keys.DOWN, () -> setPosY(getPosY() - PLAYER_SPEED * Gdx.graphics.getDeltaTime()));
		 mappingsToKey.put(Input.Keys.S, () -> setPosY(getPosY() - PLAYER_SPEED * Gdx.graphics.getDeltaTime()));
		 
		 mappingsToKey.put(Input.Keys.SHIFT_LEFT, () -> setPosY(getPosX() + PLAYER_SPEED * SPRINT_ENHANCER * deltaTime));
		 mappingsToKey.put(Input.Keys.SPACE, () -> setPosY(getPosY() + JUMP_POWER * deltaTime));
		 
		 mappingsToKey.put(Input.Buttons.LEFT, () -> leftButtonClick());
		 mappingsToKey.put(Input.Buttons.RIGHT, () -> rightButtonClick());
	}
	
	private Object rightButtonClick() {
		// TODO Auto-generated method stub
		return null;
	}

	private Object leftButtonClick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean keyDown(int keyPressed) {
		//Error Handling
		Runnable keyAction;
		try 
		{
			keyAction = mappingsToKey.get(keyPressed);
			if (keyAction != null) {
				keyAction.run();
				return true;
			
			return false;
			//handleInput(keyPressed);
			//return true;
		}
		catch (Exception e) {
			System.out.printf("Error processing key down event: %s%n", e.getMessage());
			return false;
		}
	}
	
	public boolean touchDown (int x, int y, int pointer, int button) {
	      if (button == Input.Buttons.LEFT){
	    	  leftButtonClick();
	      }
	      else if (button == Input.Buttons.RIGHT){
	    	  rightButtonClick();
	      }
	      else {
	    	  return false;
	      }
	      return true; // return true to indicate the event was handled
	}
	
	public boolean touchUp (int x, int y, int pointer, int button) {
	      // your touch up code here
	      return true; // return true to indicate the event was handled
	}
	
	/*private void handleInput(int keyPressed) {
		float deltaTime = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            setPosX(getPosX() - PLAYER_SPEED * deltaTime);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.isKeyPressed(Input.Keys.D)) {
            setPosX(getPosX() + PLAYER_SPEED * deltaTime);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)|| Gdx.input.isKeyPressed(Input.Keys.W)){
            setPosY(getPosY() + PLAYER_SPEED * deltaTime);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            setPosY(getPosY() - PLAYER_SPEED * deltaTime);
        }
        else {
        	if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                setPosY(getPosX() + PLAYER_SPEED * SPRINT_ENHANCER * deltaTime);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                setPosY(getPosY() + JUMP_POWER * deltaTime);
            }
            
            
        }
        
	} */
}
