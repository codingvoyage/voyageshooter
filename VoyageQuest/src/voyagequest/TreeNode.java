package voyagequest;

import java.awt.Rectangle;
import java.util.LinkedList;
/**
 *
 * @author user
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
    public LinkedList<Entity> entities = new LinkedList<Entity>();
    
    
    public TreeNode(TreeNode parent, Rectangle boundary, int level, QuadTree tree) {
        this.tree = tree;
        this.parent = parent;
        this.boundary = boundary;
        this.level = level;
    }
  
    private void splitBranches()
    {
        //
        int halfWidth = (int)(boundary.getWidth() / 2);
        int halfHeight = (int)(boundary.getHeight() / 2);
        int topULX = (int)(boundary.getX();
        int topULY = (int)(boundary.getY();
        
        Rectangle ULRect = new Rectange(topULX, topULY, halfWidth, halfHeight);
        Rectangle URRect = new Rectange(topULX + halfWidth, topULY, halfWidth, halfHeight);
        Rectangle BLRect = new Rectange(topULX, topULY + halfHeight, halfWidth, halfHeight);
        Rectangle BRRect = new Rectange(topULX + halfWidth, topULY + halfHeight, halfWidth, halfHeight);
        
        //public TreeNode(TreeNode parent, Rectangle boundary, int level, QuadTree tree)
        children = new TreeNode[4];
        children[UL] = new TreeNode(this, ULRect, this.level + 1, tree)
        children[UR] = new TreeNode(this, URRect, this.level + 1, tree)
        children[BL] = new TreeNode(this, BLRect, this.level + 1, tree)
        children[BR] = new TreeNode(this, BRRect, this.level + 1, tree)
    }

    public void removeEntity(Entity e) {
      this.entities.remove(e);
    }
    
    public boolean addEntity(Entity e) {
        // If a leaf partition then simply add the object here
        if(level == tree.maxLevel) {
          myobjects.add(obj);
          obj.qp = this;
          return true;
        }
        // Attempt to add object to sub tree
        if(children != null) {
          int q = ((obj.pos.x < cX) ? 0 : 1) + ((obj.pos.y < cY) ? 2 : 0);
          if(children[q].contains(obj)) {
            children[q].addGameObject(obj);
            return true;
          }
        }
        // If we have no children and the number of objects is not too great
        // then add to myobjects
        if(children == null && myobjects.size() < tree.maxObjects) {
          myobjects.add(obj);
          obj.qp = this;
          return true;
        }
        // Object not yet added so if we have no children create them
        if(children == null)
          buildSubTree();
        myobjects.add(obj);
        obj.qp = this;
        return true;
    }


    
    public boolean contains(Entity e) {
        float nLowX = obj.pos.x - obj.colRad;
        float nHighX = obj.pos.x + obj.colRad;
        float nLowY = obj.pos.y - obj.colRad;
        float nHighY = obj.pos.y + obj.colRad;
        return (nLowX > startX && nHighX < endX && 
                nLowY > startY && nHighY < endY);
    }
    
    public boolean contains(Rectangle r) {
        float nLowX = obj.pos.x - obj.colRad;
        float nHighX = obj.pos.x + obj.colRad;
        float nLowY = obj.pos.y - obj.colRad;
        float nHighY = obj.pos.y + obj.colRad;
        return (nLowX > startX && nHighX < endX && 
                nLowY > startY && nHighY < endY);
    }
    
    public TreeNode getParent()
    {
        return parent;
    }
}
