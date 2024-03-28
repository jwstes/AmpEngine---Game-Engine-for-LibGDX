package com.mygdx.Animated;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Entities.AdversarialEntity;
import com.mygdx.game.Entities.Entity;
import com.mygdx.game.Entities.EntityFactoryInterface;

public class AnimatedEntity extends Entity implements EntityFactoryInterface {
	
	//CONSTRUCTOR
	public AnimatedEntity(String n, float x, float y, Texture[] t) {
		super(n, x, y, t);
	}
	
	public AnimatedEntity() {
		
	}
	
	//CLASS METHODS
	@Override
    public void update(long lastEntityUpdate) {
		lastEntityUpdate = System.currentTimeMillis();
    }

	@Override
	public Entity createEntity(int x, int y, Texture[] textures, boolean[] properties, String name) {
		AnimatedEntity entity = new AnimatedEntity("n", x, y, textures);
		entity.setIsAlive(properties[0]);
        entity.setIsKillable(properties[1]);
        entity.setIsMovable(properties[2]);
        entity.setEntityType(name);
        entity.setIsCollidable(properties[4]);
        entity.setIsHostile(properties[5]);
        
		return entity;
	}
}

