package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class AdversarialEntity extends Entity {
    private AIManager ai; // Tells the entity to walk back and forth,  stand still or it go up down. Or shoot at intervals
    private AdversarialEntity projectile; // Optional to use (e.g. when you EnemyA shoot a bullet, it is technically
                                            //  shooting another AdversarialEntity Object out, just that this one not
                                            // killable.
    
   

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
    public long update(long lastEntityUpdate) {
       
        return System.currentTimeMillis();
    }


}