package map;

import voyagequest.Global;
import voyagequest.DoubleRect;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.ListIterator;

import voyagequest.VoyageQuest;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.tiled.Layer;
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
        
        //Draw the bottom two layers which are below everything else.
        Global.currentMap.tileMap.render(extraX, extraY, (int)(vRect.x/64), (int)(vRect.y/64), 17, 13,
                        0, false);
        Global.currentMap.tileMap.render(extraX, extraY, (int)(vRect.x/64), (int)(vRect.y/64), 17, 13,
                        1, false);
        
        //We render from (int)(vRect.x/64) and (int)(vRect.y/64)
        
        
        ListIterator deferIterator = null;
        LinkedList<Rectangular> drawingDeferrals = new LinkedList<>();
        
        //Draw things in the buffer if our current row contains the bottom.
        deferIterator = drawingDeferrals.listIterator();
        while (deferIterator.hasNext())
        {
            Rectangular currentRectangular = (Rectangular)deferIterator.next();
            //The Rectangular is likely to be an entity, and getRect() for an Entity returns
            //the boundary for the entity. Therefore, to get the lower Y bound for this Rectangular,
            //add its height to its UL y-position
            double lowerY = currentRectangular.getRect().y + currentRectangular.getRect().height;
            if (lowerY fits inside this row)
            {
                //Draw currentRectangular
                if (currentRectangular instanceof Entity && ((Entity)e).isPlayer)
                {
                    ((Entity)currentRectangular).draw(g,
                           (float)(currentRectangular.getRect().x - vRect.x),
                           (float)(currentRectangular.getRect().y - vRect.y));
                }
                
                //Remove currentRectangular from the drawingDeferrals list.
                deferIterator.remove();
            }
            else
            {
                //Do nothing...
            }
        }

        //Examine the next row now to see which things can be drawn immediately...
        
        
        
        //Get the entities which we need to draw:
        LinkedList<Rectangular> entList = Global.currentMap.collisions.rectQuery(vRect);
        for (Rectangular e : entList)
        {
            //Temporary solution... only draw player
            if (e instanceof Entity && ((Entity)e).isPlayer)
            {
                ((Entity)e).draw(g,
                       (float)(e.getRect().x - vRect.x),
                       (float)(e.getRect().y - vRect.y));
            }
            
        }
        Global.currentMap.tileMap.render(extraX, extraY, (int)(vRect.x/64), (int)(vRect.y/64), 17, 13,
                        2, false);
        
        
        
        
        
        //Draw the things which tower above all else.
        Global.currentMap.tileMap.render(extraX, extraY, (int)(vRect.x/64), (int)(vRect.y/64), 17, 13,
                        3, false);
        Global.currentMap.tileMap.render(extraX, extraY, (int)(vRect.x/64), (int)(vRect.y/64), 17, 13,
                        4, false);
        
        //The partitions help me debug, so draw these too
        drawPartitionBoxes(g);
        
        //Draw all of the collision rectangles.
        for (Rectangular e : entList)
        {
            //Temporary solution...
            if (!(e instanceof Entity))
            {
                //It was a GroupObjectWrapper...
                g.setLineWidth(2.0f);
                g.setColor(Color.black);
                DoubleRect ourRect = e.getRect();
                g.drawRect(
                        (float)(ourRect.x - vRect.x),
                        (float)(ourRect.y - vRect.y),
                        (float)(ourRect.width),
                        (float)(ourRect.height));
            }
            
        }
        
        //Draw the awkward boundary boxes too
        g.setColor(Color.red);
        entList = Global.currentMap.boundaries.rectQuery(vRect);
        for (Rectangular e : entList)
        {
            //It was a GroupObjectWrapper...
                    g.setLineWidth(2.0f);
                    DoubleRect ourRect = e.getRect();
                    g.drawRect(
                            (float)(ourRect.x - vRect.x),
                            (float)(ourRect.y - vRect.y),
                            (float)(ourRect.width),
                            (float)(ourRect.height));
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
