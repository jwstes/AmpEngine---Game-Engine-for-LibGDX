package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.Text;

public class Entity {

	private String name;
	private int health;
	private float x;
	private float y;
	private float speed;
	private Texture tex; // this is the overlaying texture (sprite image)
	private boolean isKillable; // Some "enemies" are meant to just attack like a floating canon
								// Not necessary for all games, see how you want to implement the logic.
	private boolean isAlive; // For logic that require a boolean way to check if the entity is alive.
							// You can also check if health = 0 but that's scuffed innit.


	// Constructor
	public Entity(){};
																// name is identifier for scene so mandatory to set one
	public Entity(String name, float x, float y, Texture t){   //focus on making it appear, then add speed & health later
		this.x = x;
		this.y = y;
		this.tex = t;
	}


	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}


	// Setter & Getter
	public void setHealth(int health) {
		this.health = health;
	}

	public int getHealth(){
		return this.health;
	}

	public void setPosX(float newPosX){
		this.x = newPosX;
	}
	public float getPosX(){
		return this.x;
	}

	public void setPosY(float newPosY){
		this.y = newPosY;
	}
	public float getPosY(){
		return this.y;
	}

	public void setSpeed(float speed){
		this.speed = speed;
	}
	public float getSpeed(){
		return this.speed;
	}

	public void setTexture(Texture t){
		this.tex = t;
	}




}

