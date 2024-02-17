package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class Scene {
	private List<int[]> entityCoords;
	private List<Texture> entityTextures;
    private List<boolean[]> entityProperties;
    
    
    
    //delete later for testing only
    private List<GameEntity> entities;

    public Scene() {
        entityCoords = new ArrayList<int[]>();
        entityTextures = new ArrayList<Texture>();
        entityProperties = new ArrayList<boolean[]>();
        
        
        
        //delete later for testing only
        entities = new ArrayList<>();
    }
    // each tile is 32width 26 height
    public void ParseFromJSON(String jsonString) {
        try {
            JsonReader jsonReader = new JsonReader();
            
            JsonValue base = jsonReader.parse(Gdx.files.internal(jsonString).readString("UTF-8"));
            JsonValue entities = base.get("entities");
            // Check that the "entities" array is present
            if (entities != null) { 
                for (JsonValue entity : entities) {
                	
                	// Add coordinates to the coord list
                    int[] coords = {entity.get("coords").getInt(0), entity.get("coords").getInt(1)};
                    entityCoords.add(coords); 
                    
                    //add texture to tex list
                    String texturePath = entity.getString("texture");
                    Texture texture = new Texture(Gdx.files.internal(texturePath));
                    entityTextures.add(texture);
                    
                    //store the properties into 1 list in this order
                    boolean[] properties = new boolean[4];
                    properties[0] = entity.getBoolean("isAlive");
                    properties[1] = entity.getBoolean("isKillable");
                    properties[2] = entity.getBoolean("isMovable");
                    properties[3] = entity.getBoolean("isBreakable");
                    entityProperties.add(properties); 
                  
                }
            } else {
                System.out.println("NO GO UR JSON IS EMPTY");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    //=============================================delete later for testing only===========================================
    public void createEntities() {
        for (int i = 0; i < entityCoords.size(); i++) {
            int[] coords = entityCoords.get(i);
            Texture texture = entityTextures.get(i);

            GameEntity entity = new GameEntity(coords[0], coords[1], texture);
            entities.add(entity);
        }
    }
    //delete later for testing only
    public List<GameEntity> getEntities() {
        return entities;
    }
    //======================================================================================================================
    
    
    
    // Getters for the different lists
    public List<int[]> GetEntityCoords() {
		return entityCoords;
    	
    }
    public List<Texture> GetEntityTextures() {
		return entityTextures; 
    	
    }
    public List<boolean[]> GetEntityProperty() {
		return entityProperties;
		
    }

}
