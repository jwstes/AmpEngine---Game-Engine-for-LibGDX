package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScene {
    private Texture gameOverTexture;

    public GameOverScene(Texture texture) {
        this.gameOverTexture = texture;
    }

    public void render(SpriteBatch batch) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float x = (screenWidth - gameOverTexture.getWidth()) / 2f;
        float y = (screenHeight - gameOverTexture.getHeight()) / 2f;

        batch.begin();
        batch.draw(gameOverTexture, x, y);
        batch.end();
    }
}
