package com.mygdx.Animated;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Entities.Entity;

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

