package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class AnimatedEntity extends Entity {

	public AnimatedEntity(String n, float x, float y, Texture[] t) {
		super(n, x, y, t);
	}
	
	@Override
    public void update(long lastEntityUpdate) {
        System.currentTimeMillis();
    }
}
