package map;

import java.util.ArrayList;
import java.util.LinkedList;
import org.newdawn.slick.tiled.TiledMapPlus;
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
        
        //Initialize the QuadTrees of collisions and boundaries
        collisions = new QuadTree(20, 20, MAP_RECT);
        boundaries = new QuadTree(20, 20, MAP_RECT);
        
        //Create the LinkedList of all entities
        entities = new LinkedList<>();
        
    }
    
}
