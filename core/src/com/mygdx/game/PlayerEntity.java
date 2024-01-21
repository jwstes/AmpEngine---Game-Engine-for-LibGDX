package com.mygdx.game;
import com.mygdx.game.AmpEngine;
import com.mygdx.game.EntityManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.TimeUtils;
import org.w3c.dom.Text;
import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.css.Rect;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;


/* This Class handles the Player Entity (Player's Character/Sprite) only.*/
/* This record character's health, positioning, image texture and hitbox(rectangle) */
/* Possible implementations to split into smaller classes possible. Possible addition such as guns, etc to be decided)*/
public abstract class PlayerEntity extends EntityManager implements InputProcessor{
	public PlayerEntity(int initialHealth) {
		super(initialHealth); //mean to call the constructor of the parent class
	}
	
	@Override
	public boolean keyDown(int keyPressed) {
		return false;
	}
	
	@Override
	public boolean keyUp(int keyPressed) {
		handleInput(keyPressed);
		return false;
	}
	private void handleInput(int keyPressed) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            setPosX(this.getPosX() - PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            setPosX(this.getPosX() + PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            setPosY(this.getPosY() + PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            setPosY(this.getPosY() - PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        }
	} //included PLAYER_SPEED which needs to be added to Entity Class 

}
