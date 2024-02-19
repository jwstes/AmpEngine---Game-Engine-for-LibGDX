package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class CollisionManager {
	//private EntityManager entityManager = new EntityManager();
    private QuadTreeNode quadTree;
    private Array<PlayerEntity> pList;
    private Array<StaticEntity> sList;
    private Array<AdversarialEntity> aList;
    private Array<AIManager> aiList; 
    
    public CollisionManager(Rectangle worldBounds, int maxObjectsPerNode,Array<PlayerEntity> pList, Array<StaticEntity> sList, Array<AdversarialEntity> aList, Array<AIManager>aiList) {
        // Initialize the quad tree with the size of your game world and max objects per node
        this.quadTree = new QuadTreeNode(worldBounds, maxObjectsPerNode);
        this.pList = pList; 
        this.sList = sList;
        this.aList = aList;
        this.aiList = aiList;
    }

    public void rebuildQuadTree() {
        quadTree.clear();
        // Insert all entities into the QuadTree
        for (PlayerEntity player : pList) {
            quadTree.insert(player);
            //System.out.print(player);
        }
        for (StaticEntity staticEntity : sList) {
            quadTree.insert(staticEntity);
        }
        for (AdversarialEntity ad : aList) {
            quadTree.insert(ad);
        }
        for (AIManager ai : aiList) {
        	quadTree.insert(ai);
        }
    }

    public Entity checkPlayerCollisions() {
        rebuildQuadTree(); // Consider optimizing this for performance
        //System.out.print("ANIMATED SPIKE "+aiList.get(0));
       
        for (PlayerEntity player : pList) {
        	//System.out.print(pList);
            List<Entity> potentialCollisions = quadTree.query(player.getRec(), new ArrayList<>());
            for (Entity other : potentialCollisions) {
                if (other != player && Intersector.overlaps(player.getRec(), other.getRec())) {
                    // Handle collision between player and other entity
                	if(other.getEntityType() == "static") {
                		System.out.println("Collision with entity type: static");
                		
                		return other;
                		
                		//add other logic such as Player.setHealth(-10) or smth
                		
                	}else if(other.getEntityType() == "adversarial") {
                		System.out.println("Collision with entity type: adversarial");
                		//player.setPosX(0);
                		
                		return other;
                	}
                	
                }
            }
        }
		return null;
		
		
    }
}
