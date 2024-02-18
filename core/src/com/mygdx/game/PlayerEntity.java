package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;

/* This Class handles the Player Entity (Player's Character/Sprite) only.*/
/* This record character's health, positioning, image texture and hitbox(rectangle) */
/* Possible implementations to split into smaller classes possible. Possible addition such as guns, etc to be decided)*/
public class PlayerEntity extends Entity {
	
	private boolean isMovable;
	


    // Constructor
    public PlayerEntity(String n, float x, float y, Texture t){   // focus on making it appear, then add speed & health later
        super(n,x,y,t);
    }
    
    public void setIsMovable(boolean b) {
    	isMovable = b;
    }
    public boolean getIsMovable() {
    	return isMovable;
    }

	@Override
	public long update(long lastEntityUpdate) {
		// TODO Auto-generated method stub
		return 0;
	}


}
