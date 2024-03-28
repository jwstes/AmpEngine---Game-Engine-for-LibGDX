package com.mygdx.game.Entities;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.AiControlled.AIManager;



// Static Entity Class stores the properties of an entity meant to be passive elements of a world such as floors
// isBreakable, AIManager ai, isMovable
// It inherits more properties from it SuperClass Entity.
// name, health, x, y, speed, Texture tex, Texture[] animatedTexture, entityType, rec
// isKillable, isMovable, isAlive, isCollidable


public class StaticEntity extends Entity implements EntityFactoryInterface{
    private boolean isBreakable; //walls you can destroy i.e.

    private AIManager ai;    // If you have a moving platform, it'll need an ai to time the movement.
    
    private boolean isMovable; // If you have AIManager, isMovable is set to True by default.

    public StaticEntity(String n, float x, float y, Texture t){
        super(n,x,y,t);
    }
    public StaticEntity() {}

    public void setIsMovable(boolean b) {
    	isMovable = b;
    }
    public boolean getIsMovable() {
    	return isMovable;
    }
    
    public void setIsBreakable(boolean b) {
    	isBreakable = b;
    }
    public boolean getIsBreakable() {
    	return isBreakable;
    }

	@Override
	public void update(long lastEntityUpdate) {
		// TODO Auto-generated method stub
    }

	@Override
	public Entity createEntity(int x, int y, Texture[] textures, boolean[] properties, String name) {
		StaticEntity entity = new StaticEntity("n", x, y, textures[0]);
		entity.setIsAlive(properties[0]);
        entity.setIsKillable(properties[1]);
        entity.setIsMovable(properties[2]);
        entity.setEntityType(name);
        entity.setIsCollidable(properties[4]);
        
		return entity;
	}
}