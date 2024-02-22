package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;


// Adversarial Entity Class stores the properties of an entity meant to be "Enemies"
// It inherits more properties from it SuperClass Entity.
// name, health, x, y, speed, Texture tex, Texture[] animatedTexture, entityType, rec
// isKillable, isMovable, isAlive, isCollidable

public class AdversarialEntity extends Entity {
    private AIManager ai; // Tells the entity to walk back and forth,  stand still or it go up down. Or shoot at intervals
    
    public AdversarialEntity(String n,float x, float y, Texture t){
        super(n,x,y,t); //need name, x, y, Texture
    }

    public void setAIManager(AIManager ai){
        this.ai = ai;
    }

    public AIManager getAIManager(){ //Why would you want to grab an AI manager? (maybe for clone function later)
        return this.ai;
    }
    
    
 
    
	
    @Override
    public void update(long lastEntityUpdate) {

        System.currentTimeMillis();
    }


}