package map;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import java.util.LinkedList;
import voyagequest.VoyageQuest;

/**
 *
 * @author Edmund
 */
public class Camera {
    private float x, y;
    
    public Camera()
    {
        x = 0.0f;
        y = 0.0f;
    }
    
    public void attemptMove(float xMove, float yMove)
    {
           //Where would we end up at
           float tempX = x + xMove;
           float tempY = y + yMove;

           float mapWidth = VoyageQuest.MAP_WIDTH;
           float mapHeight = VoyageQuest.MAP_HEIGHT;
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
        LinkedList<Entity> entList = VoyageQuest.partitionTree.rectQuery(vRect);
        
        //now draw them all
        for (Entity e : entList)
        {
            g.drawRect(
                    e.r.getX() - x,
                    e.r.getY() - y,
                    e.r.getWidth(),
                    e.r.getHeight());
        }
                
    }
  
    public void drawPartitionBoxes(Graphics g)
    {
        g.setColor(org.newdawn.slick.Color.yellow);
        LinkedList<TreeNode> partitionBoxes = VoyageQuest.partitionTree.getPartitions();
        for (TreeNode t : partitionBoxes)
        {
            Rectangle r = t.boundary;
            g.drawRect(
                    r.getX() - x,
                    r.getY() - y,
                    r.getWidth(),
                    r.getHeight());
            
        }
        
    }
    
}
