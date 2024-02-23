package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface DashboardInterface {
    void render(SpriteBatch batch);
    void reduceHealth(int amount);
    void resetDashboard();
    void setSceneManager(SceneManager sceneManager);
}