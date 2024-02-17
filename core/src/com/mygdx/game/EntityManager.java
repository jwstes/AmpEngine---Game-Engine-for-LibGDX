package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    // Attributes
    private List<Entity> entityList;


    //Constructor
    public EntityManager(){
        entityList = new ArrayList<>();
    }




    // Getter Setter
    public List<Entity> getList()
    {
        return entityList;
    }

    // Functions
    public void add(Entity EntityObj){
        entityList.add(EntityObj);
    }


    //Remove Entity by passing Object itself
    public void remove(Entity EntityObj){
        boolean removed = entityList.removeIf(e -> e.getName().equals(EntityObj.getName()));
        if (removed) {
            System.out.println(EntityObj.getName() + " has been removed from EntityManager List" );
        } else {
            System.out.println(EntityObj.getName() + " failed to be removed from list. Check if Object and Name is correct" );
        }
    }

    // Remove Entity by name (string )
    public void remove(String name){
        boolean removed = entityList.removeIf(e -> e.getName().equals(name));
        if (removed) {
            System.out.println(name + " has been removed from EntityManager List" );
        } else {
            System.out.println(name+ " failed to be removed from list. Check if Name is correct or exists" );
        }
    }

    // Remove EVERY Entity in the list (Clear all in entity)
    public void clearAllEntities(String name){
        entityList.clear();
    }

}
