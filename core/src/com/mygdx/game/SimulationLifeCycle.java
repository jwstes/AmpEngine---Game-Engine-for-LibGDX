package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import java.util.Random;


public class SimulationLifeCycle{
	private Random rand;
	private long time;
	private SceneManager sceneManager;
	
	private AnimatedEntity entity;
	private long endTime;
	private long animationTime;
	private int animationFrame;
	
	private SpriteBatch batch;
	
	
	private AnimatedEntityAction action;
	
	public SimulationLifeCycle(long t, SceneManager s) {
		rand = new Random();
		time = t;
		animationTime = time;
		sceneManager = s;
		batch = sceneManager.getBatch();
		animationFrame = 0;
	}
	
	
	public AnimatedEntity getEntity() {
		return entity;
	}
	
	
	public void simulationCycle(AnimatedEntity e, AnimatedEntityAction a, long rf) {
		endTime = System.currentTimeMillis() + rf;
		entity = e;
		action = a;
	}
	
	public void simulationUpdate() {
		time = System.currentTimeMillis();
		
		if(time >= endTime) {
			entity = null;
		}

		if(entity != null) {
			animationTime = action.execute(entity, animationTime, time);
			
			sceneManager.beginBatch();
			entity.draw(batch);
			sceneManager.endBatch();
		}
	}
	
//	public void simulationUpdate() {
//		time = System.currentTimeMillis();
//		
//		if(time >= endTime) {
//			entity = null;
//		}
//
//		if(entity != null) {
//			Texture[] entityTextures = entity.getAnimatedTexture();
//			if(time >= (animationTime + 35)) {
//				
//				if(animationFrame < entityTextures.length - 1) {
//					animationFrame++;
//					entity.setTexture(entityTextures[animationFrame]);
//				}
//				else {
//					animationFrame = 0;
//				}
//				animationTime = time;
//			}
//			
//			sceneManager.beginBatch();
//			entity.draw(batch);
//			sceneManager.endBatch();
//		}
//		
//	}
	
	public Boolean roll(int chance) {
		int pool = 100;
		
		int r = rand.nextInt(pool);
		if(chance <= r) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
}
