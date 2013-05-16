package map;

import org.newdawn.slick.geom.Rectangle;
import java.util.LinkedList;
import java.util.ListIterator;

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
    private LinkedList<Entity> entities = new LinkedList<Entity>();
    
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
                entities = new LinkedList<>();
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
            if (t.contains(e)) 
                t.adjustPartitions(e);
        }
        
        //Now, are we a penultimate node, with all-leaf children?
        if (children[UL].isLeaf == true &&
            children[UR].isLeaf == true &&
            children[BL].isLeaf == true &&
            children[BR].isLeaf == true)
        {
            //We need to check whether the number of entities (without repeats)
            //is less than or equal to the MAX. If it is less than or equal to,
            //then we merge. If at any point it's greater than, then we bail.
            
            //Get all the candidates.
            LinkedList<Entity> candidateList = new LinkedList<>();
            for (TreeNode t : children)
                candidateList.addAll(t.getEntities());
          
            //Make one pass through the list to remove the ones that fit.
            ListIterator iter = candidateList.listIterator();
            LinkedList<Entity> cleanedList = new LinkedList<>();
            while (iter.hasNext())
            {
                Entity ent = (Entity)iter.next();
                //If it fits perfectly...
                if (getIndex(ent.r) != -1)
                {
                    cleanedList.add(ent);
                    iter.remove();
                    
                    //If we've hit the limit already, then we don't merge.
                    if (cleanedList.size() > tree.MAX_OBJECTS)
                        return;
                }
            }
            
            //Now, candidateList is filled with the duplicates!
            //For each given thing on candidateList, there is either one copy or
            //three copies. 
            while (iter.hasNext())
            {
                iter = candidateList.listIterator();
                Entity ent = (Entity)iter.next();
                iter.remove();
                boolean searching = true;
                int numberFound = 0;
                while (searching)
                {
                    Entity currentComparisonEnt = (Entity)iter.next();
                    if (currentComparisonEnt.equals(ent))
                    {
                        iter.remove();
                        numberFound++;
                        
                        if (numberFound > 1)
                        {
                            if (numberFound == 3)
                            {
                                searching = false;
                                cleanedList.add(ent);
                            }
                        }
                        else 
                        {
                            //Alright, well... are there two more to go? (Like, 
                            int collNumber = 0;
                            for (TreeNode t : children)
                            {
                                if (t.contains(ent)) collNumber++;
                            }
                            if (collNumber == 2)
                            {
                                searching = false;
                                cleanedList.add(ent);
                            }
                        }
                    }
                }
                    
                //If at any point we can bail...
                if (cleanedList.size() > tree.MAX_OBJECTS)
                    return;
            }
            
            entities = cleanedList;
            children = null;
            isLeaf = true;
            
        }
        
    }
    
    
    /*
     * Determine which node the object belongs to. -1 means
     * object cannot completely fit within a child node and is part
     * of the parent node
     */
    private int getIndex(Rectangle pRect) {
        int index = -1;
        double verticalMidpoint = boundary.getX() + (boundary.getWidth() / 2);
        double horizontalMidpoint = boundary.getY() + (boundary.getHeight() / 2);

        // Object can completely fit within the top quadrants
        boolean topQuadrant = (pRect.getY() < horizontalMidpoint &&
                pRect.getY() + pRect.getHeight() < horizontalMidpoint);
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (pRect.getY() > horizontalMidpoint);

        // Object can completely fit within the left quadrants
        if (pRect.getX() < verticalMidpoint && pRect.getX() + pRect.getWidth() < verticalMidpoint) {
           if (topQuadrant) {
             index = 1;
           }
           else if (bottomQuadrant) {
             index = 2;
           }
         }
         // Object can completely fit within the right quadrants
         else if (pRect.getX() > verticalMidpoint) {
          if (topQuadrant) {
            index = 0;
          }
          else if (bottomQuadrant) {
            index = 3;
          }
        }

        return index;
    }
    
    /*
     * Determine which node the object belongs to. -1 means
     * object cannot completely fit within a child node and is part
     * of the parent node
     */
    private int getIndex(int rectX, int rectY, int rectWidth, int rectHeight) {
        int index = -1;
        double verticalMidpoint = boundary.getX() + (boundary.getWidth() / 2);
        double horizontalMidpoint = boundary.getY() + (boundary.getHeight() / 2);

        // Object can completely fit within the top quadrants
        boolean topQuadrant = (rectY < horizontalMidpoint &&
                rectY + rectHeight < horizontalMidpoint);
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (rectY > horizontalMidpoint);

        // Object can completely fit within the left quadrants
        if (rectX < verticalMidpoint && rectX + rectWidth < verticalMidpoint) {
           if (topQuadrant) {
             index = 1;
           }
           else if (bottomQuadrant) {
             index = 2;
           }
         }
         // Object can completely fit within the right quadrants
         else if (rectX > verticalMidpoint) {
          if (topQuadrant) {
            index = 0;
          }
          else if (bottomQuadrant) {
            index = 3;
          }
        }

        return index;
    }
 
    
    public LinkedList<Entity> rectQuery(Rectangle queryRect)
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
            LinkedList<Entity> childrenEntities = new LinkedList<>();
            for (TreeNode t : children)
            {
                if (t.boundary.intersects(queryRect))
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
    
    public LinkedList<TreeNode> getPartitionBoxes()
    {
        LinkedList<TreeNode> containedBoxes = new LinkedList<>();
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
    
    public LinkedList<Entity> getEntities()
    {
        return entities;
    }
}