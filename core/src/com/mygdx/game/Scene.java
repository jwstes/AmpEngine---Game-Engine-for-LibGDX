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
    private List<String> entityTypes;
    private int entitiesSize;
    private Texture backgroundTexture;
    
   
    public Scene() {
        entityCoords = new ArrayList<int[]>();
        entityTextures = new ArrayList<Texture>();
        entityProperties = new ArrayList<boolean[]>();
        entityTypes = new ArrayList<String>();
    }
    // each tile is 32width 26 height
    public void ParseFromJSON(String jsonString) {
        try {
            JsonReader jsonReader = new JsonReader();
            
            JsonValue base = jsonReader.parse(Gdx.files.internal(jsonString).readString("UTF-8"));
            backgroundTexture = new Texture(Gdx.files.internal(base.getString("background")));
            
            
            JsonValue entities = base.get("entities");
            // Check that the "entities" array is present
            if (entities != null) { 
                for (JsonValue entity : entities) {
                	
                	// Add coordinates to the coord list
                    int[] coords = {entity.get("coords").getInt(0), entity.get("coords").getInt(1)};
                    
                    entityCoords.add(coords); 
                    
                    //add texture to tex list
                    String texturePath = entity.getString("texture");
                    
                    String type = entity.getString("entityType");
                    entityTypes.add(type);
                    
                    Texture texture = new Texture(Gdx.files.internal(texturePath));
                    entityTextures.add(texture);
                    
                    //store the properties into 1 list in this order
                    boolean[] properties = new boolean[5];
                    properties[0] = entity.getBoolean("isAlive");
                    properties[1] = entity.getBoolean("isKillable");
                    properties[2] = entity.getBoolean("isMovable");
                    properties[3] = entity.getBoolean("isBreakable");
                    properties[4] = entity.getBoolean("isCollidable");
                    
                    entityProperties.add(properties); 
                  
                }
            } else {
                System.out.println("NO GO UR JSON IS EMPTY");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        entitiesSize = entityCoords.size();
    }
    
    
    
    
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
    public int GetEntityArrSize() {
    	return entitiesSize;
    }
    public List<String> GetEntityTypes() {
    	return entityTypes;
    }
    public Texture GetBackgroundTexture() {
    	return backgroundTexture;
    }

}
