package com.mygdx.game;


import com.badlogic.gdx.graphics.Texture;

/* This Class handles the Player Entity (Player's Character/Sprite) only.*/
/* This record character's name, health, x, y, speed, Texture tex, Texture[] animatedTexture, entityType, rec
// isKillable, isMovable, isAlive, isCollidable
// Inherits from Entity Class
/* Possible implementations to split into smaller classes possible. Possible addition such as guns, etc to be decided)*/

public class PlayerEntity extends Entity {
	
	private boolean isMovable;
	

	public PlayerEntity(PlayerEntity player) {
		player = new PlayerEntity();
		player.setPosX(200);
	}
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

	public void update(float deltaTime) {
		setPosX(getPosX() + 2 * deltaTime);
		//System.out.print("ITS MOVING????");
	}

	@Override
	public void update(long lastEntityUpdate) {
		// TODO Auto-generated method stub
	}
	
}
