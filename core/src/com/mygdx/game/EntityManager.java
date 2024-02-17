package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class EntityManager{
	
	
	
	private List<Entity> entityList;


	  
	private int health;
	private float posX;
	private float posY;

	public Rectangle rectangle; // Stores a gdx.math.Rectangle instance. // this is the hitbox

	public Texture texture; // this is the overlaying texture (sprite image)



	// GETTER SETTERS --------------------------------
	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth(){
		return this.health;
	}

	public void setPosX(float newPosX){
		this.posX = newPosX;
	}
	public float getPosX(float newPosX){
		return this.posX;
	}

	public void setPosY(float newPosY){
		this.posY = newPosY;
	}
	public float getPosY(float newPosY){
		return this.posY;
	}





	// GENERIC METHODS --------------------------------
	// Check if player is still alive
	public boolean isAlive(){
		int hp = this.health;
		return hp > 0;
	}

	// Load image of player entity
	public Texture setTexture(String filename){
		Texture instanceImage;
		instanceImage = new Texture(Gdx.files.internal(filename));
		this.texture = instanceImage;
		return instanceImage;
	}


	// CONSTRUCTORS --------------------------------
	// generic instance of player entity // player is centered by default
	public EntityManager() {
		this.health = 10;
		this.posX = (float) Gdx.graphics.getWidth() / 4 ;
		this.posY = (float) Gdx.graphics.getHeight() / 3;   // Locate entity near bottom left
		this.rectangle = new Rectangle();
		
		
	
		
		entityList = new ArrayList<>();

	       
			
		
	}
	
	   // Add an entity to the list
    public void addEntity(Entity entity) {
        entityList.add(entity);
    }
    
	// specific parameters for player entity
	public EntityManager(int initialHealth){
		this.health = initialHealth;
		this.posX = (float) Gdx.graphics.getWidth() / 4;
		this.posY = (float) Gdx.graphics.getHeight() / 3;
		this.rectangle = new Rectangle();
	}

	public EntityManager(int initialHealth, Rectangle rectangleInstance) {
		this.health = initialHealth;
		this.posX = (float) Gdx.graphics.getWidth() / 4;
		this.posY = (float) Gdx.graphics.getHeight() / 3;
		this.rectangle = rectangleInstance;
	}

	public EntityManager(int initialHealth, float initial_posX, float initial_posY) {
		this.health = initialHealth;
		this.posX = initial_posX;
		this.posY = initial_posY;
		this.rectangle = new Rectangle();
	}

	public EntityManager(int initialHealth, float initial_posX, float initial_posY, Rectangle rectangleInstance) {
		this.health = initialHealth;
		this.posX = initial_posX;
		this.posY = initial_posY;
		this.rectangle = rectangleInstance;
	}
	
	
	//

	
	
	public void updateEntities() {
	    for (Entity entity : entityList) {
	        entity.update();
	    }
	}

	public void drawEntities(SpriteBatch batch) {
	    for (Entity entity : entityList) {
	        entity.draw(batch);
	        
            System.out.println("Drawing entity: " + entity.getClass().getSimpleName());

	    }
	    
	}

	   
	   public List<Entity> getAllEntities() {
	        return entityList;
	    }

	   
	   public void removeEntity(Entity entity) {
	        entityList.remove(entity);
	    }
	   
	   public void clearAllEntities() {
	        entityList.clear();
	    }
	  
	  
	   
	   
	
}

