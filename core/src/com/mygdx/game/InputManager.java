package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.audio.*;

import java.util.HashMap;
import java.util.Map;

public class InputManager{
	private Map<Integer, Runnable> keyBindings;
	private Map<Integer, Boolean> keyStatus; //Key, false = Up
	
	private SceneManager sceneManager;
	private PlayerControl playerControl;
	
	private Runnable continuousConditionalAction;
 	
	public void runnable() {
		keyBindings.forEach((key, action) -> {
            if (Gdx.input.isKeyPressed(key)) {
            	
            	if(key == 62) { // Space Pressed
            		continuousConditionalAction = action;
            	}
            	
                action.run();
                
                keyStatus.put(key, true);
            }
            else {
            	keyStatus.put(key, false);
            }
        });
	}
	
	public void CCRunnable(String condition) {
    	if(continuousConditionalAction != null) {
    		continuousConditionalAction.run();
    		
    		switch(condition) {
	    		case "onGround":
	    			if(playerControl.getIsOnGround() == true) {
	            		continuousConditionalAction = null;
	            	}
	    			break;
    		}
    	}
    }
	
	public boolean isAnyKeyDown() {
		for(Boolean status : keyStatus.values()) {
			if(status == true) {
				return true;
			}
		}
		return false;
	}
	
	
	public Sound loadSound(String fileName) {
		return Gdx.audio.newSound(Gdx.files.internal(fileName));
	}
	
	
	public InputManager(SceneManager scm) {
		sceneManager = scm;
		keyBindings = scm.playerControl.getKeyBindings();
		playerControl = scm.playerControl;
		
		keyStatus = new HashMap<>();
		
		keyBindings.forEach((key, action) -> {
            keyStatus.put(key, false); // Key , Up
        });
	}
	
	
}
