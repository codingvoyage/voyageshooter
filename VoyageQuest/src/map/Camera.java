package map;

import voyagequest.Global;
import voyagequest.DoubleRect;
import voyagequest.Util;
import voyagequest.VoyageQuest;
import map.Entity;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.HashMap;

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
      
        //Perform important calculations
        Layer objLayer = Global.currentMap.tileMap.getLayers().get(2);
        int tile_length = Global.currentMap.TILE_LENGTH;
        int startRow = (int)(vRect.y/64) - 4;
        int endRow = startRow + 17;
        int rowsDrawn = 0;
        
        //The deferred list is the list of Entities which must wait...
        LinkedList<Rectangular> drawingDeferrals = new LinkedList<>();
        ListIterator deferIterator = null;

        //Query the region for Rectangulars
        DoubleRect queryRect = this.getViewRect();
        LinkedList<Rectangular> entitiesToConsider = Global.currentMap.collisions.rectQuery(queryRect);
        
        //Rectangulars include the GroupObjectWrappers which work in collision detection, so let's
        //filter them out.
        ListIterator entityIterator = entitiesToConsider.listIterator();
        while (entityIterator.hasNext())
        {
            Rectangular currentRectangular = (Rectangular)entityIterator.next();
            if (currentRectangular instanceof GroupObjectWrapper)
            {
                entityIterator.remove();
            }
            else
            {
                //Also don't forget to set their rendersettings to null
                ((Entity)currentRectangular).renderSetting = null;
            }
        }
        
        
        //The following procedure will associate each Entity with a RenderSetting object which
        //describes which tile it should be rendered in, and...
        for (Rectangular r : entitiesToConsider)
        {
            //We are in fact working with an Entity here...
            Entity e = (Entity)r;
            DoubleRect entityRect = e.getRect();
            
            //Get a list of all the BoundaryWrappers this Entity could collide with
            LinkedList<Rectangular> collisionChoices = Global.currentMap.boundaries.rectQuery(e.getRect());
            
            //RenderSetting to be set soon.
            RenderSetting newRenderSetting;
            
            //Find the first one it actually collides with. 
            BoundaryWrapper chosenBoundary = null;
            for (Rectangular check : collisionChoices)
            {
                //If this boundary touches the Entity, then we have our match
                if ( ((BoundaryWrapper)check).getRect().intersects( entityRect ) )
                {
                    chosenBoundary = (BoundaryWrapper)check;
                    //We're done. We found the first thing Entity collides with.
                    break;
                }
            }
            
            //If chosenBoundary is still null after the process, then we can just
            //insert this entity for drawing and move on to the next entity because
            //we don't have to deal with touching something on the map.
            if (chosenBoundary == null)
            {
                System.out.println("We can just draw it!");
                
                //Find the UL corner's x,y and get the tile it belongs in
                DoubleRect entityUL = Util.coordinateToTile(
                        e.getRect().x, e.getRect().y);
                
                
                System.out.println("We should render right after tile " + 
                        entityUL.toString());
                
                newRenderSetting = new RenderSetting((int)entityUL.y, false);
                e.renderSetting = newRenderSetting;
                continue;
            }
            
            System.out.println("Need to figure out a way to draw it!");
            
            //Compare the collisionbox of the entity with the collisionbox in
            //the boundary to determine what should be done
            GroupObjectWrapper boundaryColl = chosenBoundary.getSecondaryGroupObject();
            double boundaryCollY = boundaryColl.getRect().getY();
            
            DoubleRect entityColl = e.getCollRect();
            double entityCollY = entityColl.getY();
            
            System.out.println("Entity collision Y: " + entityCollY);
            System.out.println("Boundary collision Y: " + boundaryCollY);
            
            if (entityCollY >= boundaryCollY)
            {
                //This means that the entity is ahead of the boundary.
                System.out.println("The entity is ahead of the object");
                DoubleRect setting = chosenBoundary.getLowestTile();
                System.out.println("We should render right after tile " + 
                        setting.toString());
                
                newRenderSetting = new RenderSetting((int)setting.y, false);
                e.renderSetting = newRenderSetting;
                
            }
            else
            {
                System.out.println("The entity is behind the object");
                DoubleRect setting = chosenBoundary.getTopTile();
                System.out.println("We should render right before tile " + 
                        setting.toString());
                newRenderSetting = new RenderSetting((int)setting.y - 1, true);
                e.renderSetting = newRenderSetting;
            }
            
        }
        
        
            
        for (int i = startRow; i < endRow; i++)
        {
            
            //Render all objects which are in the row and
            //are supposed to be behind the tile
            entityIterator = entitiesToConsider.listIterator();
            while (entityIterator.hasNext())
            {
                Entity e = (Entity)entityIterator.next();
                
                if (e.renderSetting.drawnRow == i &&
                    e.renderSetting.drawBefore == true)
                {
                    e.draw(g,
                            (float)(e.r.x - vRect.getX()),
                            (float)(e.r.y - vRect.getY())
                           );
                    entityIterator.remove();
                }
            }
            
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
            
            //Render all objects which are in the row and
            //are supposed to be ahead of the tile
            entityIterator = entitiesToConsider.listIterator();
            while (entityIterator.hasNext())
            {
                Entity e = (Entity)entityIterator.next();
                
                if (e.renderSetting.drawnRow == i &&
                    e.renderSetting.drawBefore == false)
                {
                    e.draw(g,
                            (float)(e.r.x - vRect.getX()),
                            (float)(e.r.y - vRect.getY())
                           );
                    entityIterator.remove();
                }
            }
        }
        
            
        //Draw the things which tower above all else.
        Global.currentMap.tileMap.render(extraX, extraY, startX, startY, 17, 13,
                        3, false);
        Global.currentMap.tileMap.render(extraX, extraY, startX, startY, 17, 13,
                        4, false);
        
        //Draw the tile borders
        g.setColor(Color.black);
        g.setLineWidth(1.0f);
        for (int i = 0; i < 17; i++)
        {
            g.drawLine(0, i * tile_length + extraY, 1200, i * tile_length + extraY);
            Util.FONT.drawString(0, i * tile_length + extraY + 30, "Row " + (startY + i));
        }
        for (int i = 0; i < 17; i++)
        {
            g.drawLine(i * tile_length + extraX, 0, i * tile_length + extraX, 800);
            
        }
        
        
        //The partitions help me debug, so draw these too
        drawPartitionBoxes(g);
        
        //Draw the awkward boundary boxes too
        g.setColor(Color.red);
        LinkedList<Rectangular> entList = Global.currentMap.boundaries.rectQuery(vRect);
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