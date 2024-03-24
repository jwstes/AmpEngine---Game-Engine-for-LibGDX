package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Scene.SceneManager;

public interface DashboardInterface {
    void render(SpriteBatch batch);
    void reduceHealth(int amount);
    void resetDashboard();
    void setSceneManager(SceneManager sceneManager);
}