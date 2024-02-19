package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;


import com.badlogic.gdx.utils.Array;


public class SceneManager{
	// INSTANCES AND VARIABLES
	public EntityManager entityManager;
	private Texture developerLogo;
	private Array<Scene> allScenes;
	
	private Scene currentScene;
	private int currentSceneID;
	
	private SpriteBatch batch;
	
	//
    private long lastAIUpdate;

    
	private long lastEntityUpdate;
	private int animatedTextureID;
	
	Rectangle worldBounds = new Rectangle(1, 1, 1279, 718);
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private QuadTreeNode quadTree = new QuadTreeNode(worldBounds, 4);
    private CollisionManager collisionManager;
    
    //=======================================================================TESTING MOVEMENT ============================================================================
    float verticalVelocity = 0;
    final float GRAVITY = -500; // Gravity pulling the player down each frame
    final float JUMP_VELOCITY = 300; // The initial velocity impulse when jumping
    boolean isOnGround = true; // Initially, assume the player is on the ground
    PlayerControl player;
    //====================================================================================================================================================================
	
	// METHODS
	public void clearScreen() {
//		ScreenUtils.clear(100, 100, 100, 1);
		Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public float[] getWindowSize() {
		float[] size = {
				Gdx.graphics.getWidth(), Gdx.graphics.getHeight()
		};
	    return size;
	}
	
//	public void setDeveloperLogo(Texture assetName) {
//		developerLogo = entity.setTexture(assetName);
//	}
//	
//	public boolean displaySplashScreen(boolean timerMode, int seconds, long startTime) {
//		clearScreen();
//		
//		SpriteBatch spriteBatch = new SpriteBatch();
//		spriteBatch.begin();
//		
//		float[] windowSize = getWindowSize();
//		float x = (windowSize[0] - developerLogo.getWidth()) / 2f;
//	    float y = (windowSize[1] - developerLogo.getHeight()) / 2f;
//	    
//		spriteBatch.draw(developerLogo, x, y);
//		spriteBatch.end();
//		
//		if(timerMode == true) {
//			float elapsedTime = (TimeUtils.nanoTime() - startTime) / 1000000000.0f;
//			return elapsedTime > seconds;
//		}
//		return false;
//	}
	
	public void updateScene() {
	    long currentTime = System.currentTimeMillis();

		if (System.currentTimeMillis() >= (lastEntityUpdate + 300)) {
			//System.out.println("Updating");
			if(animatedTextureID < 3) {
				animatedTextureID++;
				//aiManager.updateColl(animatedTextureID);
			}
			else {
				animatedTextureID = 0;
			}
			checkCollision();
	        
			lastEntityUpdate = System.currentTimeMillis();
		}
		
//===========================================================================Michael stuff
		
		// time taken before movable entity moves 
        if (currentTime >= (lastAIUpdate + 800)) 
        {
              for (AIManager ai : entityManager.getAllAIMEntity()) {
                  if (ai.getIsMovable()) {
                  	
                  	// movable entity moves to the right
                      moveEntityRight(ai);
                      
                      if (animatedTextureID == 2) 
                      {   	
                      ai.updateCollider(ai.getPosX(), ai.getPosY(), 32, 48);
                      }
                      else {
	                        ai.updateCollider(ai.getPosX(), ai.getPosY(), 32, 12);
                      }
                   
                  }
              }
          }
		
//=====================================================================IDK HOW TO IMPLEMENT TO OTHER CLASS PLEASE HELP!!!!!!===============================================	
		
		Entity player = entityManager.getAllPEntity().get(0);
		// Apply gravity every frame, reducing the vertical velocity
        verticalVelocity += GRAVITY * Gdx.graphics.getDeltaTime();
        // Check for the jump key press and initiate the jump if the player is on the ground
        if (Gdx.input.isKeyJustPressed(Keys.SPACE) && isOnGround) {
            System.out.print("SPACE - Jump Initiated\n");
            verticalVelocity = JUMP_VELOCITY; // Apply the jump velocity
            isOnGround = false; // The player is no longer on the ground
        }
        
        //float originalPosY = player.getPosY();
        // Update the player's position based on the vertical velocity
        float newY = player.getPosY() + verticalVelocity * Gdx.graphics.getDeltaTime();
        player.setPosY(newY);
        
        
        // Update the collider for the new position
        player.updateCollider(player.getPosX(), newY, 32, 32);
        //colm.checkCollisionsAndUpdateGroundStatus(isOnGround, verticalVelocity);
        
        checkCollisionsAndUpdateGroundStatus();
		
        
	}
	
//	==============================Michael stuff=========================
	private void moveEntityRight(AIManager ai) {
	    float increment = 1f; // adjusting distance of movable entity travel
	    float maxX = Gdx.graphics.getWidth(); // Get screen size

	    float initialX = ai.getInitialPosX(); // get initial position x from AImanager (which is from super entity)
	    float targetX = ai.getPosX() + increment; // entity next position to the right with increment

	    // use interpolation gdx lib to smooth the animation sliding of the entity when it's moving
	    float alpha = MathUtils.clamp((targetX - ai.getPosX()) / increment, 0f, 1f);

	    // Experiment with different interpolation functions for smoother sliding
	    float newX = Interpolation.smooth.apply(ai.getPosX(), targetX, alpha);

	    // Check if the entity has reached the right edge
	    if (newX > maxX) {
	        // Reset the entity to the left with a different starting position
	        ai.setPosX(initialX - ai.getWidth()); // Subtract entity width to avoid overlapping
	    } else {
	        ai.setPosX(newX);
	    }
	    
	}
//	====================================================================
	private void checkCollisionsAndUpdateGroundStatus() {
	    // Assume the player is not on the ground at the start of each check
	    isOnGround = false;
	    Entity player = entityManager.getAllPEntity().get(0);
	    Rectangle playerRect = player.getRec();
	    for (Entity groundEntity : entityManager.getAllSEntity()) { // Loop through ground entities
	        Rectangle groundRect = groundEntity.getRec();

	        // Check if the player's bottom edge is within the top edge of a ground entity
	        if (playerRect.y <= groundRect.y + groundRect.height && playerRect.y > groundRect.y) {
	            // Check for horizontal overlap
	            if (playerRect.x + playerRect.width > groundRect.x && playerRect.x < groundRect.x + groundRect.width) {
	                isOnGround = true; // Player is on the ground
	                player.setPosY(groundRect.y + groundRect.height); // Adjust position to stand on the ground
	                verticalVelocity = 0; // Reset vertical velocity
	                break; // Exit the loop after finding ground collision
	            }
	        }
	     // Check if the player's top edge is colliding with the bottom edge of an entity (hitting head)
	        if (playerRect.y + playerRect.height >= groundRect.y && playerRect.y + playerRect.height < groundRect.y + groundRect.height) {
	            // Check for horizontal overlap
	            if (playerRect.x + playerRect.width > groundRect.x && playerRect.x < groundRect.x + groundRect.width) {
	                // Player has hit the bottom side of an entity
	                // You might want to handle this differently, e.g., stopping upward movement
	                verticalVelocity = Math.min(verticalVelocity, 0); // Ensure vertical velocity is not positive (not moving upwards)
	                player.setPosY(groundRect.y - playerRect.height); // Adjust player's position to be just below the entity
	                // Note: No need to set isOnGround = true here, as the player is not landing on top of the entity
	            }
	        }
	    }
	}
//================================================================================================================================================================================
	
	
	
	
	
	
	
	
	public void populateScene(int sceneID) {
		Scene selectedScene = allScenes.get(sceneID);
		entityManager.createEntities(selectedScene);
	}
	public void loadScene(int sceneID) {
		Scene selectedScene = allScenes.get(sceneID);
		//entityManager.createEntities(selectedScene);
		
		Texture backgroundTexture = selectedScene.GetBackgroundTexture();
		loadBackground(backgroundTexture);
		
		
		
		Array<AdversarialEntity> allAdversarialEntity = entityManager.getAllAdEntity();
		Array<StaticEntity> allStaticEntity  = entityManager.getAllSEntity();
		Array<PlayerEntity> allPlayerEntity = entityManager.getAllPEntity();
		Array<AIManager> allAIMEntity = entityManager.getAllAIMEntity();
		
		//DELETE LATER
		player = new PlayerControl(allPlayerEntity, allStaticEntity,allAdversarialEntity,allAIMEntity );
		player.Movement();
		//DELETE LATER
		
		
		batch.begin();
		//System.out.print("BEGIN");
		for(AdversarialEntity e : allAdversarialEntity) {
			e.draw(batch);
			
		}
		for(StaticEntity e : allStaticEntity) {
			e.draw(batch);
			//System.out.print(e.getTexture());
		}
		for(PlayerEntity e : allPlayerEntity) {
			e.draw(batch);
		}
		for(AIManager e : allAIMEntity) {
			e.setTexture(e.getTextures()[animatedTextureID]);
			e.draw(batch);
		}
		
		
		currentScene = selectedScene;
		currentSceneID = sceneID;
		batch.end();
	}
	public void loadBackground(Texture backgroundTexture) {
		batch.begin();
		batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
	}
    public void checkCollision() {
        collisionManager = new CollisionManager(worldBounds, 1, entityManager.getAllPEntity(), entityManager.getAllSEntity(), entityManager.getAllAdEntity(),entityManager.getAllAIMEntity());
        collisionManager.checkPlayerCollisions();
    }
    public void drawCollider() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        for (PlayerEntity player : entityManager.getAllPEntity()) {
            shapeRenderer.rect(player.getRec().x, player.getRec().y, player.getRec().width, player.getRec().height);
        }
        for (StaticEntity staticEntity : entityManager.getAllSEntity()) {
            shapeRenderer.rect(staticEntity.getRec().x, staticEntity.getRec().y, staticEntity.getRec().width, staticEntity.getRec().height);
        }
        for (AdversarialEntity adversary : entityManager.getAllAdEntity()) {
            shapeRenderer.rect(adversary.getRec().x, adversary.getRec().y, adversary.getRec().width, adversary.getRec().height);
        }
        for (AIManager ai : entityManager.getAllAIMEntity()) {
        	 shapeRenderer.rect(ai.getRec().x, ai.getRec().y, ai.getRec().width, ai.getRec().height);
        }


        quadTree.draw(shapeRenderer); // Draw the entire QuadTree


        shapeRenderer.end(); // End shape drawing
    }
	
	
	public Array<Scene> getAllScenes(){
		return allScenes;
	}

