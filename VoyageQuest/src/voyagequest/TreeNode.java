package voyagequest;

import java.awt.Rectangle;
import java.util.LinkedList;
/**
 *
 * @author user
 */
public class TreeNode {
    private QuadTree tree;
    
    private TreeNode parent;
    private TreeNode[] children;
    
    Rectangle boundary;
    
    public int level;
    public int size;
    
    public LinkedList<Entity> myobjects = new LinkedList<Entity>();
    
    public TreeNode(TreeNode parent, int cX, int cY, int size, int level, QuadTree tree) {
        this.tree = tree;
        this.parent = parent;
        this.cX = cX;
        this.cY = cY;
        this.size = size;
        this.level = level;
        int halfSize = size/2;
        startX = cX - halfSize;
        endX = cX + halfSize;
        startY = cY - halfSize;
        endY = cY + halfSize;
    }
    

    public void removeObject(Entity obj) {
      this.myobjects.remove(obj);
    }
  
    private void splitBranches()
    {
        children = new TreeNode[4];
        children[0] = new TreeNode(this, cX - size/4, cY + size/4, size/2, level + 1, tree);
        children[1] = new TreeNode(this, cX + size/4, cY + size/4, size/2, level + 1, tree);
        children[2] = new TreeNode(this, cX - size/4, cY - size/4, size/2, level + 1, tree);
        children[3] = new TreeNode(this, cX + size/4, cY - size/4, size/2, level + 1, tree);
    }
    
    
    public boolean addGameObject(GameObject obj) {
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
