package com.mygdx.AiControlled;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Entities.AdversarialEntity;
import com.mygdx.game.Entities.Entity;
import com.mygdx.game.Entities.EntityFactoryInterface;

public class NPCEntity extends Entity implements EntityFactoryInterface{
	
    public NPCEntity(String n, float x, float y, Texture t){
        super(n,x,y,t);
    }
    public NPCEntity() {}

	@Override
	public void update(long lastEntityUpdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity createEntity(int x, int y, Texture[] textures, boolean[] properties, String name) {
		NPCEntity entity = new NPCEntity("n", x, y, textures[0]);
		entity.setIsAlive(properties[0]);
        entity.setIsKillable(properties[1]);
        entity.setIsMovable(properties[2]);
        entity.setEntityType("npc");
        entity.setIsCollidable(true);
        entity.setIsHostile(false);
        
		return entity;
	}

}
