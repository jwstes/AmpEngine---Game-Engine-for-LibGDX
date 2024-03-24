package com.mygdx.game.Scene;
 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScene {
    private Texture gameOverTexture;
    private BitmapFont font;
    private String Text;

    //CONSTRUCTOR
  
    public GameOverScene(Texture texture) {
        this.gameOverTexture = texture;
        this.font = new BitmapFont(); // It's a good idea to initialize font in all constructors to avoid null checks
        this.Text = "";

    }


    public GameOverScene(Texture texture, String text) {
        this.gameOverTexture = texture;
        this.font = new BitmapFont();
        this.Text = text;
    }
    
    public GameOverScene(String text) {
        this.font = new BitmapFont(); 
        this.Text = text; 
        this.gameOverTexture = null;
    }


    public void render(SpriteBatch batch, String fact) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        batch.begin();
        
        if (gameOverTexture != null) {
            float x = (screenWidth - gameOverTexture.getWidth()) / 2f;
            float y = (screenHeight - gameOverTexture.getHeight()) / 2f;
            batch.draw(gameOverTexture, x, y);
        }

        // Draw text if it's not empty
        if (!fact.isEmpty()) {
            font.getData().setScale(2); // Adjust scale as needed
            // Calculate the width of the text to center it on the screen
            float textX = (screenWidth) / 2f;
            float textY = screenHeight / 2f; // Adjust Y as needed, depending on where you want the text relative to the texture
            font.draw(batch, fact, textX, textY);
        }
        
        batch.end();
    }
}
