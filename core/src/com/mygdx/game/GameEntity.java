package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameEntity {
    private int x, y;
    private Texture texture;
    // Additional properties can be included here

    public GameEntity(int x, int y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    public void draw(SpriteBatch batch) {
        if (texture != null) {
            batch.draw(texture, x, y);
        }
    }

    // TEMP ENTITY TO TEST MY CREAT(), DELETE LATER
    // NOT USED DONT CARE 
    // DELETE LATER
}