package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;

public class AnimatedEntity extends Entity {
	
	//CONSTRUCTOR
	public AnimatedEntity(String n, float x, float y, Texture[] t) {
		super(n, x, y, t);
	}
	
	//CLASS METHODS
	@Override
    public void update(long lastEntityUpdate) {
		lastEntityUpdate = System.currentTimeMillis();
    }
}

