package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Entity {
    protected float posX;
    protected float posY;
    private float speed;
    private Texture texture; // Add this field
    
    public Entity(float posX, float posY, float speed) {
    	this.posX = posX;
    	this.posY = posY;
    	this.speed = speed;
    	
    }

    public Entity(float posX, float posY, float speed, Texture texture) {
        this.posX = posX;
        this.posY = posY;
        this.speed = speed;
        this.texture = texture;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Texture getTexture() { // Add this method
        return texture;
    }

    public void setTexture(Texture texture) { // Add this method
        this.texture = texture;
    }

    public void draw(ShapeRenderer shape) {
        // Implementation for shape drawing
    }

    public void draw(SpriteBatch batch) {
        // Implementation for batch drawing
    }

    public abstract void update();
    
    public void render(SpriteBatch batch) {}
}



