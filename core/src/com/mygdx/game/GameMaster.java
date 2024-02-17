//package com.mygdx.game;
//
//import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Input.Keys;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
//import com.badlogic.gdx.physics.box2d.Shape;
//
//
//
//public class GameMaster extends ApplicationAdapter {
//
//    private SpriteBatch batch;
//    private EntityManager entityManager;
//    private AIManager aiManager;
//
//    @Override
//    public void create() {
//        batch = new SpriteBatch();
//        entityManager = new EntityManager();
//        
//        aiManager = new AIManager();
//        aiManager.addPositions(100, 200);
//        aiManager.setWaitInterval(300);
//        aiManager.makeLoop(true);
//
//        entityManager.addEntity(aiManager);
//        
//
//        // Add a spike entity
//        entityManager.addSpikeEntity();
//    }
//
//    @Override
//    public void render() {
//    	ScreenUtils.clear(0, 0, 0.2f, 1);
//	    batch.begin();
//	    entityManager.updateEntities(); // Ensure this line is present
//	    entityManager.drawEntities(batch);
//	    
//	    aiManager.render(batch);
//	    aiManager.update();
//	    
//	    batch.end();
//
//    
//    }
//
//    @Override
//    public void dispose() {
//        // Dispose of resources
//        batch.dispose();
//    }
//
//	public void movement() {
//		// TODO Auto-generated method stub
//		
//	}
//
//	
//}
//
//
