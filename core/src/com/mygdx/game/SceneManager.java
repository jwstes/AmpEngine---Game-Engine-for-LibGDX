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
	
	private long lastEntityUpdate;
	private int animatedTextureID;
	
	Rectangle worldBounds = new Rectangle(1, 1, 1279, 718);
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private QuadTreeNode quadTree = new QuadTreeNode(worldBounds, 4);
    private CollisionManager collisionManager;
	
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
		// TEST MOVING COLLIADER AND MOVING???? ==================================================================================================================================
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            // Store the player's original position
            float originalPosX = entityManager.getAllPEntity().get(0).getPosX();

            // Calculate the new position
            float newX = Math.max(0, originalPosX - 200 * Gdx.graphics.getDeltaTime());

            // Update the player's position temporarily to check for collisions
            entityManager.getAllPEntity().get(0).setPosX(newX);
            entityManager.getAllPEntity().get(0).updateCollider(newX, entityManager.getAllPEntity().get(0).getPosY(), 32, 32);

            // Check for collisions
            Entity collisionEntity = collisionManager.checkPlayerCollisions();
            if (collisionEntity != null) {
                // Collision detected, revert to the original position
                entityManager.getAllPEntity().get(0).setPosX(originalPosX);
            }
        }
        if(Gdx.input.isKeyPressed(Keys.RIGHT)){
            // Store the player's original position
            float originalPosX = entityManager.getAllPEntity().get(0).getPosX();

            // Calculate the new position
            float newX = Math.min(Gdx.graphics.getWidth(), originalPosX + 200 * Gdx.graphics.getDeltaTime());

            // Update the player's position temporarily to check for collisions
            entityManager.getAllPEntity().get(0).setPosX(newX);
            entityManager.getAllPEntity().get(0).updateCollider(newX, entityManager.getAllPEntity().get(0).getPosY(), 32, 32);

            // Check for collisions
            Entity collisionEntity = collisionManager.checkPlayerCollisions();
            if (collisionEntity != null) {
                // Collision detected, revert to the original position
                entityManager.getAllPEntity().get(0).setPosX(originalPosX);
            }
        }
        if(Gdx.input.isKeyPressed(Keys.DOWN)){
            // Store the player's original position
            float originalPosY = entityManager.getAllPEntity().get(0).getPosY();

            // Calculate the new position
            float newY = Math.max(0, originalPosY - 200 * Gdx.graphics.getDeltaTime());

            // Update the player's position temporarily to check for collisions
            entityManager.getAllPEntity().get(0).setPosY(newY);
            entityManager.getAllPEntity().get(0).updateCollider(entityManager.getAllPEntity().get(0).getPosX(), newY, 32, 32);

            // Check for collisions
            Entity collisionEntity = collisionManager.checkPlayerCollisions();
            if (collisionEntity != null) {
                // Collision detected, revert to the original position
                entityManager.getAllPEntity().get(0).setPosY(originalPosY);
                System.out.println("Collision detected with entity: " + collisionEntity);
            }
        }
        if(Gdx.input.isKeyPressed(Keys.UP)){
            // Store the player's original position
            float originalPosY = entityManager.getAllPEntity().get(0).getPosY();

            // Calculate the new position
            float newY = Math.min(Gdx.graphics.getHeight(), originalPosY + 200 * Gdx.graphics.getDeltaTime()); 

            // Update the player's position temporarily to check for collisions
            entityManager.getAllPEntity().get(0).setPosY(newY);
            entityManager.getAllPEntity().get(0).updateCollider(entityManager.getAllPEntity().get(0).getPosX(), newY, 32, 32);

            // Check for collisions
            Entity collisionEntity = collisionManager.checkPlayerCollisions();
            if (collisionEntity != null) {
                // Collision detected, revert to the original position
                entityManager.getAllPEntity().get(0).setPosY(originalPosY);
            }
        }
        //entityManager.getAllPEntity().get(0).setPosX(newX);
        //entityManager.getAllPEntity().get(0).updateCollider(entityManager.getAllPEntity().get(0).getPosX(), entityManager.getAllPEntity().get(0).getPosY() , 32, 32);
        batch.begin();
        entityManager.getAllPEntity().get(0).draw(batch);
        batch.end();
        //====================================================================================================================================================================================
	}
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