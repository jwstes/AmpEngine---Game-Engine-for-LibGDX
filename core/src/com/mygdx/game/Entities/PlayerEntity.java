package com.mygdx.game.Entities;


import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

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
		
	}

	public void setIsMovable(boolean b) {
    	isMovable = b;
    }
    public boolean getIsMovable() {
    	return isMovable;
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
	@Override
	public void drawBounds(ShapeRenderer shapeRenderer) {
		Rectangle bounds = getRec();
		shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		
	}
	@Override
	public void draw(SpriteBatch b) {
		if (getTexture() != null) {
            b.draw(getTexture(), getPosX(), getPosY());
        }
	}
	
}
