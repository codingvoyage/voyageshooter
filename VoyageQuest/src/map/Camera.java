package map;

import voyagequest.Global;
import voyagequest.DoubleRect;
import java.util.LinkedList;

import voyagequest.VoyageQuest;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Edmund
 */

public class Camera {
    private double x, y;
    
    private double screenCenterX;
    private double screenCenterY;
    
    public Camera() 
    {
        //Calculate some useful things...
        screenCenterX = VoyageQuest.X_RESOLUTION/2.0;
        screenCenterY = VoyageQuest.Y_RESOLUTION/2.0;
    }
    
//    public void pan(double xMove, double yMove)
//    {
//           //Where would we end up at if we were to pan
//           double tempX = x + xMove;
//           double tempY = y + yMove;
//
//           double mapWidth = Global.currentMap.MAP_WIDTH;
//           double mapHeight = Global.currentMap.MAP_HEIGHT;
//           
//           //If we don't end up going less than 0, and if we don't
//           //end up rendering stuff off the map...
//           if (tempX >= 0 && tempY >= 0)
//           {
//                  if ((tempX < mapWidth - VoyageQuest.X_RESOLUTION)
//                     && (tempY < mapHeight - VoyageQuest.Y_RESOLUTION))
//                  {
//                           x = tempX;
//                           y = tempY;
//                  }
//                  
//            }
//    }
    
    
    //Center around the player
    public DoubleRect getViewRect()
    {
        //Using the width and height of the player, arrive at the 
        double playerCenterX = VoyageQuest.player.r.getX() + VoyageQuest.player.r.width/2.0;
        double playerCenterY = VoyageQuest.player.r.getY() + VoyageQuest.player.r.height/2.0;
        
        //Now, compensate for the length of the screen
        double startX = playerCenterX - screenCenterX;
        double startY = playerCenterY - screenCenterY;
        
        return new DoubleRect(
                startX,
                startY, 
                VoyageQuest.X_RESOLUTION,
                VoyageQuest.Y_RESOLUTION);
    }
    
    public void display(Graphics g) throws SlickException
    {
        //get the rectangle representing the Camera's range of vision
        DoubleRect vRect = getViewRect();

        //Compensate for distance between tiles
        int extraX = -(int)(vRect.x % 64);
        int extraY = -(int)(vRect.y % 64);

        //Draw the tilemap. This part will need serious refactoring
        Global.currentMap.tileMap.render(extraX, extraY, (int)(vRect.x/64), (int)(vRect.y/64), 17, 13);

        //The partitions help me debug, so draw these too
        drawPartitionBoxes(g);
        
        //Get the entities which we need to draw:
        LinkedList<Entity> entList = Global.currentMap.collisions.rectQuery(vRect);

        
        for (Entity e : entList)
        {
            //Temporary solution...
            if (e.isPlayer)
            {
                e.draw(g, (float)x, (float)y);
            }
            else
            {
                
                g.setLineWidth(2.0f);
                g.setColor(Color.black);
                g.drawRect(
                        (float)(e.r.x - vRect.x),
                        (float)(e.r.y - vRect.y),
                        (float)(e.r.width),
                        (float)(e.r.height));
            }
            
        }
        
        
        
    }
  
    public void drawPartitionBoxes(Graphics g)
    {
        //The viewrect represents where in the map to start drawing from, so we can subtract this
        //rectangle's x and y values from an entity's x and y values in order to get the location on
        //the screen to draw them.
        DoubleRect dr = getViewRect();
                
        g.setColor(org.newdawn.slick.Color.yellow);
        
        LinkedList<TreeNode> partitionBoxes = Global.currentMap.collisions.getPartitions();
        for (TreeNode t : partitionBoxes)
        {
            DoubleRect r = t.boundary;
            g.drawRect(
                    (float)(r.x - dr.x),
                    (float)(r.y - dr.y),
                    (float)(r.width),
                    (float)(r.height));
            
        }
        
    }
    
}
