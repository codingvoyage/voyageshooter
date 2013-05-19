package map;

import voyagequest.Global;
import org.newdawn.slick.Graphics;
import voyagequest.DoubleRect;
import java.util.LinkedList;
import voyagequest.VoyageQuest;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 *
 * @author Edmund
 */
public class Camera {
    private double x, y;
    
    public Camera() 
    {
        x = 0.0d;
        y = 2000.0d;
    }
    
    public void pan(double xMove, double yMove)
    {
           //Where would we end up at if we were to pan
           double tempX = x + xMove;
           double tempY = y + yMove;

           double mapWidth = Global.currentMap.MAP_WIDTH;
           double mapHeight = Global.currentMap.MAP_HEIGHT;
           
           //If we don't end up going less than 0, and if we don't
           //end up rendering stuff off the map...
           if (tempX >= 0 && tempY >= 0)
           {
                  if ((tempX < mapWidth - VoyageQuest.X_RESOLUTION)
                     && (tempY < mapHeight - VoyageQuest.Y_RESOLUTION))
                  {
                           x = tempX;
                           y = tempY;
                  }
                  
            }
    }
    
    public DoubleRect getViewRect()
    {
        x = (double)VoyageQuest.player.r.getX();
        y = (double)VoyageQuest.player.r.getY(); 
        
        //Center around the player
        return new DoubleRect(
                (double)VoyageQuest.player.r.getX(),
                (double)VoyageQuest.player.r.getY(), 
                VoyageQuest.X_RESOLUTION,
                VoyageQuest.Y_RESOLUTION);
    }
    
    public void display(Graphics g)
    {
        //get the rectangle representing the Camera's range of vision
        DoubleRect vRect = getViewRect();
        
        
        int extraX = -(int)(vRect.x % 64);
        int extraY = -(int)(vRect.y % 64);
        
        
        //Global.currentMap.tileMap.render(extraX, extraY, (int)(vRect.x/64), (int)(vRect.y/64), 20, 15);
        
        //get the entities which we need to draw:
        LinkedList<Entity> entList = Global.currentMap.collisions.rectQuery(vRect);
        
        drawPartitionBoxes(g);
        for (Entity e : entList)
        {
            e.draw(g, (float)x, (float)y);
            g.drawRect(
                    (float)(e.r.x - x),
                    (float)(e.r.y - y),
                    (float)(e.r.width),
                    (float)(e.r.height));
        }
        
        
        
    }
  
    public void drawPartitionBoxes(Graphics g)
    {
        g.setColor(org.newdawn.slick.Color.yellow);
        LinkedList<TreeNode> partitionBoxes = Global.currentMap.collisions.getPartitions();
        for (TreeNode t : partitionBoxes)
        {
            DoubleRect r = t.boundary;
            g.drawRect(
                    (float)(r.x - x),
                    (float)(r.y - y),
                    (float)(r.width),
                    (float)(r.height));
            
        }
        
    }
    
}
