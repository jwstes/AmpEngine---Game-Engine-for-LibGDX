package com.mygdx.game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private List<Entity> entityList;

    public List<Entity> getList()
    {
        return entityList;
    }

    public EntityManager(){
        entityList = new ArrayList<>();
    }
    public void add(Entity EntityObj){
        entityList.add(EntityObj);
    }

}
