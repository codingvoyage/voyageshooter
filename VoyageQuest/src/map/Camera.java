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
    private double x, y;
    
    public Camera()
    {
        x = 0.0;
        y= 0.0;
    }
    
    public void attemptMove(double xMove, double yMove)
    {
           //Where would we end up at
           double tempX = x + xMove;
           double tempY = y + yMove;

           double mapWidth = 5 * VoyageQuest.X_RESOLUTION;
           double mapHeight = 5 * VoyageQuest.Y_RESOLUTION;
           //If we don't end up going less than 0, and if we don't
           //end up rendering stuff off the map...
           if (tempX >= 0 && tempY >= 0)
           {
                  if ((tempX < mapWidth - VoyageQuest.X_RESOLUTION)
                     && (tempY < mapHeight - VoyageQuest.Y_RESOLUTION)
                  {
                           x = tempX; y = tempY;
                  }
                  
            }
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
