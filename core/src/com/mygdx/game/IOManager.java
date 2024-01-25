package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
public abstract class IOManager extends EntityManager implements InputProcessor{
	private static int GET_WEAPON_KEY = Input.Keys.G;
	private static int DROP_WEAPON_KEY = Input.Keys.D;
	private static int SEE_Inventory = Input.Keys.I;
	public IOManager(int initialHealth) {
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
		float deltaTime = Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            setPosX(getPosX() - PLAYER_SPEED * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            setPosX(getPosX() + PLAYER_SPEED * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            setPosY(getPosY() + PLAYER_SPEED * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            setPosY(getPosY() - PLAYER_SPEED * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            setPosY(getPosX() + PLAYER_SPEED * SPRINT_ENHANCER * deltaTime);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            setPosY(getPosY() + JUMP_POWER * deltaTime);
        }
	}
}
