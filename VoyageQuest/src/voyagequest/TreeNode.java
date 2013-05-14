package voyagequest;

import java.awt.Rectangle;
import java.util.ArrayList;

/**
 *
 * @author Edmund Qiu
 */
public class TreeNode {
    public static final int UL = 0;
    public static final int UR = 1;
    public static final int BL = 2;
    public static final int BR = 3;
    
    private QuadTree tree;
    private TreeNode parent;
    private TreeNode[] children;
    
    public int level;
    Rectangle boundary; 
   private boolean isLeaf;
    private ArrayList<Entity> entities = new ArrayList<Entity>();
    
    public TreeNode(TreeNode parent, Rectangle boundary, int level, QuadTree tree)
    {
        this.tree = tree;
        this.parent = parent;
        this.boundary = boundary;
        this.level = level;
        this.isLeaf = true;
    }

    public void addEntity(Entity e)
    {
        if (isLeaf)
        {
            entities.add(e);
            //Have we gone over the limit though? And are we under the level-limit?
            if (this.getSize() > tree.MAX_OBJECTS && level < tree.MAX_LEVELS)
            {
                //If so, then we must split.
                this.splitBranches();
                
                //Now, distribute the stuff in our List between our new branches.
                for (Entity toBeMoved : entities)
                {
                    for (int i = 0; i < 4; i++)
                    {
                        if (children[i].contains(toBeMoved))
                        {
                            children[i].addEntity(toBeMoved);
                        }
                    }
                }
                
                //Now, get rid of our entities
                entities = new ArrayList<>();
            }
        }
        else
        {
            //Not a leaf... then simply add to whichever child needs it
            for (int i = 0; i < 4; i++)
            {
                if (children[i].contains(e))
                {
                    children[i].addEntity(e);
                }
            }
        }
    }

    public void removeEntity(Entity e)
    {
        //If this is a leaf...
        if (isLeaf)
        {
            entities.remove(e);
        }
        else
        {
            //Recurse through ...
            for (TreeNode child : children)
                if (child.contains(e)) child.removeEntity(e);
        }
    }
    
    /**
     * Helper method called after removing an Entity. Basically, recursively traces
     * the path that it took to remove Entity, and merges partitions with a size
     * of under the QuadTree's maximum.
     * @param e 
     */
    public void adjustPartitions(Entity e)
    {
        //Alright, if this is a leaf, then we're done.
        if (isLeaf) return;
        
        //Okay so this is not a leaf...
        for (TreeNode t : children)
        {
            t.adjustPartitions(e);
        }
        
        //Now, are we a 
        if (children[UL].isLeaf == true &&
            children[UR].isLeaf == true &&
            children[BL].isLeaf == true &&
            children[BR].isLeaf == true)
        {
                    
            if (this.getSize() <= tree.MAX_OBJECTS) {
                this.merge();
            }
        }
        
    }
    
    private void merge()
    {
        this.entities = new ArrayList<Entity>();
        
        //Take the this.entities from the children branch and put them in our branch
        this.entities.addAll(children[UL].getEntities());
        this.entities.addAll(children[UR].getEntities());
        this.entities.addAll(children[BL].getEntities());
        this.entities.addAll(children[BR].getEntities());
        
        //We are now a childless leaf
        children = null;
        isLeaf = true;
    }
    
    public ArrayList<Entity> rectQuery(Rectangle queryRect)
    {
        if (isLeaf)
        {
            //If this is a leaf, then we will return its belongings.
            return entities;
        }
        else
        {
            //For each child node, see if it touches the rect. If so, recursively
            //make the child node inspect its options too.
            ArrayList<Entity> childrenEntities = new ArrayList<>();
            for (TreeNode t : children)
            {
                if (t.contains(queryRect))
                    childrenEntities.addAll(t.rectQuery(queryRect));
            }
            return childrenEntities;
        }
    }
    
    private void splitBranches()
    {
        int halfWidth = (int)(boundary.getWidth() / 2);
        int halfHeight = (int)(boundary.getHeight() / 2);
        int topULX = (int)(boundary.getX());
        int topULY = (int)(boundary.getY());
        
        Rectangle ULRect = new Rectangle(topULX, topULY, halfWidth, halfHeight);
        Rectangle URRect = new Rectangle(topULX + halfWidth, topULY, halfWidth, halfHeight);
        Rectangle BLRect = new Rectangle(topULX, topULY + halfHeight, halfWidth, halfHeight);
        Rectangle BRRect = new Rectangle(topULX + halfWidth, topULY + halfHeight, halfWidth, halfHeight);
        
        //public TreeNode(TreeNode parent, Rectangle boundary, int level, QuadTree tree)
        children = new TreeNode[4];
        children[UL] = new TreeNode(this, ULRect, this.level + 1, tree);
        children[UR] = new TreeNode(this, URRect, this.level + 1, tree);
        children[BL] = new TreeNode(this, BLRect, this.level + 1, tree);
        children[BR] = new TreeNode(this, BRRect, this.level + 1, tree);
        
        //We are no longer a leaf
        isLeaf = false;
    }
    
    public ArrayList<TreeNode> getPartitionBoxes()
    {
        ArrayList<TreeNode> containedBoxes = new ArrayList<>();
        containedBoxes.add(this);
        if (!isLeaf)
        {
            for (int i = 0; i < 4; i++)
            {
                containedBoxes.addAll(children[i].getPartitionBoxes());
            }
        }
        return containedBoxes;
    }
    
    public boolean contains(Entity e)
    {
        return contains(e.r);
    }
    
    public boolean contains(Rectangle r)
    {
        return boundary.intersects(r);
    }
    
    public boolean isLeaf()
    { 
        //return isLeaf && children == null; 
        return isLeaf; 
    }
    
    public int getSize()
    {
        //If this is a leaf...
        if (isLeaf)
        {
            return entities.size();
        }
        else
        {
            //Recursively find sum
            int sum = children[UL].getSize() + children[UR].getSize() +
                    children[BL].getSize() + children[BR].getSize();
            return sum;
        }
    }
    
    public TreeNode getParent()
    {
        return parent;
    }
    
    public ArrayList<Entity> getEntities()
    {
        return entities;
    }
}