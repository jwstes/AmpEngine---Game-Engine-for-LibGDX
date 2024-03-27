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
        	font = new BitmapFont(Gdx.files.internal("font.fnt"));
            font.getData().setScale(0.7f); // Adjust scale as needed
            // Calculate the width of the text to center it on the screen
	        Texture backgroundImage = new Texture(Gdx.files.internal("factsBG.png"));
	        
            float textX = (screenWidth) / 2f - 300;
            float textY = screenHeight / 2f + 250; // Adjust Y as needed, depending on where you want the text relative to the texture
            batch.draw(backgroundImage, textX-50, textY-100, backgroundImage.getWidth()+200, backgroundImage.getHeight());
            font.draw(batch, fact, textX, textY);
        }
        
        batch.end();
    }
}
