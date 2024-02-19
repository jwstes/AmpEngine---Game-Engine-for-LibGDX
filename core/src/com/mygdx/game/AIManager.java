package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class AIManager extends Entity {
	
    private float initialPosX;
    private float initialPosY;
 

    private Texture[] entityTexture; // Array to store entity textures
  
    
    
    public Texture[] getTextures() {
    	return entityTexture;
    }
    
    public void setInitialPosX(float initialPosX) {
        this.initialPosX = initialPosX;
    }
    
    public float getInitialPosX() {
        return initialPosX;
    }

    public AIManager(float x, float y, Texture[] textures) {
        super("AIManager", x, y, textures[0]);
        entityTexture = textures;
        initialPosX = x; // Set the initial X position
        initialPosY = y; // Set the initial X position
    }

    public float getWidth() {
        return entityTexture[0].getWidth(); // Assuming all textures have the same width
    }

    public float getHeight() {
        return entityTexture[0].getHeight(); // Assuming all textures have the same height
    }
    	
    public void setInitialPosY(float initialPosY) {
        this.initialPosY = initialPosY;
    }

    
    public float getInitialPosY() {
        return initialPosY;
    }

    @Override
    public long update(long lastEntityUpdate) {
    	
        return lastEntityUpdate;
    }
    
    public boolean getIsMovable() {

        return true; // Replace with your actual logic
    }



}