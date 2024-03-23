package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;


// Purpose of the Scene class is to utilize the values in a certain JSON Scene file and
// initialize the entities' Texture and Properties.
// Then store them into their respective Lists.
// Therefore, creating usable objects that SceneManager and AmpEngine (& other managers) can use.
public class Scene {
	private List<int[]> entityCoords;
	private List<Texture[]> entityTextures;
    private List<boolean[]> entityProperties;
    private List<String> entityTypes;
    private List<Boolean> entityAnimated;
    private int entitiesSize;
    private Texture backgroundTexture;
    private List<Boolean> entityHostile;
    private List<String> entityName;
    
    //questions
    private List<String> facts;
    private List<String> questions;
    private List<String[]> possibleAns;
    private List<String> actualAns;
    
    List<Map<String, Object>> questionsList = new ArrayList<>();
    
    
    //CONSTRUCTOR
    public Scene() {
        entityCoords = new ArrayList<int[]>();
        entityTextures = new ArrayList<Texture[]>();
        entityProperties = new ArrayList<boolean[]>();
        entityTypes = new ArrayList<String>();
        entityAnimated = new ArrayList<Boolean>();
        entityHostile = new ArrayList<Boolean>();
        entityName = new ArrayList<String>();
        
        //For Questions
        facts = new ArrayList<String>(); 
        
    }
    
    
    
    //CLASS METHODS
    public void ParseFromJSON(String jsonString) {
        try {
            JsonReader jsonReader = new JsonReader();
            
            JsonValue base = jsonReader.parse(Gdx.files.internal(jsonString).readString("UTF-8"));
            backgroundTexture = new Texture(Gdx.files.internal(base.getString("background")));
            
            
            JsonValue entities = base.get("entities");
            JsonValue allfacts = base.get("facts");
            JsonValue allquestions = base.get("questions");
            // Check that the "entities" array is present
            if (entities != null) { 
                for (JsonValue entity : entities) {
                	
                	// Add coordinates to the coord list
                    int[] coords = {entity.get("coords").getInt(0), entity.get("coords").getInt(1)};
                    entityCoords.add(coords); 
                    
                    //add texture to tex list
                    String[] textures = entity.get("texture").asStringArray();
                    Texture[] t = new Texture[5];
                    for(int i = 0; i < textures.length; i++) {
                    	t[i] = new Texture(Gdx.files.internal(textures[i]));
                    }
                    entityTextures.add(t);
                    
                    String type = entity.getString("entityType");
                    entityTypes.add(type);
                    
                    String name = entity.getString("entityName");
                    entityName.add(name);
                    
                    //store the properties into 1 list in this order
                    boolean[] properties = new boolean[6];
                    properties[0] = entity.getBoolean("isAlive");
                    properties[1] = entity.getBoolean("isKillable");
                    properties[2] = entity.getBoolean("isMovable");
                    properties[3] = entity.getBoolean("isBreakable");
                    properties[4] = entity.getBoolean("isCollidable");
                    properties[5] = entity.getBoolean("isHostile");
                    entityProperties.add(properties); 
                    
                    Boolean isAnimated = entity.getBoolean("isAnimated");
                    entityAnimated.add(isAnimated);
                }
            } else {
                System.out.println("NO GO UR JSON IS EMPTY");
            }
            
            
            //Facts
            if(facts != null) {
            	for (JsonValue fact : allfacts) {
            		String idk = fact.getString("fact");
            		facts.add(idk);
            	}
            }

            //Questions
            if(allquestions != null) {
                for (JsonValue question : allquestions) {
                    Map<String, Object> questionMap = new HashMap<>();
                    questionMap.put("question", question.getString("question"));
                  
                    List<String> answers = new ArrayList<>();
                    for(JsonValue ans : question.get("answers")) {
                        answers.add(ans.asString());
                    }
                    questionMap.put("answers", answers);
                    
                    questionMap.put("real", question.getString("real"));
                    
                    questionsList.add(questionMap);
                }
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
    public List<Texture[]> GetEntityTextures() {
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
    public List<Boolean> GetIsAnimated() {
    	return entityAnimated;
    }

	public List<Boolean> GetIsHostile() {
		return entityHostile;
	}
	
	public List<String> GetEntityName() {
		return entityName;
	}
    
	
    //facts
    public List<String> GetAllFacts(){
    	return facts;
    }

    public List<Map<String, Object>> GetAllQuestions(){
        return questionsList;
    }
    

}