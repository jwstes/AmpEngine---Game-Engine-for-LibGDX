package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class CollisionHelper {
	private Rectangle bounds; 
    private List<Entity> objects; 
    public CollisionHelper() {
        this.objects = new ArrayList<>();
    }
    
    public void insert(Entity entity) {
        objects.add(entity);
    }
       
    public List<Entity> query(Rectangle area, List<Entity> found) {
        for (Entity e : objects) {
            if (area.overlaps(e.getRec())) {
                found.add(e);
            }
        }
        return found;
    }
    public void clear() {
        objects.clear();
    }	
	//debugging
	public void draw(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
    }

}
