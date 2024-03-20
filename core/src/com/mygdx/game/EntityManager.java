package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityManager {

    // Attributes
    private List<Entity> entityList;
    private Array<AdversarialEntity> allAdversarialEntity;
    private Array<StaticEntity> allStaticEntity;
    private Array<PlayerEntity> allPlayerEntity;
    private Array<AIManager> allAIMEntity;


    //Constructor
    public EntityManager(){
        entityList = new ArrayList<>();
        allAdversarialEntity = new Array<AdversarialEntity>();
        allStaticEntity = new Array<StaticEntity>();
        allPlayerEntity = new Array<PlayerEntity>();
        allAIMEntity = new Array<AIManager>();
    }


	// Getter Setter
	public List<Entity> getList() {
		return entityList;
	}
	public Array<AdversarialEntity> getAllAdEntity(){
    	return allAdversarialEntity;
    }
    public Array<StaticEntity> getAllSEntity(){
    	return allStaticEntity;
    }
    public Array<PlayerEntity> getAllPEntity(){
    	return allPlayerEntity;
    }
    public Array<AIManager> getAllAIMEntity(){
    	return allAIMEntity;
    }



    // CLASS METHOD
    public void add(Entity EntityObj){
        entityList.add(EntityObj);
    }


    //Remove Entity by passing Object itself
    public void remove(Entity EntityObj){
        boolean removed = entityList.removeIf(e -> e.getName().equals(EntityObj.getName()));
        if (removed) {
            System.out.println(EntityObj.getName() + " has been removed from EntityManager List" );
        } else {
            System.out.println(EntityObj.getName() + " failed to be removed from list. Check if Object and Name is correct" );
        }
    }

    // Remove Entity by name (string )
    public void remove(String name){
        boolean removed = entityList.removeIf(e -> e.getName().equals(name));
        if (removed) {
            System.out.println(name + " has been removed from EntityManager List" );
        } else {
            System.out.println(name+ " failed to be removed from list. Check if Name is correct or exists" );
        }
    }


	// Remove EVERY Entity in the list (Clear all in entity)
	public void clearAllEntities(){
		entityList.clear();
	}


	public void updateEntities() {
		for (Entity entity : entityList) {
			entity.update(0);
		}
	}

	public void drawEntities(SpriteBatch batch) {
		for (Entity entity : entityList) {
			entity.draw(batch);
			System.out.println("Drawing entity: " + entity.getClass().getSimpleName());
		}
	}

	
	public void deleteEntities() {
		entityList.clear();
		allAdversarialEntity.clear();
		allStaticEntity.clear();
		allPlayerEntity.clear();
		allAIMEntity.clear();
	}
	
	// Initializes every single entity (dictated by JSON file) into ArrayList Properties in Scene object.
	// The entities are initialized and appended to matching ArrayList base on their type - player, adversarial, static
    public void createEntities(Scene s) {
    	List<int[]> entityCoords = s.GetEntityCoords();
    	List<Texture[]> entityTextures = s.GetEntityTextures();
    	List<Boolean> entityAnimated = s.GetIsAnimated();
    	
    	
    	//alive, killable, movable, breakable
    	List<boolean[]> entityProperties = s.GetEntityProperty();
    	int entitiesSize = s.GetEntityArrSize();
    	
    	List<String> entityTypes = s.GetEntityTypes();
    	
    	
    	Array<AdversarialEntity> adEntities = new Array<AdversarialEntity>();
    	Array<PlayerEntity> pEntities = new Array<PlayerEntity>();
    	Array<StaticEntity> sEntities = new Array<StaticEntity>();
    	Array<AIManager> aimEntities = new Array<AIManager>();
    	
    	String staticString = "static";
    	String playerString = "player";
    	String adversarialString = "adversarial";
    	
    	for (int i = 0; i < entitiesSize; i++) {
    		
    		int x = entityCoords.get(i)[0];
    		int y = entityCoords.get(i)[1];
    		
    		Texture[] t;
    		t = entityTextures.get(i);
    		String type = entityTypes.get(i);
    		
    		boolean isAlive = entityProperties.get(i)[0];
    		boolean isKillable = entityProperties.get(i)[1];
    		boolean isMovable = entityProperties.get(i)[2];
    		boolean isBreakable = entityProperties.get(i)[3];
    		boolean isCollidable = entityProperties.get(i)[4];
    		    		
    		if(entityAnimated.get(i) == true) {
    			AIManager aime = new AIManager(x, y, t);
    			//System.out.print(t);
    			aime.setIsAlive(isAlive);
    			aime.setIsKillable(isKillable);
    			aime.setIsMovable(isMovable);
    			aime.setEntityType(adversarialString);
    			aime.setIsCollidable(isCollidable);
    			aimEntities.add(aime);
    		}
    		else {
    			if(type.equals(playerString)) {
        			PlayerEntity pe = new PlayerEntity("n", x, y, t[0]);
        			pe.setIsAlive(isAlive);
        			pe.setIsKillable(isKillable);
        			pe.setIsMovable(isMovable);
        			pe.setEntityType(playerString);
        			pe.setIsCollidable(isCollidable);
        			pEntities.add(pe);
        		}
        		else if (type.equals(staticString)) {
        			StaticEntity se = new StaticEntity("n", x, y, t[0]);
        			se.setIsAlive(isAlive);
        			se.setIsKillable(isKillable);
        			se.setIsMovable(isMovable);
        			se.setIsBreakable(isBreakable);
        			se.setEntityType(staticString);
        			se.setIsCollidable(isCollidable);
        			sEntities.add(se);
        		}
        		else if (type.equals(adversarialString)) {
        			AdversarialEntity ade = new AdversarialEntity("n", x, y, t[0]);
        			ade.setIsAlive(isAlive);
        			ade.setIsKillable(isKillable);
        			ade.setIsMovable(isMovable);
        			ade.setEntityType(adversarialString);
        			ade.setIsCollidable(isCollidable);
        			adEntities.add(ade);
        		}
    		}
    		
    		allAdversarialEntity = adEntities;
    		allStaticEntity = sEntities;
    		allPlayerEntity = pEntities;
    		allAIMEntity = aimEntities;
    	}
    }

}