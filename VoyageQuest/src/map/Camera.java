package map;

import org.newdawn.slick.Graphics;
import voyagequest.DoubleRect;
import java.util.LinkedList;
import voyagequest.VoyageQuest;

/**
 *
 * @author Edmund
 */
public class Camera {
    private int x, y;
    
    public Camera()
    {
        x = 0;
        y= 0;
    }
    
    public void attemptMove(double xMove, double yMove)
    {
           //Where would we end up at
           double tempX = x + xMove;
           double tempY = y + yMove;

           double mapWidth = VoyageQuest.MAP_WIDTH;
           double mapHeight = VoyageQuest.MAP_HEIGHT;
           //If we don't end up going less than 0, and if we don't
           //end up rendering stuff off the map...
           if (tempX >= 0 && tempY >= 0)
           {
                  if ((tempX < mapWidth - VoyageQuest.X_RESOLUTION)
                     && (tempY < mapHeight - VoyageQuest.Y_RESOLUTION))
                  {
                           x = (int)tempX;
                           y = (int)tempY;
                  }
                  
            }
    }
    
    public DoubleRect getViewRect()
    {
        
        return new DoubleRect(x, y, VoyageQuest.X_RESOLUTION,
                VoyageQuest.Y_RESOLUTION);
    }
    
    public void display(Graphics g)
    {
        drawPartitionBoxes(g);
        
        //get the rectangle representing the Camera's range of vision
        DoubleRect vRect = getViewRect();
        
        //get the entities which we need to draw:
        LinkedList<Entity> entList = VoyageQuest.partitionTree.rectQuery(vRect);
        
        //now draw them all
        for (Entity e : entList)
        {
            g.drawRect(
                    (float)e.r.x - x,
                    (float)e.r.y - y,
                    (float)e.r.width,
                    (float)e.r.height);
        }
                
    }
  
    public void drawPartitionBoxes(Graphics g)
    {
        g.setColor(org.newdawn.slick.Color.yellow);
        LinkedList<TreeNode> partitionBoxes = VoyageQuest.partitionTree.getPartitions();
        for (TreeNode t : partitionBoxes)
        {
            DoubleRect r = t.boundary;
            g.drawRect(
                   (float) r.x - x,
                    (float)r.y - y,
                    (float)r.width,
                    (float)r.height);
            
        }
        
    }
    
}
