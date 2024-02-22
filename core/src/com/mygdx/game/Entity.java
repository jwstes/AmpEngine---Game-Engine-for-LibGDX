package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Entity {
	
	protected String name; // name is identifier used in scene so mandatory to set one
	private int health;
	private float x;
	private float y;
	private float speed;
	private Texture tex; // this is the overlaying texture (sprite image)
	private Texture[] animatedTexture;
	private int textureID;
	private boolean isKillable; // Some "enemies" are meant to just attack like a floating canon
								// Not necessary for all games, see how you want to implement the logic.
	private boolean isMovable;
	private boolean isAlive; // For logic that require a boolean way to check if the entity is alive.
							// You can also check if health = 0 but that's scuffed innit.
	private String entityType;
	private Rectangle rec;
	private boolean isCollidable;

	// Constructors
	public Entity(String name, float x, float y, Texture t){   //focus on making it appear, then add speed & health later
		this.name = name;
	    this.x = x;
	    this.y = y;
	    this.tex = t;
	    if (t != null) {
	        this.rec = new Rectangle(x, y, t.getWidth(), t.getHeight());
	    } else {
	        // If the texture is null, you may want to log this or throw an exception
	        // For now, we'll just create a rectangle with default values
	        this.rec = new Rectangle(x, y, 0, 0);
	    }
	}
	public Entity(String name, float x, float y, Texture[] t){   //focus on making it appear, then add speed & health later
		this.x = x;
		this.y = y;
		this.tex = t[0];
		animatedTexture = t;
		textureID = 0;
		this.rec = new Rectangle(x, y, 32, 32);
	}
	public Entity(float x, float y, float speed, Texture tex) {
	    this.x = x;
	    this.y = y;
	    this.speed = speed;
	    this.tex = tex;
	}
	public Entity(){}

	// Setter & Getter
	public int getTextureID() {
		return textureID;
	}
	public void setTextureID(int tID) {
		textureID = tID;
	}

    public Rectangle getCollider() {
        return rec;
    }
    public void setCollider(Rectangle collider) {
        this.rec = collider;
    }
    public void setIsCollidable(boolean b) {
	    isCollidable = b;
	}
    public boolean getIsCollidable() {
	    return isCollidable;
	}
    public String getEntityType() {
		return entityType;
	}
	public String setEntityType(String s) {
		return entityType = s;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setIsAlive(boolean b) {
		isAlive = b;
	}
	public boolean getIsAlive() {
		return isAlive;
	}
	public void setIsKillable(boolean b) {
		isKillable = b;
	}
	public boolean getIsKillable() {
		return isKillable;
	}
	public void setIsMovable(boolean b) {
		isMovable = b;
	}
	public boolean getIsMovable() {
		return isMovable;
	}
	public void setAnimatedTexture(Texture[] t) {
		animatedTexture = t;
	}
	public Texture[] getAnimatedTexture() {
		return animatedTexture;
	}
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
	public void setTexture(Texture assetName){
		this.tex = assetName;
	}
	public Texture getTexture() {
        return tex;
    }
	public Rectangle getRec() {
		return rec;
	}
	public void setRec(Rectangle r) {
		rec = r;
	}
	
	
	
    //CLASS METHODS
    public void updateCollider(float x, float y, int width, int height) {
        // Check if rec is null and instantiate if necessary
    	//System.out.print("IS IT CALLING?");
        if (this.rec == null) {
            this.rec = new Rectangle(x, y, width, height);
        } else {
            // Update the collider's position and size
            this.rec.x = x;
            this.rec.y = y;
            this.rec.width = width;
            this.rec.height = height;
            setCollider(this.rec);
        }
    }

	public void drawBounds(ShapeRenderer shapeRenderer) {
		Rectangle bounds = getRec();
		shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	public void draw(SpriteBatch b) {
		if (tex != null) {
            b.draw(tex, x, y);
        }
	}
	public abstract void update(long lastEntityUpdate);
	public void render(SpriteBatch b) {}
	
	
}