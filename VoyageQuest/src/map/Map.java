package map;

import java.util.ArrayList;
import org.newdawn.slick.tiled.TiledMapPlus;
import org.newdawn.slick.SlickException;
import voyagequest.DoubleRect;
import static voyagequest.VoyageQuest.*;


/**
 *
 * @author Edmund
 */
public class Map {
    
    public TiledMapPlus tileMap;
    public QuadTree collisions;
    public static ArrayList<Entity> entities;
    
    public Map(String mapFileLocation) throws SlickException
    {
        tileMap = new TiledMapPlus(mapFileLocation);
        collisions = new QuadTree(20, 20,
               new DoubleRect(0, 0, MAP_WIDTH, MAP_HEIGHT));
        entities = new ArrayList<>();
    }
    
}
