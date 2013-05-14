package map;

import org.newdawn.slick.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import voyagequest.VoyageQuest;

/**
 *
 * @author Edmund
 */
public class Camera {
    public int x, y;
    
    public Camera()
    {
        x =0;y=0;
    }
    
    Camera.attemptMove()
    {
        
    }
    
    public Rectangle getViewRect()
    {
        
        return new Rectangle(x, y, VoyageQuest.X_RESOLUTION,
                VoyageQuest.Y_RESOLUTION);
    }
    
    public void display(Graphics g)
    {
        drawPartitionBoxes(g);
        
        //get the rectangle representing the Camera's range of vision
        Rectangle vRect = getViewRect();
        
        //get the entities which we need to draw:
        ArrayList<Entity> entList = VoyageQuest.partitionTree.rectQuery(vRect);
        
        //now draw them all
        for (Entity e : entList)
        {
            g.drawRect(
                    e.r.x - x,
                    e.r.y - y,
                    e.r.width,
                    e.r.height);
        }
                
    }
  
    public void drawPartitionBoxes(Graphics g)
    {
        g.setColor(org.newdawn.slick.Color.yellow);
        ArrayList<TreeNode> partitionBoxes = VoyageQuest.partitionTree.getPartitions();
        for (TreeNode t : partitionBoxes)
        {
            Rectangle r = t.boundary;
            g.drawRect(
                    r.x,
                    r.y,
                    r.width,
                    r.height);
            
        }
        
    }
    
}