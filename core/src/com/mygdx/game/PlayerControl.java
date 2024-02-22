package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class PlayerControl {
	private Map<Integer, Runnable> keyBindings = new HashMap<>();

	float verticalVelocity = 0;
    final float GRAVITY = -500;
    boolean isOnGround = true;


    // General Functions
    public void bindKey(int key, Runnable action) {
        keyBindings.put(key, action);
    }
    public Map<Integer, Runnable> getKeyBindings(){
        return keyBindings;
    }
    
    //Specific Functions to player
    public void setVerticalVelocity(float x) {
    	this.verticalVelocity = x;
    }
    public void updateVerticalVelocity(float x) {
    	this.verticalVelocity += this.GRAVITY * x;
    }
    public float getVerticalVelocity() {
    	return this.verticalVelocity;
    }
    public boolean getIsOnGround() {
    	return this.isOnGround;
    }
    public void setIsOnGround(boolean b) {
    	this.isOnGround = b;
    }
    



}

















