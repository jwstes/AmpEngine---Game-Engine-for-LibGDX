package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import com.mygdx.game.SceneManager;

public class AmpEngine extends ApplicationAdapter{
	private SceneManager sceneManager;
	private long startTime;
	
	private int selectedMenuIndex;
	
	
	//
	private boolean gameStarted;
	
	private SpriteBatch batch;
	 

	private SpikeEntity spikeTrap;
	
	private EntityManager entityManager; 
	
	private AIManager aiManager;

	
	@Override
	public void create() {
		sceneManager = new SceneManager();
	    sceneManager.setDeveloperLogo("badlogic.jpg");

	    startTime = TimeUtils.nanoTime();
	    selectedMenuIndex = 2;
	    gameStarted = false;

	    // Initialize SpriteBatch
	    batch = new SpriteBatch();

	  
	    // Initialize EntityManager before adding entities
	    entityManager = new EntityManager();

	    // Inside the create() method of AmpEngine
	    spikeTrap = new SpikeEntity(
	    	    200,
	    	    300,
	    	    100.0f,
	    	    new Texture[] {
	    	        new Texture("spike1.png"),
	    	        new Texture("spike2.png"),
	    	        new Texture("spike3.png")
	    	    },
	    	    100.0f
	    	);
	    
	    // Create an instance of AIManager and add it to the EntityManager
	    aiManager = new AIManager(1,0);
	    
	 

	    entityManager.addEntity(aiManager);
	    
	    entityManager.addEntity(spikeTrap);
	    
	 // Inside the create() method of AmpEngine
	    
	    float windowWidth = Gdx.graphics.getWidth();
	   
	 
//	    float randomX1 = MathUtils.random(windowWidth);
//	    float randomX2 = MathUtils.random(windowWidth);
//
//	    aiManager.addPositions(randomX1, 200); // Add some initial position
//	    aiManager.addPositions(randomX2, 200); // Add another initial position
	    
	 // Create multiple instances of AIManager and add them to the EntityManager
	    int numberOfSpikes = 3; // Adjust the number of spikes as needed
	    
	    for (int i = 0; i < numberOfSpikes; i++) {
	        AIManager aiManager = new AIManager(1, 0);
	        float randomX = MathUtils.random(windowWidth);
	        aiManager.addPositions(randomX, 200);
	        entityManager.addEntity(aiManager);
	    }
		    
	}
	
	
	@Override
	public void render() {
	    boolean splashScreen = sceneManager.displaySplashScreen(true, 2, startTime);
	    if (splashScreen) {
	        String[] menuItems = {"Exit", "Options", "Start Game"};
	        Runnable[] actions = {
	                () -> System.out.println("Exit selected"),
	                () -> System.out.println("Options selected"),
	                () -> {
	                    System.out.println("Start Game selected");
	                    startGame();
	                }
	        };
	        selectedMenuIndex = sceneManager.displayMenu(menuItems, actions, selectedMenuIndex);
	    }

	    if (gameStarted) {
	        ScreenUtils.clear(0, 0, 0.2f, 1);

	        batch.begin(); // Begin batch before rendering anything

	        // Inside the render() method of AmpEngine
            entityManager.updateEntities();
            
            
            
//            // Continuously randomize positions during the game
//            float windowWidth = Gdx.graphics.getWidth();
//            float randomX1 = MathUtils.random(windowWidth);
//            float randomX2 = MathUtils.random(windowWidth);
//            aiManager.addPositions(randomX1, 200); // Update position 1
//            aiManager.addPositions(randomX2, 200); // Update position 2
//            
//            

            // Continuously randomize positions during the game for each AIManager
            for (Entity entity : entityManager.getAllEntities()) {
                if (entity instanceof AIManager) {
                    AIManager aiManager = (AIManager) entity;
                    float windowWidth = Gdx.graphics.getWidth();
                    float randomX = MathUtils.random(windowWidth);
                    aiManager.addPositions(randomX, 200);
                }
            }

            entityManager.drawEntities(batch);
            
            entityManager.drawEntities(batch);
            
            
           
            spikeTrap.draw(batch);
            spikeTrap.update();
            
            

            batch.end();
	    }
	}
	
	
	
	private void startGame() {
		gameStarted = true;
		
	}


	public AmpEngine() {
		
	}
}