	public int displayMenu(String[] menuItemText, Runnable[] actions, int selectedMenuItemIndex) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    SpriteBatch spriteBatch = new SpriteBatch();
	    ShapeRenderer shapeRenderer = new ShapeRenderer();
	    BitmapFont font = new BitmapFont();
	    OrthographicCamera camera = new OrthographicCamera();
	    camera.setToOrtho(false);

	    shapeRenderer.setProjectionMatrix(camera.combined);
	    spriteBatch.setProjectionMatrix(camera.combined);

	    float menuItemWidth = 300;
	    float menuItemHeight = 100;
	    float menuItemSpacing = 20;

	    float totalHeight = menuItemText.length * menuItemHeight + (menuItemText.length - 1) * menuItemSpacing;

	    float startY = (Gdx.graphics.getHeight() - totalHeight) / 2;
	    
	    
	    if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedMenuItemIndex = Math.max(0, selectedMenuItemIndex - 1);
        }
	    else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedMenuItemIndex = Math.min(menuItemText.length - 1, selectedMenuItemIndex + 1);
        }
	    else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if (selectedMenuItemIndex >= 0 && selectedMenuItemIndex < actions.length) {
                actions[selectedMenuItemIndex].run();
            }
        }
	    
	    
	    for (int i = 0; i < menuItemText.length; i++) {
	        float rectangleX = (Gdx.graphics.getWidth() - menuItemWidth) / 2;
	        float rectangleY = startY + i * (menuItemHeight + menuItemSpacing);

	        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	        
	        
	        if (i == selectedMenuItemIndex) {
                shapeRenderer.setColor(Color.RED);
            } else {
                shapeRenderer.setColor(1, 1, 1, 1);
            }
	        
	        shapeRenderer.rect(rectangleX, rectangleY, menuItemWidth, menuItemHeight);
	        shapeRenderer.end();

	        spriteBatch.begin();

	        font.setColor(0, 0, 0, 1);

	        String currentMenuItemText = menuItemText[i];

	        GlyphLayout glyphLayout = new GlyphLayout();
	        glyphLayout.setText(font, currentMenuItemText);

	        float textX = rectangleX + (menuItemWidth - glyphLayout.width) / 2;
	        float textY = rectangleY + (menuItemHeight + glyphLayout.height) / 2;

	        font.draw(spriteBatch, currentMenuItemText, textX, textY);

	        spriteBatch.end();
	    }
	    return selectedMenuItemIndex;
	}
	
	public SceneManager(Array<String> sceneJSONArr) {
		entityManager = new EntityManager();
		allScenes = new Array<Scene>();
		
		batch = new SpriteBatch();
		
		for(String sceneJSON : sceneJSONArr) {
            Scene s = new Scene();
            s.ParseFromJSON(sceneJSON);
            allScenes.add(s);
        }
		
		lastEntityUpdate = System.currentTimeMillis();
		animatedTextureID = 0;
	}

}