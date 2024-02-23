package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
/**********************************************************************************************
 * Collision Manager class used to Manage Entity Collisions Optimised with Quad Tree and with *
 * methods like LeftRightCollision and CheckPlayerCollision                                   *
 **********************************************************************************************/

public class CollisionManager {
    private CollisionEntityManager quadTree;
    private Array<PlayerEntity> pList;
    private Array<StaticEntity> sList;
    private Array<AdversarialEntity> aList;
    private Array<AIManager> aiList; 
    

    public CollisionManager(Array<PlayerEntity> pList, Array<StaticEntity> sList, Array<AdversarialEntity> aList, Array<AIManager>aiList) {
        this.quadTree = new CollisionEntityManager();                 										//Initialise Quad Tree with the size of your world and number of Entities per node
        this.pList = pList; 
        this.sList = sList;
        this.aList = aList;
        this.aiList = aiList;
    }

    //CLASS METHODS
    public void rebuildQuadTree() {

        quadTree.clear();
        for (PlayerEntity player : pList) {
            quadTree.insert(player);
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
        rebuildQuadTree();
        																							//Check Player coolision and returns the Entity 
        for (PlayerEntity player : pList) {
            List<Entity> potentialCollisions = quadTree.query(player.getRec(), new ArrayList<>());
            for (Entity other : potentialCollisions) {
            	
            	if(other.getIsCollidable() == true) {
            		if (other != player && Intersector.overlaps(player.getRec(), other.getRec())) {
                    	if(other.getEntityType() == "static") {
                    		return other;	
                    	}else if(other.getEntityType() == "adversarial") {	
                    		return other;
                    	}
                    }
            	}
                
            }
        }
		return null;
    }
    																								
    /************************************************************
    * Methods to check Left and right Collison of Player Entity *
    * and returns the X value just before that entity           *
    *************************************************************/																								
    public float LeftRightCollision(PlayerEntity player) 
    {
    	Rectangle playerRect = player.getRec();
    	float newXPosition = playerRect.x;
        for (Entity entity : sList) {
            Rectangle entityRect = entity.getRec();

            boolean collisionLeft = playerRect.x <= entityRect.x + entityRect.width && playerRect.x > entityRect.x &&
                                    playerRect.y < entityRect.y + entityRect.height && playerRect.y + playerRect.height > entityRect.y;
            boolean collisionRight = playerRect.x + playerRect.width >= entityRect.x && playerRect.x + playerRect.width < entityRect.x + entityRect.width &&
                                     playerRect.y < entityRect.y + entityRect.height && playerRect.y + playerRect.height > entityRect.y;

            if (collisionLeft) {
            	 newXPosition = entityRect.x + entityRect.width; 
                 break;
            } else if (collisionRight) {
            	 newXPosition = entityRect.x - playerRect.width;
                 break; 
            }       
        }
        return newXPosition;		
    }
    /************************************************************
    * Methods to check Up and Down Collison of Player Entity    *
    * and returns the Y value just before that entity           *
    *************************************************************/	
    public float UpDownCollision(PlayerEntity player) 
    {
    	
    	Rectangle playerRect = player.getRec();
    	float newXPosition = playerRect.x;
    	for (Entity entity : sList) {
    	    Rectangle entityRect = entity.getRec();
  
    	    boolean collisionTop = playerRect.y + playerRect.height >= entityRect.y && playerRect.y + playerRect.height < entityRect.y + entityRect.height &&
    	                           playerRect.x < entityRect.x + entityRect.width && playerRect.x + playerRect.width > entityRect.x;

    	    boolean collisionBottom = playerRect.y <= entityRect.y + entityRect.height && playerRect.y > entityRect.y &&
    	                              playerRect.x < entityRect.x + entityRect.width && playerRect.x + playerRect.width > entityRect.x;

    	    
    	    if (collisionTop) {
    	        newXPosition = entityRect.y - playerRect.height; 
                break;
    	       
    	    }

    	    
    	    if (collisionBottom) {
    	        newXPosition = entityRect.y + entityRect.height; 
                break;  
    	    }
    	}
    	return newXPosition;
    }

}
