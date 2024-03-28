package com.mygdx.game.Entities;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;

public interface EntityFactoryInterface {
	Entity createEntity(int x, int y, Texture[] textures, boolean[] properties, String name);
}
