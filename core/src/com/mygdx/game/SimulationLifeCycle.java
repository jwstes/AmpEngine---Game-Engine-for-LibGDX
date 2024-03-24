package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.mygdx.Animated.AnimatedEntity;
import com.mygdx.Animated.AnimatedEntityAction;
import com.mygdx.game.Scene.SceneManager;

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
	
	//Constructors
	public SimulationLifeCycle(long t, SceneManager s) {
		rand = new Random();
		time = t;
		animationTime = time;
		sceneManager = s;
		batch = sceneManager.getBatch();
		animationFrame = 0;
	}
	
	
	//Getters
	public AnimatedEntity getEntity() {
		return entity;
	}
	
	
	//Functions
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
