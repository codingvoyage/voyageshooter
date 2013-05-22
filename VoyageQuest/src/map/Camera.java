package map;

import voyagequest.Global;
import voyagequest.DoubleRect;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.ListIterator;

import voyagequest.VoyageQuest;
import map.Entity;

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

        //The starting tiles of the map to draw
        int startX = (int)(vRect.x/Global.currentMap.TILE_LENGTH);
        int startY = (int)(vRect.y/Global.currentMap.TILE_LENGTH);
        
        //Compensate for distance between tiles
        int extraX = -(int)(vRect.x % 64);
        int extraY = -(int)(vRect.y % 64);

        //Draw the tilemap. This part will need serious refactoring
        
        //Draw the bottom two layers which are below everything else.
        Global.currentMap.tileMap.render(extraX, extraY, startX, startY, 17, 13,
                        0, false);
        Global.currentMap.tileMap.render(extraX, extraY, startX, startY, 17, 13,
                        1, false);
      
        
        Layer objLayer = Global.currentMap.tileMap.getLayers().get(2);
        int tile_length = Global.currentMap.TILE_LENGTH;
        
        int startRow = (int)(vRect.y/64) - 4;
        int endRow = startRow + 17;
        int rowsDrawn = 0;
        
        LinkedList<Rectangular> drawingDeferrals = new LinkedList<>();
        ListIterator deferIterator = null;

        for (int i = startRow; i < endRow; i++)
        {
            ///////////////////////////////////////////////////////////////////
            //=======================/RENDER THIS ROW/=========================
            ///////////////////////////////////////////////////////////////////
            objLayer.render(extraX,
                    rowsDrawn*tile_length + extraY - 4*tile_length,
                    startX, 
                    i, 
                    17, 1,
                    false, tile_length, tile_length);
            rowsDrawn++;
            
            ///////////////////////////////////////////////////////////////////
            ///======/Draw things in the buffer if our current row/=========///
            ///======/contains the bottom of the thing in the buffer/.======///
            ///////////////////////////////////////////////////////////////////
            
            deferIterator = drawingDeferrals.listIterator();
            System.out.println(drawingDeferrals.size());
            while (deferIterator.hasNext())
            {
                Rectangular currentRectangular = (Rectangular)deferIterator.next();
                
                //The Rectangular is likely to be an entity, and getRect() for an Entity returns
                //the boundary for the entity. Therefore, to get the lower Y bound for this Rectangular,
                //add its height to its UL y-position
                double lowerY = currentRectangular.getRect().y + currentRectangular.getRect().height;
                double rowYLower = rowsDrawn*tile_length + extraY - 4*tile_length;
                double rowYHigher = rowYLower + tile_length;
                
                if (rowYLower <= lowerY && lowerY <= rowYHigher)
                {
                    //Draw currentRectangular
                    if (currentRectangular instanceof Entity && ((Entity)currentRectangular).isPlayer)
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

            //////////////////////////////////////////////////////////////////
            ///=============/Fill the buffer for the next row/=============///
            //////////////////////////////////////////////////////////////////
            double nextRowXLower = startX*tile_length + extraX;
            double nextRowYLower = (i + 1)*tile_length + extraY;
            DoubleRect queryRect = new DoubleRect(nextRowXLower, nextRowYLower,
                    VoyageQuest.X_RESOLUTION, tile_length);
            
            LinkedList<Rectangular> entitiesToConsider = Global.currentMap.collisions.rectQuery(queryRect);
            ListIterator entityIterator = entitiesToConsider.listIterator();
            while (entityIterator.hasNext())
            {
                Rectangular currentRectangular = (Rectangular)entityIterator.next();
                if ((currentRectangular instanceof GroupObjectWrapper) 
                    || 
                            
                        !(
                            nextRowYLower <= ((Entity)currentRectangular).getRect().y 
                            && ((Entity)currentRectangular).getRect().y <= nextRowYLower + tile_length
                        ) )
                            
                    entityIterator.remove();
            }
            
            
            
            entityIterator = entitiesToConsider.listIterator();
            
            while (entityIterator.hasNext())
            {
                Rectangular currentRectangular = (Rectangular)entityIterator.next();
                //Get the first entity it collides with.
                LinkedList<Rectangular> collRectangulars =
                        Global.currentMap.boundaries.rectQuery(currentRectangular.getRect());
                if (collRectangulars.size() != 0)
                {
                    Rectangular boundary = collRectangulars.getFirst();
                    //if the boundaryEntity's collisionrect's y is less than the boundary
                    if (
                            (!(currentRectangular instanceof GroupObjectWrapper)) &&
                            (((Entity)currentRectangular).getCollRect().y
                             <= ((BoundaryWrapper)boundary).getSecondaryGroupObject().getRect().y))
                            
                    {
                        //Draw it immediately.
                        ((Entity)currentRectangular).draw(g,
                               (float)(currentRectangular.getRect().x - vRect.x),
                               (float)(currentRectangular.getRect().y - vRect.y));
                        
                    }
                    else
                    {
                        //Place it in the deferredList
                        drawingDeferrals.add(currentRectangular);
                    }
                }
                
                
            }
            
            
        }
            
        
        //Get the entities which we need to draw:
        LinkedList<Rectangular> entList = Global.currentMap.collisions.rectQuery(vRect);
//        for (Rectangular e : entList)
//        {
//            //Temporary solution... only draw player
//            if (e instanceof Entity && ((Entity)e).isPlayer)
//            {
//                ((Entity)e).draw(g,
//                       (float)(e.getRect().x - vRect.x),
//                       (float)(e.getRect().y - vRect.y));
//            }
//            
//        }

        //Draw the things which tower above all else.
        Global.currentMap.tileMap.render(extraX, extraY, startX, startY, 17, 13,
                        3, false);
        Global.currentMap.tileMap.render(extraX, extraY, startX, startY, 17, 13,
                        4, false);

        //The partitions help me debug, so draw these too
        drawPartitionBoxes(g);
 
        
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
                    
            //Draw its corresponding Boundary Wrapper
            GroupObjectWrapper asdf = ((BoundaryWrapper)e).getSecondaryGroupObject();
            g.setColor(Color.black);
                g.setLineWidth(2.0f);
                ourRect = asdf.getRect();
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
