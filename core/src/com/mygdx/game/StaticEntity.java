package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;

public class StaticEntity extends Entity{
    private boolean isBreakable; //walls you can destroy i.e.

    private AIManager ai;    // If you have a moving platform, it'll need an ai to time the movement.
    
    private boolean isMovable; // If you have AIManager, isMovable is set to True by default.

    public StaticEntity(String n, float x, float y, Texture t){
        super(n,x,y,t);
    }

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
	public long update(long lastEntityUpdate) {
		// TODO Auto-generated method stub
		return 0;
	}
}