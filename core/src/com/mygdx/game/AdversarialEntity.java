package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

public class AdversarialEntity extends Entity {
    private AIManager ai; // Tells the entity to walk back and forth,  stand still or it go up down. Or shoot at intervals
    private AdversarialEntity projectile; // Optional to use (e.g. when you EnemyA shoot a bullet, it is technically
                                            //  shooting another AdversarialEntity Object out, just that this one not
                                            // killable.
    
    //AI spike stuff, not sure if this is ok. (Subject for removal)
	private long lastTextureChangeTime;


    private boolean moveRight;
    private float movementSpeed;
    private float attackSpeed;

    private Texture[] spikeTextures; // Array to store spike textures
    private int currentSpikeImageIndex; // Index of the current spike image

    

    public AdversarialEntity(String n,float x, float y, Texture t){
        super(n,x,y,t); //need name, x, y, Texture
    }

    public void setAIManager(AIManager ai){
        this.ai = ai;
    }

    public AIManager getAIManager(){ //Why would you want to grab an AI manager? (maybe for clone function later)
        return this.ai;
    }
    
    
    //idk if im suppose to do this here  subject for removal, just integrating of AI trap first
    
    public AdversarialEntity(float initialPosX, float initialPosY, float attackSpeed, Texture[] textures, float movementSpeed) {
        super(initialPosX, initialPosY, movementSpeed, textures[0]);
        this.moveRight = true; // Set the initial direction to move right
        this.attackSpeed = attackSpeed;
        this.movementSpeed = movementSpeed; // Set the speed of the spike

        // Initialize spikeTextures array with the provided textures
        spikeTextures = textures;
        currentSpikeImageIndex = 0; // Start with the first spike image
    }
    
	
    @Override
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        float spikeMovement = movementSpeed * deltaTime;

        if (moveRight) {
            setPosX(getPosX() + spikeMovement);
        } else {
            setPosX(getPosX() - spikeMovement);
        }

        // Check if spike has reached screen boundaries
        if (getPosX() < 0) {
            moveRight = true;
        } else if (getPosX() > Gdx.graphics.getWidth() - getTexture().getWidth()) {
            moveRight = false;
        }

        // Control the speed of transition between spike textures based on attackSpeed
        float transitionSpeed = 1.0f / attackSpeed; // Adjust this value for the desired transition speed
        long currentTime = TimeUtils.millis();
        float timeElapsed = (currentTime - lastTextureChangeTime) / 1000.0f; // Convert to seconds

        if (timeElapsed > transitionSpeed) {
            // Increment currentSpikeImageIndex to smoothly transition between spike textures
            currentSpikeImageIndex++;
            if (currentSpikeImageIndex >= spikeTextures.length) {
                currentSpikeImageIndex = 0; // Reset to the first texture
            }

            setTexture(spikeTextures[currentSpikeImageIndex]); // Set the current texture
            lastTextureChangeTime = currentTime; // Update the last texture change time
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(getTexture(), getPosX(), getPosY());
    }



}
