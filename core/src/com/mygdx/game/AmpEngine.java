package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class AmpEngine extends ApplicationAdapter{
	private SceneManager sceneManager;
	private EntityManager entityManager;
	//private CollisionManager collisionManager;
	private ShapeRenderer shapeRenderer;
	
	//Experimental Collider. Can Delete Later
	private long startTime;
	private long lastCheckTime;
	
	
	
//	private int selectedMenuIndex;
	private Scene scene;
	
	
	//below is spike integration only, subject for removal
	
	//
	private boolean gameStarted;
	
	private SpriteBatch batch;
	 

	private AdversarialEntity spikeTrap;
	
	private AIManager aiManager;
	
	
	Rectangle worldBounds = new Rectangle(1, 1, 1279, 718);
	@Override
	public void create() {
		Array<String> sceneJSONArr = new Array<String>();
		sceneJSONArr.add("Level1.json");
		//... add more if needed
		sceneManager = new SceneManager(sceneJSONArr);
		entityManager = new EntityManager();
		
		
		
		
		//Experimental Collider. Can Delete Later
		startTime = System.currentTimeMillis();
		lastCheckTime = System.currentTimeMillis();

		
		//		selectedMenuIndex = 2;
		
		//AI spike stuff below, can change later if not acceptable
		
		 // Initialize SpriteBatch
	    batch = new SpriteBatch();

	  
	    // Initialize EntityManager before adding entities
	    entityManager = new EntityManager();

	    // This instantiates the sliding spikes, can modify the texture images on how long u want the spike to erect to be
	    spikeTrap = new AdversarialEntity(
	    	    200,
	    	    -2,
	    	    3.0f, //lower value means lower attack speed
	    	    new Texture[] {
	    	        new Texture("spike1.png"),
	    	        new Texture("spike2.png"),
	    	        new Texture("spike3.png")
	    	    },
	    	    100.0f // movement speed of spike moving from right to left
	    	);
	    
	    
	    // Create an instance of AIManager and add it to the EntityManager
	    aiManager = new AIManager(1,0);
	    
	 

	    entityManager.addEntity(aiManager);
	    
	    entityManager.addEntity(spikeTrap);
	    
	    //this ensures the trap only spawns within the screen size
	    float windowWidth = Gdx.graphics.getWidth();
	    
	    
	    //this creates random  spikes
	    int numberOfSpikes = 3; // Adjust the number of spikes as needed
	   
	    
	    for (int i = 0; i < numberOfSpikes; i++) {
	        AIManager aiManager = new AIManager(1, 0);
	        float randomX = MathUtils.random(windowWidth);
	        aiManager.addPositions(randomX, 90);
	        entityManager.addEntity(aiManager);
	    }
		
	}
	


	@Override
	public void render() {
		sceneManager.clearScreen();
        sceneManager.loadScene(0);
       
        
        //start of spike AI, can be change later if not ok
        
        batch.begin(); // Begin batch before rendering anything

        // Inside the render() method of AmpEngine
        entityManager.updateEntities();
        
        // Continuously randomize positions during the game for each AIManager
        for (Entity entity : entityManager.getAllEntities()) {
            if (entity instanceof AIManager) {
                AIManager aiManager = (AIManager) entity;
                float windowWidth = Gdx.graphics.getWidth();
                float randomX = MathUtils.random(windowWidth);
                aiManager.addPositions(randomX, 90);
            }
        }
        
        
        entityManager.drawEntities(batch);
  

        batch.end();
        
       
        //Experimental Collider. Can Delete Later
        if(System.currentTimeMillis() >= (lastCheckTime + 100)) {
        	//System.out.println("Checking For Collision");
        	sceneManager.checkCollision();
        	lastCheckTime = System.currentTimeMillis();
        }
        sceneManager.drawCollider();
//        batch.begin();
        
        // Draw all entities
        
//        for (GameEntity entity : scene.getEntities()) {
//            entity.draw(batch);
//        }
//        batch.end();
        
		/*
		boolean splashScreen = sceneManager.displaySplashScreen(true, 2, startTime);
		if(splashScreen) {
			String[] menuItems = {"Exit", "Options", "Start Game"};
			Runnable[] actions = {
	                () -> System.out.println("Exit selected"),
	                () -> System.out.println("Options selected"),
	                () -> System.out.println("Start Game selected")
	        };
			selectedMenuIndex = sceneManager.displayMenu(menuItems, actions, selectedMenuIndex);
		}
		*/
		
	}
	
	public AmpEngine() {
		
	}
}
