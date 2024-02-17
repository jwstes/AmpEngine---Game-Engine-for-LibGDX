package com.mygdx.saple;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
	  private List<Entity> entityList;
	  private TextureObject[] droplets;
	  
	  private ShapeRenderer shape;

	  
	  private Circle cir;
    
	  private Triangle tri;

	    public EntityManager() {
	        entityList = new ArrayList<>();
	        // Add your entities to the list
	        entityList.add(new TextureObject("spikeUP.png", 500, 0, 0, false));
	        // Add other entities...
	        
	     // Creating Circle and Triangle objects
			cir = new Circle(500, 300, 200, Color.RED, 50);
	        tri = new Triangle(100, 100, 200, Color.FOREST, 100, 100);
	        
	        // Initialize the ShapeRenderer
	        shape = new ShapeRenderer();

	        entityList.add(cir); // Add Circle to the entity list
	        entityList.add(tri); // Add Triangle to the entity list

	        
	        
	        droplets = new TextureObject[2];
			for (int i=0; i < droplets.length; i++) {
				
				// assigning random starting positions fore very droplet
				float randomX = (float) (Math.random() * Gdx.graphics.getWidth());
				float randomY = (float) (Math.random() * Gdx.graphics.getHeight());
				
				droplets[i] = new TextureObject("spikeUP.png", randomX, randomY, 2, true);
				entityList.add(droplets[i]); // Add droplets to the entity list
			}
			
		

	    }

	    public void updateEntities() {
	        for (Entity entity : entityList) {
	            entity.update();
	            
	            if (entity instanceof iMovable) {
	                ((iMovable) entity).mouseAIControlled();
	                ((iMovable) entity).moveUserControlled();
	                
	            }
	        }
	        
	        
	        // Update circle and triangle movement
	        cir.movement();
	        tri.movement();
	        
	        // Move each droplet from top to bottom
	        for (TextureObject droplet : droplets) {
	            droplet.setPosY(droplet.getPosY() - droplet.getSpeed());

	            // Reset droplet position when it reaches the bottom
	            if (droplet.getPosY() <= 0) {
	                droplet.setPosY((float) (Math.random() * Gdx.graphics.getHeight()));
	                droplet.setPosX((float) (Math.random() * Gdx.graphics.getWidth()));
	                droplet.setSpeed(droplet.getSpeed() + 2);

	                if (droplet.getSpeed() > 10) {
	                    droplet.setSpeed(10);
	                }
	            }}
	        
	      
	    }
	    
	    public void drawEntities(SpriteBatch batch) {
	        for (Entity entity : entityList) {
	            entity.draw(batch);
	        }
	        
	        	
	      
	    	// draw circle
			shape.begin(ShapeRenderer.ShapeType.Filled);
			cir.draw(shape);
			shape.end();
	
	        
	    	//draw triangle
			shape.begin(ShapeRenderer.ShapeType.Filled);
			tri.draw(shape);
			shape.end();
			
			
	    }

 
    
}
