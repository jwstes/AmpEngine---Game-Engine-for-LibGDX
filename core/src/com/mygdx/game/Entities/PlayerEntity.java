package com.mygdx.game.Entities;


import java.util.List;

import com.badlogic.gdx.graphics.Texture;

/* This Class handles the Player Entity (Player's Character/Sprite) only.*/
/* This record character's name, health, x, y, speed, Texture tex, Texture[] animatedTexture, entityType, rec
// isKillable, isMovable, isAlive, isCollidable
// Inherits from Entity Class
/* Possible implementations to split into smaller classes possible. Possible addition such as guns, etc to be decided)*/

public class PlayerEntity extends Entity implements EntityFactoryInterface {
	
	private boolean isMovable;
	

    // Constructor
    public PlayerEntity(String n, float x, float y, Texture t){   // focus on making it appear, then add speed & health later
        super(n,x,y,t);
    }
    
    public PlayerEntity() {
		// TODO Auto-generated constructor stub
	}

	public void setIsMovable(boolean b) {
    	isMovable = b;
    }
    public boolean getIsMovable() {
    	return isMovable;
    }


	@Override
	public void update(long lastEntityUpdate) {
		// TODO Auto-generated method stub
	}

	@Override
	public Entity createEntity(int x, int y, Texture[] textures, boolean[] properties, String name) {
		PlayerEntity entity = new PlayerEntity("n", x, y, textures[0]);
		entity.setIsAlive(properties[0]);
        entity.setIsKillable(properties[1]);
        entity.setIsMovable(properties[2]);
        entity.setEntityType(name);
        entity.setIsCollidable(properties[4]);
        
		return entity;
	}
	
}
