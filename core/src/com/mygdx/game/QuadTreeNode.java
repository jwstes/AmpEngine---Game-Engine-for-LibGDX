package com.mygdx.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class QuadTreeNode {
	private Rectangle bounds; // The area covered by this node
    private int maxObjects; // Maximum objects a node can hold before splitting
    private List<Entity> objects; // Entities within this node
    private QuadTreeNode[] children; // Child nodes

    public QuadTreeNode(Rectangle bounds, int maxObjects) {
        this.bounds = bounds;
        this.maxObjects = maxObjects;
        this.objects = new ArrayList<>();
        this.children = new QuadTreeNode[4]; // Four quadrants: top-left, top-right, bottom-left, bottom-right
    }
    
    public void insert(Entity entity) {
        if (children[0] != null) { // If this node is subdivided, delegate to children
            int index = getIndex(entity.getRec());
            if (index != -1) {
                children[index].insert(entity);
                return;
            }
        }

        objects.add(entity);

        if (objects.size() > maxObjects) {
            if (children[0] == null) {
                subdivide(); // Subdivide this node into 4 children
            }

            // Redistribute entities into children
            Iterator<Entity> iterator = objects.iterator();
            while (iterator.hasNext()) {
                Entity e = iterator.next();
                int index = getIndex(e.getRec());
                if (index != -1) {
                    children[index].insert(e);
                    iterator.remove();
                }
            }
        }
    }
    

	private void subdivide() {
        float subWidth = bounds.width / 2;
        float subHeight = bounds.height / 2;
        float x = bounds.x;
        float y = bounds.y;

        children[0] = new QuadTreeNode(new Rectangle(x + subWidth, y, subWidth, subHeight), maxObjects);
        children[1] = new QuadTreeNode(new Rectangle(x, y, subWidth, subHeight), maxObjects);
        children[2] = new QuadTreeNode(new Rectangle(x, y + subHeight, subWidth, subHeight), maxObjects);
        children[3] = new QuadTreeNode(new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight), maxObjects);
    }
    
    public List<Entity> query(Rectangle area, List<Entity> found) {
        if (!bounds.overlaps(area)) {
            return found; // If the query area doesn't overlap this node, return
        }

        for (Entity e : objects) {
            if (area.overlaps(e.getRec())) {
                found.add(e);
            }
        }

        if (children[0] != null) { // If this node has children, query them
            for (int i = 0; i < children.length; i++) {
                children[i].query(area, found);
            }
        }

        return found;
    }

    public void clear() {
        // First, clear all entities stored in this node
        objects.clear();

        // If this node has children, recursively clear them and then remove them
        if (children[0] != null) {
            for (int i = 0; i < children.length; i++) {
                children[i].clear();  // Recursively clear each child node
                children[i] = null;   // Remove the reference to the child node
            }
        }
    }
	
	public int getIndex(Rectangle rect) {
	    int index = -1;
	    double verticalMidpoint = bounds.x + (bounds.width / 2);
	    double horizontalMidpoint = bounds.y + (bounds.height / 2);

	    // Top or bottom half
	    boolean topHalf = rect.y < horizontalMidpoint && rect.y + rect.height <= horizontalMidpoint;
	    boolean bottomHalf = rect.y >= horizontalMidpoint;

	    // Object can completely fit within the left quadrants
	    if (rect.x < verticalMidpoint && rect.x + rect.width <= verticalMidpoint) {
	        if (topHalf) {
	            index = 1; // Top left
	        } else if (bottomHalf) {
	            index = 2; // Bottom left
	        }
	    }
	    // Object can completely fit within the right quadrants
	    else if (rect.x >= verticalMidpoint) {
	        if (topHalf) {
	            index = 0; // Top right
	        } else if (bottomHalf) {
	            index = 3; // Bottom right
	        }
	    }

	    return index;
	}
	
	
	
	//debugging
	public void draw(ShapeRenderer shapeRenderer) {
        // Draw the boundary of this node
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);

        // Recursively draw child nodes
        if (children[0] != null) {
            for (QuadTreeNode child : children) {
                child.draw(shapeRenderer);
            }
        }
    }

}
