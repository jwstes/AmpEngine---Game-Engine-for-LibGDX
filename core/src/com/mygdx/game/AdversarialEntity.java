package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
/***********************************************************************************************************************
 * Adversarial Entity Class stores the properties of an entity meant to be "Enemies"                                   *
 * It inherits more properties from it SuperClass Entity. name, health, x, y, Texture tex, Texture[] animatedTexture,  *
 * entityType, rec, isKillable, isMovable, isAlive, isCollidable.                                                      *
 **********************************************************************************************************************/
public class AdversarialEntity extends Entity {
    private AIManager ai; 									// Tells the entity to walk back and forth,  stand still or it go up down. Or shoot at intervals
    private AdversarialEntity projectile; 					// Optional to use (e.g. when you EnemyA shoot a bullet, it is technically
    														// shooting another AdversarialEntity Object out, just that this one not killable.
   
    public AdversarialEntity(String n,float x, float y, Texture t){
        super(n,x,y,t);
    }
    
    
    //Setter and Getter for AIManager
    public void setAIManager(AIManager ai){
        this.ai = ai;
    }
    public AIManager getAIManager(){
        return this.ai;
    }
    
    
   //Abstract method Found inside Entity Class
    @Override
    public void update(long lastEntityUpdate) {

        System.currentTimeMillis();
    }


}