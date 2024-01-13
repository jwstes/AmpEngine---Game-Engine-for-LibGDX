package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class EntityManager{
	
	public Texture loadTexture(String assetName) {
		return new Texture(Gdx.files.internal(assetName));
	}
	
}
