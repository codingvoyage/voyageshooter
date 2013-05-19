package map;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.tiled.*;
import org.newdawn.slick.SlickException;

import voyagequest.DoubleRect;
import voyagequest.VoyageQuest;


/**
 *
 * @author Edmund
 */
public class Map {
    public TiledMapPlus tileMap;
    public QuadTree collisions;
    public QuadTree boundaries;
    public static LinkedList<Entity> entities;
    
    //The length and width of each tile.
    public static int TILE_LENGTH;
    
    //The width and length of the entire map in pixels.
    public static int MAP_WIDTH;
    public static int MAP_HEIGHT;
    
    //A rectangle of the entire map.
    public static DoubleRect MAP_RECT;
    
    
    public Map(String mapFileLocation) throws SlickException
    {
        //Load the TiledMapPlus and extract dimensions from it
        tileMap = new TiledMapPlus(mapFileLocation);
        TILE_LENGTH = tileMap.getTileHeight();
        MAP_WIDTH = tileMap.getWidth() * TILE_LENGTH;
        MAP_HEIGHT = tileMap.getHeight() * TILE_LENGTH;
        MAP_RECT = new DoubleRect(0, 0, MAP_WIDTH, MAP_HEIGHT);
        
        //Create the LinkedList of all entities
        entities = new LinkedList<>();
        
        //Initialize the QuadTrees of collisions and boundaries
        collisions = new QuadTree(20, 10, MAP_RECT);
        boundaries = new QuadTree(20, 10, MAP_RECT);
        
        //Now that QuadTree is initialized, we are free to initialize the
        //collision rects
        ArrayList<ObjectGroup> objLayers = tileMap.getObjectGroups();
        for (GroupObject o : objLayers.get(0).getObjects())
        {
            //As a temporary solution, insert a rectangle with the same
            //dimensions as the object. WARNING WARNING WARNING WARNING
            //WARNING we need to later change things so that we can
            //insert GroupObjects which conveniently can store properties, so
            //DEFINITELY change this in the future.
            System.out.println(o.x);
            System.out.println(o.y);
            DoubleRect newRect = new DoubleRect(o.x, o.y, o.width, o.height);
            if (o.props != null && o.props.containsValue("map3"))
            {
                System.out.println("Are you shitting me");
            }
            
            Entity e = new Entity(newRect);
            collisions.addEntity(e);
            entities.add(e);
        }
        
        for (Entity e : entities)
        {
            System.out.println(e.r.x + " " + e.r.y + " " + e.r.width + " " + e.r.height);
            
        }
        
        System.out.println(collisions.getSize());
        System.out.println(entities.size());
        System.out.println("layercount: " + tileMap.getLayers().size());
        
        
        
        
    }
    
}
