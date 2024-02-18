package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.utils.Array;


public class SceneManager{
	// INSTANCES AND VARIABLES
	private EntityManager entityManager;
	private Texture developerLogo;
	private Array<Scene> allScenes;
	
	private Scene currentScene;
	private int currentSceneID;
	
	private SpriteBatch batch;
	
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
	
	public void loadBackground(Texture backgroundTexture) {
		batch.begin();
		batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.end();
	}
	public void loadScene(int sceneID) {
		Scene selectedScene = allScenes.get(sceneID);
		
		Texture backgroundTexture = selectedScene.GetBackgroundTexture();
		loadBackground(backgroundTexture);
		
		entityManager.createEntities(selectedScene);
		
		Array<AdversarialEntity> allAdversarialEntity = entityManager.getAllAdEntity();
		Array<StaticEntity> allStaticEntity  = entityManager.getAllSEntity();
		Array<PlayerEntity> allPlayerEntity = entityManager.getAllPEntity();
		
		
		batch.begin();
		
		
		for(AdversarialEntity e : allAdversarialEntity) {
			e.draw(batch);
		}
		for(StaticEntity e : allStaticEntity) {
			
			e.draw(batch);
		}
		for(PlayerEntity e : allPlayerEntity) {
			e.draw(batch);
		}
		
		
		currentScene = selectedScene;
		currentSceneID = sceneID;
		batch.end();
	}
	
	
	//Experimental Collider. Can Delete Later
	public void checkCollision() {
		Array<StaticEntity> allStaticEntity  = entityManager.getAllSEntity();
		Array<PlayerEntity> allPlayerEntity = entityManager.getAllPEntity();
		
		PlayerEntity player = allPlayerEntity.get(0);
		for(StaticEntity se: allStaticEntity) {
			if(player.getRec().overlaps(se.getRec())) {
				System.out.println("Overlapping");
			}
		}
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
		
	}

}