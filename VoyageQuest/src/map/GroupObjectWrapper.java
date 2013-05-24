package map;

import org.newdawn.slick.tiled.*;
import voyagequest.DoubleRect;
import voyagequest.Global;
import voyagequest.Util;

/**
 *
 * @author Edmund
 */
public class GroupObjectWrapper implements Rectangular {
    private GroupObject g;
    
    public GroupObjectWrapper(GroupObject g)
    {
        this.g = g;
    }
    
    public GroupObject getObject()
    {
        return g;
    }
    
    public DoubleRect getRect()
    {
        return new DoubleRect(g.x, g.y, g.width, g.height);
    }
    
    /**
     * Returns the uppermost tile this boundary occupies, so the UL corner's tile
     * @return a DoubleRect in which x and y are the tile coordinates.
     */
    public DoubleRect getTopTile()
    {
        return Util.coordinateToTile(g.x, g.y);
    }
    
    /**
     * Returns the lowest most tile this boundary occupies, so the BL corner's tile
     * @return 
     */
    public DoubleRect getLowestTile()
    {
        return Util.coordinateToTile(g.x, g.y + g.height);
    }
}
