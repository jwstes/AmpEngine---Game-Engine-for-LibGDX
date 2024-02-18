package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.w3c.dom.Text;

public abstract class Entity {

	protected String name;
	private int health;
	private float x;
	private float y;
	private float speed;
	private Texture tex; // this is the overlaying texture (sprite image)
	private boolean isKillable; // Some "enemies" are meant to just attack like a floating canon
								// Not necessary for all games, see how you want to implement the logic.
	
	private boolean isAlive; // For logic that require a boolean way to check if the entity is alive.
							// You can also check if health = 0 but that's scuffed innit.
	
	private String entityType;
	
	private Rectangle rec;
	private boolean isCollidable;
															// name is identifier for scene so mandatory to set one
	public Entity(String name, float x, float y, Texture t){   //focus on making it appear, then add speed & health later
		this.x = x;
		this.y = y;
		this.tex = t;
		
		rec = new Rectangle(x, y, tex.getWidth(), tex.getHeight());
	}
	
	
	//for spike entity constructor, will be subjected to change to merge with adversarilEntity
	
	   public Entity(float x, float y, float speed) {
	    	this.x = x;
	    	this.y = y;
	    	this.speed = speed;
	    	
	    }

	    public Entity(float x, float y, float speed, Texture tex) {
	        this.x = x;
	        this.y = y;
	        this.speed = speed;
	        this.tex = tex;
	    }
	    
	    
	    public void drawBounds(ShapeRenderer shapeRenderer) {
	        Rectangle bounds = getRec();
	        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
	    }
	    public void setIsCollidable(boolean b) {
	        isCollidable = b;
	    }
	    public boolean getIsCollidable() {
	        return isCollidable;
	    }
	public String getEntityType() {
		return entityType;
	}
	public String setEntityType(String s) {
		return entityType = s;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}
	
	
	public void setIsAlive(boolean b) {
		isAlive = b;
	}
	public boolean getIsAlive() {
		return isAlive;
	}
	public void setIsKillable(boolean b) {
		isKillable = b;
	}
	public boolean getIsKillable() {
		return isKillable;
	}
	


	// Setter & Getter
	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth(){
		return this.health;
	}

	public void setPosX(float newPosX){
		this.x = newPosX;
	}
	public float getPosX(){
		return this.x;
	}

	public void setPosY(float newPosY){
		this.y = newPosY;
	}
	public float getPosY(){
		return this.y;
	}

	public void setSpeed(float speed){
		this.speed = speed;
	}
	public float getSpeed(){
		return this.speed;
	}

	public void setTexture(Texture assetName){
		this.tex = assetName;
	}
	
	public Texture getTexture() { // Add this method
        return tex;
    }
	
	public void compile() {
//		rec = new Rectangle(x, y, tex.getWidth(), tex.getHeight());
		rec = new Rectangle(x, y, tex.getWidth(), tex.getHeight());
	}
	public Rectangle getRec() {
		return rec;
	}
	
	public void draw(SpriteBatch b) {
		if (tex != null) {
            b.draw(tex, x, y);
        }
	}
	
	//
	
	public abstract long update(long lastEntityUpdate);
	
	public void render(SpriteBatch b) {}
	
	




}

