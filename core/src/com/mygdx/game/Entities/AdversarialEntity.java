package com.mygdx.game.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.AiControlled.AIManager;



// Adversarial Entity Class stores the properties of an entity meant to be "Enemies"
// It inherits more properties from it SuperClass Entity.
// name, health, x, y, speed, Texture tex, Texture[] animatedTexture, entityType, rec
// isKillable, isMovable, isAlive, isCollidable

// Tells the entity to walk back and forth, stand still or it go up down. Or shoot at intervals

public class AdversarialEntity extends Entity implements EntityFactoryInterface{
    private AIManager ai;
    
    //CONSTRUCTOR
    public AdversarialEntity(String n,float x, float y, Texture t){
        super(n,x,y,t); 
    }
    public AdversarialEntity() {}

    //GETTER & SETTER METHODS
    public void setAIManager(AIManager ai){
        this.ai = ai;
    }
    public AIManager getAIManager(){ 

        return this.ai;
    }
    
    @Override
    public void update(long lastEntityUpdate) {

        System.currentTimeMillis();
    }

	@Override
	public Entity createEntity(int x, int y, Texture[] textures, boolean[] properties, String name) {
		AdversarialEntity entity = new AdversarialEntity("n", x, y, textures[0]);
		entity.setIsAlive(properties[0]);
        entity.setIsKillable(properties[1]);
        entity.setIsMovable(properties[2]);
        entity.setEntityType(name);
        entity.setIsCollidable(properties[4]);
        
		return entity;
	}


}