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


/* This Class handles the Player Entity (Player's Character/Sprite) only.*/
/* This record character's health, positioning, image texture and hitbox(rectangle) */
/* Possible implementations to split into smaller classes possible. Possible addition such as guns, etc to be decided)*/
public class PlayerEntity {
    private int health;
    private float posX;
    private float posY;

    public Rectangle rectangle; // Stores a gdx.math.Rectangle instance. // this is the hitbox
    public Texture texture; // this is the overlaying texture (sprite image)
    private int textureHeight;
    private int textureWidth;


    // GETTER SETTERS --------------------------------
    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth(){
        return this.health;
    }

    public void setPosX(float newPosX){
        this.posX = newPosX;
    }
    public float getPosX(float newPosX){
        return this.posX;
    }

    public void setPosY(float newPosY){
        this.posY = newPosY;
    }
    public float getPosY(float newPosY){
        return this.posY;
    }





    // GENERIC METHODS --------------------------------
    // Check if player is still alive
    public boolean isAlive(){
        int hp = this.health;
        return hp > 0;
    }

    // Load image of player entity
    public Texture setTexture(String filename){
        Texture instanceImage;
        instanceImage = new Texture(Gdx.files.internal(filename));
        this.texture = instanceImage;
        return instanceImage;
    }


    // CONSTRUCTORS --------------------------------
    // generic instance of player entity // player is centered by default
    public PlayerEntity() {
        this.health = 10;
        this.posX = (float) Gdx.graphics.getWidth() / 4 ;
        this.posY = (float) Gdx.graphics.getHeight() / 3;   // Locate entity near bottom left
        this.rectangle = new Rectangle();
    }

    // specific parameters for player entity
    public PlayerEntity(int initialHealth){
        this.health = initialHealth;
        this.posX = (float) Gdx.graphics.getWidth() / 4;
        this.posY = (float) Gdx.graphics.getHeight() / 3;
        this.rectangle = new Rectangle();
    }

    public PlayerEntity(int initialHealth, Rectangle rectangleInstance) {
        this.health = initialHealth;
        this.posX = (float) Gdx.graphics.getWidth() / 4;
        this.posY = (float) Gdx.graphics.getHeight() / 3;
        this.rectangle = rectangleInstance;
    }

    public PlayerEntity(int initialHealth, float initial_posX, float initial_posY) {
        this.health = initialHealth;
        this.posX = initial_posX;
        this.posY = initial_posY;
        this.rectangle = new Rectangle();
    }

    public PlayerEntity(int initialHealth, float initial_posX, float initial_posY, Rectangle rectangleInstance) {
        this.health = initialHealth;
        this.posX = initial_posX;
        this.posY = initial_posY;
        this.rectangle = rectangleInstance;
    }


}
