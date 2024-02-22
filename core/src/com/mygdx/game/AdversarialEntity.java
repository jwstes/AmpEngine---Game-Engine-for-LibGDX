package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;


// Adversarial Entity Class stores the properties of an entity meant to be "Enemies"
// It inherits more properties from it SuperClass Entity.
// name, health, x, y, speed, Texture tex, Texture[] animatedTexture, entityType, rec
// isKillable, isMovable, isAlive, isCollidable

// Tells the entity to walk back and forth, stand still or it go up down. Or shoot at intervals

public class AdversarialEntity extends Entity {
    private AIManager ai;
    
    //CONSTRUCTOR
    public AdversarialEntity(String n,float x, float y, Texture t){
        super(n,x,y,t); 
    }

    //GETTER & SETTER METHODS
    public void setAIManager(AIManager ai){
        this.ai = ai;
    }
    public AIManager getAIManager(){ 
        return this.ai;
    }
    
    
    //CLASS METHODS
    @Override
    public void update(long lastEntityUpdate) {

        System.currentTimeMillis();
    }


}